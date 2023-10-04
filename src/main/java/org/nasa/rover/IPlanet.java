package org.nasa.rover;

public interface IPlanet {

    int getSouthPoleDeadPoint();

    int getNorthPoleDeadPoint();

    boolean isPoleCrossed(Coordinates coordinates);

}
