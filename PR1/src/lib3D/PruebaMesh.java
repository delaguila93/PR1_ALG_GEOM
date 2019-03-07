package lib3D;

import lib2D.BasicGeom;

import com.sun.opengl.util.Animator;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import lib2D.Draw;



public class PruebaMesh implements GLEventListener, 
                             MouseListener, 
                             MouseMotionListener,
                             MouseWheelListener
{
    public static void main(String[] args) {
    	Draw.HEIGH = HEIGHT;
    	Draw.WIDTH = WIDTH;
    	Draw.DEPTH = 100;
    			
        Frame frame = new Frame("Práctica 2 de Algoritmos Geométricos");
        GLCanvas canvas = new GLCanvas();
        
        canvas.addGLEventListener(new PruebaMesh());
        frame.add(canvas);
        frame.setSize(HEIGHT, WIDTH);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
   
    static GLCanvas canvas;
    // So we can zoom in and out 
    private float zoom = 0.0f;
    // rotating the scene
    private float view_rotx = 20.0f; //20
    private float view_roty = 30.0f; //30
    // remember last mouse position
    private int oldMouseX;
    private int oldMouseY;
    //static int HEIGHT = 800, WIDTH = 800;
    static int HEIGHT = 800, WIDTH = 800;
    
    Vect3d cameraPosition, cameraLookAt, cameraUp;
    
    public TriangleMesh modelo; //variable accesible en toda la clase
    TriangleMesh cat;
    DrawPointCloud3d dcloud;
    DrawTriangleMesh dcat;
    
   
    public void initLight(GL gl) {
        gl.glPushMatrix();
        gl.glShadeModel(GL.GL_SMOOTH);

        float ambient[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float specular[] = {0.2f, 0.0f, 0.0f, 1.0f};
        float position[] = {20.0f, 30.0f, 20.0f, 0.0f};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);

        gl.glEnable(GL.GL_NORMALIZE);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glPopMatrix();

    }
    
    
////    public void init(GLAutoDrawable drawable)
////    {	
////    	
////        GL gl = drawable.getGL();
////        // Set backgroundcolor and shading mode
////        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
////        gl.glShadeModel(GL.GL_FLAT);
////        // give me some light
////        float ambient[] = {1.0f,1.0f,1.0f,1.0f };
////        float diffuse[] = {1.0f,1.0f,1.0f,1.0f };
////        float specular[]= {0.2f,1.0f,0.2f,1.0f};
////        float position[] = {20.0f,30.0f,20.0f,0.0f };
////        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT,ambient,0);
////        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse,0);
////        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position,0);
////        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR, specular, 0);
////        // and some red material
////        float[] mambient ={ 0.1745f, 0.01175f, 0.01175f, 0.55f };
////        float[] mdiffuse ={0.61424f, 0.04136f, 0.04136f, 0.55f };
////        float[] mspecular ={0.727811f, 0.626959f, 0.626959f, 0.55f };
////        float mshine =76.8f ;
////        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,mambient,0);
////        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,mdiffuse,0);
////        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,mspecular, 0);
////        gl.glMaterialf (GL.GL_FRONT,GL.GL_SHININESS,mshine);
////        gl.glEnable(GL.GL_LIGHTING);
////        gl.glEnable(GL.GL_LIGHT0);
////        gl.glEnable(GL.GL_DEPTH_TEST);
////        gl.glEnable(GL.GL_NORMALIZE);
////        drawable.addMouseListener(this);
////        drawable.addMouseMotionListener(this);
////        
////        // se carga el modelo fuera del display
////        cameraPosition = new Vect3d(0, 10, -20);
////        cameraLookAt = new Vect3d(0, 0, 0);
////        cameraUp = new Vect3d(0, 1, 0);
////        
////        try {
////        modelo = new TriangleMesh ("./modelos/cat.obj");
////        System.out.println("Modelo cargado con " + modelo.getFacesSize() + "caras.");
////        
////        }  catch (Exception e) {
////	  System.out.println("Error en la lectura del fichero");
////	}
////
////        
////    }
    
    
    public void displayExerciseB(GL gl) {

        dcloud.drawObjectC(gl, 0, 1, 0);
        dcat.drawObjectC(gl, 0.3f, 0.3f, 0.3f);
     
    }

    public void initExerciseB() {

        PointCloud3d cloud = new PointCloud3d(5);
        dcloud = new DrawPointCloud3d(cloud);

        String filename = "modelos/cat.obj";
        try {
            cat = new TriangleMesh(filename);
            dcat = new DrawTriangleMesh(cat);

            //AABB bb = cat.getAABB();
            //dbb = new DrawAABB(bb);

        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        // Set backgroundcolor and shading mode
        gl.glEnable(GL.GL_BLEND);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        gl.glEnable(GL.GL_POINT_SMOOTH);
        // Set backgroundcolor and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        System.out.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println(drawable.getChosenGLCapabilities());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        cameraPosition = new Vect3d(100, 100, 100);
        cameraLookAt = new Vect3d(0, 0, 0);
        cameraUp = new Vect3d(0, 1, 0);
        
        initLight(gl);
         
        
        initExerciseB();

        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
        drawable.addMouseWheelListener(this);

    }
    
    
    public void reshape(GLAutoDrawable drawable, 
                        int x, int y, int width, int height)
    {
	WIDTH = width; // new width and height saved
        HEIGHT = height;

        GL gl = drawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) // no divide by zero
            height = 1;
        // keep ratio
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        
    }
    
        public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU(); // needed for lookat
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        glu.gluLookAt(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ(), // eye pos
                cameraLookAt.getX(), cameraLookAt.getY(), cameraLookAt.getZ(), // look at
                cameraUp.getX(), cameraUp.getY(), cameraUp.getZ());  // up

        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);

        // Fill this method with the proposed exercises
      
        displayExerciseB(gl);
 
        gl.glFlush();
    }
    
