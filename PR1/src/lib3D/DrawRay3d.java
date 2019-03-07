package lib3D;

import Util.*;
import javax.media.opengl.*;
import lib2D.BasicGeom;
import lib2D.Draw;

public class DrawRay3d extends Draw {

    Ray3d vr;

    public DrawRay3d(Ray3d r) {
        vr = r;
    }

    public Ray3d getRay3d() {
        return vr;
    }

    @Override
    public void drawObject(GL g) {

        double ax, ay, az, bx, by, bz;
        Vect3d ba = new Vect3d(vr.dest.sub(vr.orig));
        double m = ba.module();
        double t = 80;
        Vect3d v = new Vect3d();
        if (m < BasicGeom.INFINITY) { //intersects with the lateral canvas
            v.x = vr.getDestination().x - vr.getOrigin().x;
            v.y = vr.getDestination().y - vr.getOrigin().y;
            v.z = vr.getDestination().z - vr.getOrigin().z;
        }
        ax = vr.getOrigin().x;
        ay = vr.getOrigin().y;
        az = vr.getOrigin().z;
        bx = vr.getOrigin().x + (t * v.x);
        by = vr.getOrigin().y + (t * v.y);
        bz = vr.getOrigin().z + (t * v.z);

        g.glBegin(GL.GL_LINES);
        g.glVertex3d(ax, ay, az);
        g.glVertex3d(bx, by, bz); //the fourth (w) component is zero!
        g.glEnd();


    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
}
