package org.nasa.rover;

public class Coordinates {

    public int ALTITUDE;

    public int LONGITUDE;

    private final StringBuilder coordinatesSb = new StringBuilder();

    public Coordinates(int LONGITUDE, int ALTITUDE) {
        this.LONGITUDE = LONGITUDE;
        this.ALTITUDE = ALTITUDE;
        checkValidity();
    }


    public StringBuilder value() {
        return coordinatesSb.append(this.LONGITUDE).append(",").append(this.ALTITUDE).append(",");
    }


    public void checkValidity() {
        if (!areValid()) {
            throw new IllegalArgumentException("0,0 coordinates " +
                    "are not valid");
        }
    }


    public boolean doAltitudeViolateVerticalLimitsOf(Grid grid) {
        return ALTITUDE > grid.maxLimit || ALTITUDE <= grid.minLimit;
    }


    public boolean doLongitudeViolateHorizontalLimitsOf(Grid grid) {
        return LONGITUDE <= grid.minLimit;
    }


    public void updateLongitudeOverVerticalLimitsOf(Grid grid) {
        if (isLongitudeInFirstHalfOf(grid)) {
            LONGITUDE += grid.maxLimit / 2;
            return;
        }
        if (isLongitudeInLastHalfOf(grid)) {
            LONGITUDE -= grid.maxLimit / 2;
        }
    }

    public void updateLongitudeOverHorizontalLimitsOf(Grid grid){
        LONGITUDE = grid.maxLimit - (LONGITUDE % grid.maxLimit);
    }

    private boolean isLongitudeInFirstHalfOf(Grid grid){
        return LONGITUDE >= 1 && LONGITUDE <= grid.maxLimit / 2;
    }

    private boolean isLongitudeInLastHalfOf(Grid grid){
        return LONGITUDE > grid.maxLimit / 2 && LONGITUDE <= grid.maxLimit;
    }


    public void incrementAltitude() {
        ALTITUDE++;
    }

    public void incrementLongitudeInside(Grid grid) {
        LONGITUDE++;
        LONGITUDE %= grid.maxLimit;
    }

    public void decrementAltitude() {
        ALTITUDE--;
    }

    public void decrementLongitude() {
        LONGITUDE--;
    }

    private boolean areValid() {
        return this.LONGITUDE != 0 && this.ALTITUDE != 0;
    }

}
