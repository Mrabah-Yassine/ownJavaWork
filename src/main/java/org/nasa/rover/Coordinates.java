package org.nasa.rover;

public class Coordinates {

    public int ALTITUDE;

    public int LONGITUDE;

    public Coordinates(int LONGITUDE, int ALTITUDE) {
        this.LONGITUDE = LONGITUDE;
        this.ALTITUDE = ALTITUDE;
        checkValidity();
    }


    public void checkValidity(){
        if (!areValid()) {
            throw new IllegalArgumentException("0,0 coordinates " +
                    "are not valid");
        }
    }


    private boolean areValid() {
        return this.LONGITUDE != 0 && this.ALTITUDE != 0;
    }

}
