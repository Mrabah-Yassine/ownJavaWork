package org.nasa.rover.application.command.impl;

import org.nasa.rover.application.command.itf.ICommand;
import org.nasa.rover.rover.IPlanetRover;

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
