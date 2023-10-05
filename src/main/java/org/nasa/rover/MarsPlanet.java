package org.nasa.rover;

public class MarsPlanet implements IPlanet{

    private final int SOUTH_POLE_DEAD_POINT;

    private final int NORTH_POLE_DEAD_POINT = 0;

    public MarsPlanet(Grid grid) {
        SOUTH_POLE_DEAD_POINT = grid.size;
    }

}
