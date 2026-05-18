package librarymanagement.strategy;

import librarymanagement.models.RentedBooks;

import java.time.Duration;
import java.time.Instant;

public class PremiumFeeMemebershipCalculationStrategy implements FeeCalculationStrategy {

    private final int FREE_DAYS=30;
    private final double FINE_PER_DAY=5.0;

    @Override
    public Double calculateFee(RentedBooks book) {
       Instant rentedAt = book.getRentedAt();
       Instant returnedAt = book.getReturnedAt();
       long rentedForDays= Duration.between(rentedAt,returnedAt).toDays();
       if(rentedForDays>FREE_DAYS){
           return (FINE_PER_DAY*(rentedForDays-FREE_DAYS));
       }
       return 0.0;
    }
    @Override
    public int freeRentalDays(){
        return FREE_DAYS;
    }
}
