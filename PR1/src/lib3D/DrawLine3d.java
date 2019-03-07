package lib3D;

import Util.*;
import javax.media.opengl.GL;
import lib2D.Draw;

public class DrawLine3d extends Draw{
    Line3d line;

    public DrawLine3d(Line3d e) {
        line = e;
    }

    public Line3d getLine() {
        return line;
    }

    @Override
    public void drawObject(GL g) {
        
        //XXXX
        
        g.glBegin(GL.GL_LINES); 
        //
        
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);

    }
}
