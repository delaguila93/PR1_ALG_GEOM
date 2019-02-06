package org.pr1;

import lib2D.*;
import com.sun.opengl.util.Animator;
import Util.GeomMethods;
import Util.RandomGen;
import java.awt.Frame;
import java.awt.BorderLayout;

import javax.media.opengl.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.media.opengl.glu.GLU;

/**
 * PR1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class PR1 extends Frame implements GLEventListener {

    static int A = 0;
    static int B = 1;
    static int HEIGHT = 800;
    static int WIDTH = 800;
    static int POINT_CLOUD_VERT = 20;
    static GL gl; // interface to OpenGL
    static GLCanvas canvas; // drawable in a frame
    static GLCapabilities capabilities;
    static boolean visualizeAxis = true;
    static int exercise = A;
    static Animator animator;

    // Geometric data in memory
    // Exercise A 


    SegmentLine s1;
    DrawSegment ds1;
    
    /*    PointCloud pc;
    DrawPointCloud dc;

    RayLine r1;
    DrawRay dr1, dr2;

    Line l1;
    DrawLine dl1;

    Vector v1;
    DrawVector dv1;

    Polygon pol;
    DrawPolygon dpol;

    Circle cir;
    DrawCircle dcir;*/


    public PR1(String title) {
        super(title);

        // 1. specify a drawable: canvas
        capabilities = new GLCapabilities();
        capabilities.setDoubleBuffered(false);
        canvas = new GLCanvas();

        // 2. listen to the events related to canvas: reshape
        canvas.addGLEventListener(this);

        // 3. add the canvas to fill the Frame container
        add(canvas, BorderLayout.CENTER);

        // 4. interface to OpenGL functions
        gl = canvas.getGL();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//				animator.stop(); // stop animation
                System.exit(0);
            }
        });

        addKeyListener(new KeyListener() {
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
                    case 'a':
                    case 'A':
                        exercise = A;
                        break;
                    case 'b':
                    case 'B':
                        exercise = B;
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
    }

    protected void initExerciseA() throws Exception {
        RandomGen random = RandomGen.getInst();
        /*pc = new PointCloud(POINT_CLOUD_VERT);
        //pc = new PointCloud("logs/nube17.txt");
        pc.save("nube17.txt");
        dc = new DrawPointCloud(pc);

        //Segments with 2 random points*/
        s1 = new SegmentLine(new Point(0.0f,0.0f),new Point(50.0f,50.0f));
        ds1 = new DrawSegment(s1);

        /*//Ray with random points
        r1 = new RayLine(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT), pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        dr1 = new DrawRay(r1);

        //Liney with random points
        l1 = new Line(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT), pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        dl1 = new DrawLine(l1);

        // Polygon
        ArrayList< Point> coord = new ArrayList< Point>();
        GeomMethods.get4Corners(coord, pc);
        pol = GeomMethods.create4Polygon(coord.get(0), coord.get(1), coord.get(2), coord.get(3));
        dpol = new DrawPolygon(pol);

        //Circle located at the most central point
        Point center = pc.centralPoint();
        cir = new Circle(center, 20);
        dcir = new DrawCircle(cir);*/
    }

    protected void drawExerciseA() {
        /*dpol.drawObjectC(gl, 0.5f, 0.5f, 0.5f);
        dcir.drawObjectC(gl, 0.3f, 0.7f, 0.5f);*/
        ds1.drawObjectC(gl, 1.0f, 0.5f, 0.5f);
       /* dr1.drawObjectC(gl, 1.0f, 1.0f, 1.0f);
        dl1.drawObjectC(gl, 0.0f, 1.0f, 1.0f);
        dc.drawObjectC(gl, 1.0f, 0.0f, 0.5f);*/
    }

    
    protected void initExerciseB() {
        ///XXXX

    }

  
    
    protected void drawExerciseB() {
        //XXXXXX
    }

     
    // called once for OpenGL initialization
    public void init(GLAutoDrawable drawable) {

        animator = new Animator(canvas);
        animator.start(); // start animator thread
        // display OpenGL and graphics system information
        System.out.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println(drawable.getChosenGLCapabilities());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        RandomGen random = RandomGen.getInst();
        //random.setSeed(5308170449555L);
        System.err.println("SEED: " + random.getSeed());

        try {

            initExerciseA();
            //initExerciseB();

        } catch (Exception ex) {
            System.out.println("Error en el dibujado");
            ex.printStackTrace();
        }

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {

        Draw.WIDTH = WIDTH = width; // new width and height saved
        Draw.HEIGH = HEIGHT = height;
        //DEEP = deep;
        if (Draw.HEIGH < Draw.WIDTH) {
            Draw.WIDTH = Draw.HEIGH;
        }
        if (Draw.HEIGH > Draw.WIDTH) {
            Draw.HEIGH = Draw.WIDTH;
        }
        // 7. specify the drawing area (frame) coordinates
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -100, 100);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        if (HEIGHT < WIDTH) {
            gl.glTranslatef((WIDTH - HEIGHT) / 2, 0, 0);
        }
        if (HEIGHT > WIDTH) {
            gl.glTranslatef(0, (HEIGHT - WIDTH) / 2, 0);
        }

    }

    // called for OpenGL rendering every reshape
    public void display(GLAutoDrawable drawable) {

        // limpiar la pantalla
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        /* El color de limpiado ser? cero */
        gl.glClearDepth(1.0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        if (visualizeAxis) {
            DrawAxis ejes = new DrawAxis();
            ejes.drawObject(gl);
        }

        if (exercise == A) {
            drawExerciseA();
        }

        /*
        if (exercise == B) {
            drawExerciseB();
        }
         */

        gl.glFlush();
    }

    // called if display mode or device are changed
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    public static void main(String[] args) {
        Draw.HEIGH = HEIGHT;
        Draw.WIDTH = WIDTH;
        PR1 frame = new PR1("Prac1. Algoritmos Geometricos");
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
}


