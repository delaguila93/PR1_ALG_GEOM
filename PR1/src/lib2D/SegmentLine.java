package lib2D;



/**
 * Class that represents a segment line defined by two points
 */
public class SegmentLine {

    protected Point a;
    protected Point b;

    static class Invalid_T_Parameter extends Exception {
    }

    protected void check_t(double t) throws Invalid_T_Parameter {
        if ((t < 0) || (t > 1)) {
            throw new Invalid_T_Parameter();
        }
    }

    public SegmentLine() {
        b = new Point();
        a = new Point();
    }

    public SegmentLine(Point ii, Point ss) {
        a = ii;
        b = ss;
    }

    public SegmentLine(SegmentLine sg) {
        a = sg.a;
        b = sg.b;
    }

    public SegmentLine(double ax, double ay, double bx, double by) {
        a = new Point(ax, ay);
        b = new Point(bx, by);
    }

    /**
     * Returns the area formed by the triangle composed of the current SegmentLine and the union of its bounds with p
     */
    public double triangleArea2(Point p) {
        return p.triangleArea2(a, b);
    }

    public double length() {
        return a.distance(b);
    }

    public boolean equal(SegmentLine sg) {
        return (a.equal(sg.a) && b.equal(sg.b)) || (a.equal(sg.b) && b.equal(sg.a));
    }

    public boolean distinct(SegmentLine sg) {
        return !(a.equal(sg.a) && b.equal(sg.b)) || (a.equal(sg.b) && b.equal(sg.a));
    }

    public SegmentLine copy() {
        return new SegmentLine(a, b);
    }

    public void copy(SegmentLine sg) {
        a.copy(sg.a);
        b.copy(sg.b);
    }

    public SegmentLine get() {
        return this;
    }

    public void setA(Point p) {
        a.copy(p);
    }

    /**
     * Determines whether a segment is horizontal or not (use BasicGeom.CERO)
     */
    public boolean isHorizontal() {
        //XXXX
        return true;
    }

    /**
     * Determines whether or not a segment is vertical (use BasicGeom.CERO)
     */
    public boolean isVertical() {
        //XXXXXX
        return false; 
    }

    /**
     * Determines whether p is in the left of SegmentLine
     */
    public boolean left(Point p) {
        return p.left(a, b);
    }

    /**
     * It obtains the point belonging to the segment or colineal to it for a concrete t in the parametric equation: result = a + t (b-a)
     */
    Point getPoint(double t) throws Invalid_T_Parameter {
        //XXXXX
        return new Point();
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    /**
     * Returns the slope of the implied straight line equation: m = (yb-ya) / (xb-xa)
     */
    public double slope() {
        return 0.0;
        //XXXXXX
    }

    /**
     * Returns the constant of the equation of the implied line: c = y-mx (use BasicGeom.INFINITO)
     */
    public double getC() {

        //XXXXXX
        return 0.0;
    }

    /**
     * Determines whether two segments intersect improperly, that is, when one end of a segment is contained in the other. Use integer arithmetic.     
     */
    public boolean impSegmentIntersection(SegmentLine l) {

        //XXXX
        return true;

    }

    /**
     * Determines whether two segments intersect in their own way, that is, when they intersect completely. Use only arithmetic  
     */
    public boolean segmentIntersection(SegmentLine l) {

        //XXXX
        
        return false; 

    }

    
    /**
     * Muestra en pantalla la informacion del SegmentLine.
     */
    public void out() {
        System.out.println("Punto a: ");
        a.out();
        System.out.println("Punto b: ");
        b.out();
    }

}
