package assignment1;

import java.util.*;

public class AStar {
    private Tile[][] maze;
    public char[][] result;
    private Tile start = null;
    private Tile end = null;

    public int expanded;
    public int pathCost;

    public AStar(char[][] _maze){
        maze = new Tile[_maze.length][];
        result = deepCopy(_maze);

        /* build up the maze and result objects */

        for (int r = 0; r < result.length; ++r) {
            maze[r] = new Tile[result[r].length];

            for (int c = 0; c < result[r].length; ++c) {
                /* change the maze characters to make them easier to see */
                switch (result[r][c]) {
                    case '%':
                        result[r][c] = Tile.WALL;
                        break;

                    case '-':
                        result[r][c] = Tile.SPACE;
                        break;
                }

                maze[r][c] = new Tile(r, c, result[r][c]);
            }
        }



        /* find our start point and our end point */

        for (int r = 0; r < maze.length; ++r) {
            for (int c = 0; c < maze[r].length; ++c) {
                char symbol = maze[r][c].symbol;

                if (symbol == Tile.START) {
                    if (start != null) {
                        throw new IllegalStateException("Start already found");
                    }

                    start = maze[r][c];
                }
                else if (symbol == Tile.END) {
                    if (end != null) {
                        throw new IllegalStateException("Start already found");
                    }

                    end = maze[r][c];
                }
            }
        }

        if (start == null) {
            throw new IllegalStateException("Start not found");
        }

        if (end == null) {
            throw new IllegalStateException("Start not found");
        }
    }

    public void traverseMaze() throws IllegalStateException {
        expanded = 0;

        PriorityQueue<Tile> queue = new PriorityQueue<>(new Comparator<Tile>() {
            public int compare(Tile a, Tile b) {
                return Integer.compare(a.dist(end) + a.cost, b.dist(end) + b.cost);
            }
        });
        start.cost = 0;
        queue.offer(start);

        while (!queue.isEmpty()) {
            Tile tile = queue.poll();

            if (tile.symbol != Tile.SEARCHED) {
                ++expanded;

                if (tile == end) {
                    break;
                }

                if (tile != start) {
                    tile.symbol = Tile.SEARCHED;
                }

                Tile[] candidateTiles = {
                    maze[tile.r-1][tile.c],
                    maze[tile.r+1][tile.c],
                    maze[tile.r][tile.c-1],
                    maze[tile.r][tile.c+1],
                };

                for (Tile candidate : candidateTiles) {
                    switch (candidate.symbol) {
                        case Tile.WALL:
                        case Tile.SEARCHED:
                        case Tile.START:
                            /* Don't spread into these tiles */
                            break;

                        default:
                            /* Do spread into other things */
                            candidate.symbol = Tile.FRONTER;

                            int newCost = tile.cost + 1;
                            if (newCost < candidate.cost) {
                                candidate.cost = newCost;
                                candidate.prev = tile;
                            }

                            queue.offer(candidate);
                            break;
                    }
                }
            }
        }



        /* build up the result */

        pathCost = end.cost;
        for (Tile path = end; path != null; path = path.prev) {
            if (result[path.r][path.c] == Tile.SPACE) {
                result[path.r][path.c] = Tile.PATH;
            }
        }
    }

    public char[][] deepCopy(char[][] original) {
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
        public char symbol;
        public Tile prev;
        public int cost;

        static final char WALL     = '█';
        static final char FRONTER  = '▒';
        static final char SEARCHED = '░';
        static final char SPACE    = ' ';
        static final char START    = 'P';
        static final char END      = '*';
        static final char PATH     = '⋅';

        public Tile(int r, int c, char symbol) {
            this.r = r;
            this.c = c;
            this.symbol = symbol;
            this.prev = null;
            this.cost = Integer.MAX_VALUE;
        }

        public int dist(Tile that) {
            return Math.abs(this.r - that.r) + Math.abs(this.c - that.c);
        }

        public int compareTo(Tile that) {
            if (this.r != that.r) {
                return Integer.compare(this.r, that.r);
            }
            else {
                return Integer.compare(this.c, that.c);
            }
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

        @Override
        public int hashCode() {
            /* TODO: better hash function! */
            return 19*this.r + 23*this.c;
        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;

            if (obj != null && Tile.class.isAssignableFrom(obj.getClass())) {
                final Tile that = (Tile) obj;

                result = (this.r == that.r && this.c == that.c);
            }

            return result;

        }
    }
}
