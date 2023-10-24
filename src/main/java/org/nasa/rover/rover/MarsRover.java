package org.nasa.rover.rover;

import org.nasa.rover.localisation.Pole;
import org.nasa.rover.localisation.Position;

public class MarsRover implements IPlanetRover {

    private final Position position;

    private Pole pole = Pole.NORTH;


    public MarsRover(Position position) {
        this.position = position;
    }


    @Override
    public Position getPosition() {
        return position;
    }


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
