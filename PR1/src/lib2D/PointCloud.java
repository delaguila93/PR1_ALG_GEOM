package lib2D;

import java.io.BufferedReader;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import Util.*;
import java.util.Random;

class SaveIOException extends Exception {

    private static final long serialVersionUID = 1L;
}

class ReadIOException extends Exception {

    private static final long serialVersionUID = 2L;
}

/**
 * Class that represents a point cloud
 */
public class PointCloud {

    ArrayList<Point> nubepuntos;

    public PointCloud() {
        nubepuntos = new ArrayList<Point>();
    }

    /**
     * Constructor of the point cloud of random form giving the total number of
     * points.
     */
    public PointCloud(int tam) {
        nubepuntos = new ArrayList<Point>();

        RandomGen r = RandomGen.getInst();
        r.setSeed(SeedGenerator.getInst().getSeed());

        for (int i = 0; i < tam; i++) {
            nubepuntos.add(new Point(r.nextInt( BasicGeom.RANGE) - (BasicGeom.RANGE/2), r.nextInt( BasicGeom.RANGE) - (BasicGeom.RANGE/2)));
        }

    }

    /**
     * Constructor of the point cloud from the coordinates of points stored in
     * file
     */
    public PointCloud(String nombre) throws ReadIOException, FileNotFoundException, IOException {
        nubepuntos = new ArrayList();
        Point punto;
        String cadena[];
        String cad;
        FileReader f = new FileReader(nombre);
        BufferedReader b = new BufferedReader(f);
        while ((cad = b.readLine()) != null) {
            cadena = cad.split(" ");
            punto = new Point(Double.parseDouble(cadena[0]), Double.parseDouble(cadena[1]));
            nubepuntos.add(punto);
        }
        b.close();
    }

    public void addPoint(Point p) {
        nubepuntos.add(p);
    }

    public int size() {
        return nubepuntos.size();
    }

    /**
     * Saves the cloud of points in file with the same format used by the
     * constructor
     */
    public void save(String nombre) throws SaveIOException {
        //XXXXX
    }

    public Point getPoint(int pos) {
        if ((pos >= 0) && (pos < nubepuntos.size())) {
            return nubepuntos.get(pos);
        }
        return null;
    }

    /**
     * Returns the more central existing point in the cloud
     */
    public Point centralPoint() {
        ArrayList<Integer> totales = new ArrayList<Integer>();

        int num = nubepuntos.size();
        int suma = 0;
        for (int i = 0; i < num; i++) {
            suma = 0;
            for (int j = 0; j < num; j++) {
                suma += nubepuntos.get(i).distance(nubepuntos.get(j));
            }
            totales.add(suma);
        }
        ArrayList<Integer> totalesAux = new ArrayList<Integer>(totales);
        Collections.sort(totales);
        int pos = -1;
        for (int r = 0; r < totalesAux.size(); r++) {
            if (totalesAux.get(r).equals(totales.get(0))) {
                pos = r;
                break;
            }
        }
        return nubepuntos.get(pos);

    }

    public Point yMaxPoint() {
        Point punto = new Point();
        double ymax = -9999;
        for (Point p : nubepuntos) {
            if (p.y > ymax) {
                ymax = p.y;
                punto.set(p.x, p.y);
            }
        }
        return punto;
    }

    public Point yMinPoint() {
        Point punto = new Point();
        double ymin = 9999;
        for (Point p : nubepuntos) {
            if (p.y < ymin) {
                ymin= p.y;
                punto.set(p.x, p.y);
            }
        }
        return punto;
    }

    public Point xMaxPoint() {
        Point punto = new Point();
        double xmax = -9999;
        for (Point p : nubepuntos) {
            if (p.x > xmax) {
                xmax= p.x;
                punto.set(p.x, p.y);
            }
        }
        return punto;
    }

    public Point xMinPoint() {
        Point punto = new Point();
        double xmin = 9999;
        for (Point p : nubepuntos) {
            if (p.x < xmin) {
                xmin = p.x;
                punto.set(p.x, p.y);
            }
        }
        return punto;
    }
}
