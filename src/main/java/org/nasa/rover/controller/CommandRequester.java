package org.nasa.rover.controller;

import org.nasa.rover.domain.application.commands.MoveBackward;
import org.nasa.rover.domain.application.commands.MoveForward;
import org.nasa.rover.domain.application.commands.RotateLeft;
import org.nasa.rover.domain.application.commands.RotateRight;
import org.nasa.rover.domain.application.commands.ICommand;
import org.nasa.rover.domain.application.IPlanetRover;

import java.util.LinkedList;
import java.util.List;

public class CommandRequester {

    private final String fullCommand;

    private final IPlanetRover iRover;

    private final List<ICommand> listOfCommands = new LinkedList<>();

    public CommandRequester(String fullCommand, IPlanetRover iRover) {
        this.fullCommand = fullCommand;
        this.iRover = iRover;
    }


    public List<ICommand> get() {
        for (Character stepCommand : fullCommand.toCharArray()) {
            if (stepCommand.toString().equalsIgnoreCase("l")) {
                listOfCommands.add(new RotateLeft(iRover));
            } else if (stepCommand.toString().equalsIgnoreCase("r")) {
                listOfCommands.add(new RotateRight(iRover));
            } else if (stepCommand.toString().equalsIgnoreCase("f")) {
                listOfCommands.add(new MoveForward(iRover));
            } else if (stepCommand.toString().equalsIgnoreCase("b")) {
                listOfCommands.add(new MoveBackward(iRover));
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return listOfCommands;
    }
}
