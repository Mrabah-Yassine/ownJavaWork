package org.nasa.rover.domain.application.localisation;

import org.nasa.rover.domain.entities.localisation.Direction;
import org.nasa.rover.domain.entities.localisation.ICoordinate;
import org.nasa.rover.domain.entities.grid.IGrid;

public class ManageLongitude implements ICoordinate {

    private int LONGITUDE;
    private final IGrid iGrid;

    public ManageLongitude(int LONGITUDE, IGrid iGrid) {
        this.LONGITUDE = LONGITUDE;
        this.iGrid = iGrid;
    }

    @Override
    public void increment() {
        LONGITUDE++;
        LONGITUDE %= iGrid.width();
    }

    @Override
    public void decrement() {
        LONGITUDE--;
    }

    @Override
    public void wrap(Direction direction) {
        if(direction == Direction.HORIZONTAL){
            wrapHorizontally();
            return;
        }
        wrapVertically();
    }

    @Override
    public boolean isOutOfBounds() {
        return LONGITUDE <= iGrid.origin();
    }

    @Override
    public boolean isValid() {
        return this.LONGITUDE != iGrid.origin();
    }

    @Override
    public String toString(){
        return String.valueOf(LONGITUDE);
    }

    private void wrapVertically() {
        if (isInFirstHalfOfGrid()) {
            LONGITUDE += iGrid.width() / 2;
            return;
        }
        if (isInLastHalfOfGrid()) {
            LONGITUDE -= iGrid.width() / 2;
        }
    }


    private void wrapHorizontally() {
        LONGITUDE = iGrid.width() - (LONGITUDE % iGrid.width());
    }


    private boolean isInFirstHalfOfGrid() {
        return LONGITUDE > iGrid.origin() && LONGITUDE <= iGrid.width() / 2;
    }

    private boolean isInLastHalfOfGrid() {
        return LONGITUDE > iGrid.width() / 2 && LONGITUDE <= iGrid.width();
    }
}
