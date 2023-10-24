package org.nasa.rover.localisation.impl;

import org.nasa.rover.localisation.Direction;
import org.nasa.rover.localisation.itf.ICoordinate;
import org.nasa.rover.localisation.itf.IGrid;

public class ManageLatitude implements ICoordinate {

    private int LATITUDE;
    private final IGrid iGrid;

    public ManageLatitude(int LATITUDE, IGrid iGrid) {
        this.LATITUDE = LATITUDE;
        this.iGrid = iGrid;
    }


    @Override
    public void increment() {
        LATITUDE++;
    }

    @Override
    public void decrement() {
        LATITUDE--;
    }

    @Override
    public void wrap(Direction direction) {
        return;
    }

    @Override
    public boolean isOutOfBounds() {
        return LATITUDE > iGrid.height() || LATITUDE <= iGrid.origin();
    }

    @Override
    public boolean isValid() {
        return this.LATITUDE != iGrid.origin();
    }

    @Override
    public String toString(){
        return String.valueOf(LATITUDE);
    }
}
