package org.nasa.rover.drivers;

import org.nasa.rover.controller.IInput;

import java.util.Scanner;

public class ConsoleInput {

    private IInput iInput;


    public String getUserInputs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your robot's initial coordinates");
        String coordinates = scanner.nextLine();
        System.out.println("coordinates typed are : " +coordinates);

        System.out.println("Enter your robot's initial orientation");
        String orientation = scanner.nextLine();
        System.out.println("Orientation is : " +orientation);

        System.out.println("Enter your robot's commands to be sent");
        String commands = scanner.nextLine();
        System.out.println("Commands to be sent are : " +commands);

        scanner.close();

        String completeInput = String.format("%s,%s:%s", coordinates, orientation, commands);
        System.out.println("complete inputs are : " +completeInput);

        return completeInput;

    }
}
