package librarymanagement.service;

import librarymanagement.models.Books;
import librarymanagement.models.RentedBooks;
import librarymanagement.models.User;
import librarymanagement.strategy.FeeCalculationStrategy;

public class FeeCalculationService {

    public Double calculateFee(RentedBooks book) {
        User user = book.getUser();
        FeeCalculationStrategy feeCalculationStrategy =user.getFeeCalculationStrategy();
        return  feeCalculationStrategy.calculateFee(book);
    }
}
