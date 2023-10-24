package org.nasa.rover.localisation;

import org.nasa.rover.localisation.itf.ICoordinate;

public class Position {

    private final ICoordinate iLongitude;

    private final ICoordinate iLatitude;

    public Orientation orientation;

    private Pole pole = Pole.NORTH;

    public Position(ICoordinate iLongitude, ICoordinate iLatitude, Orientation orientation) {
        this.iLongitude = iLongitude;
        this.iLatitude = iLatitude;
        this.orientation = orientation;
        checkValidity();
    }


    public String value() {
        return String.format("%s,%s,%s", iLongitude.toString(), iLatitude.toString(), orientation.value());
    }


    public void forward() {
        pole = pole.selectPoleFromOrientation(orientation);

        if (isVertical()) {
            updateVerticallyTowards(pole);
            return;
        }
        if (isRoverOrientedToThe(Orientation.WEST)) {
            horizontalUpdateTowards(Orientation.WEST);
            return;
        }
        if (isRoverOrientedToThe(Orientation.EAST)) {
            horizontalUpdateTowards(Orientation.EAST);
        }
    }

    public void backward() {
        pole = pole.selectPoleFromOrientation(orientation);

        if (isVertical()) {
            pole = pole.toTheOpposite();
            updateVerticallyTowards(pole);
            return;
        }
        if (isRoverOrientedToThe(Orientation.WEST)) {
            horizontalUpdateTowards(Orientation.EAST);
            return;
        }
        if (isRoverOrientedToThe(Orientation.EAST)) {
            horizontalUpdateTowards(Orientation.WEST);
        }
    }

    public void toTheLeft(){
        orientation = orientation.toTheLeft();
    }

    public void toTheRight(){
        orientation = orientation.toTheRight();
    }


    private void updateVerticallyTowards(Pole pole) {
        if (pole == Pole.NORTH) {
            iLatitude.decrement();
        } else {
            iLatitude.increment();
        }
        if (iLatitude.isOutOfBounds()) {
            pole = pole.toTheOpposite();
            updateVerticallyTowards(pole);
            wrapLongitudeVertically();
            orientation = orientation.toTheOpposite();
        }
    }


    private void wrapLongitudeVertically() {
        iLongitude.wrap(Direction.VERTICAL);
    }


    private void horizontalUpdateTowards(Orientation orientation) {
        if (orientation.isEqualTo(Orientation.WEST)) {
            iLongitude.decrement();
            if (iLongitude.isOutOfBounds()) {
                iLongitude.wrap(Direction.HORIZONTAL);
            }
            return;
        }
        if (orientation.isEqualTo(Orientation.EAST)) {
            iLongitude.increment();
        }
    }


    private boolean areCoordinatesValid() {
        return this.iLongitude.isValid() && this.iLatitude.isValid();
    }


    private void checkValidity() {
        if (!areCoordinatesValid()) {
            throw new IllegalArgumentException("0,0 coordinates " +
                    "are not valid");
        }
    }


    private boolean isVertical() {
        return this.orientation == Orientation.NORTH || this.orientation == Orientation.SOUTH;
    }

    private boolean isHorizontal() {
        return this.orientation == Orientation.WEST || this.orientation == Orientation.EAST;
    }

    private boolean isRoverOrientedToThe(Orientation orientationToCheck) {
        return orientation.isEqualTo(orientationToCheck);
    }
}
