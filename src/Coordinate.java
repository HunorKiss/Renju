import java.io.Serializable;
import java.util.Objects;

public class Coordinate implements Serializable{
    
    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate rhs = (Coordinate) o;
        return x == rhs.x && y == rhs.y;
    }
}
