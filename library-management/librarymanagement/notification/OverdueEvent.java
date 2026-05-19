package librarymanagement.notification;

import librarymanagement.models.RentedBooks;

public class OverdueEvent {
    private final RentedBooks rentalBook;
    private final long overdueDays;

    public OverdueEvent(RentedBooks rentalBook, long overdueDays) {
        this.rentalBook = rentalBook;
        this.overdueDays = overdueDays;
    }

    public RentedBooks getRentalBook() {
        return rentalBook;
    }
    public long getOverdueDays() {
        return overdueDays;
    }

}
