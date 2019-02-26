package lib2D;

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
 * author: Jose Maria del Aguila
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

    PointCloud pc;
    DrawPointCloud dc;

    Circle cir;
    DrawCircle dcir;

    RayLine r1;
    DrawRay dr1, dr2;

    Line l1;
    DrawLine dl1;

    DrawPoint dp;

    Polygon pol;
    DrawPolygon dpol;

    //Exercise B
    Point pB;
    DrawPoint drpB;

    Circle circleB;
    DrawCircle drcrclB;

    Circle secantCircle;
    DrawCircle drSecant;

    Circle tangentCircle;
    DrawCircle drTangent;

    SegmentLine segB;
    DrawSegment drsgB;

    RayLine rayB;
    DrawRay drrB;

    Line lnB;
    DrawLine drlB;
    
    PointCloud pcResults;
    DrawPointCloud drPointCloud;

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
                        exercise = A;
                        break;
                    case 'A':
                        exercise = A;
                        break;
                    case 'b':
                        exercise = B;
                        break;
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

        pc = new PointCloud(30);
        dc = new DrawPointCloud(pc);

        pc.save("datosNube.txt");

        //Ray with random points
        r1 = new RayLine(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT), pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        dr1 = new DrawRay(r1);

        //Segment with random points
        s1 = new SegmentLine(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT), pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        ds1 = new DrawSegment(s1);

        //Line with random points
        l1 = new Line(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT), pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        dl1 = new DrawLine(l1);

        //Circle located at the most central point
        Point center = pc.centralPoint();
        Point p = new Point(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        dp = new DrawPoint(center);

        cir = new Circle(center, center.distance(p));
        dcir = new DrawCircle(cir);

        pol = new Polygon();
        pol.add(pc.xMaxPoint());
        pol.add(pc.yMaxPoint());
        pol.add(pc.xMinPoint());
        pol.add(pc.yMinPoint());

        Point p1 = new Point(pc.getPoint(random.nextUInt() % POINT_CLOUD_VERT));
        for (int i = 0; i < pol.vertexSize(); i++) {
            if (p1.left(pol.getEdge(i).getA(), pol.getEdge(i).getB())) {
                pol.addPosition(i + 1, p1);
                break;
            }
        }

        dpol = new DrawPolygon(pol);
    }

    protected void drawExerciseA() {
        dc.drawObjectC(gl, 1.0f, 0.0f, 0.5f);
        dl1.drawObjectC(gl, 0.0f, 1.0f, 1.0f);
        ds1.drawObjectC(gl, 1.0f, 0.5f, 0.5f);
        dr1.drawObjectC(gl, 1.0f, 1.0f, 1.0f);
        dcir.drawObjectC(gl, 0.3f, 0.7f, 0.5f);
        dp.drawObjectC(gl, 0.6f, 0.6f, 0.6f);
        dpol.drawObjectC(gl, 0.5f, 0.5f, 0.5f);
    }

    protected void initExerciseB() throws SegmentLine.Invalid_T_Parameter {
        
        pcResults = new PointCloud();
        drPointCloud = new DrawPointCloud(pcResults);

        //Apartado A
        lnB = new Line(new Point(40.0f, 70.0), new Point(-40.f, 70.0f)); // A
        drlB = new DrawLine(lnB);

        rayB = new RayLine(new Point(-60.0f, -30.0), new Point(0.f, 20.0f)); // B
        drrB = new DrawRay(rayB);

        circleB = new Circle(new Point(0.0f, 0.0f), 30); // C
        drcrclB = new DrawCircle(circleB);

        segB = new SegmentLine(new Point(30f, 60.0), new Point(30.f, -60.0f)); // D
        drsgB = new DrawSegment(segB);

        pB = new Point(-50.0f, 50.0f);
        drpB = new DrawPoint(pB);

        //Apartado B
        System.out.println("Distancia de P-A: " + lnB.distance(new Vector(pB.getX(), pB.getY())));
        System.out.println("Distancia de P-B: " + rayB.distPointRay(new Vector(pB.getX(), pB.getY())));
        System.out.println("Distancia de P-D: " + segB.distance(new Vector(pB.getX(), pB.getY())));

        //Apartado C
        Vector inters1 = new Vector();
        Vector inters2 = new Vector();
        if (lnB.intersect(rayB, inters1)) {
            System.out.println("Punto de inerseccion A - B: " + inters1.getX() + " " + inters1.getY());
            pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
        }
        if (circleB.intersects(lnB, inters1, inters2) == Circle.RelationCircleLine.INTERSECTS) {
            System.out.println("Punto de inerseccion A - C: " + inters1.getX() + " " + inters1.getY());
            pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
        }
        if (lnB.intersect(segB, inters1)) {
            System.out.println("Punto de inerseccion A - D: " + inters1.getX() + " " + inters1.getY());
            pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
        }
        if (circleB.intersects(rayB, inters1, inters2) != Circle.RelationCircleLine.NO_INTERSECTS) {
            if (circleB.intersects(rayB, inters1, inters2) == Circle.RelationCircleLine.TANGENTS) {
                System.out.println("Punto de inerseccion B - C: " + inters1.getX() + " " + inters1.getY());
                pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
            } else {
                System.out.println("Punto de inerseccion B - C: " + inters1.getX() + " " + inters1.getY());
                System.out.println("Punto de inerseccion B - C: " + inters2.getX() + " " + inters2.getY());
                pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
                pcResults.addPoint(new Point(inters2.getX(),inters2.getY()));
            }
        }
        if (rayB.intersect(segB, inters1)) {
            System.out.println("Punto de inerseccion B - D: " + inters1.getX() + " " + inters1.getY());
            pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
        }

        if (circleB.intersects(segB, inters1, inters2) != Circle.RelationCircleLine.NO_INTERSECTS) {
            if (circleB.intersects(segB, inters1, inters2) == Circle.RelationCircleLine.TANGENTS) {
                System.out.println("Punto de inerseccion C - D: " + inters1.getX() + " " + inters1.getY());
                pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
            } else {
                System.out.println("Punto de inerseccion C - D: " + inters1.getX() + " " + inters1.getY());
                System.out.println("Punto de inerseccion C - D: " + inters2.getX() + " " + inters2.getY());
                pcResults.addPoint(new Point(inters1.getX(),inters1.getY()));
                pcResults.addPoint(new Point(inters2.getX(),inters2.getY()));
            }
        }
        // Apartado D
        secantCircle = new Circle(new Point(27.5f, 0.0f), 10);
        drSecant = new DrawCircle(secantCircle);

        if (circleB.circleRelation(secantCircle) == Circle.RelationCircles.SECANT) {
            System.out.println("Es secante");
        }

        tangentCircle = new Circle(new Point(-20.0f, 0.0f), 10);
        drTangent = new DrawCircle(tangentCircle);

        if (circleB.circleRelation(tangentCircle) == Circle.RelationCircles.INNER_TANG) {
            System.out.println("Es tangente");
        }
    }

    protected void drawExerciseB() {
        drcrclB.drawObjectC(gl, 0.3f, 0.7f, 0.5f);
        drsgB.drawObjectC(gl, 1.0f, 0.5f, 0.5f);
        drlB.drawObjectC(gl, 0.0f, 1.0f, 1.0f);
        drrB.drawObjectC(gl, 1.0f, 1.0f, 1.0f);
        drpB.drawObjectC(gl, 0.5f, 0.5f, 0.5f);
        drSecant.drawObjectC(gl, 0.7f, 0.7f, 0.5f);
        drTangent.drawObjectC(gl, 0.3f, 0.1f, 0.5f);
        
        drPointCloud.drawObjectC(gl, 0.1f, 0.1f, 1.0f);
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
            initExerciseB();

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

        if (exercise == B) {
            drawExerciseB();
        }

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
