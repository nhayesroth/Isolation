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
import java.util.Iterator;
import java.io.*;

public class Play{
    
    /* variables that affect the base game */
    static int[] x_start = {0,0}; // x's starting indeces (0 indexed)
    static int[] o_start = {7,7}; // o's starting indeces (0 indexed)
    static int board_index = 1; // how the user refers to to the first row/column
    static int infinity = 9999;
    static int neg_infinity = -9999; // to be used in alpha-beta


    /* variables that will change as the game runs */
    public static Node root = new Node(); // root node for each search
    public static char computer_char; // character for computer player
    public static char player_char; // character for the human player
    public static char[][] board = new char[Node.puzzle_size][Node.puzzle_size]; //current board
    public static int time_limit; // time limit for moves
    public static char current_player; // indicates which player's turn it is
    public static int[] current_move = new int[2]; // coordinates of current move
    public static boolean computer_turn; // indicates if it is the computer's turn to move
    static int[] best_move = new int[2];
    
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
     * welcomes the user and prompts the choosing of player type, which sets each player
     * returns: NA
     * args: NA
     */
    public static void intro(){
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
            if (choice.charAt(0) == 'x'){
                computer_char = 'x';
                player_char = 'o';
                break;
            }
            if (choice.charAt(0)  == 'o'){
                computer_char = 'o';
                player_char = 'x';
                break;
            }
        }
        System.out.println("\nAlright, and how many seconds should it spend making each move?");
        int time_limit;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            choice = br.readLine();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        time_limit = Integer.parseInt(choice);
        System.out.println("\nGot it, each move will take, at most, "+time_limit+" seconds.\n");
    }
    
    /*
     * readPlayerMove()
     * prompts the user to input his/her move
     *     if invalid, reports so
     *     if valid, prints resulting board and confirms the choice before carrying through
     */
    public static void readPlayerMove(){
        int [] coordinates = root.findChar(player_char);
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
            System.out.println("Are you sure you want to move to (" + 
                               current_move[0] + ", " + 
                               current_move[1] + ")?");
            String answer = yesNo();
            if (answer.equals("n"))
                readPlayerMove();
            else{
                Point to_check = new Point(current_move[0]-board_index, current_move[1]-board_index);
                if (root.getValidMoves().contains(to_check)){
                    root.setState((current_move[0]-board_index),(current_move[1]-board_index),player_char);
                    root.setState(coordinates[0], coordinates[1], '*');
                }
                else {
                   System.out.println("\nSorry, but that's an invalid move. I told you I'd win, sucka.\n");
                   System.exit(0);
                } 
            }
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
                System.out.println("Sorry, that was a yes/no question." + 
                                   "('y' to answer 'yes')\n\t" +
                                   "('n' to answer 'no')");
            }
            else return answer;
        }
    }
    
    /*
     * computerMove()
     * administers the computer's move
     * returns: 
     * args: 
     */
    public static void computerMove(){
        long start_time;
        System.out.println("\n\nAlpha-Beta Score: " + alphaBetaSearch(root, 3));
    }

    /*
     * alphaBetaSearch(Node root, int alpha, int beta)
     * returns the best move for the root Node
     * based on the alpha-beta search algorithm
     * returns: int[]
     * args: Node, char, char
     */
    public static int alphaBetaSearch(Node root, int depth_limit){
        System.out.println("DEBUG: in alphabeta");
        int score = maxValue(root, neg_infinity, infinity, depth_limit);
        return score;
    }

    /*
     * maxValue(Node node, int alpha, int beta, int depth_limit)
     */
    public static int maxValue(Node node, int alpha, int beta, int depth_limit){
        if (node.getDepth() >= depth_limit || node.getValidMoves().size() == 0)
            return node.evaluate();
        
        System.out.println("DEBUG: finished terminal check");
        int value = neg_infinity;
        Iterator itr = node.getValidMoves().iterator();
        while(itr.hasNext()){
        System.out.println("DEBUG: iterating");
            Point move = (Point)itr.next();

        System.out.println("DEBUG: Point: (" + (int)move.getX() + " " + (int)move.getY() + ")");
            Node child = new Node(node, move, node.getTurn());
            value = Math.max(value, minValue(child, alpha, beta, depth_limit));
            if (value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    /*
     * minValue(Node node, int alpha, int beta, int depth_limit)
     */
    public static int minValue(Node node, int alpha, int beta, int depth_limit){
         if (node.getDepth() >= depth_limit || node.getValidMoves().size() == 0)
            return node.evaluate();
         int value = infinity;
        Iterator itr = node.getValidMoves().iterator();
        while(itr.hasNext()){
            Point move = (Point)itr.next();
            Node child = new Node(node, move, node.getTurn());
            value = Math.min(value, minValue(child, alpha, beta, depth_limit));
            if (value >= beta)
                return value;
            alpha = Math.min(beta, value);
        }
        return value;
    }




    
    /*
     * main function
     * administers the program
     */
    public static void main(String [] args){
        fillBoard();
        root.setState(board);
        intro();
        if (computer_char == 'x')
            computer_turn = true;
        else computer_turn = false;
        while(true){
            root.clearValidMoves();
            if(computer_turn){
                root.validMoves = root.setValidMoves();
                //root.setValidMoves(computer_char);
                computerMove();
                root.printState();
                root.setState(board);
            }
            else{
                //root.setValidMoves(player_char);
                root.validMoves = root.setValidMoves();
                readPlayerMove();
                root.printState();
                if ((current_move[0] == 0) && (current_move[1] == 0))
                    break;
            }
            computer_turn = !computer_turn;
        }
        System.out.println();
    }
} 
