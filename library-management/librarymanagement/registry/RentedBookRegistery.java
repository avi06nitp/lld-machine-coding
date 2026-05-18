package librarymanagement.registry;

import librarymanagement.models.RentedBooks;

import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class RentedBookRegistery {
   private static final Map<Integer, RentedBooks> currentRentedBooksMap=new HashMap<>();
   private static final Map<Integer,RentedBooks> allRentedBooksMap=new HashMap<>();

   public static RentedBooks getRentedBooks(int id) {
       return currentRentedBooksMap.get(id);
   }
   public static void addRentedBooks(int id, RentedBooks rentedBooks) {
       currentRentedBooksMap.put(id, rentedBooks);
       allRentedBooksMap.put(id, rentedBooks);
   }
   public static void deleteRentedBooks(int id) {
       currentRentedBooksMap.remove(id);
   }
   public static Map<Integer,RentedBooks> getAllRentedBooksMap() {
       return Map.copyOf(currentRentedBooksMap);
   }
   public static Map<Integer,RentedBooks> getAllRentedBooksMapById() {
       return Map.copyOf(allRentedBooksMap);
   }

}
