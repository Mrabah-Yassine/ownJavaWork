package org.nasa.rover.domain.application.commands;

import org.nasa.rover.domain.application.IPlanetRover;

public class MoveBackward implements ICommand {

    private final IPlanetRover iRover;

    public MoveBackward(IPlanetRover iRover) {
        this.iRover = iRover;
    }

    @Override
    public void execute() {
        iRover.moveBackward();
    }
}
