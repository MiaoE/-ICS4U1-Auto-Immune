public class Point {

    private int x, y;

    Point(Point point) {
        x = point.getX();
        y = point.getY();
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getLocation() {
        return new Point(this);
    }

    @Override
    public String toString() {
        return (x + " " + y);
    }
}
