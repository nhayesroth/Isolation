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
    private Vector<Point> validMoves;

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
    public Node(Node parent, Point moveTo, char val){
        this.parent = parent;
        this.state = parent.state;
        this.setState((int)moveTo.getX(), (int)moveTo.getY(), val);
        if (parent.turn == 'x')
            this.turn = 'o';
        else 
            this.turn = 'x';
        this.depth = parent.depth + 1;
        this.validMoves = this.getValidMoves();
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
    public void setValidMoves(char current_char){
        int limit; // to be used in diagonals
        int [] coordinates = findChar(current_char); // location of current player's character
        /* explore in all directions, adding moves as far as you can */
        // up
        for (int index = coordinates[0] + 1; index < puzzle_size; index++){
            if (this.state[index][coordinates[1]] == '-')
                this.validMoves.add(new Point(index, coordinates[1]));
            else
                break;
        }
        // down
        for (int index = coordinates[0] - 1; index >= 0; index--){
            if (this.state[index][coordinates[1]] == '-')
                this.validMoves.add(new Point(index, coordinates[1]));
            else
                break;
        }
        // left
        for (int index = coordinates[1] + 1; index < puzzle_size; index++){
            if (this.state[coordinates[0]][index] == '-')
                this.validMoves.add(new Point(coordinates[0], index));
            else
                break;
        }
        // right
        for (int index = coordinates[1] - 1; index >= 0; index--){
            if (this.state[coordinates[0]][index] == '-')
                this.validMoves.add(new Point(coordinates[0], index));
            else
                break;
        }
        // up/left
        if (coordinates[0] > coordinates[1])
            limit = coordinates[1];
        else limit = coordinates[0];
        System.out.println("coordinates0: " + coordinates[0]);
        System.out.println("coordinates1: " + coordinates[1]);
        System.out.println("limit: " + limit);

        for (int index = 1; index < limit; index++){
            System.out.println("I'm about to die!!!!");
            if (this.state[coordinates[0]-index][coordinates[1]-index] == '-'){
                this.validMoves.add(new Point((coordinates[0]-index), (coordinates[1]-index)));
                System.out.println("DEBUG: up/left added");
            }
            else
                break;
        }
        // up/right

        // down/left

        // down/right

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
     * buildNextNode(Point newSpace)
     * builds a child node 
     * returns: 
     * args: 
     */
    public static Node buildNextNode(Node Parent, Point newSpace){
        System.out.println("buildNextNode in progress");
        //start with a blank node
        Node child = new Node();
        
        //point its parent to the root node
        child.parent = Parent;
        
        //copy the parent's state
                
        //add 1 to the depth
        child.depth = Parent.depth + 1;
        
        //find value in position of newSpace, replace with zero
               
        // find previous space, replace with value
                
        return child;
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
     * checkMoveValidity(char current_char, int[] move)
     * checks whether the move entered is valid
     * returns: boolean, true if valid, false otherwise
     * args: char, int[]
     */
    public static boolean checkMoveValidity(char current_char, int[] move){
        boolean validity = false;

        return validity;
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
        System.out.print("Next Spaces: ");
        while(itr.hasNext()){
            Point next = (Point)itr.next();
            System.out.print("(" + (int)next.getX() + " " + (int)next.getY()+")");
        }
    }
}
