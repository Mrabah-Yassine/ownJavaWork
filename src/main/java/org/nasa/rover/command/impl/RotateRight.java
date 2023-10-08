package org.nasa.rover.command.impl;

import org.nasa.rover.Orientation;
import org.nasa.rover.command.itf.ICommand;

public class RotateRight implements ICommand {


    private Orientation orientation;

    public RotateRight(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public void execute() {
        this.orientation = this.orientation.toTheRight();
    }
}
