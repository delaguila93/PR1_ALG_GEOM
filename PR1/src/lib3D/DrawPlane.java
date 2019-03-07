package lib3D;

import Util.*;
import javax.media.opengl.*;
import lib2D.Draw;

public class DrawPlane extends Draw {

    Plane pl;

    public DrawPlane(Plane p) {
        pl = p;
    }

    public Plane getPlane() {
        return pl;
    }

    public void drawPlane(GL g, boolean rotation) {

        Vect3d punto4;
        Vect3d ab = new Vect3d(pl.b.sub(pl.a));
        Vect3d ac = new Vect3d(pl.c.sub(pl.a));
        punto4 = new Vect3d(ab.add(ac));
        if (!rotation) {

            g.glBegin(GL.GL_POLYGON);
            g.glVertex3d(pl.a.x, pl.a.y, pl.a.z);
            g.glVertex3d(pl.b.x, pl.b.y, pl.b.z);
            g.glVertex3d(pl.c.x, pl.c.y, pl.c.z);
            g.glVertex3d(punto4.x, punto4.y, punto4.z);
            g.glEnd();
        } else {
            g.glRotated(90.0f, 1.0f, 0.0f, 0.0f);
            g.glBegin(GL.GL_POLYGON);
            g.glVertex3d(pl.a.x, pl.a.y, pl.a.z);
            g.glVertex3d(pl.b.x, pl.b.y, pl.b.z);
            g.glVertex3d(pl.c.x, pl.c.y, pl.c.z);
            g.glVertex3d(punto4.x, punto4.y, punto4.z);
            g.glEnd();
        }

    }

    @Override
    public void drawObject(GL g) {
        Vect3d punto4;
        Vect3d ab = new Vect3d(pl.b.sub(pl.a));
        Vect3d ac = new Vect3d(pl.c.sub(pl.a));
        punto4 = new Vect3d(ab.add(ac));

        g.glBegin(GL.GL_POLYGON);
        g.glVertex3d(pl.a.x, pl.a.y, pl.a.z);
        g.glVertex3d(pl.b.x, pl.b.y, pl.b.z);
        g.glVertex3d(pl.c.x, pl.c.y, pl.c.z);
        g.glVertex3d(punto4.x, punto4.y, punto4.z);
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }

    public void drawObjectC(GL g, float R, float G, float B, float A, boolean rotation) {
        g.glColor4f(R, G, B, A);
        drawPlane(g, rotation);
    }

}
