package org.nasa.rover.factory;

import org.nasa.rover.command.impl.MoveBackward;
import org.nasa.rover.command.impl.MoveForward;
import org.nasa.rover.command.impl.RotateLeft;
import org.nasa.rover.command.impl.RotateRight;
import org.nasa.rover.command.itf.ICommand;
import org.nasa.rover.rover.IPlanetRover;

public class CommandGeneratorFactory {

    public static ICommand getCommandToBeAppliedOn(Character command, IPlanetRover rover) {
        if(command.toString().equalsIgnoreCase("l")){
            return new RotateLeft(rover);
        }
        if (command.toString().equalsIgnoreCase("r")) {
            return new RotateRight(rover);
        }
        if(command.toString().equalsIgnoreCase("f")){
            return new MoveForward(rover);
        }
        if(command.toString().equalsIgnoreCase("b")){
            return new MoveBackward(rover);
        }
        throw new IllegalArgumentException(String.format("This command %s is not allowed", command));
    }
}
