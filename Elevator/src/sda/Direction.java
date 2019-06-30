package sda;

public enum Direction {

    Up(1),
    Down(-1),
    Stop (0);

    int value;
     
    Direction(int value)    {
        this.value = value;
    }

    public int giveValue()  {
        return  value;
    }
}
