package org.nasa.rover.controller;

import org.nasa.rover.domain.application.commands.ICommand;

public class RoverController {

    private final CommandRequester request;

    public RoverController(CommandRequester request) {
        this.request = request;
    }


    public void execute() {
        for (ICommand iCommand : request.get()) {
            iCommand.execute();
        }
    }
}
