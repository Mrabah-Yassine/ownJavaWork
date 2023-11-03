package org.nasa.rover.domain.application;

import org.nasa.rover.domain.entities.localisation.Position;

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
