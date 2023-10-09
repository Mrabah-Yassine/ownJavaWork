package org.nasa.rover.command;

import org.nasa.rover.command.itf.IRoverCommand;

import java.util.LinkedList;
import java.util.List;

public class RoverNavigationInvoker {

    private final List<IRoverCommand> commandsList = new LinkedList<>();


    public void takeNewCommand(IRoverCommand command){
        commandsList.add(command);
    }

    public void executeCommand(){
        for (IRoverCommand command : commandsList){
            command.execute();
        }
    }
}
