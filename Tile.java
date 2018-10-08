package assignment1;

class Tile implements Comparable<Tile> {
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
