package lib2D;

import static java.lang.Math.*;

/**
 * Class that represents a 2D point
 */
enum PointClassification {
    LEFT, RIGHT, FORWARD, BACKWARD, BETWEEN, ORIGIN, DEST
};

public class Point {

    protected double x;
    protected double y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * Constructor from an alpha angle (radians) and the vector module rp. If
     * polar == true, it must be builtup with the polar coordinates
     */
    public Point(double alpha, double rp, boolean polar) {
        if (polar) {

            x = rp * cos(alpha);
            y = rp * sin(alpha);

        } else {
            x = alpha;
            y = rp;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Return the alpha angle (radians) (note: consider the point quadrant)
     */
    public double getAlpha() {

        double alpha = 0;

        if (x > 0 && y >= 0) { // First Cuadrant
            alpha = Math.atan(y / x);
        } else if (x < 0 && y >= 0) { // Second cuadrant
            alpha = Math.atan(y / x) + Math.PI;
        } else if (x < 0 && y < 0) { // Third Cuadrant
            alpha = Math.atan(y / x) + Math.PI;
        } else if (x > 0 && y < 0) { // Fourth Cuadrant
            alpha = Math.atan(y / x) + 2 * Math.PI;
        }
        return alpha;

    }

    /**
     * Return the vector module rp (note: consider the point quadrant)
     */
    public double getModule() {
        return sqrt(pow(this.x, 2) + pow(this.y, 2));
    }

    public Point minus(Point p) {
        Point a = new Point();
        a.x = p.getX() - this.x;
        a.y = p.getY() - this.y;
        return a;

    }

    /**
     * Determines the relative position of a point (this) with respect to other
     * two given as a parameter (which can form a segment)
     */
    public PointClassification classify(Point p0, Point p1) {

        Point p2 = this;
        Point a = p1.minus(p0);
        Point b = p2.minus(p0);

        double sa = a.getX() * b.getY() - b.getX() * a.getY();
        if (sa < 0.0) {
            return PointClassification.LEFT;
        } else if (sa > 0.0) {
            return PointClassification.RIGHT;
        } else if (a.getX() * b.getX() < 0.0 || a.getY() * b.getY() > 0.0) {
            return PointClassification.BACKWARD;
        } else if (a.getModule() < b.getModule()) {
            return PointClassification.FORWARD;
        } else if (equal(p0)) {
            return PointClassification.ORIGIN;
        } else if (equal(p1)) {
            return PointClassification.DEST;
        }
        return PointClassification.BETWEEN;
    }

    boolean equal(Point pt) {
        return (BasicGeom.equal(x, pt.x) && BasicGeom.equal(y, pt.y));

    }

    /**
     * Distance between two points
     */
    public double distance(Point p) {
        double xDif = pow(p.x - this.x, 2);
        double yDif = pow(p.y - this.y, 2);

        return sqrt(xDif + yDif);

    }

    /**
     * Calculate the double area of the triangle formed by (this, a, b)
     */
    public double triangleArea2(Point a, Point b) {
        return (this.x * a.y - this.y * a.x + a.x * b.y - a.y * b.x + b.x * this.y - b.y * this.x);
    }

    public boolean distinct(Point p) {
        return (Math.abs(x - p.x) > BasicGeom.ZERO || Math.abs(y - p.y) > BasicGeom.ZERO);
    }

    public Point copy() {
        return new Point(x, y);
    }

    public void copy(Point p) {
        x = p.x;
        y = p.y;
    }

    public Point get() {
        return this;
    }

    public void set(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public void setX(double xx) {
        x = xx;
    }

    public void setY(double yy) {
        y = yy;
    }

    /**
     * It must used the method classify
     */
    public boolean left(Point a, Point b) {
        return classify(a, b) == PointClassification.LEFT;
    }

    /**
     * It must used the method classify
     */
    public boolean right(Point a, Point b) {
        return classify(a, b) == PointClassification.RIGHT;
    }

    /**
     * It must used the method classify
     */
    public boolean colinear(Point a, Point b) {
        PointClassification resultado = classify(a, b);
        return (resultado != PointClassification.LEFT) && (resultado != PointClassification.RIGHT);
    }

    /**
     * It must used the method classify
     */
    public boolean leftAbove(Point a, Point b) {
        PointClassification resultado = classify(a, b);
        return (resultado == PointClassification.LEFT) || (resultado != PointClassification.RIGHT);
    }

    /**
     * It must used the method classify
     */
    public boolean rightAbove(Point a, Point b) {
        PointClassification resultado = classify(a, b);
        return (resultado == PointClassification.RIGHT) || (resultado != PointClassification.LEFT);
    }

    /**
     * It must used the method classify
     */
    public boolean isBetween(Point a, Point b) {
        return classify(a, b) == PointClassification.BETWEEN;
    }

    /**
     * It must used the method classify
     */
    public boolean forward(Point a, Point b) {
        return classify(a, b) == PointClassification.FORWARD;
    }

    /**
     * It must used the method classify
     */
    public boolean backward(Point a, Point b) {
        return classify(a, b) == PointClassification.BACKWARD;
    }

    /**
     * Calculate the slope between two points (this and a)
     */
    public double slope(Point p) {

        return (this.y - p.y) / (this.x - p.x);
    }

    /**
     * Muestra en pantalla los valores de las coordenadas del Point.
     */
    public void out() {
        System.out.print("Coordinate x: ");
        System.out.println(x);
        System.out.print("Coordinate y: ");
        System.out.println(y);
    }

}
