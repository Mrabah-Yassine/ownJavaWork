package org.nasa.rover.localisation;

public class Coordinates {

    private int LATITUDE;

    private int LONGITUDE;

    private final Grid grid;

    private final StringBuilder coordinatesSb = new StringBuilder();

    public Coordinates(int LONGITUDE, int LATITUDE, Grid grid) {
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.grid = grid;
        checkValidity();
    }


    public StringBuilder value() {
        return coordinatesSb.append(this.LONGITUDE).append(",").append(this.LATITUDE).append(",");
    }



    public boolean isLatitudeOutOfBounds() {
        return LATITUDE > grid.maxLimit || LATITUDE <= grid.minLimit;
    }



    public void updateVerticallyTowardsThe(Pole pole){
        if (pole == Pole.NORTH) {
            decrementLatitude();
        } else {
            incrementLatitude();
        }
    }

    public void updateAfterMovingTo(Orientation orientation){
        if (orientation.isEqualTo(Orientation.WEST)) {
            decrementLongitude();
            if (isLongitudeOutOfBounds()) {
                wrapLongitudeHorizontally();
            }
            return;
        }
        if (orientation.isEqualTo(Orientation.EAST)) {
            incrementLongitude();
        }
    }



    public void wrapLongitudeVertically() {
        if (isLongitudeInFirstHalfOfGrid()) {
            LONGITUDE += grid.maxLimit / 2;
            return;
        }
        if (isLongitudeInLastHalfOfGrid()) {
            LONGITUDE -= grid.maxLimit / 2;
        }
    }

    private void wrapLongitudeHorizontally(){
        LONGITUDE = grid.maxLimit - (LONGITUDE % grid.maxLimit);
    }

    private void decrementLongitude() {
        LONGITUDE--;
    }

    private void incrementLongitude() {
        LONGITUDE++;
        LONGITUDE %= grid.maxLimit;
    }

    private void incrementLatitude() {
        LATITUDE++;
    }

    private void decrementLatitude() {
        LATITUDE--;
    }

    private boolean isLongitudeInFirstHalfOfGrid(){
        return LONGITUDE >= 1 && LONGITUDE <= grid.maxLimit / 2;
    }

    private boolean isLongitudeInLastHalfOfGrid(){
        return LONGITUDE > grid.maxLimit / 2 && LONGITUDE <= grid.maxLimit;
    }

    private boolean isLongitudeOutOfBounds() {
        return LONGITUDE <= grid.minLimit;
    }

    private boolean areValid() {
        return this.LONGITUDE != 0 && this.LATITUDE != 0;
    }


    private void checkValidity() {
        if (!areValid()) {
            throw new IllegalArgumentException("0,0 coordinates " +
                    "are not valid");
        }
    }
}
