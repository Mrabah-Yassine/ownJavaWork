package org.nasa.rover.command.impl;

import org.nasa.rover.command.itf.ICommand;
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
