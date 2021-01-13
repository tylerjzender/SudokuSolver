/********************************************************************/
/* Tyler Zender                                                     */
/* AI Project 3                                                     */
/* SudokuConstraintProcessor - Determines, based on a number of     */
/* constraints from the game rules, where a value can be placed on  */
/* the board based on the current status of the board.              */
/********************************************************************/

import java.util.Scanner;

public class SudokuConstraintProcessor {

  static int[][] board; // Holds the current sudoku board
  
  
  // Object constructor
  public SudokuConstraintProcessor(int[][] sudokuBoard)
  {
   board = sudokuBoard; 
   printBoard();
  }
  
  // Processes the placement of a single value onto the board. Does this by looping through all 
  // 9 possible digits to be placed, and then considers every location on the board to see if
  // the digit can be placed. This is done by considering the constraints based on the game rules.
  public void processStep()
  {
    Scanner scanner = new Scanner(System.in);
    
    // Loops through all 9 digits that can be placed
    for (int value = 1; value < 10; value++)
    {
      
      boolean[] rowCheck = rowChecker(value); // Boolean array that indicates what rows have the digit in them already
      boolean[] colCheck = columnChecker(value); // Boolean array that indicates what columns have the digit in them already
      boolean[] squCheck = squareChecker(value); // Boolean array that indicates what 3x3s have the digit in them already
    
      for (int colCtr = 0; colCtr < 9; colCtr++) // Loops through column coordinates
        for (int rowCtr = 0; rowCtr < 9; rowCtr++) // Loops through row coordinates
          if (board[rowCtr][colCtr] == 0) // If the currently considered coordinate on the board is empty
            if (rowCheck[rowCtr] == false && colCheck[colCtr] == false) // If neither the current row nor column has the digit value in them
                if (squCheck[(colCtr/3)+(3*(rowCtr/3))] == false) // If the 3x3 associated with the coordinate does not contain the digit being considered
                  if (((checkRowSet(rowCtr/3, rowCheck) == 2) && (checkColSet(colCtr/3, colCheck) == 2)) || (remainingSpotsForVal(rowCheck, colCheck, rowCtr, colCtr) == 1))
                    // Checks to see if the other two rows of the 3x3 are deductively constrained, or that there is only a single place for the digit to go in the 3x3 left
                    {
                      board[rowCtr][colCtr] = value; // Places the digit on the board
                      printBoard();
                      System.out.println("Placed a " + value + " at row " + rowCtr + ", col " + colCtr);
                      System.out.println("Enter into the console to make another move.");
                      scanner.nextLine();
                      return;
                    }
    }
  }
  

  // Indicates if any rows on the board have the argument value.
  // Returns a boolean array. A true indicates the value exists in the row.
  // The first element in the boolean array refers to the row at the top
  // of the board, and the last element refers to the row at the bottom.
  public static boolean[] rowChecker(int value)
  {
    boolean[] rowCheck = new boolean[9];
    for (int rowCtr = 0; rowCtr < 9; rowCtr++)
      for (int colCtr = 0; colCtr < 9; colCtr++)
        if (board[rowCtr][colCtr] == value)
          rowCheck[rowCtr] = true;
    
    return rowCheck;
  }
  
  // Indicates if any columns on the board have the argument value.
  // Returns a boolean array. A true indicates the value exists in the column.
  // The first element in the boolean array refers to the leftmost column
  // of the board, and the last element refers to the rightmost column.
  public boolean[] columnChecker(int value)
  {
    boolean[] colCheck = new boolean[9];
    for (int colCtr = 0; colCtr < 9; colCtr++)
      for (int rowCtr = 0; rowCtr < 9; rowCtr++)
        if (board[rowCtr][colCtr] == value)
          colCheck[colCtr] = true;
    
    return colCheck;
  }
  
 // Indicates if any 3x3 squares on the board have the argument value.
 // Returns a boolean array. A true indicates the value exists in the square.
 // The first element in the boolean array refers to the top left square
 // of the board, and the last element refers to the bottom right square
 // as shown below: 
 // 0 1 2
 // 3 4 5
 // 6 7 8
 // Two sets of counters are used: One 'normal' set used to locate which 3x3
 // square we are currently interested in, and another set denoted as 'small'
 // that are used to locate where specifically we are looking at inside the 3x3.
 public static boolean[] squareChecker(int value)
 {
   boolean[] squCheck = new boolean[9];
   for (int rowCtr = 0; rowCtr < 3; rowCtr++)
     for (int colCtr = 0; colCtr < 3; colCtr++)
       for (int smallRowCtr = 0; smallRowCtr < 3; smallRowCtr++)
         for (int smallColCtr = 0; smallColCtr < 3; smallColCtr++)
           if (board[(3*rowCtr)+smallRowCtr][(3*colCtr)+smallColCtr] == value)
             squCheck[(3*rowCtr)+colCtr] = true;
   
   return squCheck;
 }
 
