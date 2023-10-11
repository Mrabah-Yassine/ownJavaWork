package org.nasa.rover.command.impl;

import org.nasa.rover.command.itf.IRoverCommand;
import org.nasa.rover.rover.IPlanetRover;

public class MoveBackward implements IRoverCommand {

    private final IPlanetRover iRover;

    public MoveBackward(IPlanetRover iRover) {
        this.iRover = iRover;
    }

    @Override
    public void execute() {
        iRover.moveBackward();
    }
}
