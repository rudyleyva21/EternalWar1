package com.example.rudy.eternalwar;

import java.util.Random;

import static java.sql.Types.NULL;

/**
 * Created by Rudy on 7/23/2017.
 */

public class simpleAI
{
    private int playerGrid[][];
    private int rows;
    private int columns;
    private Random randy = new Random();
    private boolean hitSuccess;
    private boolean isHit = false;
    private BattleCoordinates lastHit = new BattleCoordinates();
    private int locCounter=0;


    public simpleAI(int playerGrid[][])
    {
        this.playerGrid = playerGrid;
        rows = playerGrid.length;
        columns = playerGrid[0].length;
    }

   /* public int startingPoint()
    {

    }*/

    public BattleCoordinates aiAttack()
    {
            if(isHit == true)
            {
                return coordinatedAttack();
            }
            else
            {
                return randomAttack();
            }

    }

    public BattleCoordinates randomAttack()
    {
        BattleCoordinates coordinatesAttacked = new BattleCoordinates();
        hitSuccess = false;
        int ranRow;
        int ranCol;

        while(hitSuccess == false)
        {
            ranRow = randy.nextInt(rows);
            ranCol  = randy.nextInt(columns);
            if(playerGrid[ranRow][ranCol] == NULL | playerGrid[ranRow][ranCol] == 1)
            {
                /*if(playerGrid[ranRow][ranCol] == 1)
                {
                    isHit = true;
                    lastHit.setRow(ranRow);
                    lastHit.setColumn(ranCol);
                }*/
                coordinatesAttacked.setRow(ranRow);
                coordinatesAttacked.setColumn(ranCol);
                hitSuccess = true;

                return coordinatesAttacked;
            }
        }
        return coordinatesAttacked;
    }

    public BattleCoordinates coordinatedAttack()
    {
        BattleCoordinates coordinatesAttacked = new BattleCoordinates();

        if(locCounter == 0)
        {
            //check up
            int toCheck = lastHit.getColumn();
            if(playerGrid[0].length-toCheck!=0)
            {

            }

        }
        else if(locCounter == 1)
        {
            //check down
        }
        else if(locCounter == 2)
        {
            //check left
        }
        else if(locCounter == 3)
        {
            //check right
        }
        else if(locCounter == 4)
        {

        }

        return coordinatesAttacked;
    }

    public BattleCoordinates adjCheck(BattleCoordinates mycoor)
    {
        return mycoor;
    }
}
