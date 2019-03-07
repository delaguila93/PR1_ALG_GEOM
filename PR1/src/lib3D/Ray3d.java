package lib3D;

public class Ray3d extends Edge3d {

    public Ray3d(Vect3d o, Vect3d d) {
        super(o, d);
    }

    /**
     * Show values of Edge3d
     */
    public void out() {
        System.out.print("Ray->Origin: ");
        orig.out();
        System.out.print("Ray->Destination: ");
        dest.out();
    }

}
