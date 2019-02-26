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

    @Override
    public boolean intersect(SegmentLine r, Vector inter) {
        DoubleType s = new DoubleType();
        DoubleType t = new DoubleType();
        Vector c = new Vector(r.a);
        Vector d = new Vector(r.b);
        if (this.intersect(c, d, t, s)) {
            if (t.getV() >= 0 && t.getV() <= 1) {
                inter.x = c.x + t.getV() * (d.x - c.x);
                inter.y = c.y + t.getV() * (d.y - c.y);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean intersect(RayLine r, Vector inter) {
        DoubleType s = new DoubleType();
        DoubleType t = new DoubleType();
        Vector c = new Vector(r.a);
        Vector d = new Vector(r.b);
        if (this.intersect(c, d, t, s)) {

            if (t.getV() >= 0) {
                inter.x = a.x + s.getV() * (b.x - a.x);
                inter.y = a.y + s.getV() * (b.y - a.y);
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public boolean intersect(Line r, Vector inter) {
        DoubleType s = new DoubleType();
        DoubleType t = new DoubleType();
        Vector c = new Vector(r.a);
        Vector d = new Vector(r.b);
        this.intersect(c, d, t, s);
        inter.x = a.x + s.getV() * (b.x - a.x);
        inter.y = a.y + s.getV() * (b.y - a.y);
        return true;
    }

    public double distance(Vector p) {

        Vector d = new Vector(this.a.minus(b));
        double t0 = (d.dot(new Vector(a.minus(p)))) / (d.dot(d));
        d = d.scalarMult(t0);
        Vector c = new Vector(this.a);
        Vector sum = new Vector(c.add(d));
        Point distance = new Point(sum.minus(p));
        double dist = distance.getModule();
        return dist;
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
