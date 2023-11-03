package org.nasa.rover.domain.application.commands;

import org.nasa.rover.domain.application.IPlanetRover;

public class RotateLeft implements ICommand {

    private final IPlanetRover iRover;

    public RotateLeft(IPlanetRover iRover) {
        this.iRover = iRover;
    }

    @Override
    public void execute() {
        iRover.rotateToLeft();
    }
}
