package assignment1;

import java.util.Stack;

public class DFS {
    
    char[][] maze;
    //keeps track of which nodes are visited
    boolean[][] visited;
    int posX;
    int posY;
    int cost = 0;
    int nodes = 0;
    char space = " ".charAt(0);
    char dash = "-".charAt(0);
    char period = ".".charAt(0);
    char finish = "*".charAt(0);
    char start = "P".charAt(0);
    Node currentPosition;
    Stack<Integer> stackX = new Stack<>();
    Stack<Integer> stackY = new Stack<>();
    
    public DFS(char[][] input){
        maze = input;
        visited = new boolean[maze.length][maze[0].length];
        //visited array starts out with every value being false
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[0].length; j++){
                //set all the values in the visited array to false
                visited[i][j] = false;
                //find starting point
                if(maze[i][j] == 'P'){
                    posX = i;
                    posY = j;
                }
            }
        }
        //make the current position into a node
        currentPosition = new Node(posX, posY);
        //put starting point on the stack
        stackX.push(currentPosition.x);
        stackY.push(currentPosition.y);
        //visit starting position
        visit(currentPosition);
    }
    
    //the main method called to traverse the maze
    public void traverseMaze(){
        while(!solved()){
            move();
        }
        printSolution(maze);
    }
    
    //checks each direction and moves the current position to it if one is found
    //otherwise backtracks
    public void move(){
        //determine what to do next
        //if position above is available
        if(checkUp()){
            moveUp();
            cost++;
        }
        //if position below is available
        else if(checkDown()){
            moveDown();
            cost++;
        }
        //if position left is available
        else if(checkLeft()){
            moveLeft();
            cost++;
        }
        //if position right is available
        else if(checkRight()){
            moveRight();
            cost++;
        }
        //else pop current position off stack
        else{
            popFromStack();
            //decrement the cost if backtracking
            cost--;
        }
    }
    
    //checks if there is a possible move
    public boolean moveIsAvailable(){
        if(checkUp()){
            return true;
        }
        else if(checkDown()){
            return true;
        }
        else if(checkLeft()){
            return true;
        }
        else if(checkRight()){
            return true;
        }
        else{
            return false;
        }
    }
    
    //goal test
    public boolean solved(){
        //check up
        if(maze[posX-1][posY]==finish){
            return true;
        }
        //check down
        else if(maze[posX+1][posY]==finish){
            return true;
        }
        //check left
        else if(maze[posX][posY-1]==finish){
            return true;
        }
        //check right
        else if(maze[posX][posY+1]==finish){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void moveUp(){
        //change position
        posX--;
        //update the current position
        update(currentPosition);
        //push position onto stack
        addToStack();
        //mark as visited
        visit(currentPosition);
    }
    
    public void moveDown(){
        //change position
        posX++;
        //update the current position
        update(currentPosition);
        //push position onto stack
        addToStack();
        //mark as visited
        visit(currentPosition);
    }
    
    public void moveLeft(){
        posY--;
        //update the current position
        update(currentPosition);
        //push position onto stack
        addToStack();
        //mark as visited
        visit(currentPosition);
    }
    
    public void moveRight(){
        //change position
        posY++;
        //update the current position
        update(currentPosition);
        //push position onto stack
        addToStack();
        //mark as visited
        visit(currentPosition);
    }
    
    //check if surrounding nodes are visited
    //make sure the character is a dash
    public boolean checkUp(){
        return (!visited[posX-1][posY])&&(maze[posX-1][posY]==dash);
    }
    public boolean checkDown(){
        return (!visited[posX+1][posY])&&(maze[posX+1][posY]==dash);
    }
    public boolean checkLeft(){
        return (!visited[posX][posY-1])&&(maze[posX][posY-1]==dash);
    }
    public boolean checkRight(){
        return (!visited[posX][posY+1])&&(maze[posX][posY+1]==dash);
    }
    
    //mark current position as visited and change it's character to a period
    public void visit(Node currentPosition){
        visited[currentPosition.x][currentPosition.y] = true;
        if(maze[currentPosition.x][currentPosition.y] != "P".charAt(0)){
            maze[currentPosition.x][currentPosition.y] = period;
        }
    }
    
    //update the position object with new coordinates
    public void update(Node position){
        position.x = posX;
        position.y = posY;
    }
    
    //pop a position from the stack
    public void popFromStack(){
        stackX.pop();
        stackY.pop();
        //change to that position
        posX = stackX.peek();
        posY = stackY.peek();
        //change the current node
        update(currentPosition);
    }
    
    //add a position to the stack
    public void addToStack(){
        stackX.push(currentPosition.x);
        stackY.push(currentPosition.y);
    }
    
    //print the final maze
    public void printSolution(char[][] maze){
        for(int i = 0; i < maze.length; i++){
            //make a string for the row
            String row = "";
            for(int j = 0; j < maze[0].length; j++){
                char c=maze[i][j];
                //changes the dashes back to spaces
                if(c==dash){
                    row+=Character.toString(space);
                }
                else if(c==start||c==finish||c==period){
                    row+=Character.toString(c);
                    nodes++;
                }
                else{
                    row+=Character.toString(c);
                }
            }
            //print out row
            System.out.println(row);
        }
        System.out.println("Path cost: " + cost + " steps.");
        System.out.println("Number of nodes expanded: " + nodes + " nodes.");
    }
}
