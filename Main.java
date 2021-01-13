/********************************************************************/
/* Tyler Zender                                                     */
/* AI Project 3                                                     */
/* Main - Generates the 'SudokuConstraintProcessor' object, as well */
/* as handling program to user interactions and reading the board.  */
/********************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

  
  public static void main(String [] args)
  {
    int[][] startingBoard = new int[9][9];
    Scanner scanner = new Scanner(System.in);
    System.out.println ("Enter a file name to read in a Sudoku board.");
    String fileName = scanner.nextLine();
    File sudokuFile = new File(fileName);
    
    
    // Scans in the board from the indicated file
    try {
      Scanner fileScanner = new Scanner(sudokuFile);
      
      int inRowCtr = 0;
      int inColCtr = 0;
      
      int fileLineCtr = 0;
      
      while (fileScanner.hasNextLine())
      {
        String nextLine = fileScanner.nextLine();
        fileLineCtr++;
        
        if (fileLineCtr == 4 | fileLineCtr == 8)
          continue;
        
        nextLine = nextLine.replace('-', '0');
        
        while (nextLine.length() > 0)
        {
          char nextChar = nextLine.charAt(0);
          nextLine = nextLine.substring(1);
          if (Character.isDigit(nextChar))
          {
            startingBoard[inRowCtr][inColCtr] = Integer.parseInt(String.valueOf(nextChar));
            
            if (inColCtr == 8)
            {
              if (inRowCtr == 8)
                break;
              inColCtr = 0;
              inRowCtr++;
            }
            else
              inColCtr++;
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    SudokuConstraintProcessor solver = new SudokuConstraintProcessor(startingBoard);
    
    System.out.println("Enter into the console to make the first move.");
    scanner.nextLine();
    
    // Infinitely searches the board for a next step/next placement of a number
    while (true)
      solver.processStep();
  }
}
