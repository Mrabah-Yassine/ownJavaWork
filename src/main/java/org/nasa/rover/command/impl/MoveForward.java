package org.nasa.rover.command.impl;

import org.nasa.rover.command.itf.IRoverCommand;
import org.nasa.rover.rover.IPlanetRover;

public class MoveForward implements IRoverCommand {

    private final IPlanetRover rover;

    public MoveForward(IPlanetRover rover) {
        this.rover = rover;
    }

    @Override
    public void execute() {
        rover.moveForward();
    }
}
