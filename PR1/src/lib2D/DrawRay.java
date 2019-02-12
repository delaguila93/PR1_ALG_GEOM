package lib2D;

import javax.media.opengl.*;

/**
 * Class for drawing a ray
 */
public class DrawRay extends Draw {

    RayLine rl;

    public DrawRay(RayLine l) {
        rl = l;
    }

    public RayLine getRay() {
        return rl;
    }

    @Override
    public void drawObject(GL g) {
               double ax , ay , bx , by ;
        double m = rl.slope();
        double t = 80;
        Point v = new Point();
        if (m < BasicGeom.INFINITY) { //intersects with the lateral canvas
            v.x = rl.getB().x - rl.getA().x;
            v.y = rl.getB().y - rl.getA().y;
        } else {

            //XXXXX
        }
        ax =convCoordX( rl.getA().x);
        ay =convCoordY( rl.getA().y);
        bx =convCoordX( rl.getA().x + (t * v.x));
        by =convCoordY( rl.getA().y + (t * v.y));
        g.glBegin(GL.GL_LINES);
        g.glVertex2d(ax, ay);
        g.glVertex2d(bx, by); //the fourth (w) component is zero!
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glLineWidth(3.0f);
        g.glColor3f(R, G, B);
        drawObject(g);

    }

}
