package assignment1;

import java.io.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                InputStream input;

                if (args[1].equals("-")) {
                    input = System.in;
                }
                else {
                    input = new FileInputStream(args[1]);
                }

                char[][] maze = readFile(input);
                printMaze(maze);

                Searcher searcher;
                switch (args[0]) {
                    case "dfs":
                        searcher = new DFS(maze);
                        break;

                    case "greedy":
                        searcher = new GFS(maze);
                        break;

                    case "astar":
                    case "a*":
                        searcher = new AStar(maze);
                        break;
                    case "bfs":
                        searcher = new BFS(maze);
                        break;

                    default:
                        throw new IllegalArgumentException("Must be dfs/bfs/greedy/astar");
                }

                searcher.traverseMaze();
            }
            catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        }
        else {
            System.err.println("Usage: java assignment1.Main METHOD MAZE_FILE");
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
