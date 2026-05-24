package hotelbooking.models;

import java.time.Instant;
import java.util.Objects;

public class Reservation {
    Instant checkinDate;
    Instant checkoutDate;

    public Reservation(Instant checkinDate, Instant checkoutDate) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }
    public Instant getCheckinDate() {
        return checkinDate;
    }
    public Instant getCheckoutDate() {
        return checkoutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation r)) return false;
        return Objects.equals(checkinDate, r.checkinDate)
                && Objects.equals(checkoutDate, r.checkoutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkinDate, checkoutDate);
    }

}
