package librarymanagement.strategy;

import librarymanagement.models.RentedBooks;

public interface FeeCalculationStrategy {

    Double calculateFee(RentedBooks book);
    int freeRentalDays();
}
