package org.nasa.rover.command.impl;

import org.nasa.rover.command.itf.IRoverCommand;
import org.nasa.rover.rover.IPlanetRover;

public class RotateRight implements IRoverCommand {

    private final IPlanetRover rover;

    public RotateRight(IPlanetRover rover) {
        this.rover = rover;
    }

    @Override
    public void execute() {
        rover.rotateToRight();
    }
}
