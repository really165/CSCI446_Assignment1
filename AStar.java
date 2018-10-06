package assignment1;

import java.util.*;

public class AStar {
    static Tile start = null;
    static Tile end = null;

    public static char[][] search(char[][] _maze) {
        char[][] maze = deepCopy(_maze);

        /* Step 0: reformat the maze for my entertainment */
        for (int r = 0; r < maze.length; ++r) {
            for (int c = 0; c < maze[r].length; ++c) {
                switch (maze[r][c]) {
                    case '%':
                        maze[r][c] = Tile.WALL;
                        break;

                    case '-':
                        maze[r][c] = Tile.SPACE;
                        break;
                }
            }
        }

        char[][] result = deepCopy(maze);

        /* Step 1: find our start point and our end point */
        for (int r = 0; r < maze.length; ++r) {
            for (int c = 0; c < maze[r].length; ++c) {
                char cell = maze[r][c];

                if (cell == Tile.START) {
                    assert start == null;
                    start = new Tile(r,c);
                }
                else if (cell == Tile.END) {
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

            if (maze[tile.r][tile.c] != Tile.SEARCHED) {
                if (maze[tile.r][tile.c] != Tile.START) {
                    maze[tile.r][tile.c] = Tile.SEARCHED;
                }

                if (tile.r == end.r && tile.c == end.c) {
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
                        switch (maze[candidate.r][candidate.c]) {
                            case Tile.WALL:
                            case Tile.SEARCHED:
                            case Tile.START:
                                /* Don't spread into these tiles */
                                break;

                            default:
                                /* Do spread into these */
                                maze[candidate.r][candidate.c] = Tile.FRONTER;
                                queue.offer(candidate);
                                break;
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
            if (result[path.r][path.c] == Tile.SPACE) {
                result[path.r][path.c] = Tile.PATH;
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
        public final int cost;

        static final char WALL     = '█';
        static final char FRONTER  = '▒';
        static final char SEARCHED = '░';
        static final char SPACE    = ' ';
        static final char START    = 'P';
        static final char END      = '*';
        static final char PATH     = '⋅';

        public Tile(int r, int c) {
            this.r = r;
            this.c = c;
            this.prev = null;
            this.cost = 0;
        }

        public Tile(int r, int c, Tile prev) {
            assert prev != null;

            this.r = r;
            this.c = c;
            this.prev = prev;
            this.cost = prev.cost + 1;
        }

        public int dist() {
            return Math.abs(this.r - end.r) + Math.abs(this.c - end.c);
        }

        public int compareTo(Tile that) {
            return Integer.compare(this.dist() + this.cost, that.dist() + that.cost);
        }

        public void print() {
            System.out.printf("[%d][%d] (%d) -> ", this.r, this.c, this.cost);
            if (this.prev != null) {
                System.out.printf("[%d][%d] (%d)\n", this.prev.r, this.prev.c, this.prev.cost);
            }
            else {
                System.out.printf(" null\n");
            }
        }
    }
}
