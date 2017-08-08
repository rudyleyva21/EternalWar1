package com.example.rudy.eternalwar;

/**
 * Created by Rudy on 7/23/2017.
 */

public class BattleCoordinates {
    private int row;
    private int column;

    public BattleCoordinates(){}

    public BattleCoordinates(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public void setRow(int x)
    {
        row = x;
    }

    public void setColumn(int y)
    {
        column  = y;
    }

    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }
}
