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
    char dash = "-".charAt(0);
    char period = ".".charAt(0);
    char finish = "*".charAt(0);
    Node currentPosition;
    Stack<Integer> stackX = new Stack<>();
    Stack<Integer> stackY = new Stack<>();
    Stack<Integer> outputX = new Stack<>();
    Stack<Integer> outputY = new Stack<>();
    
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
        printMaze(maze);
    }
    
    public void traverseMaze(){
        while(!solved()){
            move();
        }
    }
    
    public void move(){
        //determine what to do next
        //if position above is available
        if(checkUp()){
            moveUp();
        }
        //if position below is available
        else if(checkDown()){
            moveDown();
        }
        //if position left is available
        else if(checkLeft()){
            moveLeft();
        }
        //if position right is available
        else if(checkRight()){
            moveRight();
        }
        //else pop current position off stack
        else{
            popFromStack();
            if(moveIsAvailable()){
                move();
            }
        }
        printMaze(maze);
    }
    
    public void backtrack(){
        if(maze[posX-1][posY]==period){
            
        }
    }
    
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
    
    public boolean solved(){
        //check up
        if(maze[posX-1][posY]==finish){
            System.out.println("solved");
            return true;
        }
        //check down
        else if(maze[posX+1][posY]==finish){
            System.out.println("solved");
            return true;
        }
        //check left
        else if(maze[posX][posY-1]==finish){
            System.out.println("solved");
            return true;
        }
        //check right
        else if(maze[posX][posY+1]==finish){
            System.out.println("solved");
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
        //add to output sequence
        addToOutput();
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
        //add to output sequence
        addToOutput();
        //mark as visited
        visit(currentPosition);
    }
    
    public void moveLeft(){
        posY--;
        //update the current position
        update(currentPosition);
        //push position onto stack
        addToStack();
        //add to output sequence
        addToOutput();
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
        //add to output sequence
        addToOutput();
        //mark as visited
        visit(currentPosition);
    }
    
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
    
    public void visit(Node currentPosition){
        visited[currentPosition.x][currentPosition.y] = true;
        maze[currentPosition.x][currentPosition.y] = period;
    }
    
    public void update(Node position){
        position.x = posX;
        position.y = posY;
    }
    
    public void popFromStack(){
        checkPosition();
        stackX.pop();
        stackY.pop();
        //change to that position
        posX = stackX.peek();
        posY = stackY.peek();
        //change the current node
        update(currentPosition);
        checkPosition();
    }
    
    public void addToStack(){
        stackX.push(currentPosition.x);
        stackY.push(currentPosition.y);
    }
    
    public void addToOutput(){
        outputX.push(currentPosition.x);
        outputY.push(currentPosition.y);
    }
    
    public void printMaze(char[][] maze){
        for(int i = 0; i < maze.length; i++){
            //make a string for the row
            String row = "";
            for(int j = 0; j < maze[0].length; j++){
                char c=maze[i][j];    
                row+=Character.toString(c);  
            }
            //print out row
            System.out.println(row);
        }
    }
    
    //for debugging
    public void checkPosition(){
        System.out.println("current position: (" + posX + ", " + posY + ")");
        System.out.println("has character " + maze[posX][posY]);
    }
}
