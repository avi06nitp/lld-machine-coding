package librarymanagement.notification;

import librarymanagement.models.RentedBooks;
import librarymanagement.registry.RentedBookRegistery;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class OverdueChecker {
    private final OverduePublisher publisher;
    private final Clock clock;

    public OverdueChecker(OverduePublisher publisher, Clock clock) {
        this.publisher = publisher;
        this.clock = clock;
    }

    public OverdueChecker(OverduePublisher publisher) {
        this(publisher, Clock.systemUTC());
    }

    public void scan() {
        for (RentedBooks rb : RentedBookRegistery.getAllRentedBooksMap().values()) {
            if (rb.getReturnedAt() != null) continue;
            long days = daysSinceRental(rb);
            int free  = rb.getUser().getFeeCalculationStrategy().freeRentalDays();
            if (days > free) {
                publisher.publishOverdueEvent(new OverdueEvent(rb, days - free));
            }
        }
    }

    private long daysSinceRental(RentedBooks book) {
        return Duration.between(book.getRentedAt(), Instant.now(clock)).toDays();
    }
}