package sda;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private List<Passenger> InElevator = new ArrayList<>();
    private Direction direction;
    private Floor actualOnFloor;

    Elevator(Floor startFloor) {
        actualOnFloor = startFloor;
        direction = Direction.Stop;
    }

    public boolean isItEmpty() {
        return InElevator.isEmpty();
    }

    public boolean someoneWantOut() {
        return InElevator.stream().anyMatch(passenger -> passenger.doWantOutOnThisFloor(actualOnFloor));

    }

    public boolean putPassenger() {
        boolean orPutPassenger = false;
        for(int indexPassenger = InElevator.size() - 1; indexPassenger >= 0; indexPassenger--) {
            Passenger passenger = InElevator.get(indexPassenger);
            if(passenger.doWantOutOnThisFloor(actualOnFloor)) {
                InElevator.remove(indexPassenger);
                orPutPassenger = true;
            }
        }
        return orPutPassenger;
    }

    public Floor giveActualFloor()  {
        return actualOnFloor;
    }

    public void putPassenger(List<Passenger> toElevator)  {
        InElevator.addAll(toElevator);
    }

    public void setActualFloor(Floor nextFloor) {
        actualOnFloor = nextFloor;
    }

    public Direction giveDirection()    {
        return direction;
    }

    public void setDirection(Direction direction)    {
        this.direction = direction;
    }
}
