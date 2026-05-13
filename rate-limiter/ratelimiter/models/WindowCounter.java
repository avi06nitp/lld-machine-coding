package ratelimiter.models;

public class WindowCounter {
    private  long window;
    private  long count;

    public WindowCounter() {
        this.window = 0;
        this.count = 0;
    }

    public long getWindow() {
        return window;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    public void setWindow(long window) {
        this.window = window;
    }
}
