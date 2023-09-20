package org.nasa.rover;

public class Rover {

    private String actualPosition;
    private String direction;

    private int xCoordinate;
    private int yCoordinate;

    private int gridSize;

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
        System.out.println("START");
        int numberOfStepCommandsSent = command.length();

        if(yCoordinate == 1){
            xCoordinate = calculateXCoordinate();
            reverseDirection();
            yCoordinate = numberOfStepCommandsSent;
        }else{
            for (int i = 0; i < numberOfStepCommandsSent; i++){
                yCoordinate--;
            }
        }

        if(yCoordinate <= 0){
            xCoordinate = calculateXCoordinate();
            reverseDirection();
            yCoordinate = Math.abs(yCoordinate - 1);
        }



        actualPosition = String.format("%s,%s,%s", xCoordinate, yCoordinate, direction);
        System.out.println("END");
    }

    private void reverseDirection() {
        if(direction.equals("N")){
            direction = "S";
        }else{
            direction = "N";
        }
    }


    private int calculateXCoordinate() {
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

    public String getActualPosition() {
        return actualPosition;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
}
