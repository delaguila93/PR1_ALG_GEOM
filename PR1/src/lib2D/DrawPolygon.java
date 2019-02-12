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
        
        int tama = vp.vertexSize();

        g.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < tama; i++) {
            float sy = convCoordY(vp.getVertexAt(i).getY());
            float sx = convCoordX(vp.getVertexAt(i).getX());
            g.glVertex2f(sx, sy);
        }
        g.glEnd();


    }

    public void drawObjectLine(GL g) {
        
        int tama = vp.vertexSize();

        g.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < tama; i++) {
            float sy = convCoordY(vp.getVertexAt(i).getY());
            float sx = convCoordX(vp.getVertexAt(i).getX());
            g.glVertex2f(sx, sy);
        }
        g.glEnd();

    }
    
    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObjectLine(g);
    }

}
