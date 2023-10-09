package org.nasa.rover.rover;

import org.nasa.rover.localisation.Grid;
import org.nasa.rover.localisation.Orientation;
import org.nasa.rover.localisation.Pole;
import org.nasa.rover.localisation.Position;

public class MarsRover implements IPlanetRover {

    public int howManyTimesDidItCrossPole = 0;

    private final Grid grid;

    private final Position position;

    private Pole pole = Pole.NORTH;
    ;


    public MarsRover(Position position, Grid grid) {
        this.position = position;
        this.grid = grid;
    }


    @Override
    public Position getPosition() {
        return position;
    }


    @Override
    public void moveForward() {
        pole = pole.selectPoleFromOrientation(this.position.orientation);

        if (isRoverInVerticalMode()) {
            moveOneStepCloserTo(pole);
            return;
        }
        if (isRoverOrientedToThe(Orientation.WEST)) {
            moveTowardsTheWest();
            return;
        }
        if (isRoverOrientedToThe(Orientation.EAST)) {
            moveTowardsTheEast();
        }
    }

    @Override
    public void moveBackward() {
        pole = pole.selectPoleFromOrientation(this.position.orientation);

        if (isRoverInVerticalMode()) {
            pole = pole.toTheOpposite();
            moveOneStepCloserTo(pole);
            return;
        }
        if (isRoverOrientedToThe(Orientation.WEST)) {
            moveTowardsTheEast();
            return;
        }
        if (isRoverOrientedToThe(Orientation.EAST)) {
            moveTowardsTheWest();
        }
    }

    @Override
    public void rotateToLeft() {
        this.position.orientation = this.position.orientation.toTheLeft();
    }

    @Override
    public void rotateToRight() {
        this.position.orientation = this.position.orientation.toTheRight();
    }


    private void moveTowardsTheWest(){
        position.coordinates.decrementLongitude();
        if (didTheRoverCrossLongitudeLimitLine()) {
            position.coordinates.updateLongitudeOverHorizontalLimitsOf(grid);
        }
    }


    private void moveTowardsTheEast(){
        this.position.coordinates.incrementLongitudeInside(grid);
    }


    private void moveOneStepCloserTo(Pole pole) {
        if (pole == Pole.NORTH) {
            position.coordinates.decrementAltitude();
        } else {
            position.coordinates.incrementAltitude();
        }
        if (didTheRoverCrossPole()) {
            pole = pole.toTheOpposite();
            moveOneStepCloserTo(pole);
            howManyTimesDidItCrossPole++;
            updateLongitudeAndDirectionAfterCrossingBoundaries();
        }
    }



    private void updateLongitudeAndDirectionAfterCrossingBoundaries() {
        position.coordinates.updateLongitudeOverVerticalLimitsOf(grid);
        position.orientation = position.orientation.toTheOpposite();
    }


    private boolean isRoverOrientedToThe(Orientation orientationToCheck){
        return this.position.orientation.isEqualTo(orientationToCheck);
    }

    private boolean isRoverInVerticalMode() {
        return this.position.orientation.isEqualTo(Orientation.NORTH) || this.position.orientation.isEqualTo(Orientation.SOUTH);
    }


    private boolean didTheRoverCrossPole() {
        return position.coordinates.doAltitudeViolateVerticalLimitsOf(grid);
    }

    private boolean didTheRoverCrossLongitudeLimitLine() {
        return position.coordinates.doLongitudeViolateHorizontalLimitsOf(grid);
    }

}
