package sda;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class Floor implements Comparable<Floor> {

    private List<Passenger> waiting;
    private int floorNumber;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        waiting = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Floor)) return false;
        Floor floor = (Floor) o;
        return floorNumber == floor.floorNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorNumber);
    }


    public void addWaiting(Passenger passenger) {
        waiting.add(passenger);
    }

    public boolean someoneWantGoInDirection(Direction direction) {
        return waiting.stream().anyMatch(passenger -> passenger.doWantGoDirection(direction));
    }

    @Override
    public int compareTo(Floor o) {
        return this.floorNumber - o.floorNumber;
    }

    public List<Passenger> remoweWaiting(Direction direction) {

        List<Passenger> remove = new ArrayList<>();

        for (int i = waiting.size() - 1; i >= 0; i--) {
            Passenger passenger = waiting.get(i);
            if (passenger.doWantGoDirection(direction)) {
                waiting.remove(i);
                remove.add(passenger);
            }
        }
        return remove;
    }

    public boolean doesAnyoneWantGo() {
        return !waiting.isEmpty();
    }

    @Override
    public String toString() {
        return String.valueOf(floorNumber);
    }
}
