package org.nasa.rover;

public class Rover {

    private String actualNewPosition;
    private String direction;

    private int xCoordinate;
    private int yCoordinate;

    private int gridSize;

    private String command;

    public Rover(String[] position) {
        this.xCoordinate = Integer.parseInt(position[0]);
        this.yCoordinate = Integer.parseInt(position[1]);
        this.direction = position[2];
    }

    public void executeCommand(String command) {
        if (areCoordinatesNotValid()) {
            throw new UnsupportedOperationException("Unable to move the rover from 0,0 coordinates " +
                    "because they are not defined");
        }
        this.command = command;
        int numberOfStepsSentByCommand = this.command.length();

        if(numberOfStepsSentByCommand <= gridSize * 2 - 1 ){
            setNewYCoordinateAfterCommandExecution(numberOfStepsSentByCommand);

            if (isNorthPoleCrossed()) {
                setNewRealPositionAfterCrossingPole();
            }
        }



        actualNewPosition = String.format("%s,%s,%s", xCoordinate, yCoordinate, direction);
    }


    private void setNewYCoordinateAfterCommandExecution(int numberOfStepsSentByCommand) {
        for (int i = 0; i < numberOfStepsSentByCommand; i++) {
            yCoordinate--;
        }
    }


    private boolean isNorthPoleCrossed() {
        return yCoordinate <= 0;
    }


    private void setNewRealPositionAfterCrossingPole() {
        xCoordinate = calculateNewXCoordinate();
        direction = getNewDirection();
        yCoordinate = Math.abs(yCoordinate - 1);
    }

    private String getNewDirection() {
        if (direction.equals("N")) {
            return "S";
        }
        return "N";
    }


    private int calculateNewXCoordinate() {
        if (xCoordinate >= 1 && xCoordinate <= gridSize / 2) {
            return xCoordinate + gridSize / 2;
        }
        if (xCoordinate > gridSize / 2 && xCoordinate <= gridSize) {
            return xCoordinate - gridSize / 2;
        }
        return xCoordinate;
    }


    private boolean areCoordinatesNotValid() {
        return this.xCoordinate == 0 || this.yCoordinate == 0;
    }

    public String getActualNewPosition() {
        return actualNewPosition;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
}
