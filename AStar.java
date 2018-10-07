package assignment1;

import java.util.*;

public class AStar {
    Tile start = null;
    Tile end = null;

    public int expanded;
    public int pathCost;

    public char[][] search(char[][] _maze) {
        char[][] maze = deepCopy(_maze);

        expanded = 0;
        /* start pathCost at -1 so to not include the starting point */
        pathCost = -1;

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
        Comparator<Tile> ordering = new Comparator<Tile>() {
            public int compare(Tile a, Tile b) {
                return Integer.compare(a.dist(end) + a.cost, b.dist(end) + b.cost);
            }
        };

        //PriorityQueue<Tile> queue = new PriorityQueue<>(ordering);
        LinkedList<Tile> queue = new LinkedList<>();
        queue.offer(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            ++expanded;
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

                                Tile oldCandidate = candidate;
                                for (Tile other : queue) {
                                    if (candidate.r == other.r && candidate.c == other.c) {
                                        if (candidate.compareTo(other) > 0) {
                                            candidate = other;
                                        }
                                    }
                                }

                                while (queue.remove(candidate));

                                assert !queue.contains(candidate);

                                queue.offer(candidate);
                                queue.sort(ordering);
                                break;
                        }
                    }
                }

                try {
                    while (System.in.read() != (int)'\n');
                    Main.printMaze(maze);
                }
                catch (Exception e) {
                }
            }
        }
        /* NOTE: what happens if we find don't break from this loop? */

        /* Step 3: profit */
        pathCost = end.cost;
        for (Tile path = end; path != null; path = path.prev) {
            if (result[path.r][path.c] == Tile.SPACE) {
                result[path.r][path.c] = Tile.PATH;
            }
        }

        return result;
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
