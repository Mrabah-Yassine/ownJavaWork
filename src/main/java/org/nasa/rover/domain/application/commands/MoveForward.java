package org.nasa.rover.domain.application.commands;

import org.nasa.rover.domain.application.IPlanetRover;

public class MoveForward implements ICommand {

    private final IPlanetRover iRover;

    public MoveForward(IPlanetRover iRover) {
        this.iRover = iRover;
    }

    @Override
    public void execute() {
        iRover.moveForward();
    }
}
