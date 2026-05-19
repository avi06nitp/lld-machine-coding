package librarymanagement.models;

import java.time.Instant;

public class RentedBooks {
    private final int id;
    private static int idCounter=1;
    private final Books books;
    private final Instant rentedAt;
    private  Instant returnedAt;
    public final User user;

    public RentedBooks( Books book, User user) {
        this.user = user;
        this.id = idCounter++;
        this.books = book;
        this.rentedAt = Instant.now();
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public Books getBooks() {
        return books;
    }
    public Instant getRentedAt() {
        return rentedAt;
    }
    public Instant getReturnedAt() {
        return returnedAt;
    }
    public User getUser() {
        return user;
    }
    public void setReturnedAt(Instant returnedAt) {
        this.returnedAt = returnedAt;
    }
}