////    public void display(GLAutoDrawable drawable)
////    {
////        GL gl = drawable.getGL();
////        GLU glu = new GLU(); // needed for lookat
////        // Clear the drawing area
////        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
////
////        // Reset the current matrix to the "identity"
////        gl.glLoadIdentity();
////        
////        //glu.gluLookAt(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ(), // eye pos
////        //        cameraLookAt.getX(), cameraLookAt.getY(), cameraLookAt.getZ(), // look at
////        //        cameraUp.getX(), cameraUp.getY(), cameraUp.getZ());  // up
////        
////        //0,10,-10
////        glu.gluLookAt(0,10,-10,  // eye pos
////                     0,0,0,   // look at
////                     0,1,0);  // up
////
////        gl.glTranslatef(0.50f, 0.50f, 0.50f);       
////        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
////        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
////        gl.glTranslatef(-0.5f, -0.5f, -0.5f);
////
////
////    
////        gl.glBegin(GL.GL_LINES);
////	      gl.glVertex3d(0,5,0);
////	      gl.glVertex3d(0,-5,0);
////		gl.glEnd();
////        gl.glBegin(GL.GL_LINES);
////	      gl.glVertex3d(-5,0,0);
////	      gl.glVertex3d(5,0,0);
////		gl.glEnd();
////        gl.glBegin(GL.GL_LINES);
////	      gl.glVertex3d(0,0,0);
////	      gl.glVertex3d(0,0,5);
////		gl.glEnd();
////        
////                
////        
////        //dibujamos el modelo
////       
////        DrawTriangleMesh dmodel = new DrawTriangleMesh(modelo);
////        dmodel.drawObject(gl);
////        
////                
////         gl.glFlush();
////    }
    
    public void displayChanged(GLAutoDrawable drawable, 
                              boolean modeChanged, boolean deviceChanged)
    { }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { }
    public void mousePressed(MouseEvent e) {
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = 360.0f * ( (float)(x-oldMouseX)/(float)size.width);
        float thetaX = 360.0f * ( (float)(oldMouseY-y)/(float)size.height);
        oldMouseX = x;
        oldMouseY = y;
        view_rotx += thetaX;
        view_roty += thetaY;
    }
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getUnitsToScroll() < 0) {
            Segment3d seg = new Segment3d(cameraPosition, cameraLookAt);
            cameraPosition = seg.getPoint(zoom - ((double) e.getUnitsToScroll() / 100.0f));
        } else {
            Segment3d seg = new Segment3d(cameraPosition, cameraLookAt);
            cameraPosition = seg.getPoint(zoom - ((double) e.getUnitsToScroll() / 100.0f));
        }
    }
}