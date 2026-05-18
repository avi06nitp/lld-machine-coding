package librarymanagement.notification;

import java.util.ArrayList;
import java.util.List;

public class OverduePublisher {
    private final List<OverdueObserver> observers=new ArrayList<>();

    public void subscribeObserver(OverdueObserver observer) {
        observers.add(observer);
    }
    public void unsubscribeObserver(OverdueObserver observer) {
        observers.remove(observer);
    }

    public void publishOverdueEvent(OverdueEvent event) {
        for (OverdueObserver observer : observers) {
            try {
                observer.onOverdue(event);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
