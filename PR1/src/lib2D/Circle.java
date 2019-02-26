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

        Polygon polig = new Polygon();

        double heading = 0;

        for (int a = 0; a < 360; a += 360 / NUMBER_SIDES) {
            heading = (a * BasicGeom.PI) / 180;
            polig.add((new Point((Math.cos(heading) * r) + c.x, (Math.sin(heading) * r) + c.y)));
        }

        return polig;
    }

    /**
     * Returns the relation between two circles
     */
    public RelationCircles circleRelation(Circle circle) {
        double distance = circle.c.distance(this.c);
        if ( ((this.r - circle.r) < distance) && (distance < (this.r + circle.r))) {
            return RelationCircles.SECANT;
        } else if (distance >= this.r - circle.r  ) {
            return RelationCircles.INNER_TANG;
        } else if (distance < this.r + circle.r ) {
            return RelationCircles.OUTER;
        } else if ((distance - (this.r + circle.r) < BasicGeom.ZERO)) {
            return RelationCircles.OUTER_TANG;
        } else if (distance < (this.r - circle.r)) {
            return RelationCircles.INNER;
        }
        return RelationCircles.CONCENTRIC;
    }

    /**
     * Returns the relation between a circle and a line
     */
    public RelationCircleLine lineRelation(Line l) {

        double distance = l.distance(new Vector(this.c));
        if (distance < this.r) {
            return RelationCircleLine.INTERSECTS;
        } else if (distance > this.r) {
            return RelationCircleLine.NO_INTERSECTS;
        }
        return RelationCircleLine.TANGENTS;
    }

    /**
     * Check if the line intersects with the circle and returns the two
     * intersection points, if any
     */
    public RelationCircleLine intersects(Line l, Vector inner1, Vector inner2) throws SegmentLine.Invalid_T_Parameter {
        Vector delta = new Vector(c.minus(l.a));
        Vector d = new Vector(l.a.minus(l.b));
        double moduleD = d.getModule();
        double moduleDelta = delta.getModule();
        double gamma = Math.pow(d.dot(delta), 2) - (Math.pow(moduleD, 2) * (Math.pow(moduleDelta, 2) - Math.pow(r, 2)));
        double t1 = 0;
        double t2 = 0;
        if (gamma < BasicGeom.ZERO) {
            Vector dMinus = new Vector(-d.x, -d.y);
            t1 = (dMinus.dot(delta)) / Math.pow(d.getModule(), 2);
            if (t1 >= 0) {
                inner1.set(l.getPoint(t1).x, l.getPoint(t1).y);
                return RelationCircleLine.TANGENTS;
            }
        } else if (gamma > 0) {
            Vector dMinus = new Vector(-d.x, -d.y);
            double valor = dMinus.dot(delta);
            t1 = (valor - Math.sqrt(gamma)) / Math.pow(d.getModule(), 2);
            t2 = (valor + Math.sqrt(gamma)) / Math.pow(d.getModule(), 2);
            if (t1 >= 0) {
                inner1.set(l.getPoint(t1).x, l.getPoint(t1).y);              
            }
            if (t2 >= 0) {
                inner2.set(l.getPoint(t2).x, l.getPoint(t2).y);                
            }
            return RelationCircleLine.INTERSECTS;
        }
        return RelationCircleLine.NO_INTERSECTS;

    }

    /**
     * Check if the segment intersects with the circle and returns the two
     * intersection points, if any
     */
    public RelationCircleLine intersects(SegmentLine l, Vector inner1, Vector inner2) throws SegmentLine.Invalid_T_Parameter {
        Vector delta = new Vector(c.minus(l.a));
        Vector d = new Vector(l.a.minus(l.b));
        double moduleD = d.getModule();
        double moduleDelta = delta.getModule();
        double gamma = Math.pow(d.dot(delta), 2) - (Math.pow(moduleD, 2) * (Math.pow(moduleDelta, 2) - Math.pow(r, 2)));
        double t1 = 0;
        double t2 = 0;
        if (gamma < BasicGeom.ZERO) {
            Vector dMinus = new Vector(-d.x, -d.y);
            t1 = (dMinus.dot(delta)) / Math.pow(d.getModule(), 2);
            if (t1 >= 0) {
                inner1.set(l.getPoint(t1).x, l.getPoint(t1).y);
                return RelationCircleLine.TANGENTS;
            }
        } else if (gamma > 0) {
            Vector dMinus = new Vector(-d.x, -d.y);
            double valor = dMinus.dot(delta);
            t1 = (valor - Math.sqrt(gamma)) / Math.pow(d.getModule(), 2);
            t2 = (valor + Math.sqrt(gamma)) / Math.pow(d.getModule(), 2);
            if (t1 >= 0) {
                inner1.set(l.getPoint(t1).x, l.getPoint(t1).y);              
            }
            if (t2 >= 0) {
                inner2.set(l.getPoint(t2).x, l.getPoint(t2).y);                
            }
            return RelationCircleLine.INTERSECTS;
        }
        return RelationCircleLine.NO_INTERSECTS;

    }

    /**
     * Check if the line intersects with a ray and returns the two intersection
     * points, if any
     */
    public RelationCircleLine intersects(RayLine l, Vector inner1, Vector inner2) throws SegmentLine.Invalid_T_Parameter {
        Vector delta = new Vector(c.minus(l.a));
        Vector d = new Vector(l.a.minus(l.b));
        double moduleD = d.getModule();
        double moduleDelta = delta.getModule();
        double gamma = Math.pow(d.dot(delta), 2) - (Math.pow(moduleD, 2) * (Math.pow(moduleDelta, 2) - Math.pow(r, 2)));
        double t1 = 0;
        double t2 = 0;
        if (gamma < BasicGeom.ZERO) {
            Vector dMinus = new Vector(-d.x, -d.y);
            t1 = (dMinus.dot(delta)) / Math.pow(d.getModule(), 2);
            if (t1 >= 0) {
                inner1.set(l.getPoint(t1).x, l.getPoint(t1).y);
                return RelationCircleLine.TANGENTS;
            }
        } else if (gamma > 0) {
            Vector dMinus = new Vector(-d.x, -d.y);
            double valor = dMinus.dot(delta);
            t1 = (valor - Math.sqrt(gamma)) / Math.pow(d.getModule(), 2);
            t2 = (valor + Math.sqrt(gamma)) / Math.pow(d.getModule(), 2);
            if (t1 >= 0) {
                inner1.set(l.getPoint(t1).x, l.getPoint(t1).y);              
            }
            if (t2 >= 0) {
                inner2.set(l.getPoint(t2).x, l.getPoint(t2).y);                
            }
            return RelationCircleLine.INTERSECTS;
        }
        return RelationCircleLine.NO_INTERSECTS;
    }

}
