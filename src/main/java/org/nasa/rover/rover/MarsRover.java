package org.nasa.rover.rover;

import org.nasa.rover.localisation.Position;

public record MarsRover(Position position) implements IPlanetRover {


    @Override
    public void moveForward() {
        position.forward();
    }

    @Override
    public void moveBackward() {
        position.backward();
    }

    @Override
    public void rotateToLeft() {
        position.toTheLeft();
    }

    @Override
    public void rotateToRight() {
        position.toTheRight();
    }

}
