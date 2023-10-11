package org.nasa.rover.rover;

import org.nasa.rover.localisation.Orientation;
import org.nasa.rover.localisation.Pole;
import org.nasa.rover.localisation.Position;

public class MarsRover implements IPlanetRover {

    public int howManyTimesDidItCrossPole = 0;

    private final Position position;

    private Pole pole = Pole.NORTH;
    ;


    public MarsRover(Position position) {
        this.position = position;
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
            moveOneStepTowardsThe(Orientation.WEST);
            return;
        }
        if (isRoverOrientedToThe(Orientation.EAST)) {
            moveOneStepTowardsThe(Orientation.EAST);
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
            moveOneStepTowardsThe(Orientation.EAST);
            return;
        }
        if (isRoverOrientedToThe(Orientation.EAST)) {
            moveOneStepTowardsThe(Orientation.WEST);
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


    private void moveOneStepTowardsThe(Orientation orientation) {
        position.coordinates.updateAfterMovingTo(orientation);
    }


    private void moveOneStepCloserTo(Pole pole) {
        position.coordinates.updateVerticallyTowardsThe(pole);
        if (didTheRoverCrossPole()) {
            pole = pole.toTheOpposite();
            moveOneStepCloserTo(pole);
            howManyTimesDidItCrossPole++;
            position.coordinates.wrapLongitudeVertically();
            position.orientation = position.orientation.toTheOpposite();
        }
    }



    private boolean isRoverOrientedToThe(Orientation orientationToCheck) {
        return this.position.orientation.isEqualTo(orientationToCheck);
    }

    private boolean isRoverInVerticalMode() {
        return this.position.orientation.isEqualTo(Orientation.NORTH) || this.position.orientation.isEqualTo(Orientation.SOUTH);
    }


    private boolean didTheRoverCrossPole() {
        return position.coordinates.isLatitudeOutOfBounds();
    }

}
