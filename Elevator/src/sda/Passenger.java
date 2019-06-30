package sda;

public class Passenger {

    private Floor goOn;
    private Floor goFrom;
    private Direction direction;


    public Passenger(Floor goOn, Floor goFrom) {
        this.goOn = goOn;
        this.goFrom = goFrom;
        int result = goFrom.compareTo(goOn);

        if (result > 0) {
            direction = Direction.Down;
        } else if (result < 0) {
            direction = Direction.Up;
        } else {
            direction = direction.Stop;
        }
    }

    public boolean doWantOutOnThisFloor(Floor actualOnFloor) {
        return goOn.equals(actualOnFloor);
    }

    public boolean doWantGoDirection(Direction direction)   {
        return this.direction == direction;
    }
}
