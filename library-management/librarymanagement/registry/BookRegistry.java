package librarymanagement.registry;

import librarymanagement.models.Books;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookRegistry {
    Map<Integer, Books> books=new HashMap();

    public Optional<Books> findBookById(int id) {
        return Optional.ofNullable(books.get(id));
    }

    public Optional<Books> findBookByName(String name) {
       for (Books book : books.values()) {
           if (book.getTitle().equals(name)) {
               return Optional.of(book);
           }
       }
       return Optional.empty();
    }

    public void addBook(Books book) {
        if(!books.containsKey(book.getId())) {
            books.put(book.getId(), book);
        }else{
            throw new IllegalArgumentException("Book already exists");
        }
    }
    public void updateBook(Books book) {
        if(!books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Book does not exist");
        }
        books.put(book.getId(), book);
    }
}
