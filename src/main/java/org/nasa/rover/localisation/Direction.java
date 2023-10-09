package org.nasa.rover.localisation;

public enum Direction {

    FORWARD,
    BACKWARD;

    public Direction reverse(){
        if(this == Direction.FORWARD) return Direction.BACKWARD;
        if(this == Direction.BACKWARD) return Direction.FORWARD;
        return Direction.FORWARD;
    }
}
