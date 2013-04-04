/*
 * Node.java
 * ------------
 * Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Project 2: Comparison of Alternative Search Algorithms
 *            (using The 15 Puzzle)
 * ------------
 * Handles nodes related to the computer player's move search tree.
 */

package isolation;

import java.awt.Point;
import java.util.Vector;
import java.util.Iterator;
import java.lang.Math;

public class Node implements Comparable{
    
    /* constants */
    public static int puzzle_size = 8;   // width of board

    /* class attributes */
    private Node parent;
    private char[][] state;
    private char turn;
    private int depth;
    public Vector<Point> validMoves;

    /*
     * initial constructor
     * returns: Node
     * args: NA
     */
    public Node(){
        this.parent = null;
        this.state = new char[puzzle_size][puzzle_size];
        this.turn = 'x';
        this.depth = 0;
        this.validMoves = new Vector<Point>();
    }
    
    /* 
     * general constructor
     * returns: Node
     * args: Node, Point, Player
     */
    public Node(Node parent, Point move_to, char val){
        this.parent = parent;
        //this.state = parent.state;
        this.state = new char[puzzle_size][puzzle_size];
        for (int i = 0; i<puzzle_size; i++){
            for (int j = 0; j<puzzle_size; j++)
                this.state[i][j] = parent.state[i][j];
        }
        int [] old_coordinates = this.findChar(val);
        this.setState((int)move_to.getX(), (int)move_to.getY(), val);
        this.setState(old_coordinates[0], old_coordinates[1], '*');
        if (val == 'x')
            this.turn = 'o';
        else 
            this.turn = 'x';
        this.depth = parent.depth + 1;
        this.validMoves = this.setValidMoves();
    }
    
    /*
     * getParent()
     * returns the node's parent
     * returns: Node
     * args: NA
     */
    public Node getParent(){
        return this.parent;
    }
    
    /* 
     * getState() 
     * returns this node's state
     * returns: char[][] 
     * args: NA
     */
    public char[][] getState(){
        return this.state;
    }

    /* 
     * getTurn() 
     * returns this node's turn
     * returns: char
     * args: NA
     */
    public char getTurn(){
        return this.turn;
    }

    /*
     * getDepth()
     * returns this node's depth
     * returns: int 
     * args: NA
     */
    public int getDepth(){
        return this.depth;
    }
    
    /*
     * getValidMoves()
     * returns this node's validMoves vector
     * returns: Vector<Point> 
     * args: NA
     */
    public Vector<Point> getValidMoves(){
        return this.validMoves;
    }

    /*
     * setParent(Node other)
     * sets this node's parent to the one specified
     * returns: NA
     * args: NA
     */
    public void setParent(Node other){
        this.parent = other;
    }

    /*
     * setState(char[][] that_state)
     * sets this node's state to the one specified
     * returns: NA
     * args: char[][]
     */
    public void setState(char[][] that_state){
        this.state = that_state;
    }

    /* 
     * setState(int i, int j, char val)
     * assigns a value to a specific state cell w/coordinates (i,j)
     * returns: NA 
     * args: int, int, char
     */
    public void setState(int i, int j, char val){
        this.state[i][j] = val;
    }

    /*
     * setDepth(int d)
     * assigns the depth d to the current node
     * returns: NA
     * args: int
     */
    public void setDepth(int d){
        this.depth = d;
    }

