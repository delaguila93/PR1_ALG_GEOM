package lib3D;

import lib2D.BasicGeom;

enum PointPosition {
    POSITIVE, NEGATIVE, COPLANAR
};

enum PointTrianglePosition {
    PARALELL, COLLINEAR, INTERSECTS, NO_INTERSECTS
};

public class Triangle3d {

    /**
     * a triangle is defined by three points
     */
    protected Vect3d a, b, c;

    /**
     * default constructor with values (0,0,0)
     */
    public Triangle3d() {
        a = new Vect3d();
        b = new Vect3d();
        c = new Vect3d();
    }

    /**
     * constructor from their coordinates
     */
    public Triangle3d(double ax, double ay, double az,
            double bx, double by, double bz,
            double cx, double cy, double cz) {
        a = new Vect3d(ax, ay, az);
        b = new Vect3d(bx, by, bz);
        c = new Vect3d(cx, cy, cz);
    }

    /**
     * copy constructor
     */
    public Triangle3d(Triangle3d t) {
        a = new Vect3d(t.a);
        b = new Vect3d(t.b);
        c = new Vect3d(t.c);
    }

    /**
     * constructor given three points
     */
    public Triangle3d(Vect3d va, Vect3d vb, Vect3d vc) {
        a = new Vect3d(va);
        b = new Vect3d(vb);
        c = new Vect3d(vc);
    }

    /**
     * set new values
     */
    public void set(Vect3d va, Vect3d vb, Vect3d vc) {
        a = va;
        b = vb;
        c = vc;
    }

    /**
     * get a
     */
    public Vect3d getA() {
        return a;
    }

    /**
     * get b
     */
    public Vect3d getB() {
        return b;
    }

    /**
     * get c
     */
    public Vect3d getC() {
        return c;
    }

    /**
     * get the vertex i
     */
    public Vect3d getPoint(int i) {
        return (i == 0 ? a : (i == 1 ? b : c));
    }

    /**
     * get the vertex the set of vertices
     */
    public Vect3d[] getPoints() {
        Vect3d[] vt = {a, b, c};
        return vt;
    }

    /**
     * get a copy
     */
    public Triangle3d copy() {
        return new Triangle3d(a, b, c);
    }

    /**
     * set new value to a
     */
    public void setA(Vect3d pa) {
        a = pa;
    }

    /**
     * set new value to b
     */
    public void setB(Vect3d pb) {
        b = pb;
    }

    /**
     * set new value to c
     */
    public void setC(Vect3d pc) {
        c = pc;
    }

    /**
     * get the normal vector
     */
    public Vect3d normal() {
        Vect3d v1 = new Vect3d(b.sub(a));
        Vect3d v2 = new Vect3d(c.sub(a));
        Vect3d n = new Vect3d(v1.xProduct(v2));
        double longi = n.module();

        return (n.scalarMul(1.0 / longi));
    }

    /**
     * show the vertices
     */
    public void out() {
        System.out.println("Triangle3d: (" + a + "-" + b + "-" + c + ")");
    }

    public double area() {
        Vect3d ab = new Vect3d(this.b.sub(this.a));
        Vect3d ac = new Vect3d(this.c.sub(this.a));
        double area = 0.5 * Math.sqrt(Math.pow(ab.y * ac.z - ab.z * ac.y, 2.0) + Math.pow(ab.z * ac.z - ab.x * ac.z, 2) + Math.pow(ab.x * ac.y - ab.y * ac.x, 2));
        return area;

    }

    public PointPosition classify(Vect3d p) {

        Vect3d v = new Vect3d(p.sub(a));
        double len = v.module();
        if (BasicGeom.equal(len, 0.0)) {
            return PointPosition.COPLANAR;
        }
        v = v.scalarMul(1.0 / len);
        double d = v.dot(this.normal());
        if (d > BasicGeom.ZERO) {
            return PointPosition.POSITIVE;
        } else if (d < BasicGeom.ZERO) {
            return PointPosition.NEGATIVE;
        } else {
            return PointPosition.COPLANAR;
        }
    }

}
