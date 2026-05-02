package snakeandladder.exceptions;

public class AlreadyOccupiedWithSnake extends RuntimeException {
    public AlreadyOccupiedWithSnake(String message) {
        super(message);
    }
}
