package lib2D;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Class that represents a Polygon fromed by Vertex objects
 */
public class Polygon {

    protected int nVertexs;
    protected ArrayList<Vertex> Vertexs;

    /**
     * Default empty constructor
     */
    public Polygon() {
        Vertexs = new ArrayList<Vertex>();
        nVertexs = 0;
    }

    public Polygon(int nV) {
        Vertexs = new ArrayList<Vertex>(nV);
        nVertexs = nV;
    }

    public Polygon(Polygon pl) {
        Vertexs = new ArrayList<Vertex>(pl.Vertexs);
        nVertexs = pl.nVertexs;
    }

    public Polygon(ArrayList<Vertex> vert, int nV) {
        Vertexs = new ArrayList<Vertex>(vert);
        nVertexs = nV;
    }

    public Polygon copy() {
        Polygon nuevoPolygon = new Polygon(nVertexs);
        nuevoPolygon.Vertexs = new ArrayList<Vertex>(Vertexs);
        nuevoPolygon.nVertexs = nVertexs;
        return nuevoPolygon;
    }

    public void copy(Polygon pl) {
        Vertexs.clear();                           //Se limpia el vector.
        Vertexs = new ArrayList<Vertex>(pl.Vertexs);    //Se copy el vector.
        nVertexs = pl.nVertexs;
    }

    public void set(Vertex v, int pos) {
        if (pos >= 0 && pos < nVertexs) {
            Vertex antiguo = new Vertex((Vertex) Vertexs.get(pos));
            antiguo.setPolygon(null);
            antiguo.setPosition(-1);
            Vertexs.set(pos, (Vertex) v);
            v.setPolygon(this);
            v.setPosition(pos);
        }
    }

    /**
     * Adds the vertex in the last position
     */
    public void add(Vertex v) {
        Vertexs.add((Vertex) v);
        v.setPolygon(this);
        v.setPosition(nVertexs);
        nVertexs++;
    }

    /**
     * Adds the point in the last position
     */
    public void add(Point p) {
        Vertex v = new Vertex(p, this, nVertexs);
        Vertexs.add((Vertex) v);
        nVertexs++;
    }

    public Vertex getVertexAt(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return (Vertex) Vertexs.get(pos);
        } else {
            return null;
        }
    }

    public Vertex setVertexAt(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return new Vertex((Vertex) Vertexs.get(pos));
        } else {
            return null;
        }
    }
    
    public void setVertexs(ArrayList<Vertex> v){
        this.Vertexs = v;
    }

    public int vertexSize() {
        return nVertexs;
    }

    public SegmentLine getEdge(int i) {
        return new SegmentLine(getVertexAt(i), getVertexAt((i + 1) % nVertexs));
    }

    /**
     * Polygon builder from file
     */
    public Polygon(String nombre) throws ReadIOException, FileNotFoundException, IOException {

        Vertexs = new ArrayList();
        Point punto;
        Vertex vert;
        String cadena[];
        String cad;
        FileReader f = new FileReader(nombre);
        BufferedReader b = new BufferedReader(f);
        while ((cad = b.readLine()) != null) {
            cadena = cad.split(" ");
            punto = new Point(Double.parseDouble(cadena[0]), Double.parseDouble(cadena[1]));
            vert = new Vertex(punto);
            Vertexs.add(vert);
        }
        b.close();
    }

    /**
     * Saves the coordinates of the polygon in file with the same format as the
     * constructor
     */
    public void save(String nombre) throws SaveIOException {
        //XXXXX
    }

    /**
     * Assuming that this is a convex polygon, indicate if the point p is inside
     * the polygon
     */
    public boolean pointInCovexPolygon(Point pt) {

        //XXXXX
        return true;
    }

    public boolean convex() {
        for (int i = 0; i < nVertexs; i++) {
            if (!getVertexAt(i).convex()) {
                return false;
            }
        }
        return true;
    }

    /*public boolean intersects(Line r, Vector interseccion) {
        //XXXXX
        return false;
    }

    public boolean intersects(RayLine r, Vector interseccion) {
        //XXXXX
        
        return false;
    }*/
    public boolean intersects(SegmentLine r, Vector interseccion) {
        //XXXXX
        return false;
    }

    public void out() {
        Vertex v;
        for (int i = 0; i < nVertexs; i++) {
            v = (Vertex) Vertexs.get(i);
            v.out();
        }
    }

}
