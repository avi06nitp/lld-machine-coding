package librarymanagement.notification;

public class EmailReminder implements OverdueObserver {
    @Override
    public void onOverdue(OverdueEvent overdueEvent) {
        System.out.println("EMAIL -> " + overdueEvent.getRentalBook().getUser().getEmail()
                + " : '" + overdueEvent.getRentalBook().getBooks().getTitle()
                + "' is " + overdueEvent.getOverdueDays() + " days overdue.");

    }
}
