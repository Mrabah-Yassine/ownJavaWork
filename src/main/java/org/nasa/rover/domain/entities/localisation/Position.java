package org.nasa.rover.domain.entities.localisation;

public class Position {

    private final ICoordinate iLongitude;

    private final ICoordinate iLatitude;

    private Orientation orientation;

    private Pole pole = Pole.NORTH;

    public Position(ICoordinate iLongitude, ICoordinate iLatitude, Orientation orientation) {
        this.iLongitude = iLongitude;
        this.iLatitude = iLatitude;
        this.orientation = orientation;
        checkValidity();
    }


    public String value() {
        return String.format("%s,%s,%s", iLongitude.toString(), iLatitude.toString(), orientation.toString());
    }


    public void forward() {

        if (isVertical()) {
            pole = pole.selectPoleFromOrientation(orientation);
            updateVerticallyTowards(pole);
            return;
        }
        if (isOrientedTo(Orientation.WEST)) {
            updateHorizontallyTowards(Orientation.WEST);
            return;
        }
        if (isOrientedTo(Orientation.EAST)) {
            updateHorizontallyTowards(Orientation.EAST);
        }
    }

    public void backward() {

        if (isVertical()) {
            pole = pole.selectPoleFromOrientation(orientation).toTheOpposite();
            updateVerticallyTowards(pole);
            return;
        }
        if (isOrientedTo(Orientation.WEST)) {
            updateHorizontallyTowards(Orientation.WEST.toTheOpposite());
            return;
        }
        if (isOrientedTo(Orientation.EAST)) {
            updateHorizontallyTowards(Orientation.EAST.toTheOpposite());
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
            iLongitude.wrap(Direction.VERTICAL);
            orientation = orientation.toTheOpposite();
        }
    }


    private void updateHorizontallyTowards(Orientation orientation) {
        if (orientation.isEqualTo(Orientation.WEST)) {
            iLongitude.decrement();
            checkLongitudeBoundsAndWrap();
            return;
        }
        if (orientation.isEqualTo(Orientation.EAST)) {
            iLongitude.increment();
        }
    }


    private void checkLongitudeBoundsAndWrap(){
        if (iLongitude.isOutOfBounds()) {
            iLongitude.wrap(Direction.HORIZONTAL);
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

    private boolean isOrientedTo(Orientation orientationToCheck) {
        return orientation.isEqualTo(orientationToCheck);
    }
}
