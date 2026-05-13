package elevatorsystem.models;
import java.util.List;

public class Building {

    private static Building INSTANCE=null;
    private  List<Elevator>elevators;
    private  int noOfFloors;
    private Building() {}


    //Getters and Setters
    public static Building getInstance() {
        synchronized (Building.class) {
            if (INSTANCE == null) {
                INSTANCE = new Building();
            }
        }
        return INSTANCE;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public int getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(int noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

}
