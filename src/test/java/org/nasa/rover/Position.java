package org.nasa.rover;

public class Position {

    public Coordinates coordinates;

    public Orientation orientation;

    public Position(Coordinates coordinates, Orientation orientation) {
        this.coordinates = coordinates;
        this.orientation = orientation;
    }
}
