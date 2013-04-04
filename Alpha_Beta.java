/*
 * Alpha_Beta.java
 * ------------
 * Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Project 3: Project 3 - Isolation
 * ------------
 * Administers the playing of the game
 *      - introduces the game
 *      - assigns player positions
 *      - handles both players turns until an end state occurs
 */

package isolation;

import java.awt.Point;
import java.util.Vector;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.String;
import java.io.*;

public class Play{
    
    /* variables that affect the base game */
    static int[] x_start = {0,0}; // x's starting indeces (0 indexed)
    static int[] o_start = {7,7}; // o's starting indeces (0 indexed)
    static int board_index = 1; // how the user refers to to the first row/column


    /* variables that will change as the game runs */
    public static Node root = new Node(); // root node for each search
    public static char computer_char; // character for computer player
    public static char player_char; // character for the human player
    public static char[][] board = new char[Node.puzzle_size][Node.puzzle_size]; //current board
    public static int time_limit; // time limit for moves
    public static char current_player; // indicates which player's turn it is
    public static int[] current_move = new int[2]; // coordinates of current move
    public static boolean computer_turn; // indicates if it is the computer's turn to move
    
    /*
     * fillBoard(Point xStart, Point oStart)
     * fills the board at the beginning of the game
     * returns: NA
     * args: NA
     */
