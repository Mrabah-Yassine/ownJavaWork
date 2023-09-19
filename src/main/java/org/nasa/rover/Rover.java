package org.nasa.rover;

public class Rover {

    String actualFacedDirection = "N";
    private String actualPosition;
    private String direction;

    private int xCoordinate;
    private int yCoordinate;

    private int gridSize;

    public Rover(String[] position, String direction) {
        this.xCoordinate = Integer.parseInt(position[0]);
        this.yCoordinate = Integer.parseInt(position[1]);
        this.direction = direction;
    }

    public void executeCommand(String command) {
        if (areCoordinatesNotValid()) {
            throw new UnsupportedOperationException("Unable to move the rover from 0,0 coordinates " +
                    "because they are not defined");
        }

        int numberOfStepCommandsSent = command.length();

        if (yCoordinate == 1 && numberOfStepCommandsSent > 0) {

            xCoordinate = calculateXCoordinate(numberOfStepCommandsSent);
            yCoordinate = calculateYCoordinate(numberOfStepCommandsSent);
        }


        actualPosition = String.format("%s,%s,%s", xCoordinate, yCoordinate, direction);
    }


    private int calculateXCoordinate(int numberOfStepCommandsSent) {
        if (numberOfStepCommandsSent <= gridSize) {
            direction = "S";
            if (xCoordinate >= 1 && xCoordinate <= gridSize / 2) {
                return xCoordinate + gridSize / 2;
            }
            if (xCoordinate > gridSize / 2 && xCoordinate <= gridSize) {
                return xCoordinate - gridSize / 2;
            }
        }
        return xCoordinate;
    }


    private int calculateYCoordinate(int numberOfStepCommandsSent) {

        if (numberOfStepCommandsSent / gridSize > 1 && numberOfStepCommandsSent % gridSize == 0) {
            return yCoordinate;
        }
        if (numberOfStepCommandsSent == gridSize || numberOfStepCommandsSent == gridSize + 1) {
            return gridSize;
        }
        if (numberOfStepCommandsSent > gridSize + 1) {
            return gridSize - (numberOfStepCommandsSent % gridSize) + 1;
        }
        if (numberOfStepCommandsSent > 1) {
            return numberOfStepCommandsSent;
        }
        return yCoordinate;
    }


    private boolean areCoordinatesNotValid() {
        return this.xCoordinate == 0 || this.yCoordinate == 0;
    }

    public String getActualPosition() {
        return actualPosition;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
}
