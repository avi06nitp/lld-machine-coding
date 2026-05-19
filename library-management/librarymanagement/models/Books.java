package librarymanagement.models;

public class Books {

    private static int  idCounter=1;
    private final int id;
    private final String title;
    private  final String author;
    private final Long isbnNumber;
    private int totalCopies;
    private int totalAvailableCopies;


    public Books(String title, String author, Long isbnNumber) {
        this.id = idCounter;
        idCounter++;
        this.title = title;
        this.author = author;
        this.isbnNumber = isbnNumber;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public Long getIsbnNumber() {
        return isbnNumber;
    }
    public int getTotalCopies() {
        return totalCopies;
    }
    public int getTotalAvailableCopies() {
        return totalAvailableCopies;
    }
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }
    public void setTotalAvailableCopies(int totalAvailableCopies) {
        this.totalAvailableCopies = totalAvailableCopies;
    }

}
