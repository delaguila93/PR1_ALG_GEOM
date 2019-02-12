package lib2D;

import javax.media.opengl.*;

public class DrawPointCloud extends Draw {

    PointCloud pc;

    public DrawPointCloud(PointCloud p) {
        pc = p;
    }

    public PointCloud getPointCloud() {
        return pc;
    }

    @Override
    public void drawObject(GL g) {

        g.glPointSize(05);
        for (int i = 0; i < pc.size(); ++i) {
            g.glBegin(GL.GL_POINTS);
            g.glVertex2d(convCoordX(pc.getPoint(i).getX()), convCoordY(pc.getPoint(i).getY()));
            g.glEnd();
        }
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        this.drawObject(g);
    }

}
