package assignment1;

public class AStar {
    public static char[][] search(char[][] maze) {
        Node start = null;
        Node end = null;

        char[][] result = maze.clone();

        /* Step one: find our start point and our end point */
        for (int y = 0; y < maze.length; ++y) {
            for (int x = 0; x < maze[y].length; ++x) {
                char cell = maze[y][x];

                if (cell == 'P') {
                    assert start == null;
                    start = new Node(x,y);
                }
                else if (cell == '*') {
                    assert end == null;
                    end = new Node(x,y);
                }
            }
        }

        /* Step 2: ??? */

        /* Step 3: profit */
        return result;
    }
}
