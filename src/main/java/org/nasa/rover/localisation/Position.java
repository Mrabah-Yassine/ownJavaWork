package org.nasa.rover.localisation;

public class Position {

    public Coordinates coordinates;

    public Orientation orientation;

    public Position(Coordinates coordinates, Orientation orientation) {
        this.coordinates = coordinates;
        this.orientation = orientation;
    }


    public String value(){
        return coordinates.value().append(orientation.value()).toString();
    }
}
