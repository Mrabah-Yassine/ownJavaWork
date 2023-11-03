package org.nasa.rover.domain.application.commands;

import org.nasa.rover.domain.application.IPlanetRover;

public class RotateRight implements ICommand {

    private final IPlanetRover iRover;

    public RotateRight(IPlanetRover iRover) {
        this.iRover = iRover;
    }

    @Override
    public void execute() {
        iRover.rotateToRight();
    }
}
