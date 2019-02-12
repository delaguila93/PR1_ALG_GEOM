package lib2D;

import javax.media.opengl.*;

/**
 * Class for drawing a line
 */
public class DrawLine extends Draw {

    Line vl;

    public DrawLine(Line l) {
        vl = l;
    }

    public Line getLine() {
        return vl;
    }

    @Override
    public void drawObject(GL g) {

        // screen coordiantes
        double ax, ay, bx, by;
        double m = vl.slope();
        double c = vl.getC();
        if (m < BasicGeom.INFINITY) { //intersects with the lateral canvas

            //XXXXX
        } else {

           //XXXXX
        }

//        ax = convCoordX(ax);
//        ay = convCoordX(ay);
//        bx = convCoordX(bx);
//        by = convCoordX(by);
//
//        g.glBegin(GL.GL_LINES);
//        g.glVertex2d(ax, ay);
//        g.glVertex2d(bx, by); //the fourth (w) component is zero!
//        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glLineWidth(1.5f);
        g.glColor3f(R, G, B);
        drawObject(g);
    }

}
