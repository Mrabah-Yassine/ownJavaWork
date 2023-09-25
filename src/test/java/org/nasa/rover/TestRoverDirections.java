package org.nasa.rover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class TestRoverDirections {

    private Rover rover;

    private final int gridSize = 8;


    @ParameterizedTest(name = "{index} => inputPosition={0}, command={1}, expectedNewPosition={2}")
    @CsvSource({
            "'1,8,N', '', '1,8,N'",
            "'1,8,N', 'f', '1,7,N'",
            "'1,8,N', 'ff', '1,6,N'",
            "'1,8,N', 'fff', '1,5,N'",
            "'1,8,N', 'ffffff', '1,2,N'",
            "'1,8,N', 'fffffff', '1,1,N'",
            "'1,8,N', 'ffffffff', '5,1,S'",
            "'1,8,N', 'fffffffff', '5,2,S'",
            "'1,8,N', 'ffffffffff', '5,3,S'",
            "'1,5,N', 'ffff', '1,1,N'",
            "'1,1,N', 'f', '5,1,S'",
            "'1,1,N', 'ff', '5,2,S'",
            "'1,1,N', 'ffff', '5,4,S'",
            "'1,2,N', 'ffff', '5,3,S'",
            "'1,2,N', 'fffffffff', '5,8,S'",
            "'1,7,N', 'ffffff', '1,1,N'",
            "'1,7,N', 'ffffffffffff', '5,6,S'",
            "'1,6,N', 'fffffffffffff', '5,8,S'",
            "'1,4,N', 'fff', '1,1,N'",
            "'1,4,N', 'ffff', '5,1,S'",
            "'1,4,N', 'fffffff', '5,4,S'",
            "'1,4,N', 'ffffffffffffffff', '1,4,N'",
            "'1,4,N', 'fffffffffffffffff', '1,3,N'",
            "'1,4,N', 'fffffffffffffffffffffff', '5,4,S'",
            "'1,4,N', 'ffffffffffffffffffffffff', '5,5,S'",
            "'1,8,N', 'fffffffffffffffffffffff', '1,1,N'",
            "'4,8,N', 'fff', '4,5,N'",
            "'4,3,N', 'ffff', '8,2,S'",
            "'5,3,N', 'ffff', '1,2,S'",
            "'8,8,N', 'ffffffff', '4,1,S'",
            "'4,4,N', 'ffffffff', '8,5,S'",
            "'4,4,N', 'fffffffffffff', '4,7,N'",
            "'4,4,N', 'fffffffffffffff', '4,5,N'",
            "'4,8,N', 'fffffffffffffff', '8,8,S'",
            "'4,8,N', 'fffffffffffffffffffffffffffff', '8,6,S'",
            "'4,4,N', 'fffffffffffffffffffffff', '8,4,S'",
            "'1,1,N', 'fffffffffffffffffffffff', '5,7,S'",


    })
    void givenInitialDirectionIsNorthThenMoveForward(String inputPosition,
                                                     String command,
                                                     String expectedNewPosition){


        String[] positionArray = inputPosition.split(",");

        rover = new Rover(positionArray);

        rover.setGridSize(gridSize);
        rover.executeCommand(command);
        //assertions,
        Assertions.assertEquals(expectedNewPosition, rover.getNewActualPosition());

    }


    @ParameterizedTest(name = "{index} => inputPosition={0}, command={1}, expectedNewPosition={2}")
    @CsvSource({
            "'1,8,S', '', '1,8,S'",
            "'1,8,S', 'f', '5,8,N'",
            "'1,8,S', 'ff', '5,7,N'",
            "'1,8,S', 'fff', '5,6,N'",
            "'1,1,S', 'fff', '1,4,S'",
            "'5,5,S', 'ffffffff', '1,4,N'",
            "'8,8,S', 'ffffffff', '4,1,N'",
            "'7,6,S', 'ffffffff', '3,3,N'",
            "'7,6,S', 'ffffffffffffffff', '7,6,S'",
            "'7,6,S', 'fffffffffffffff', '7,5,S'",
            "'7,3,S', 'fffffffffffff', '3,1,N'",
            "'7,3,S', 'fffffffffffffff', '7,2,S'",
            "'7,7,S', 'fffffffffffffffff', '7,8,S'",
            "'4,8,S', 'fffffffffffffffffffffffffffff', '4,5,S'",


    })
    void givenInitialDirectionIsSouthThenMoveForward(String inputPosition,
                                                     String command,
                                                     String expectedNewPosition){


        String[] positionArray = inputPosition.split(",");

        rover = new Rover(positionArray);

        rover.setGridSize(gridSize);
        rover.executeCommand(command);
        //assertions,
        Assertions.assertEquals(expectedNewPosition, rover.getNewActualPosition());
    }



    @ParameterizedTest(name = "{index} => inputPosition={0}, command={1}, expectedNewPosition={2}")
    @CsvSource({
            "'1,8,S', 'r', '1,8,W'",


    })
    void givenInitialDirectionRotate(String inputPosition,
                                     String command,
                                     String expectedNewPosition){

        String[] positionArray = inputPosition.split(",");

        rover = new Rover(positionArray);

        rover.setGridSize(gridSize);
        rover.executeCommand(command);
        //assertions,
        Assertions.assertEquals(expectedNewPosition, rover.getNewActualPosition());
    }



    @Test
    void whenZeroZeroCoordinatesThenThrowUnsupportedOperationException(){

        rover = new Rover(new String[]{"0", "0", "N"});
        UnsupportedOperationException thrown = Assertions.assertThrows(UnsupportedOperationException.class, () ->
                rover.executeCommand("f"));

        Assertions.assertEquals("Unable to move the rover from 0,0 coordinates " +
                "because they are not defined", thrown.getMessage());
    }

}
