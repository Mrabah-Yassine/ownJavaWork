package org.nasa.rover.command.impl;

import org.nasa.rover.command.itf.ICommand;
import org.nasa.rover.rover.IPlanetRover;

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
