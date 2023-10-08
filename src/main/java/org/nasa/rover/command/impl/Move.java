package org.nasa.rover.command.impl;

import org.nasa.rover.Coordinates;
import org.nasa.rover.Grid;
import org.nasa.rover.Orientation;
import org.nasa.rover.Pole;

public class Move {

    private Orientation orientation;

    private Pole pole;

    private Coordinates coordinates;

    private Grid grid;

    public Move(Orientation orientation, Pole pole, Coordinates coordinates, Grid grid) {
        this.orientation = orientation;
        this.pole = pole;
        this.coordinates = coordinates;
        this.grid = grid;
    }

    protected boolean isRoverInVerticalMode() {
        return this.orientation == Orientation.NORTH || this.orientation == Orientation.SOUTH;
    }

    protected boolean didTheRoverCrossLongitudeLimitLine() {
        return coordinates.doLongitudeViolateHorizontalLimitsOf(grid);
    }


    protected void moveOneStepCloserTo(Pole pole) {
        if (pole == Pole.NORTH) {
            coordinates.decrementAltitude();
        } else {
            coordinates.incrementAltitude();
        }
        if (didTheRoverCrossPole()) {
            pole = pole.toTheOpposite();
            moveOneStepCloserTo(pole);
            updateLongitudeAndDirection();
        }
    }

    protected boolean didTheRoverCrossPole() {
        return coordinates.doAltitudeViolateVerticalLimitsOf(grid);
    }

    protected void updateLongitudeAndDirection() {
        coordinates.updateLongitudeOverVerticalLimitsOf(grid);
        orientation = orientation.toTheOpposite();
    }
}
