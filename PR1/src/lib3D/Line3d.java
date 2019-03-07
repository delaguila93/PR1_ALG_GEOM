package lib3D;

import lib2D.BasicGeom;

enum classifyLines {
    NON_INTERSECT, PARALLEL, INTERSECT, THESAME
}

public class Line3d extends Edge3d {

    public Line3d(Vect3d o, Vect3d d) {
        super(o, d);
    }

    /**
     *
     * @return distance between line/point
     *
     */
    public double distance(Vect3d p) {
        Vect3d u = new Vect3d(this.dest.sub(this.orig));
        Vect3d ap = new Vect3d(p.sub(this.orig));

        return (u.xProduct(ap).module()) / u.module();
    }

    /**
     *
     * @return distance between line/line
     *
     */
    public double distance(Line3d l) {
        Vect3d subQ = new Vect3d(l.orig.sub(orig));
        Vect3d tLine1 = new Vect3d(dest.sub(orig));
        Vect3d tLine2 = new Vect3d(l.dest.sub(l.orig));
        Vect3d xProdV = new Vect3d(tLine1.xProduct(tLine2));
        return (subQ.dot(xProdV)) / xProdV.module();
    }

    /**
     *
     * @return true if are parallel
     *
     */
    public boolean parallel(Line3d l) {
        Vect3d a = new Vect3d(dest.sub(orig));
        Vect3d b = new Vect3d(l.dest.sub(l.orig));

        double result = a.x / b.x;

        return (a.y / b.y) == result && (a.z / b.z) == result;

    }

    /**
     *
     * @return true if they ara perpendicular
     *
     */
    public boolean perpendicular(Line3d l) {
        Vect3d a = new Vect3d(dest.sub(orig));
        Vect3d b = new Vect3d(l.dest.sub(l.orig));

        return a.dot(b) < BasicGeom.ZERO;
    }

    /**
     *
     * @return the normal line in the point p
     *
     */
    public Line3d normalLine(Vect3d p) {
        Vect3d v = new Vect3d(dest.sub(orig));
        double landa = 0;
        if (v.module() == 1) {
            landa = v.dot(p.sub(orig));
        } else {
            landa = (v.dot(p.sub(orig))) / v.dot(v);
        }
        Vect3d add = new Vect3d(orig.add(v.scalarMul(landa)));
        Vect3d u = new Vect3d(p.sub(add));
        return new Line3d(u, p);
    }

    public void out() {
        System.out.print("Line->Origin: ");
        orig.out();
        System.out.print("Line->Destination: ");
        dest.out();
    }

}
