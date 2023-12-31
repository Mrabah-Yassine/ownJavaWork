package org.nasa.rover.domain.application.localisation;

import org.nasa.rover.domain.entities.localisation.Orientation;

public class OrientationFactory {

    public static Orientation getOrientationFrom(String orientation){
        switch (orientation) {
            case "N" -> {
                return Orientation.NORTH;
            }
            case "S" -> {
                return Orientation.SOUTH;
            }
            case "W" -> {
                return Orientation.WEST;
            }
            case "E" -> {
                return Orientation.EAST;
            }
        }
        throw new IllegalArgumentException("This direction is not identified");
    }
}
