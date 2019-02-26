package lib2D;

/**
 * Class that represents a ray in the plane
 */
public class RayLine extends SegmentLine {

    public RayLine(Point a, Point b) {
        super(a, b);
    }

    public RayLine(SegmentLine s) {
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

    /**
     * t paramenter should be > 0
     */
    @Override
    protected void check_t(double t) throws Invalid_T_Parameter {
        if (t < 0) {
            throw new Invalid_T_Parameter();
        }
    }

    @Override
    public boolean intersect(SegmentLine r, Vector inter) {
        DoubleType s = new DoubleType();
        DoubleType t = new DoubleType();
        Vector c = new Vector(r.a);
        Vector d = new Vector(r.b);
        if (this.intersect(c, d, t, s)) {
            if (s.getV() >= 0 && (t.getV() >= 0 && t.getV() <= 1)) {
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
    public boolean intersect(RayLine r, Vector inter) {
        DoubleType s = new DoubleType();
        DoubleType t = new DoubleType();
        Vector c = new Vector(r.a);
        Vector d = new Vector(r.b);
        this.intersect(c, d, t, s);
        if (s.getV() >= 0 && t.getV() >= 0) {
            inter.x = a.x + s.getV() * (b.x - a.x);
            inter.y = a.y + s.getV() * (b.y - a.y);
            return true;
        } else {
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
        if (s.getV() >= 0) {
            inter.x = a.x + s.getV() * (b.x - a.x);
            inter.y = a.y + s.getV() * (b.y - a.y);
            return true;
        } else {
            return false;
        }
    }

    public double distPointRay(Vector v) {
        Vector d = new Vector(this.a.minus(b));
        double t0 = (d.dot(new Vector(a.minus(v)))) / (d.dot(d));
        double dist;
        if (t0 <= 0) {
            Vector distance = new Vector(a.minus(v));
            dist = distance.getModule();
        } else {
            d = d.scalarMult(t0);
            Vector c = new Vector(this.a);
            Vector sum = new Vector(c.add(d));
            Vector distance = new Vector(sum.minus(v));
            dist = distance.getModule();
        }

        return dist;
    }

    public void out() {
        System.out.println("RayLine->");
        System.out.println("Punto a: ");
        a.out();
        System.out.println("Punto b: ");
        b.out();
    }

}
