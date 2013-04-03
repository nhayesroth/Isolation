/*
 * Play.java
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
    
    /* variables */
    static int[] x_start = {0,0};
    static int[] o_start = {7,7};

    /* static variables */
    public static Node root = new Node();    // root node for each search
    public static char computer_char;    // character for computer player
    public static char[][] board = new char[Node.puzzle_size][Node.puzzle_size]; //current board
    public static int time_limit;    // time limit for moves
    public static char current_player;    // indicates which player's turn it is
    public static int[] current_move = new int[2];    // coordinates of current move
    
    /*
     * fillBoard(Point xStart, Point oStart)
     * fills the board at the beginning of the game
     * returns: NA
     * args: NA
     */
    public static void fillBoard(){
        for (int i = 0; i<Node.puzzle_size; i++){
            for (int j = 0; j<Node.puzzle_size; j++){
                board[i][j] = '-';
            }
        }
        board[x_start[0]][x_start[1]] = 'x';
        board[o_start[0]][o_start[1]] = 'o';
    }
    
    /*
     * intro()
     * welcomes the user and prompts the choosing of player type
     * returns: char
     * args: NA
     */
    public static char intro(){
        System.out.println("\nWelcome to Project 3 - Isolation. Prepare to lose, sucka!");
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        System.out.println("\nPlease tell the program which player to assume.");
        System.out.println("\n\tx will move first");
        System.out.println("\to will move second");
        String choice;
        while(true){
            System.out.println("\nMake your selection by entering either x or o.");
            Scanner in = new Scanner(System.in);
            choice = in.next();
            if (choice.charAt(0) == 'x')
                return choice.charAt(0);
            if (choice.charAt(0)  == 'o')
                return choice.charAt(0); 
        }
    }
    
    /*
     * readPlayerMove()
     * prompts the user to input his/her move
     *     if invalid, reports so
     *     if valid, prints resulting board and confirms the choice before carrying through
     */
    public static void readPlayerMove(){
        int count = 0;
        System.out.println("\nPlease enter your next move.\n\tformat: (row column)\n");
        String str = "";
        while(count<2){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                str = br.readLine();
                //br.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(str); 
            while ((count<2) && (m.find())){
                current_move[count] = Integer.parseInt(m.group());
                count++;
            }
            System.out.println("Are you sure you want to move to (" + current_move[0] + ", " + current_move[1] + ")");
            String answer = yesNo();
            if (answer.equals("n"))
                readPlayerMove();
        }
    }

    /*
     * yesNo()
     * administers yes/no question, only accepting appropriate answers
     * returns: String
     * args: NA
     */
    public static String yesNo(){
        String answer = "";    // the answer to a yes/no question
        while(true){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                answer = br.readLine();
                //br.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            if (!(answer.equals("n")||answer.equals("y"))){
                System.out.println("Sorry, that was a yes/no question." + "('y' to answer 'yes')\n\t" +"('n' to answer 'no')");
            }
            else return answer;
        }
    }
    
    /*
     * main function
     * administers the program
     */
    public static void main(String [] args){
        fillBoard();
        root.setState(board);
        computer_char = intro();
        while(true){
            readPlayerMove();
            root.printState();
            if ((current_move[0] == 0) && (current_move[1] == 0))
                   break; 
        }
        System.out.println();
    }
}


        
        
    
             
    
    
    
    
    
