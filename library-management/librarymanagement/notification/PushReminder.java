package librarymanagement.notification;


public class PushReminder implements OverdueObserver {
    @Override
    public void onOverdue(OverdueEvent overdueEvent) {

        System.out.println("Push -> " + overdueEvent.getRentalBook().getUser().getDeviceId()
                + " : '" + overdueEvent.getRentalBook().getBooks().getTitle()
                + "' is " + overdueEvent.getOverdueDays() + " days overdue.");
    }
}
