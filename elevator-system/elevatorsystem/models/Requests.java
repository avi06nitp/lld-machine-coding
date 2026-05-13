package elevatorsystem.models;

import elevatorsystem.enums.ExternalRequestDirection;
import elevatorsystem.enums.RequestType;

public class Requests {

    private RequestType requestType;
    private  ExternalRequestDirection elevatorDirection;
    private  int currentFloor;
    private  Elevator elevatorName;
    private  int targetFloor;

    public  Requests(RequestBuilder request) {
        this.requestType = request.requestType;
        this.elevatorName = request.elevatorName;
        this.targetFloor = request.targetFloor;
        this.elevatorDirection = request.elevatorDirection;
        this.currentFloor = request.currentFloor;
    }

    public static class RequestBuilder {
        private RequestType requestType;
        private ExternalRequestDirection elevatorDirection;
        private int currentFloor;
        private Elevator elevatorName;
        private int targetFloor;


        public RequestBuilder elevatorDirection(ExternalRequestDirection elevatorDirection) {
            this.elevatorDirection = elevatorDirection;
            return this;
        }
        public RequestBuilder targetFloor(int targetFloor) {
            this.targetFloor = targetFloor;
            return this;
        }
        public RequestBuilder elevatorName(Elevator elevatorName) {
            this.elevatorName = elevatorName;
            return this;
        }
        public RequestBuilder requestType(RequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        public RequestBuilder currentFloor(int currentFloor) {
            this.currentFloor = currentFloor;
            return this;
        }
        public Requests build() {
            return new Requests(this);
        }

    }

    //Getters
    public int getCurrentFloor(){
        return currentFloor;
    }
    public Elevator getElevator(){
        return elevatorName;
    }
    public RequestType getRequestType(){
        return requestType;
    }
    public int getTargetFloor(){
        return targetFloor;
    }

}
