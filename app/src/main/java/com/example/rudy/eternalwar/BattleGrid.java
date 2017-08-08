package com.example.rudy.eternalwar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import java.util.Random;

import static com.example.rudy.eternalwar.R.layout.activity_main;
import static java.sql.Types.NULL;

/**
 * Created by Rudy on 7/20/2017.
 */

public class BattleGrid extends View
{
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private Paint redPaint = new Paint();
    private Paint greyPaint = new Paint();
    private int[][] topGrid;
    private int[][] botGrid;
    private simpleAI myAI;
    private StatKeeper statBot = new StatKeeper();;

    public BattleGrid(Context context)
    {
        this(context, null);
    }

    public BattleGrid(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //Ship Color
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //Miss Color
        greyPaint.setColor(Color.rgb(128,128,128));
        //Hit Color
        redPaint.setColor(Color.rgb(255,0,0));
    }


    public void setNumColumns(int numColumns)
    {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns()
    {
        return numColumns;
    }

    public void setNumRows(int numRows)
    {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows()
    {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    public boolean shipChecker(int iterationNum, int col, int row, boolean vertiHor, int gridChecked[][])
    {
        int i = iterationNum;

        if(vertiHor == true)
        {
            if (row > numRows / 2)
            {
                for (int j = 0; j < i; j++)
                {
                    if(gridChecked[col][row-j] == 1)
                        return false;
                }
            }
            else //If it is not more than half way down it will build down
            {
                for (int j = 0; j < i; j++)
                {
                    if(gridChecked[col][row+j] ==  1)
                        return false;
                }
            }
        }
        else if(vertiHor == false)
        {
            if (col > numColumns / 2)
            {
                for (int j = 0; j < i; j++)
                {
                    if(gridChecked[col-j][row] == 1)
                        return false;
                }
            }
            else
            {
                for (int j = 0; j < i; j++)
                {
                    if(gridChecked[col+j][row] == 1)
                        return false;
                }
            }
        }

        return true;
    }



    //Randomly Sets user ships on the grid
    private int[][] setShips(int grid[][])
    {
        int aGrid[][] = grid;
        Random randy = new Random();
        boolean verOrhor;
        int i = 5;

        //Runs 5 Times Making ships of size 5 to size 1
        while(i>0)
        {
            verOrhor = randy.nextBoolean();
            //True is Vertical False is Horizontal
            if (verOrhor == true)
            {
                int rowChecker = randy.nextInt(numRows);//sets a random row
                int colChecker = randy.nextInt(numColumns);//sets a random column
                if(shipChecker(i, colChecker, rowChecker,verOrhor, aGrid ) == false)
                    continue;
                //If grid is more than half way down the grid it will build the ship up
                if (rowChecker > numRows / 2)
                {
                    for (int j = 0; j < i; j++)
                    {
                        aGrid[colChecker][rowChecker-j] = 1;
                    }
                }
                else //If it is not more than half way down it will build down
                {
                    for (int j = 0; j < i; j++)
                    {
                        aGrid[colChecker][rowChecker+j] = 1;
                    }
                }
                i--;
            }
            else if (verOrhor == false)
            {
                int rowChecker = randy.nextInt(numRows);
                int colChecker = randy.nextInt(numColumns);
                if(shipChecker(i, colChecker, rowChecker, verOrhor, aGrid) == false)
                    continue;

                //same as above but will build left and right instead of up and down
                if (colChecker > numColumns / 2)
                {
                    for (int j = 0; j < i; j++)
                    {
                        aGrid[colChecker-j][rowChecker] = 1;
                    }
                }
                else
                {
                    for (int j = 0; j < i; j++)
                    {
                        aGrid[colChecker+j][rowChecker] = 1;
                    }
                }
                i--;
            }
        }

        return aGrid;
    }

    private void calculateDimensions()
    {
        if (numColumns < 1 || numRows < 1)
        {
            return;
        }

        cellWidth = getWidth()/11;
        cellHeight = (getHeight()/2)/11;

        topGrid = new int[numColumns][numRows];
        botGrid = new int[numColumns][numRows];
        topGrid = setShips(topGrid);
        botGrid = setShips(botGrid);
        //invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        canvas.drawColor(Color.CYAN);
        //calculateDimensions();
        if (numColumns == 0 || numRows == 0)
        {
            return;
        }

        int width = getWidth();
        int height = getHeight();


        //*************************TOP GRID START******************************************//
        for (int i = 0; i < numColumns; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                if (topGrid[i][j]==1)
                {
                    //canvas.drawRect(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight, blackPaint);
                }
                else if(topGrid[i][j]==2)
                {
                    canvas.drawRect(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight, redPaint);
                }
                else if(topGrid[i][j]==3)
                {
                    canvas.drawRect(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight, greyPaint);
                }
            }
        }

        for (int i = 1; i < numColumns; i++)
        {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height/2, blackPaint);
        }

        for (int i = 1; i <= numRows; i++)
        {
            //This if is for the dividing line
            if(i==numRows)
            {
                Paint centerLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                centerLinePaint.setStrokeWidth(15);
                centerLinePaint.setColor(0xFF910000);
                canvas.drawLine(0, i * cellHeight, width, i * cellHeight, centerLinePaint);

            }
            else
            {canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);}
        }
        //*************************TOP GRID END ******************************************//

        //*************************BOTTOM GRID START******************************************//
        for (int i = 0; i < numColumns; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                if (botGrid[i][j]==1)
                {
                    canvas.drawRect(i * cellWidth, (j * cellHeight)+(height/2) , (i + 1) * cellWidth, ((j + 1) * cellHeight)+(height/2), blackPaint);
                }
                else if(botGrid[i][j]==2)
                {
                    canvas.drawRect(i * cellWidth,(j * cellHeight)+(height/2), (i + 1) * cellWidth, ((j + 1) * cellHeight)+(height/2), redPaint);
                }
                else if(botGrid[i][j]==3)
                {
                    canvas.drawRect(i * cellWidth, (j * cellHeight)+(height/2), (i + 1) * cellWidth, ((j + 1) * cellHeight)+(height/2), greyPaint);
                }
            }
        }

