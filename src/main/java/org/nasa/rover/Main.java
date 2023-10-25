package org.nasa.rover;

import org.nasa.rover.application.Request.IRequest;
import org.nasa.rover.application.Request.Request;
import org.nasa.rover.application.view.InputCommandsOnKeyboard;

public class Main {

    public static void main(String[] args) {

        InputCommandsOnKeyboard inputKeyboard = new InputCommandsOnKeyboard();
        String userInputs = inputKeyboard.getUserInputs();
        IRequest iRequest = new Request(userInputs);
        inputKeyboard.send(iRequest);

    }
}
