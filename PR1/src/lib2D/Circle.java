package lib2D;

//import Util.BasicGeom;

import java.util.ArrayList;

//import java.util.ArrayList;
/**
 * This class can represent both a circle and a circle.
 */
public class Circle {

    public enum RelationCircles {
        CONCENTRIC,
        OUTER,
        INNER,
        SECANT,
        INNER_TANG,
        OUTER_TANG

    };

    public enum RelationCircleLine {
        INTERSECTS,
        TANGENTS,
        NO_INTERSECTS
    };

    static int NUMBER_SIDES = 30;

    protected Point c; // Circle center
    protected double r; // Circle radius

    public Circle() {
        c = new Point();
        r = 0;
    }

    public Circle(Point center, double radius) {
        c = center;
        r = radius;
    }

    public Circle(Circle cc) {
        c = cc.c;
        r = cc.r;
    }

    public Point getCenter() {
        return c;
    }

    public double getRadius() {
        return r;
    }

    public double area() {
        return BasicGeom.PI * r * r;
    }

    public double diameter() {
        return BasicGeom.PI * 2 * r;

    }

    /**
     * Returns true if the point is inside the circle
     */
    public boolean isInside(Point p) {
        double dist = p.distance(c);
        return dist < r;
    }

    /**
     * Returns a polygon with the points that define the circle
     */
    public Polygon getPointsCircle() {
        ArrayList<Vertex> vertices = new ArrayList();
        Polygon c =new Polygon();
        double heading = 0;
        for (int a = 0; a < 360; a += 360 / NUMBER_SIDES) {
            heading = a * Math.toRadians(a);
            vertices.add(new Vertex(Math.cos(heading) * this.r, Math.sin(heading) * this.r,c));
        }
         c.setVertexs(vertices);
        return c;
    }

    /**
     * Returns the relation between two circles
     */
    public RelationCircles circleRelation(Circle c) {
        // XXXX
        return RelationCircles.INNER_TANG;
    }

    /**
     * Returns the relation between a circle and a line
     */
    public RelationCircleLine lineRelation(Line l) {

        //XXXXX
        return RelationCircleLine.NO_INTERSECTS;
    }

    /**
     * Check if the line intersects with the circle and returns the two
     * intersection points, if any
     */
    public RelationCircleLine intersects(Line l, Vector inner1, Vector inner2) {
        //XXXX
        return RelationCircleLine.INTERSECTS;
    }

    /**
     * Check if the segment intersects with the circle and returns the two
     * intersection points, if any
     */
    public RelationCircleLine intersects(SegmentLine l, Vector inner1, Vector inner2) {
        //XXXX
        return RelationCircleLine.INTERSECTS;
    }

    /**
     * Check if the line intersects with a ray and returns the two intersection
     * points, if any
     */
    public RelationCircleLine intersects(RayLine l, Vector inner1, Vector inner2) {
        //XXXX
        return RelationCircleLine.INTERSECTS;

    }

}
