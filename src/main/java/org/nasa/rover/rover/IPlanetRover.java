package org.nasa.rover.rover;

import org.nasa.rover.localisation.Position;

public interface IPlanetRover {
    Position getPosition();

    void moveForward();

    void moveBackward();

    void rotateToLeft();

    void rotateToRight();
}
