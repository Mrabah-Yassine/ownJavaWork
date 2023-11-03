package org.nasa.rover.domain.application.localisation;

import org.nasa.rover.domain.entities.localisation.Direction;
import org.nasa.rover.domain.entities.localisation.ICoordinate;
import org.nasa.rover.domain.entities.grid.IGrid;

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
