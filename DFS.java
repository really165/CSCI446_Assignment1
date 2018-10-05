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
    Stack<Node> stack = new Stack<>();
    Stack<Node> output = new Stack<>();
    
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
    }
    
    public void traverseMaze(){
        //put starting point on the stack
        Node startingPosition = new Node(posX, posY);
        stack.push(startingPosition);
        System.out.println("Next we will move " + nextMove());
    }
    
    public void moveUp(){
        posX--;
    }
    
    public void moveDown(){
        posX++;
    }
    
    public void moveLeft(){
        posY--;
    }
    
    public void moveRight(){
        posY++;
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
    
    public String nextMove(){
        String next;
        if(checkUp()){
            next = "up";
        }
        else if(checkDown()){
            next = "down";
        }
        else if(checkLeft()){
            next = "left";
        }
        else if(checkRight()){
            next = "right";
        }
        else{
            //no moves; must pop from the stack
            next = "pop";
        }
        return next;
    }
    
    //for debugging
    public void checkPosition(){
        System.out.println("current position: (" + posX + ", " + posY + ")");
        System.out.println("has character " + maze[posX][posY]);
    }
}