        // Draws the Grid lines
        for (int i = 1; i < numColumns; i++)
        {
            canvas.drawLine(i * cellWidth, height/2, i * cellWidth, height, blackPaint);
        }

        for (int i = 1; i <= numRows; i++)
        {
            canvas.drawLine(0, (i * cellHeight)+(height/2), width, (i * cellHeight)+(height/2), blackPaint);
        }

        //*************************BOTTOM GRID END******************************************//
        Paint centerLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerLinePaint.setStrokeWidth(11);
        centerLinePaint.setColor(0x0000ff00);
        canvas.drawLine(0, height/2, width, height/2, centerLinePaint);
    }

    public void aiAttack()
    {
        //set up the cpu opponent
        myAI = new simpleAI(botGrid);
        BattleCoordinates aiAttackCoordinates = new BattleCoordinates();
        aiAttackCoordinates = myAI.aiAttack();

        //get the coordinates of where the ai attacks
        if(botGrid[aiAttackCoordinates.getRow()][aiAttackCoordinates.getColumn()]==NULL)
        {
            //MISS
            botGrid[aiAttackCoordinates.getRow()][aiAttackCoordinates.getColumn()] = 3;
        }
        else if(botGrid[aiAttackCoordinates.getRow()][aiAttackCoordinates.getColumn()]==1)
        {
            //HIT
            botGrid[aiAttackCoordinates.getRow()][aiAttackCoordinates.getColumn()] = 2;
        }
        else if(botGrid[aiAttackCoordinates.getRow()][aiAttackCoordinates.getColumn()]==3)
        {
            //Nothing
        }
    }

    public boolean winCheck(int grid[][])
    {
        for(int i=0; i<grid.length; i++)
        {
            for(int j=0; j<grid[0].length; j++)
            {
                if(grid[i][j]==1)
                    return false;

            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_DOWN && ((int)event.getY()/cellHeight)!= 11 && event.getY()<getHeight()/2)
        {

            //Get the coordinate of where ethe user touches for an attack
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);
            if(topGrid[column][row]==NULL)
            {
                //MISS
                topGrid[column][row] = 3;
                statBot.wasMiss();
                aiAttack();
            }
            else if(topGrid[column][row]==1)
            {
                //HIT
                topGrid[column][row] = 2;
                statBot.wasHit();
                aiAttack();
            }
            else if(topGrid[column][row]==3)
            {
                //Nothing
            }

            if(winCheck(topGrid) == true)
            {
                statBot.wasWin();
                endMatch(true);
            }

            if(winCheck(botGrid) == true)
            {
                endMatch(false);
            }


            invalidate();
        }

        return true;
    }

    public void endMatch(boolean winner)
    {
        statBot.gameFinished();
        if(winner == true)
        {
            Intent intent = new Intent().setClass(getContext(), EndGameScreen.class);
            ((Activity) getContext()).startActivity(intent);
        }
        else if(winner == false)
        {
            Intent intent = new Intent().setClass(getContext(), LoseGameScreen.class);
            ((Activity) getContext()).startActivity(intent);
        }


    }


}

