package librarymanagement.notification;


public class SmsReminder implements OverdueObserver {
    @Override
    public void onOverdue(OverdueEvent overdueEvent) {
        System.out.println("SMS -> " + overdueEvent.getRentalBook().getUser().getPhoneNumber()
                + " : '" + overdueEvent.getRentalBook().getBooks().getTitle()
                + "' is " + overdueEvent.getOverdueDays() + " days overdue.");

    }
}
