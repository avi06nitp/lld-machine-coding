import librarymanagement.enums.MembershipType;
import librarymanagement.models.Books;
import librarymanagement.models.RentedBooks;
import librarymanagement.models.User;
import librarymanagement.notification.EmailReminder;
import librarymanagement.notification.OverdueChecker;
import librarymanagement.notification.OverdueEvent;
import librarymanagement.notification.OverduePublisher;
import librarymanagement.notification.PushReminder;
import librarymanagement.notification.SmsReminder;
import librarymanagement.registry.BookRegistry;
import librarymanagement.registry.RentedBookRegistery;
import librarymanagement.registry.UserRegistry;
import librarymanagement.service.FeeCalculationService;
import librarymanagement.service.RentalService;

kimport java.time.Clock;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        BookRegistry bookRegistry = new BookRegistry();
        FeeCalculationService feeCalculationService = new FeeCalculationService();
        RentalService rentalService = new RentalService(feeCalculationService);

        Books cleanCode = new Books("Clean Code", "Robert Martin", 9780132350884L);
        cleanCode.setTotalCopies(3);
        cleanCode.setTotalAvailableCopies(3);
        bookRegistry.addBook(cleanCode);

        Books designPatterns = new Books("Design Patterns", "Gang of Four", 9780201633610L);
        designPatterns.setTotalCopies(2);
        designPatterns.setTotalAvailableCopies(2);
        bookRegistry.addBook(designPatterns);

        System.out.println("=== Books in catalogue ===");
        System.out.println(cleanCode.getTitle() + " by " + cleanCode.getAuthor()
                + " [Copies: " + cleanCode.getTotalCopies() + "]");
        System.out.println(designPatterns.getTitle() + " by " + designPatterns.getAuthor()
                + " [Copies: " + designPatterns.getTotalCopies() + "]");

        System.out.println("\n=== Registering members ===");
        User alice = User.createUser("Alice", "alice@example.com", MembershipType.STANDARD);
        User bob = User.createUser("Bob", "bob@example.com", MembershipType.PREMIUM);
        System.out.println("Registered: " + alice.getName() + " (" + alice.getMembershipType() + "), id=" + alice.getId());
        System.out.println("Registered: " + bob.getName() + " (" + bob.getMembershipType() + "), id=" + bob.getId());

        System.out.println("\n=== Lookups ===");
        Optional<Books> searched = bookRegistry.findBookByName("Clean Code");
        searched.ifPresent(b -> System.out.println("findBookByName(\"Clean Code\") -> " + b.getTitle() + " by " + b.getAuthor()));

        Optional<User> userByEmail = UserRegistry.getUserByEmail("bob@example.com");
        userByEmail.ifPresent(u -> System.out.println("getUserByEmail(\"bob@example.com\") -> " + u.getName()));

        System.out.println("\n=== Renting ===");
        rentalService.rentBook(cleanCode, alice);
        rentalService.rentBook(designPatterns, bob);
        System.out.println("Current active rentals: " + RentedBookRegistery.getAllRentedBooksMap().size());

        System.out.println("\n=== Returning ===");
        for (RentedBooks rb : RentedBookRegistery.getAllRentedBooksMap().values()) {
            System.out.println("Returning '" + rb.getBooks().getTitle() + "' rented by " + rb.getUser().getName());
            rentalService.returnBook(rb);
        }

        System.out.println("\n=== Final state ===");
        System.out.println(cleanCode.getTitle() + " copies: " + cleanCode.getTotalCopies());
        System.out.println(designPatterns.getTitle() + " copies: " + designPatterns.getTotalCopies());

        System.out.println("\n=== Fee calculation ===");
        Books feeBook = new Books("Fee Test Book", "Test Author", 9999999999L);
        feeBook.setTotalCopies(10);
        feeBook.setTotalAvailableCopies(10);
        bookRegistry.addBook(feeBook);

        RentedBooks premiumOverdue = new RentedBooks(feeBook, bob);
        premiumOverdue.setReturnedAt(premiumOverdue.getRentedAt().plus(35, ChronoUnit.DAYS));
        System.out.println("Premium  / 35 days kept -> fee = $" + feeCalculationService.calculateFee(premiumOverdue));

        RentedBooks premiumWithin = new RentedBooks(feeBook, bob);
        premiumWithin.setReturnedAt(premiumWithin.getRentedAt().plus(20, ChronoUnit.DAYS));
        System.out.println("Premium  / 20 days kept -> fee = $" + feeCalculationService.calculateFee(premiumWithin));

        RentedBooks standardOverdue = new RentedBooks(feeBook, alice);
        standardOverdue.setReturnedAt(standardOverdue.getRentedAt().plus(35, ChronoUnit.DAYS));
        System.out.println("Standard / 35 days kept -> fee = $" + feeCalculationService.calculateFee(standardOverdue));

        RentedBooks standardWithin = new RentedBooks(feeBook, alice);
        standardWithin.setReturnedAt(standardWithin.getRentedAt().plus(5, ChronoUnit.DAYS));
        System.out.println("Standard /  5 days kept -> fee = $" + feeCalculationService.calculateFee(standardWithin));

        RentedBooks standardBoundary = new RentedBooks(feeBook, alice);
        standardBoundary.setReturnedAt(standardBoundary.getRentedAt().plus(20, ChronoUnit.DAYS));
        System.out.println("Standard / 20 days kept -> fee = $" + feeCalculationService.calculateFee(standardBoundary));

        System.out.println("\n=== Observer pattern: overdue notifications ===");
        alice.setPhoneNumber("+91-9999900001");
        alice.setDeviceId(1001);
        bob.setPhoneNumber("+91-9999900002");
        bob.setDeviceId(1002);

        OverduePublisher publisher = new OverduePublisher();
        EmailReminder emailObserver = new EmailReminder();
        SmsReminder smsObserver = new SmsReminder();
        PushReminder pushObserver = new PushReminder();
        publisher.subscribeObserver(emailObserver);
        publisher.subscribeObserver(smsObserver);
        publisher.subscribeObserver(pushObserver);

        Books reminderBook = new Books("Effective Java", "Joshua Bloch", 9780134685991L);
        reminderBook.setTotalCopies(10);
        reminderBook.setTotalAvailableCopies(10);
        bookRegistry.addBook(reminderBook);
        RentedBooks demoRental = new RentedBooks(reminderBook, alice);

        System.out.println("-- direct publish, 3 channels subscribed --");
        publisher.publishOverdueEvent(new OverdueEvent(demoRental, 7L));

        System.out.println("\n-- after unsubscribing SMS --");
        publisher.unsubscribeObserver(smsObserver);
        publisher.publishOverdueEvent(new OverdueEvent(demoRental, 8L));
        publisher.subscribeObserver(smsObserver);

        rentalService.rentBook(reminderBook, alice);
        rentalService.rentBook(reminderBook, bob);

        System.out.println("\n-- scan() with real clock (no rental actually overdue yet) --");
        new OverdueChecker(publisher).scan();
        System.out.println("(silent above = correct: rentals are 0 days old in real time)");

        System.out.println("\n-- scan() with clock fast-forwarded 20 days --");
        Clock plus20 = Clock.offset(Clock.systemUTC(), Duration.ofDays(20));
        new OverdueChecker(publisher, plus20).scan();
        System.out.println("(Alice/STANDARD fires: 20 > 15. Bob/PREMIUM silent: 20 <= 30.)");

        System.out.println("\n-- scan() with clock fast-forwarded 35 days --");
        Clock plus35 = Clock.offset(Clock.systemUTC(), Duration.ofDays(35));
        new OverdueChecker(publisher, plus35).scan();
        System.out.println("(Both fire: Alice 20 days overdue, Bob 5 days overdue.)");

        System.out.println("\n=== Edge case: rent when no copies left ===");
        Books rare = new Books("Rare Book", "Anon", 1234567890L);
        rare.setTotalCopies(0);
        rare.setTotalAvailableCopies(0);
        bookRegistry.addBook(rare);
        try {
            rentalService.rentBook(rare, alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
    }
}