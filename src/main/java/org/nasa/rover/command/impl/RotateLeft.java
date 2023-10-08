package org.nasa.rover.command.impl;

import org.nasa.rover.Orientation;
import org.nasa.rover.command.itf.ICommand;

public class RotateLeft implements ICommand {

    private Orientation orientation;

    public RotateLeft(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public void execute() {
        this.orientation = this.orientation.toTheLeft();
    }
}
