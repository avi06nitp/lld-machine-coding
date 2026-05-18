package librarymanagement.service;

import librarymanagement.models.Books;
import librarymanagement.models.RentedBooks;
import librarymanagement.models.User;
import librarymanagement.registry.RentedBookRegistery;

import java.time.Instant;


public class RentalService {

    private final FeeCalculationService feeCalculationService;

    public RentalService(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }

    public void rentBook(Books book, User user) {
        synchronized (book) {
            RentedBooks rentalBook = new RentedBooks(book, user);
            if (book.getTotalCopies() > 0) {
                book.setTotalCopies(book.getTotalAvailableCopies() - 1);
                RentedBookRegistery.addRentedBooks(rentalBook.getId(), rentalBook);
                System.out.println("Book rented: "+book.getTotalCopies());
            } else {
                throw new IllegalArgumentException("Rental Book does not have enough copies");
            }
        }

    }

    public Double returnBook(RentedBooks rentalBook) {
        synchronized (rentalBook) {
            rentalBook.setReturnedAt(Instant.now());
            Double fee= feeCalculationService.calculateFee(rentalBook);
            Books books=rentalBook.getBooks();
            books.setTotalCopies(books.getTotalAvailableCopies() + 1);
           return fee;
        }
    }
}
