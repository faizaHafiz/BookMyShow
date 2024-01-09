package com.sgsc.BMS.services;

import com.sgsc.BMS.Repositories.BookingRepository;
import com.sgsc.BMS.Repositories.ShowRepository;
import com.sgsc.BMS.Repositories.ShowSeatRepository;
import com.sgsc.BMS.Repositories.UserRepository;
import com.sgsc.BMS.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private BookingRepository bookingRepository;
    private ShowSeatRepository showSeatRepository;
    private PricingService pricingService;

    @Autowired
    public BookingService(UserRepository userRepository, ShowRepository showRepository, BookingRepository bookingRepository, ShowSeatRepository showSeatRepository, PricingService pricingService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
        this.pricingService = pricingService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatsIds, Long showId){
        /*
        this is just the confirmation flow, just before payment flow.
        After successful completion status will be blocked
         */
          /* for now take lock here
          1. Get the user from the user ID
          2. Get the show from show ID
          ...........Take a lock..........
          3. Get the show seats from seat ids
          4. Check if seats are available
          5. If yes, make the status as booked or booking in progress
          ............Release lock.........
          6. Save updated show seats in DB and end the lock
          /* for now end lock here
         */
        //step 1
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException(); //create custom exception
        }
        User bookedBy = userOptional.get();

        //step 2
        Optional<Show> showOptional = showRepository.findById(showId);
        if (showOptional.isEmpty()){
            throw new RuntimeException(); //create custom exception
        }
        Show bookedShow = showOptional.get();

        //step 3
        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatsIds);

        //step 4
        for(ShowSeat showSeat: showSeats){
           // ! se pehle ki condition if true then I can ook ticket--
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) || (
                    showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            Duration.between(showSeat.getBlockedAt().toInstant(),
                            new Date().toInstant()).toMinutes() > 15
                    ))){
                //someone has taken a lock you canoot book the same seat
                throw new RuntimeException(); // throwing when status has not been changed even when time exceeded 15 mins
            }
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>();//we need to save showseat obj in Booking
        //seats are available then change status and save
        //step 5
        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
        //showSeat should be stored in Booking obj
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeats);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setShow(bookedShow);
        booking.setAmount(pricingService.calculatePrice(savedShowSeats,bookedShow));

        Booking savedBooking = bookingRepository.save(booking);

        return savedBooking;

    }
}
