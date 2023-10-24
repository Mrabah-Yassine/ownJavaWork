package org.nasa.rover.localisation.itf;

import org.nasa.rover.localisation.Direction;

public interface ICoordinate {

    void increment();

    void decrement();

    void wrap(Direction direction);

    boolean isOutOfBounds();

    boolean isValid();
}