    /*
     * clearValidMoves()
     * clears the validMoves vector
     * returns: NA
     * args: NA
     */
   public void clearValidMoves(){
        this.validMoves = new Vector<Point>();
   }

    
    /*
     * setValidMoves(char current_char)
     * sets the validMoves Vector with appropriate values;
     * returns: NA
     * args: NA
     */
    public Vector<Point> setValidMoves(){
        int limit; // to be used in diagonals
        Vector<Point> valid_moves = new Vector<Point>();
        int [] coordinates = findChar(this.turn); // location of current player's character
        /* explore in all directions, adding moves as far as you can */
        // up
        for (int index = coordinates[0] + 1; index < puzzle_size; index++){
            if (this.state[index][coordinates[1]] == '-')
                valid_moves.add(new Point(index, coordinates[1]));
            else
                break;
        }
        // down
        for (int index = coordinates[0] - 1; index >= 0; index--){
            if (this.state[index][coordinates[1]] == '-'){

        System.out.println("DEBUG: before");
                valid_moves.add(new Point(index, coordinates[1]));
        System.out.println("DEBUG: after");
            }
            else
                break;
        }
        // left
        for (int index = coordinates[1] + 1; index < puzzle_size; index++){
            if (this.state[coordinates[0]][index] == '-')
                valid_moves.add(new Point(coordinates[0], index));
            else
                break;
        }
        // right
        for (int index = coordinates[1] - 1; index >= 0; index--){
            if (this.state[coordinates[0]][index] == '-')
                valid_moves.add(new Point(coordinates[0], index));
            else
                break;
        }
        // up/left
        if (coordinates[0] > coordinates[1])
            limit = coordinates[1];
        else limit = coordinates[0];
        for (int index = 1; index <= limit; index++){
            if (this.state[coordinates[0]-index][coordinates[1]-index] == '-'){
                valid_moves.add(new Point((coordinates[0]-index), (coordinates[1]-index)));
            }
            else
                break;
        }
        // up/right
        if (coordinates[0] > puzzle_size - coordinates[1] - 1)
            limit = puzzle_size - coordinates[1] - 1;
        else limit = coordinates[0];
        for (int index = 1; index <= limit; index++){
            if (this.state[coordinates[0]-index][coordinates[1]+index] == '-'){
                valid_moves.add(new Point((coordinates[0]-index), (coordinates[1]+index)));
            }
            else
                break;
        }
        
        // down/left
        if (puzzle_size - coordinates[0] - 1 < coordinates[1])
            limit = puzzle_size - coordinates[0] - 1;
        else limit = coordinates[1];
        for (int index = 1; index <= limit; index++){
            if (this.state[coordinates[0]+index][coordinates[1]-index] == '-'){
                valid_moves.add(new Point((coordinates[0]+index), (coordinates[1]-index)));
            }
            else
                break;
        }
        // down/right
        if (puzzle_size - coordinates[0] - 1 < puzzle_size - coordinates[1] - 1)
            limit = puzzle_size - coordinates[0] - 1;
        else limit = puzzle_size - coordinates[1] - 1;
        for (int index = 1; index <= limit; index++){
            if (this.state[coordinates[0]+index][coordinates[1]+index] == '-'){
                valid_moves.add(new Point((coordinates[0]+index), (coordinates[1]+index)));
            }
            else
                break;
        }
        return valid_moves;
    }

   /*
    * findChar(char current)
    * returns the coordinates of current_char in this.state
    * returns: int[2]
    * args: char
    */
    public int[] findChar(char current_char){
        int[] coordinates = new int[2];
        // find the char
        for (int i = 0; i<puzzle_size; i++){
            for (int j = 0; j<puzzle_size; j++){
                if (this.state[i][j] == current_char){
                    coordinates[0] = i;
                    coordinates[1] = j;
                }
            }
        }
        return coordinates;
    } 
     
    /*
     * generateSuccessor(Point newSpace)
     * builds a child node 
     * returns: 
     * args: 
     */
    public static void generateChildren(){
        System.out.println("Iterate across validMoves and construct new Node for each.");
    }
   
    /* 
     * compareTo()
     * returns 0 if two nodes' states are identical
     * returns 1 otherwise
     * returns: 
     * args: 
     */
    public int compareTo(Object other){
        char[][] thisState = this.getState();
        char[][] thatState = ((Node)other).getState();
        int length = this.getState().length;
        for (int i = 0; i<length; i++){
            for (int j = 0; j<length; j++){
                if (!(thisState[i][j]==thatState[i][j]))
                    return 1;
            }
        }
        return 0;
    }

     /*
     * equals(Object other)
     * overrides the equals() method used in searching stacks
     * returns: true if states are the same, false otherwise
     * args: Node 
     */
    public boolean equals(Object other){
        if (this.compareTo(other)==0)
            return true;
        else
            return false;
    }  
    
    /*
     * printState()
     * prints a node's state in an appealing way
     * returns: NA
     * args: NA
     */
    public void printState(){
        for (int i = 0; i<puzzle_size; i++){
            System.out.println();
            for (int j = 0; j<puzzle_size; j++)
                System.out.print(String.valueOf(this.state[i][j]) + " ");
        }
        System.out.println();
    }
     
    /*
     * printValidMoves()
     * prints an aesthetically pleasing representation of a node's 
     * possible next spaces
     * returns: NA
     * args: NA
     */
    public void printValidMoves(){
        Iterator itr = this.validMoves.iterator();
        while(itr.hasNext()){
            Point next = (Point)itr.next();
            System.out.print("(" + (int)next.getX() + " " + (int)next.getY()+")");
        }
    }

    /*
     * evaluate()
     * evaluates the utility of a Node
     * returns: int
     * args: NA
     */
    public int evaluate(){
        return this.validMoves.size();
    }

    /*
     * isMax()
     * returns true if the current node is a max Node
     * returns: boolean
     * args: NA
     */
    public boolean isMax(){
        if (this.turn == Play.computer_char)
            return true;
        else return false;
    }

    /*
     * isMin()
     * returns true if the current node is a min Node
     * returns: boolean
     * args: NA
     */
    public boolean isMin(){
        if (this.turn == Play.player_char)
            return true;
        else return false;
    }

}
