package lib2D;

/**
 * Class that represents a 2D vector
 */
public class Vector extends Point {

    public Vector() {
        super(0, 0);
    }

    public Vector(double xx, double yy) {
        super(xx, yy);
    }

    public Vector(Point p) {
        super(p.x, p.y);
    }

    public Vector(Vector v) {
        super(v.x, v.y);
    }

    /**
     * c = this+b (sum of vectors)
     */
    public Vector add(Vector b) {
        return new Vector(this.x + b.x, this.y + b.y);
    }

    /**
     * c= a-b (substraction of vectors)
     */
    public Vector sub(Vector b) {
        return new Vector(this.x - b.x, this.y - b.y);
    }

    /**
     * c= a . b (scalar product)
     */
    public double dot(Vector b) {

        return (this.x * b.x) + (this.y * b.y);

    }

    /**
     * c= t . a (vector product by a scalar)
     */
    public Vector scalarMult(double t) {

        return new Vector(t * this.x, t * this.y);
    }

    @Override
    public Vector copy() {
        return new Vector(x, y);
    }

    public void copy(Vector v) {
        x = v.x;
        y = v.y;
    }

    @Override
    public Vector get() {
        return this;
    }

    public Point getPoint() {
        return this;
    }

}
