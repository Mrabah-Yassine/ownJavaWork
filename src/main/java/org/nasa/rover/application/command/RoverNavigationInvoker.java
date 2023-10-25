package org.nasa.rover.application.command;

import org.nasa.rover.application.command.itf.ICommand;

import java.util.LinkedList;
import java.util.List;

public class RoverNavigationInvoker {

    private final List<ICommand> commandsList = new LinkedList<>();


    public void takeNewCommand(ICommand command){
        commandsList.add(command);
    }

    public void executeCommand(){
        for (ICommand command : commandsList){
            command.execute();
        }
    }
}