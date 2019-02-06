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
        //XXXX

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glLineWidth(3.0f);
        g.glColor3f(R, G, B);
        drawObject(g);

    }

}
