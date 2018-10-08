package assignment1;

import java.io.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                InputStream input;

                if (args[0].equals("-")) {
                    input = System.in;
                }
                else {
                    input = new FileInputStream(args[0]);
                }

                char[][] maze = readFile(input);
                printMaze(maze);

                //AStar searcher = new AStar(maze);
                BFS searcher = new BFS(maze);
                searcher.traverseMaze();

                System.out.println("Resulting path:");
                printMaze(searcher.result);

                System.out.printf("path cost: %d\n", searcher.pathCost);
                System.out.printf("nodes expanded : %d\n", searcher.expanded);
            }
            catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        }
        else {
            System.err.println("Usage: java assignment1.Main MAZE_FILE");
        }
    }

    private static char[][] readFile(InputStream stream) throws FileNotFoundException {
        ArrayList<char[]> maze = new ArrayList<>();
        char[][] finalizedMaze = null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        try {
            while (reader.ready()) {
                String line = reader.readLine();
                maze.add(line.replaceAll(" ", "-").toCharArray());
            }

            finalizedMaze = maze.toArray(new char[0][0]);
        }
        catch (IOException e) {
            System.err.println("Fuck.");
        }

        return finalizedMaze;
    }

    public static void printMaze(char[][] maze) {
        for(char[] row : maze) {
            assert row != null;

            System.out.println(row);
        }
    }
}
