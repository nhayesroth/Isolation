Nathan Hayes-Roth
COMS W4701 - Artificial Intelligence
Project #3
Game Playing Tournament - ISOLATION

-----------------------------------

*** Development Information ***

Developed remotely on the CLIC machines using VIM:
-Programming Language: Java 
-Language Version: 1.6.0_27
-Development Environment: Unix

-----------------------------------

*** Instructions to compile and run on CLIC machines ***

1. From the parent directory, make the default target to compile the program.
    ~/.../nbh2113_assignment3_isolation$ make
2. From the parent directory, make the play target to run the program.
    ~/.../nbh2113_assignment3_isolation$ make play
3. While running the program, type two integers in any format to enter a move.
    (1 3)
    (1, 3)
    1 3
    1 asdsd 3
4. Press Ctrl + c, enter (0 0), or any other invalid move  to exit the program.

-----------------------------------

*** Program Description ***

This program allows the user to play a game of isolation against a computer
character. The computer performs a version of Alpha-Beta search to determine
the best possible move, within specified time and space cutoffs.

The program's search algorithm relies on an evaluation function that weights 
certain characteristics in determining favorable moves.
    Positions with more possible moves are viewed positively.
    Positions where the opponent has many possible moves are viewed negatively.
    Positions near walls are avoided.
    Positions with empty surroundings are viewed favorably.
    Positions where the opponents surroundings are full are viewed favorably.

Cutoffs are implemented manually by the user (time cutoff) and automatically
by the program (depth limit). When these cutoffs are reached, the program
stops searching and returns the best possible move that has been evaluated.

-----------------------------------

*** Files ***

/nbh2113_assignment3_isolation/
Makefile
    - make        - compile the program
    - make play   - run the program
    - make clean  - delete executable files

/nbh2113_assignment3_isolation/isolation/
Node.java
    - Holds all attributes and functions for nodes of the search tree
    - Contains the evaluation function (Node.evaluate())
Play.java
    - Administers the game
    - Contains the search function (Play.alphaBetaSearch())
