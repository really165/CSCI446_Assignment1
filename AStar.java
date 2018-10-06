package assignment1;

import java.util.*;

public class AStar {
    static Tile start = null;
    static Tile end = null;

    static final char WALL     = '█';
    static final char FRONTER  = '▒';
    static final char SEARCHED = '░';
    static final char SPACE    = ' ';

    public static char[][] search(char[][] _maze) {
        char[][] maze = deepCopy(_maze);

        /* Step 0: reformat the maze for my entertainment */
        for (int r = 0; r < maze.length; ++r) {
            for (int c = 0; c < maze[r].length; ++c) {
                char symbol = maze[r][c];

                switch (symbol) {
                    case '%':
                        symbol = WALL;
                        break;

                    case '-':
                        symbol = SPACE;
                        break;
                }

                maze[r][c] = symbol;
            }
        }

        char[][] result = deepCopy(maze);

        /* Step 1: find our start point and our end point */
        for (int r = 0; r < maze.length; ++r) {
            for (int c = 0; c < maze[r].length; ++c) {
                char cell = maze[r][c];

                if (cell == 'P') {
                    assert start == null;
                    start = new Tile(r,c);
                }
                else if (cell == '*') {
                    assert end == null;
                    end = new Tile(r,c);
                }
            }
        }

        assert start != null && end != null;

        /* Step 2: run the algorithm */
        PriorityQueue<Tile> queue = new PriorityQueue<>();
        queue.offer(start);

        while (queue.peek() != null) {
            Tile tile = queue.poll();

            if (maze[tile.r][tile.c] != SEARCHED) {
                maze[tile.r][tile.c] = SEARCHED;

                if (tile.r == end.r && tile.c == end.c) {
                    System.out.println("We gucci.");
                    end = tile;
                    break;
                }
                else {
                    Tile[] directionsToCheck = {
                        new Tile(tile.r-1, tile.c, tile),
                        new Tile(tile.r+1, tile.c, tile),
                        new Tile(tile.r, tile.c-1, tile),
                        new Tile(tile.r, tile.c+1, tile),
                    };

                    for (Tile candidate : directionsToCheck) {
                        if (maze[candidate.r][candidate.c] != WALL && maze[candidate.r][candidate.c] != SEARCHED) {
                            maze[candidate.r][candidate.c] = FRONTER; /* mark as being on the fronter */
                            queue.offer(candidate);
                        }
                    }

                    try {
                        System.in.read();
                    }
                    catch (Exception e) {
                    }

                    Main.printMaze(maze);
                }
            }
        }
        /* NOTE: what happens if we find don't break from this loop? */

        /* Step 3: profit */
        for (Tile path = end; path != null; path = path.prev) {
            if (result[path.r][path.c] == SPACE) {
                result[path.r][path.c] = '⋅';
            }
        }

        return result;
    }

    public static char[][] deepCopy(char[][] original) {
        if (original == null) {
            return null;
        }

        char[][] result = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }

        return result;
    }

    private static class Tile implements Comparable<Tile> {
        public final int r;
        public final int c;
        public final Tile prev;
        public final int step;

        public Tile(int r, int c) {
            this.r = r;
            this.c = c;
            this.prev = null;
            this.step = 0;
        }

        public Tile(int r, int c, Tile prev) {
            assert prev != null;

            this.r = r;
            this.c = c;
            this.prev = prev;
            this.step = prev.step + 1;
        }

        public int compareTo(Tile that) {
            int thisdist = Math.abs(this.r - end.r) + Math.abs(this.c - end.c);
            int thatdist = Math.abs(that.r - end.r) + Math.abs(that.c - end.c);

            return Integer.compare(thisdist + this.step, thatdist + that.step);
        }

        public void print() {
            System.out.printf("[%d][%d] (%d) -> ", this.r, this.c, this.step);
            if (this.prev != null) {
                System.out.printf("[%d][%d] (%d)\n", this.prev.r, this.prev.c, this.prev.step);
            }
            else {
                System.out.printf(" null\n");
            }
        }
    }
}
