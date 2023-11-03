package org.nasa.rover.domain.entities.localisation;

public interface ICoordinate {

    void increment();

    void decrement();

    void wrap(Direction direction);

    boolean isOutOfBounds();

    boolean isValid();
}
