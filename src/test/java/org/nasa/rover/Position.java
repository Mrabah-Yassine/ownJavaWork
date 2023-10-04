package org.nasa.rover;

public class Position {

    public Coordinates coordinates;

    public Direction direction;

    public Position(Coordinates coordinates, Direction direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }
}
