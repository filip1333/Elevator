package sda;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Building {

    private Elevator elevator;
    private List<Floor> floors;

    AtomicBoolean elevatorActive = new AtomicBoolean(false);
    private Thread goElevator;

    private static final int HOW_OFTEN_STEP = 2_000;


    public Building(Elevator elevator, List<Floor> floors) {
        this.elevator = elevator;
        this.floors = floors;
    }

    public void startElevator() {

        System.out.println("Elevator is running");

        elevatorActive = new AtomicBoolean(true);

        Runnable runnable = () -> goElevator();
        goElevator = new Thread(runnable);
        goElevator.start();
    }

    private void goElevator() {
        while (elevatorActive.get()) {
            try {
                Thread.sleep(HOW_OFTEN_STEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            nextStep();

            System.out.println("Next step");
        }
    }

    private void nextStep() {
        if (!elevator.isItEmpty() && elevator.someoneWantOut()) {
            if (elevator.putPassenger()) {
                System.out.println("Passengers left elevetor on floor: " + elevator.giveActualFloor());
                return;
            }
        }

        Floor actualFloor = elevator.giveActualFloor();
        if (actualFloor.someoneWantGoInDirection(elevator.giveDirection())) {
            List<Passenger> toelevator = actualFloor.remoweWaiting(elevator.giveDirection());
            elevator.putPassenger(toelevator);
            System.out.println("Passengers entered to the elevator on floor: " + elevator.giveActualFloor());
            return;
        }

        if (actualFloor.doesAnyoneWantGo() && elevator.giveDirection() == Direction.Stop) {

            List<Passenger> toElevator = actualFloor.remoweWaiting(Direction.Down);
            if (!toElevator.isEmpty()){
                elevator.putPassenger(toElevator);
                System.out.println("Passengers entered to the elevator on floor: " + elevator.giveActualFloor());
                elevator.setDirection(Direction.Down);
            } else {
                toElevator = actualFloor.remoweWaiting(Direction.Up);
                if (toElevator.isEmpty()){
                    elevator.putPassenger(toElevator);
                    System.out.println("Passengers entered to the elevator on floor: " + elevator.giveActualFloor());
                    elevator.setDirection(Direction.Up);
                }
            }
        }

        if (elevator.giveDirection() == Direction.Stop && elevator.isItEmpty())  {
            Floor floorWaiting = getFloorWithWaiting();
        if (floorWaiting != null) {
            Floor floorElevator = elevator.giveActualFloor();

            int result = floorWaiting.compareTo(floorElevator);
            if (result > 0) {
                elevator.setDirection(Direction.Up);
            }   else if (result < 0) {
                elevator.setDirection(Direction.Down);
            }
        }
    }

        if(elevator.giveDirection() != Direction.Stop) {
            Floor floorElevator = elevator.giveActualFloor();
            Floor nextFloor = giveNextFloor(floorElevator, elevator.giveDirection());
            if (nextFloor == null) {
                System.out.println("Stoped elevator");
                elevator.setDirection(Direction.Stop);
            } else {
                System.out.println("We go on the " + nextFloor + " floor");
                elevator.setActualFloor(nextFloor);
            }
        }   else    {
            System.out.println("Elevator is stop");
        }
    }

    private Floor giveNextFloor(Floor floorElevator, Direction direction) {
        Floor nextFloor = null;
        int size = floors.size();
        Floor floor;
        for(int index = 0; index < size; index++)   {
            floor = floors.get(index);

            if(floor == floorElevator){
            index += direction.giveValue();
                if(index >= 0 && index < size)  {
                    nextFloor = floors.get(index);
                }
                break;
            }
        }
        return nextFloor;
    }

    private Floor getFloorWithWaiting() {
        for(int i = floors.size()-1; i >= 0; i--)   {
            Floor floor = floors.get(i);
            if(floor.doesAnyoneWantGo())   {
                return floor;
            }
        }
        return null;
    }

    public void stopElevator() {
        System.out.println("Elevator stop");

        elevatorActive.set(false);
        goElevator.interrupt();
    }
}
