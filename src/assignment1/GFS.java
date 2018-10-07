package assignment1;

import java.util.Random;
import java.util.Stack;

import com.sun.javafx.scene.traversal.TopMostTraversalEngine;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TopLevelAttribute;

public class GFS {
    char[][] maze;
    boolean[][] visited;
    int posX;
    int posY;
    int endX;
    int endY;
    int manhat;
    int cost = 0;
    char dash = "-".charAt(0);
    Stack<Node> stack = new Stack<>();
    Stack<Node> output = new Stack<>();
    boolean foundEnd = false; //used to see if we found the end

    public GFS(char[][] input) {
        maze = input;
        visited = new boolean[maze.length][maze[0].length];
        //visited array starts out with every value being false
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                //set all the values in the visited array to false
                visited[i][j] = false;
                //find starting point
                if (maze[i][j] == 'P') {
                    posX = i;
                    posY = j;
                } else if (maze[i][j] == '*') //getting the values of the end point for manhatten distance
                {
                    endX = i;
                    endY = j;
                }
            }
        }
    }

    //lifted from the main
    public static void printMaze(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            //make a string for the row
            String row = "";
            for (int j = 0; j < maze[0].length; j++) {
                char c = maze[i][j];
                row += Character.toString(c);
            }
            //print out row
            System.out.println(row);
        }
    }


    public void updatePosition(int x, int y) //updating the posX and y values
    {
        //updaging the x and Y
        posX = x;
        posY = y;
    }


    public int get_manHat(int x, int y) //getting manhattan distance
    {
        //get the distance to the final char
        int manDist = Math.abs(x - endX) + Math.abs(y - endY);
        return manDist;
    }

    public void printStack() //printing out the stack too see what "nodes" x,y pairs we visited
    {
        System.out.println("Nodes Visited Are: ");
        stack.forEach(k -> {
            System.out.println(k.x + " | " + k.y);
        });

    }

    public void traverseMaze() {

        //start loop of traversing the maze here (setting to star char)
        char endChar = 'P';
        //pushing the first node onto the stack
        Node startingPosition = new Node(posX, posY);
        stack.push(startingPosition);
        Node bestNeighbor = new Node(0, 0); //starting at null values
        //values to hold old x and y
        int oldX = 0, oldY = 0;

        //loop go until we get out endchar from our bestNeibor NOTE:: This will get stuck as greedy is not complete
        while (foundEnd == false) {
            //call to check the best neighbors and view their different values
            //update our char to be equal to our current position
            endChar = maze[posX][posY];
            bestNeighbor = checkNeighbors();
            if (endChar == '*') {
                foundEnd = true;
            }
            //set current value to '.' char
            maze[posX][posY] = '.';
            //save off old position to check if we are still in the same spot after an update
            oldX = posX;
            oldY = posY;
            //update our position using values in node holding the coords of the best neighbor and push bestneighbor onto stack
            updatePosition(bestNeighbor.x, bestNeighbor.y);
            stack.push(bestNeighbor);
            cost++;

            //if we are still in the same spot we are stuck and better break out
            if (oldX == posX && oldY == posY) {
                break;
            }

        }

        printMaze(maze);
        System.out.println("Cost = " + cost);
        printStack();
    }


    public Node checkNeighbors() {
        Node bestNeighbor = new Node(0, 0); //creating a node to store the best neighbor
        //NOTE:: This does not do backtracking as greedy is not complete and can get stuck in the same vals
        //4 varibles to hold manhattan distance for each neighbor in the array
        int topMan = 0, botMan = 0, leftMan = 0, rightMan = 0;


        //did not use a switch due to comparing the differing positions of the maze to '%' for a wall
        //and setting to -1 if a wall character

        //get manhat of top node if node is not a % (else set to INTERGER.MAXVALUE)
        if (maze[posX - 1][posY] != '%' && maze[posX - 1][posY] != '.') {
            topMan = get_manHat(posX - 1, posY);  //getting manhattan distance from node above us to end
        } else {
            topMan = Integer.MAX_VALUE;
        }
        //get manhat of bottom node if node is not a %
        if (maze[posX + 1][posY] != '%' && maze[posX + 1][posY] != '.') {
            botMan = get_manHat(posX + 1, posY);  //getting manhattan distance from node above us to end
        } else {
            botMan = Integer.MAX_VALUE;
        }
        //get manhat of left node if node is not a %
        if (maze[posX][posY - 1] != '%' && maze[posX][posY - 1] != '.') {
            leftMan = get_manHat(posX, posY - 1);  //getting manhattan distance from node above us to end
        } else {
            leftMan = Integer.MAX_VALUE;
        }
        //get manhat of right node if node is not a %
        if (maze[posX][posY + 1] != '%' && maze[posX][posY + 1] != '.') {
            rightMan = get_manHat(posX, posY + 1);  //getting manhattan distance from node above us to end
        } else {
            rightMan = Integer.MAX_VALUE;
        }

        //if we get the event of two being even we need to find out which 2 are even and then randomly choose
        //randomly decide top or other

        if (topMan == botMan) {
            int ran = new Random().nextInt(1);
            if (ran == 0) {
                botMan = Integer.MAX_VALUE;
            } else {
                topMan = Integer.MAX_VALUE;
            }
        } else if (topMan == leftMan) {
            int ran = new Random().nextInt(1);
            if (ran == 0) {
                leftMan = Integer.MAX_VALUE;
            } else {
                topMan = Integer.MAX_VALUE;
            }
        } else if (topMan == rightMan) {
            int ran = new Random().nextInt(1);
            if (ran == 0) {
                rightMan = Integer.MAX_VALUE;
            } else {
                topMan = Integer.MAX_VALUE;
            }
        }
        //randomly decide for bot
        if (botMan == leftMan) {
            int ran = new Random().nextInt(1);
            if (ran == 0) {
                leftMan = Integer.MAX_VALUE;
            } else {
                rightMan = Integer.MAX_VALUE;
            }
        } else if (botMan == rightMan) {
            int ran = new Random().nextInt(1);
            if (ran == 0) {
                rightMan = Integer.MAX_VALUE;
            } else {
                botMan = Integer.MAX_VALUE;
            }
        }
        if (leftMan == rightMan) {
            int ran = new Random().nextInt(1);
            if (ran == 0) {
                rightMan = Integer.MAX_VALUE;
            } else {
                leftMan = Integer.MAX_VALUE;
            }
        }

        //if they are all equal then we are stuck so set current pos to end
        if ((topMan == botMan) && (topMan == rightMan) && (topMan == leftMan)) {
            //set bool used to track end of loop to false
            foundEnd = true;
        }

        //end of dealing with equality


        //compare the 4 different neighbors values to the and choose the one with smallest manhat
        //using another if chain here to pass coords into the new Node (also Math.min is acutally quite complex)
        if ((topMan < botMan) && (topMan < leftMan) && (topMan < rightMan)) {
            //add pos for node above us
            bestNeighbor.x = posX - 1; //posY stays the same
            bestNeighbor.y = posY;
        } else if ((botMan < topMan) && (botMan < leftMan) && (botMan < rightMan)) //if top was smaller we would have grabbed it
        {
            //add pos for node below us
            bestNeighbor.x = posX + 1; //posY stays the same
            bestNeighbor.y = posY;

        } else if ((leftMan < botMan) && (leftMan < topMan) && (leftMan < rightMan)) //if bottem was smaller than we would have caught it
        {
            //add pos for node left of us
            bestNeighbor.y = posY - 1; //posX stays the same
            bestNeighbor.x = posX;
        } else if ((rightMan < botMan) && (rightMan < leftMan) && (rightMan < topMan)) // go right!
        {
            //add pos for node right of us
            bestNeighbor.y = posY + 1; //posX stays the same
            bestNeighbor.x = posX;
        }

        //returning a node containing the best fitting x and y values
        return bestNeighbor;
    }


}
