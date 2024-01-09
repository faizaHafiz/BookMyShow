package com.sgsc.BMS.controllers;

import com.sgsc.BMS.Dtos.BookMovieRequestDto;
import com.sgsc.BMS.Dtos.BookMovieResponseDto;
import com.sgsc.BMS.Dtos.ResponseStatus;
import com.sgsc.BMS.models.Booking;
import com.sgsc.BMS.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//@Component //tells spring to create object
@Controller
public class BookingController {

    private BookingService bookingService;

    @Autowired // inject the bookingservice which you have created
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto){
        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();

        Booking booking;
        try{
            booking = bookingService.bookMovie(
              bookMovieRequestDto.getUserId(),
              bookMovieRequestDto.getShowSeatIds(),
              bookMovieRequestDto.getShowId()
            );
            bookMovieResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            bookMovieResponseDto.setBookingId(booking.getId());
            bookMovieResponseDto.setAmount(booking.getAmount());
        }catch(Exception ex){
            bookMovieResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookMovieResponseDto;

    }
}
