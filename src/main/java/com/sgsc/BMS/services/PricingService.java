package com.sgsc.BMS.services;

import com.sgsc.BMS.Repositories.ShowSeatRepository;
import com.sgsc.BMS.Repositories.ShowSeatTypeRepository;
import com.sgsc.BMS.models.Show;
import com.sgsc.BMS.models.ShowSeat;
import com.sgsc.BMS.models.ShowSeatType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {
    int amount = 0;
    private ShowSeatTypeRepository showSeatTypeRepository;
    public int calculatePrice(List<ShowSeat> showSeats, Show show){
        //Getting all the showSeatTypes in a given show
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);

        //interested only in the showSeats which are available in Seat
        for(ShowSeat showSeat: showSeats){
            for(ShowSeatType showSeatType: showSeatTypes){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }
        return amount;
    }
}
