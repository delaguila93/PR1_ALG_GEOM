package lib3D;

import Util.*;
import lib2D.BasicGeom;

enum intersectionType {
    POINT, SEGMENT, COPLANAR
}

public class Plane {

    static public class IntersectionTriangle {

        public intersectionType type;
        public Vect3d p;
        public Segment3d s;
    }

    static public class IntersectionLine {

        public intersectionType type;
        public Vect3d p;
    }

    Vect3d a, b, c; //tres puntos cualquiera del plano  

    /**
     *
     * @param p en pi = p+u * lambda + v * mu -> r en los puntos (R,S,T)
     * @param u en pi = p+u * lambda + v * mu -> d en los puntos (R,S,T)
     * @param v en pi = p+u * lambda + v * mu -> t en los puntos (R,S,T)
     * @param arePoints = false, then params are p+u * lambda + v * mu,
     * otherwise are points (R,S,T)
     */
    public Plane(Vect3d p, Vect3d u, Vect3d v, boolean arePoints) {
        if (!arePoints) { //are vectors: pi =  p+u * lambda + v * mu 
            a = p;
            b = u.add(a);
            c = v.add(a);
        } else { // are 3 points in the plane 
            a = p;
            b = u;
            c = v;
        }
    }

    /**
     *
     * @return A in AX+BY+CZ+D = 0;
     *
     */
    public double getA() {

        return (BasicGeom.determinant2x2(c.getZ() - a.getZ(), c.getY() - a.getY(), b.getY() - a.getY(), b.getZ() - a.getZ()));
    }

    /**
     *
     * @return B in AX+BY+CZ+D = 0;
     *
     */
    public double getB() {

        return (BasicGeom.determinant2x2(c.getX() - a.getX(), c.getZ() - a.getZ(), b.getZ() - a.getZ(), b.getX() - a.getX()));
    }

    /**
     *
     * @return C in AX+BY+CZ+D = 0;
     *
     */
    public double getC() {
        return (BasicGeom.determinant2x2(c.getY() - a.getY(), c.getX() - a.getX(), b.getX() - a.getX(), b.getY() - a.getY()));
    }

    /**
     *
     * @return D in AX+BY+CZ+D = 0;
     *
     */
    public double getD() {

        return (-1) * (getA() * a.getX() + getB() * a.getY() + getC() * a.getZ());
    }

    /**
     *
     * @return the normal vector of (A,B,C) in Ax+By+Cz+D = 0
     */
    public Vect3d getNormal() {
        double A = getA();
        double B = getB();
        double C = getC();
        Vect3d normal = new Vect3d(A, B, C);
        return normal.scalarMul(1 / normal.module());
    }

    /**
     * @return the point of the parametric function (plano=p+u*lambda+v*mu)
     */
    public Vect3d getPointParametric(double lambda, double mu) {
        Vect3d u = b.sub(a),
                v = c.sub(a);

        return a.add(u.scalarMul(lambda)).add(v.scalarMul(mu));
    }

    /**
     * @return Distance between a plane/point
     */
    public double distance(Vect3d p) {

        double aa = this.getA();
        double bb = this.getB();
        double cc = this.getC();
        double dd = this.getD();

        double num = aa * p.x + bb * p.y + cc * p.z + dd;
        double det = Math.sqrt(Math.pow(aa, 2) - Math.pow(bb, 2) - Math.pow(cc, 2));

        return num / det;
    }

    /**
     * @return true if p is in the plane
     */
    public boolean coplanar(Vect3d p) {

        return this.distance(p) < BasicGeom.ZERO;
    }

    public Vect3d intersect(Line3d p) {

        Vect3d normal = new Vect3d(this.getNormal());
        Vect3d v = new Vect3d(p.dest.sub(p.orig));
        double lambda = 0;
        if (normal.dot(v) < BasicGeom.ZERO) {
            return null;
        }
        if ((normal.module() == v.module()) && normal.module() == 1) {
            lambda = -1 * (normal.dot(p.orig) + this.getD());
        } else {
            lambda = (-1 * (normal.dot(p.orig) + this.getD())) / normal.dot(v);
        }
        return p.getPoint(lambda);
    }

    public Line3d intersect(Plane p) {
        Vect3d n1 = new Vect3d(this.getNormal());
        Vect3d n2 = new Vect3d(p.getNormal());
        Vect3d n3 = new Vect3d(n1.xProduct(n2));

        double det = BasicGeom.determinant3x3(this.getA(), this.getB(), this.getC(), p.getA(), p.getB(), p.getC(), n3.x, n3.y, n3.z);

        if (det < BasicGeom.ZERO) {
            return null;
        } else {
            double det1 = BasicGeom.determinant2x2(this.getB(), this.getC(), n3.y, n3.z);
            double det2 = BasicGeom.determinant2x2(p.getB(), p.getC(), n3.y, n3.z);
            double x0 =(p.getD() * det1 - this.getD()*det2 ) / det;
            
            double det3 = BasicGeom.determinant2x2(n3.x, n3.z, this.getA(), this.getC());
            double det4 = BasicGeom.determinant2x2(n3.x, n3.z, p.getA(), p.getC());
            double y0 = (p.getD()*det3 - this.getD()*det4) / det;
            
            double det5 = BasicGeom.determinant2x2(this.getA(), this.getB(), n3.x, n3.y);
            double det6 = BasicGeom.determinant2x2(p.getA(), p.getB(), n3.x, n3.y);
            double z0 = (p.getD()*det5 - this.getD()*det6) / det;
            
            return new Line3d(new Vect3d(x0,y0,z0), n3);
        }
    }

    /**
     * shows the plane values
     */
    public void out() {
        System.out.print("Plane->a: ");
        System.out.println(a);
        System.out.print("Plane->b: ");
        System.out.println(b);
        System.out.print("Plane->c: ");
        System.out.println(c);
    }

}