 // Checks a set of 3 rows that are part of a 3x3 square to see if two of the
 // three are filled by the value as previously indicated. This determines
 // if, through deductive reasoning, there is only one location the value can 
 // go in that specific 3x3.
 // Additionally, the associated rows are checked to see if they are filled
 // in the columns not in the 3x3 of interest, or if the rows in the 3x3
 // are filled.
 public static int checkRowSet(int rowVal, boolean[] rowCheck)
 {
   int valCtr = 0; // Return int indicating which rows cannot have the digit of interest placed in them
   
   // Check if the value of interest is in the rows
   boolean[] rowsCheck = {false, false, false};
   for (int checkCtr = 0; checkCtr<3; checkCtr++)
     if (rowCheck[checkCtr + (3*rowVal)])
       rowsCheck[checkCtr] = true;
   
   
   // Check if the rows of interest are fully occupied inside the 3x3 of interest
   // and takes note of how many empty spaces are in each row
   boolean[] occupiedRowInside = {true, true, true};
   int[] occupiedRowsInsideInt = {0, 0, 0};
   for (int rowCtr = 0; rowCtr < 3; rowCtr++)
     for (int colCtr = 0; colCtr < 3; colCtr++)
       if (board[rowCtr][colCtr] == 0)
         occupiedRowsInsideInt[rowCtr] = occupiedRowsInsideInt[rowCtr] + 1;
   
  
   // Check if the rows of interest are fully occupied outside of the 3x3 of interest
   boolean[] occupiuedRow = {true, true, true};
   for (int rowCtr = 0; rowCtr < 3; rowCtr++)
   {     
     int unoccupiedCtr = 0;
     for (int colCtr = 0; colCtr < 9; colCtr++)
       if (board[rowCtr][colCtr] == 0)
         unoccupiedCtr++;
     
     if ((unoccupiedCtr - occupiedRowsInsideInt[rowCtr]) == 0) // Indicates all spaces besides the ones in that 3x3 are filled
       occupiuedRow[rowCtr] = false;
   }
   
   
   // Combine boolean arrs to check if at least one of the three conditions are true
   for (int arrCtr = 0; arrCtr < 3; arrCtr++)
     if (rowsCheck[arrCtr] | occupiuedRow[arrCtr] | occupiedRowInside[arrCtr])
       valCtr++;
   
   return valCtr;
 }
 
 
//Checks a set of 3 columns that are part of a 3x3 square to see if two of the
// three are filled by the value as previously indicated. This determines
// if, through deductive reasoning, there is only one location the value can 
// go in that specific 3x3.
 // Additionally, the associated cols are checked to see if they are filled
 // in the columns not in the 3x3 of interest, or if the cols in the 3x3
 // are filled.
 public static int checkColSet(int colVal, boolean[] colCheck)
 {
   int valCtr = 0; // Return int indicating which cols cannot have the digit of interest placed in them
   
   // Check if the value of interest is in the rows
   boolean[] colsCheck = {false, false, false};
   for (int checkCtr = 0; checkCtr<3; checkCtr++)
     if (colCheck[checkCtr + (3*colVal)])
       colsCheck[checkCtr] = true;
   
  
   // Check if the rows of interest are fully occupied outside of the 3x3 of interest
   boolean[] occupiuedCol = {true, true, true};
   for (int colCtr = 0; colCtr < 3; colCtr++)
     for (int rowCtr = 0; rowCtr < 6; rowCtr++)
       if (board[rowCtr][colCtr] == 0)
         occupiuedCol[colCtr] = (false);
   
   
   // Check if the rows of interest are fully occupied inside the 3x3 of interest
   boolean[] occupiuedColInside = {true, true, true};
   for (int colCtr = 0; colCtr < 3; colCtr++)
     for (int rowCtr = 0; rowCtr < 3; rowCtr++)
       if (board[rowCtr][colCtr] == 0)
         occupiuedColInside[colCtr] = (false);
   
   // Combine boolean arrs to check if at least one of the three conditions are true
   for (int arrCtr = 0; arrCtr < 3; arrCtr++)
     if (colsCheck[arrCtr] | occupiuedCol[arrCtr] | occupiuedColInside[arrCtr])
       valCtr++;
   
   return valCtr;
 }
 
 // Determines the remaining positions for a value in a cell
 public int remainingSpotsForVal(boolean[] rowCheck, boolean[] colCheck, int rowPos, int colPos)
 {
   int positionsRemaining = 0;
   
   rowPos = rowPos/3;
   colPos = colPos/3;
   
   for (int rowCtr = 0; rowCtr < 3; rowCtr++)
     for (int colCtr = 0; colCtr < 3; colCtr++)
       if (board[rowCtr + (3*rowPos)][colCtr + (3*colPos)] == 0)
         if (!rowCheck[rowCtr + (3*rowPos)])
           if (!colCheck[colCtr + (3*colPos)])
             positionsRemaining = positionsRemaining + 1;
   
   return positionsRemaining;
  }
 
 
 // Prints the board to the console.
 public void printBoard()
 {
   for (int rowCtr = 0; rowCtr < 9; rowCtr++)
   {
     if (rowCtr % 3 == 0 && rowCtr > 0)
       System.out.println("---------------------");
     
     String outString = board[rowCtr][0] + " " + board[rowCtr][1] + " " + board[rowCtr][2] + " | " 
       + board[rowCtr][3] + " " + board[rowCtr][4] + " " + board[rowCtr][5] + " |" +
       board[rowCtr][6] + " " + board[rowCtr][7] + " " + board[rowCtr][8];
     outString = outString.replace("0", "-");
     System.out.println(outString);
   }
 }
  
  
} // End class