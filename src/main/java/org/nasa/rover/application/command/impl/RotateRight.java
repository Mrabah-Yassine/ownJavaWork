package org.nasa.rover.application.command.impl;

import org.nasa.rover.application.command.itf.ICommand;
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
