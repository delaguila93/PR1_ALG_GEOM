package lib2D;

/**
 * Class that represents a line in the plane
 */
public class Line extends SegmentLine {

    public Line(Point a, Point b) {
        super(a, b);
    }

    public Line(SegmentLine s) {
        a = s.a;
        b = s.b;
    }

    @Override
    public boolean segmentIntersection(SegmentLine l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean impSegmentIntersection(SegmentLine l) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void check_t(double t) throws Invalid_T_Parameter {
    }
    
    
    public double distance(final Vector v) {
        //XXXXX
        return 0.0;
    }

    
    @Override
    public void out() {
        System.out.println("Line->");
        System.out.println("Point a: ");
        a.out();
        System.out.println("Point b: ");
        b.out();
    }

}
