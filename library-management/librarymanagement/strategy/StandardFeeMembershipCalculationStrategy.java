package librarymanagement.strategy;

import librarymanagement.models.RentedBooks;
import librarymanagement.models.User;

import java.awt.print.Book;
import java.time.Duration;
import java.time.Instant;

public class StandardFeeMembershipCalculationStrategy implements FeeCalculationStrategy {

    private final int FREE_DAYS=15;
    private final Double FINE_PER_DAY=10.0;

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
