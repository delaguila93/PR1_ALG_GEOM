/**
 * Java Library for Geometric Algorithms subject
 * 
 * @author Lidia Ortega, Alejandro Graciano
 * @version 1.0
 */


package lib3D;
import Util.RandomGen;
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

public class Test extends Frame implements GLEventListener,
        MouseListener,
        MouseMotionListener,
        MouseWheelListener {

    public static void main(String[] args) {
        Draw.HEIGH = HEIGHT;
        Draw.WIDTH = WIDTH;
        Draw.DEPTH = 100;

        Frame frame = new Frame("Alg. Geom. Pract. 2");
        canvas = new GLCanvas();

        canvas.addGLEventListener(new Test());
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

        frame.addKeyListener(new KeyListener() {
            long clock = 0;

            /**
             * Handle the key typed event from the text field.
             */
            public void keyTyped(KeyEvent e) {
//                        System.out.println(e + "KEY TYPED: ");
            }

            /**
             * Handle the key pressed event from the text field.
             */
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'E':
                    case 'e':
                        visualizeAxis = !visualizeAxis;
                        canvas.repaint();
                        clock = e.getWhen();
                        break;
                   
                    case 27: // esc
                        System.exit(0);
                }

            }

            /**
             * Handle the key released event from the text field.
             */
            public void keyReleased(KeyEvent e) {
                clock = e.getWhen();
//                        System.out.println(e + "KEY RELEASED: ");
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
    static int HEIGHT = 800, WIDTH = 800;
    //static int HEIGHT = 10, WIDTH = 10;

    Vect3d cameraPosition, cameraLookAt, cameraUp;

    // Assigment
    static boolean visualizeAxis = true;
    
    DrawAxis dAxis = new DrawAxis();
    DrawTriangle3d dt, dtp;
    
    Plane pl1;
    Plane pl2;
    DrawPlane dplane1;
    DrawPlane dplane2;
    
    DrawPointCloud3d dcloud;
    DrawPointCloud3d dupper;
    DrawPointCloud3d dlower;
    DrawPointCloud3d dlay;
    DrawTriangle3d dprojTr;
    DrawRay3d dr;
    TriangleMesh cat;
    DrawTriangleMesh dcat;
    
    DrawAABB dbb;

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

   

    public void initExerciseA() {
        // Point cloud
        
        RandomGen random = RandomGen.getInst();
        PointCloud3d pc = new PointCloud3d(50);
        dcloud = new DrawPointCloud3d(pc);
        
        dbb = new DrawAABB(pc.getAABB());
        // Plane
        Vect3d p1 = pc.getPoint(0);
        Vect3d p2 = pc.getPoint(1);
        Vect3d p3 = pc.getPoint(2);
        
        pl1 = new Plane(p1,p2,p3,true);
        dplane1 = new DrawPlane(pl1);
        
        pl2 = new Plane (p1,p2,p3,true);
        dplane2 = new DrawPlane(pl2);

        Triangle3d t = new Triangle3d(p1, p2, p3);
        dt = new DrawTriangle3d(t);
/*
        Plane pl = new Plane(p1, p2, p3, true);
        dpl = new DrawPlane(pl);

        // Classify the points
        PointCloud3d upper = new PointCloud3d();
        PointCloud3d lower = new PointCloud3d();
        PointCloud3d lay = new PointCloud3d();
        dupper = new DrawPointCloud3d(upper);
        dlower = new DrawPointCloud3d(lower);
        dlay = new DrawPointCloud3d(lay);

        for (int i = 0; i < 50; ++i) {
            Vect3d p = pc.getPoint(i);
            PointPosition pos = t.classify(p);

            if (pos == PointPosition.COPLANAR) {
                lay.addPoint(p);
            } else if (pos == PointPosition.POSITIVE) {
                upper.addPoint(p);
            } else {
                lower.addPoint(p);
            }

        }

        // Projection
        Vect3d pl0 = lower.getPoint(0);
        Vect3d pl1 = lower.getPoint(1);
        Vect3d pl2 = lower.getPoint(2);
        dtp = new DrawTriangle3d(new Triangle3d(pl0, pl1, pl2));

       //Vect3d projP1 = pl.project(pl0);
       // Vect3d projP2 = pl.project(pl1);
       // Vect3d projP3 = pl.project(pl2);

        //Triangle3d projTr = new Triangle3d(projP1, projP2, projP3);
        //dprojTr = new DrawTriangle3d(projTr);

        // Ray
        double dist = BasicGeom.ZERO;

        Vect3d maxLower = null;
        for (int i = 0; i < lower.size(); ++i) {
            Vect3d p = lower.getPoint(i);
            double currentDist = pl.distance(p);
            if (currentDist > dist) {
                dist = currentDist;
                maxLower = p;
            }
        }

        dist = BasicGeom.ZERO;

        Vect3d maxUpper = null;
        for (int i = 0; i < upper.size(); ++i) {
            Vect3d p = upper.getPoint(i);
            double currentDist = pl.distance(p);
            if (currentDist > dist) {
                dist = currentDist;
                maxUpper = p;
            }
        }
        Ray3d r = new Ray3d(new Vect3d(0, 0, 0), maxLower);
        dr = new DrawRay3d(r);

*/

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
         
        
        initExerciseA();
       

        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
        drawable.addMouseWheelListener(this);

    }

    public void reshape(GLAutoDrawable drawable,
            int x, int y, int width, int height) {
        WIDTH = width; // new width and height saved
        HEIGHT = height;

        GL gl = drawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) {
            height = 1;
        }
        // keep ratio
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 0.1, 2000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    public void displayExerciseA(GL gl) {
        dplane1.drawObjectC(gl, 1.0f, 1.0f, 0.0f,1.0f,false);
        dplane2.drawObjectC(gl, 1.0f, 1.0f, 0.0f,1.0f,true);
        dcloud.drawObjectC(gl, 0.0f, 0.0f, 0.0f);
        dbb.drawObjectC(gl, 0.5f, 0.5f, 0.5f);
        /*
        dpl.drawObjectC(gl, 0.f, 0.f, 0.f, 0.2f);

        dlower.drawObjectC(gl, 1.0f, 0.0f, 1.0f);
        dupper.drawObjectC(gl, 0.0f, 1.0f, 1.0f);
        dlay.drawObjectC(gl, 1.0f, 1.0f, 0.0f);

        dprojTr.drawObjectC(gl, 1.0f, 0.0f, 0.0f);
        dtp.drawObjectC(gl, 1.0f, 0.0f, 1.0f);
        dr.drawObject(gl);
*/
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
        if (visualizeAxis) {
            DrawAxis ejes = new DrawAxis();
            ejes.drawObject(gl);
        }

        
        displayExerciseA(gl);
        

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = 360.0f * ((float) (x - oldMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (oldMouseY - y) / (float) size.height);
        oldMouseX = x;
        oldMouseY = y;
        view_rotx += thetaX;
        view_roty += thetaY;
    }

    public void mouseMoved(MouseEvent e) {
    }
    

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
