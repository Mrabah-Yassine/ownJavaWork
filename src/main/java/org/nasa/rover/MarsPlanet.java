package org.nasa.rover;

public class MarsPlanet implements IPlanet{

    private final int SOUTH_POLE_DEAD_POINT;

    private final int NORTH_POLE_DEAD_POINT = 0;

    public MarsPlanet(Grid grid) {
        SOUTH_POLE_DEAD_POINT = grid.size;
    }

    @Override
    public int getSouthPoleDeadPoint() {
        return SOUTH_POLE_DEAD_POINT;
    }

    @Override
    public int getNorthPoleDeadPoint() {
        return NORTH_POLE_DEAD_POINT;
    }

    @Override
    public boolean isPoleCrossed(Coordinates coordinates) {
        return coordinates.ALTITUDE > SOUTH_POLE_DEAD_POINT || coordinates.ALTITUDE <= NORTH_POLE_DEAD_POINT;

    }
}
