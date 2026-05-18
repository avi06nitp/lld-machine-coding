package librarymanagement.notification;

import librarymanagement.models.RentedBooks;

public interface OverdueObserver {
    void onOverdue(OverdueEvent overdueEvent);
}
