package librarymanagement.notification;


public interface OverdueObserver {
    void onOverdue(OverdueEvent overdueEvent);
}
