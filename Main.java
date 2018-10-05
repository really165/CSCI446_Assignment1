package assignment1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                DFS depthFirstSearch = new DFS(readFile(args[0]));
                depthFirstSearch.traverseMaze();
            }
            catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        }
        else {
            System.err.println("Usage: java assignment1.Main MAZE_FILE");
        }
    }

    private static char[][] readFile(String path) throws FileNotFoundException {
        ArrayList<char[]> maze = new ArrayList<>();
        char[][] finalizedMaze = null;

        try {
            FileInputStream input = new FileInputStream(path);
            InputStreamReader stream = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(stream);

            while (reader.ready()) {
                String line = reader.readLine();
                maze.add(line.replaceAll(" ", "-").toCharArray());
            }

            reader.close();
            stream.close();
            input.close();

            finalizedMaze = maze.toArray(new char[0][0]);
            for(char[] row : finalizedMaze) {
                if (row == null) {
                    System.out.println("NULL!");
                }
                else {
                    System.out.println(row);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Fuck.");
        }

        return finalizedMaze;
    }
}
