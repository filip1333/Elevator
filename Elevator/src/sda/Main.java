package sda;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Floor floor0 = new Floor(0);
        Floor floor1 = new Floor(1);
        Floor floor2 = new Floor(2);
        Floor floor3 = new Floor(3);
        Floor floor4 = new Floor(4);

        Elevator elevator = new Elevator(floor0);

        Building building = new Building(elevator, Arrays.asList(floor0, floor1, floor2, floor3, floor4));

        floor1.addWaiting(new Passenger(floor1, floor3));
        floor4.addWaiting(new Passenger(floor4, floor2));

        building.startElevator();

        try {
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        building.stopElevator();

    }
}