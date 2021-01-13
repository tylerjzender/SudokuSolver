# SudokuSolver

A constraint satisfaction problem (CSP) is a scenario where a set of 
variables must complete the scenario while still operating within a given
set of rules. One application of this idea is that of a sudoku board. Each 
position on a sudoku board represents a place where an argument must be 
provided in the form of a digit from 1 to 9, inclusive. Each position must 
have a digit that does not repeat itself again in the row or column in which 
it belongs to. In addition, the digit must also not be repeated in the 3-by-3 
subgrid in which it belongs to. These rules represent constraints to the 
overall problem of sudoku that make it an appropriate practice problem for 
those studying CSPs.

Written in java, this project represents my personal attempt at solving this
exercise. Each of the three constraints relating to no repetitions allowd
across rows, columns, and subgrids is represented in code. Additionally,
the code also checks if the other two columns and rows belonging to the
subgrid are filled with a particular digit outside that subgrid. This 
implicitly provides a fourth additional constraint to solving the problem 
at hand. Finally, a fifth additional constraint is determined by checking 
if the rest of the subgrid is filled except for a single spot being 
considered. If this is the only empty position in a subgrid while only 
one digit is not used in that subgrid, it must go in that position.

![SudokuSolver_InputCapture](https://user-images.githubusercontent.com/77171947/104414913-0596c680-553f-11eb-965c-d36f87eaa25a.JPG)
Figure 1: An example of an input text file representing an incomplete sudoku board

The program prompts the user for the name of a text file to use as an input.
Inputs are then read in and displayed to the console. The program then 'steps'
the user through solving the puzzle by prompting them to hit enter to see
the next digit placement on the board.

![SudokuSolver_OutputCapture](https://user-images.githubusercontent.com/77171947/104414916-062f5d00-553f-11eb-9e8f-6a08409c30fa.JPG)
Figure 2: The output of the program to the console for the last few steps of completing a sudoku board

This project was originally completed as an assigment for my elective
course in artifical intelligence completed as a part of my degree
in computer engineering and as a part of my computer science minor.
