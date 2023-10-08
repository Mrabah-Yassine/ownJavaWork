package org.nasa.rover;

public enum Direction {

    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT;

    public Direction reverse(){
        if(this == Direction.FORWARD) return Direction.BACKWARD;
        if(this == Direction.BACKWARD) return Direction.FORWARD;
        return Direction.FORWARD;
    }
}
