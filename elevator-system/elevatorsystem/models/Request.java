package elevatorsystem.models;

import elevatorsystem.enums.ExternalRequestDirection;
import elevatorsystem.enums.RequestType;

public class Request {

    private final RequestType requestType;
    private final ExternalRequestDirection elevatorDirection;
    private final int currentFloor;
    private final Elevator elevator;
    private final int targetFloor;

    private Request(RequestBuilder builder) {
        this.requestType = builder.requestType;
        this.elevator = builder.elevator;
        this.targetFloor = builder.targetFloor;
        this.elevatorDirection = builder.elevatorDirection;
        this.currentFloor = builder.currentFloor;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public ExternalRequestDirection getElevatorDirection() {
        return elevatorDirection;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public static class RequestBuilder {
        private RequestType requestType;
        private ExternalRequestDirection elevatorDirection;
        private int currentFloor;
        private Elevator elevator;
        private int targetFloor;

        public RequestBuilder requestType(RequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        public RequestBuilder elevatorDirection(ExternalRequestDirection elevatorDirection) {
            this.elevatorDirection = elevatorDirection;
            return this;
        }

        public RequestBuilder currentFloor(int currentFloor) {
            this.currentFloor = currentFloor;
            return this;
        }

        public RequestBuilder elevator(Elevator elevator) {
            this.elevator = elevator;
            return this;
        }

        public RequestBuilder targetFloor(int targetFloor) {
            this.targetFloor = targetFloor;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}