package assignment1;

import java.util.*;

public class BFS {
    private Tile[][] maze;
    public char[][] result;
    private Tile start = null;
    private Tile end = null;

    public int expanded;
    public int pathCost;

    public BFS(char[][] _maze){
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

        LinkedList<Tile> queue = new LinkedList<>();
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

                try {
                    System.in.read();
                    for (Tile[] row : maze) {
                        for (Tile cell : row) {
                            System.out.print(cell.symbol);
                        }
                        System.out.println();
                    }
                }
                catch (Exception e) {
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
        char[][] copy = null;

        if (original != null) {
            copy = new char[original.length][];

            for (int i = 0; i < original.length; i++) {
                copy[i] = Arrays.copyOf(original[i], original[i].length);
            }
        }

        return copy;
    }
}
