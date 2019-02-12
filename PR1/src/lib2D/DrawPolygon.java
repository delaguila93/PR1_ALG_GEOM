package lib2D;

import javax.media.opengl.*;

/**
 * Class for drawing a Polygon
 */
public class DrawPolygon extends Draw {

    Polygon vp;

    public DrawPolygon(Polygon p) {
        vp = p;
    }

    /**
     Draw the interior of the polygon
     */
    
    @Override
    public void drawObject(GL g) {
        
        //int tama = vp.vertexSize();

        g.glBegin(GL.GL_POLYGON);
        // iterate through the vertices
        g.glEnd();

    }

    public void drawObjectLine(GL g) {
        
        //int tama = vp.vertexSize();

        g.glBegin(GL.GL_LINE_LOOP);
        // iterate through the vertices
        g.glEnd();

    }
    
    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        drawObject(g);

    }

}
