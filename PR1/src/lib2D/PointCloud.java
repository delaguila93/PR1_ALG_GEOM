package lib2D;

import java.io.BufferedReader;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

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
     * Constructor of the point cloud of random form giving the total number of points.
     */
    public PointCloud(int tam) {
        //XXXXX
    }

    /**
     * Constructor of the point cloud from the coordinates of points stored in file
     */
    public PointCloud(String nombre) throws ReadIOException {
        //XXXXX
    }

    public void addPoint(Point p) {
        nubepuntos.add(p);
    }

    public int size() {
        return nubepuntos.size();
    }

    /**
     * Saves the cloud of points in file with the same format used by the constructor
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
        //XXXXX
        return new Point();
    }

}
