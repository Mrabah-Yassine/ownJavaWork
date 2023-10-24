package org.nasa.rover.command.impl;

import org.nasa.rover.command.itf.ICommand;
import org.nasa.rover.rover.IPlanetRover;

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
