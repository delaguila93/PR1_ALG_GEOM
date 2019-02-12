
package lib2D;



//import Util.Draw;
import javax.media.opengl.*;

/**
 * Class for drawing a circle/circuference
 */
public class DrawCircle extends Draw {
    
    Circle dc;

    public DrawCircle(Circle c) {
        dc = c;
    }

    public Circle getCircle() {
        return dc;
    }

   
    @Override
    public void drawObject(GL g) {
        
        
    }
    
    @Override
    public void drawObjectC(GL g, float red, float green, float blue) {

        g.glColor3f(red, green, blue);
        
        Polygon circle = this.dc.getPointsCircle();
        
        DrawPolygon dcir = new DrawPolygon(circle);
        dcir.drawObjectLine(g);
    }
    
    
    public void drawObjectCircle(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        
        Polygon circle = this.dc.getPointsCircle();
        
        DrawPolygon dcir = new DrawPolygon(circle);
        dcir.drawObject(g);
    }
    
    
       
}
