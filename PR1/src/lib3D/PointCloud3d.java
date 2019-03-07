package lib3D;

import Util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import lib2D.BasicGeom;

public class PointCloud3d {

    ArrayList<Vect3d> points;

    public PointCloud3d() {
        points = new ArrayList<Vect3d>();
    }

    public PointCloud3d(int size) {
        points = new ArrayList<Vect3d>();
        if (size > 0) {

            Random r = new Random(40);

            double x, y, z;
            int pos;
            for (int i = 0; i < size; i++) {
                pos = r.nextBoolean() ? 1 : -1;
                x = r.nextDouble() * pos * BasicGeom.RANGE;
                pos = r.nextBoolean() ? 1 : -1;
                y = r.nextDouble() * pos * BasicGeom.RANGE;
                pos = r.nextBoolean() ? 1 : -1;
                z = r.nextDouble() * pos * BasicGeom.RANGE;

                Vect3d p = new Vect3d(x, y, z);
                points.add(p);
            }
        }
    }

    public int size() {
        return points.size();
    }

    public void clear() {
        points.clear();
    }

//    public PointCloud (Mesh m){
//        // constructor a partir de una mesh
//    }
    public PointCloud3d(String path) throws ReadIOException {
        File f;
        FileReader fr = null;
        BufferedReader br;

        points = new ArrayList<Vect3d>();
        try {
            f = new File(path);
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split(" ");
                Vect3d p = new Vect3d(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[2]));
                points.add(p);
            }
        } catch (IOException e) {
            throw new ReadIOException();
        } catch (NumberFormatException e) {
            throw new ReadIOException();
        } finally { // If the exception is launched
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e2) {
            }
        }
    }

    public void save(String nombre) throws SaveIOException {
        FileWriter file = null;
        PrintWriter pw;
        try {
            file = new FileWriter(nombre);
            pw = new PrintWriter(file);
            String line;
            for (int i = 0; i < points.size(); i++) {
                line = Double.toString(points.get(i).getX()) + " "
                        + Double.toString(points.get(i).getY()) + " "
                        + Double.toString(points.get(i).getZ());
                pw.println(line);
            }

        } catch (IOException e) {
            throw new SaveIOException();
        } finally {
            try {
                if (null != file) {
                    file.close();
                }
            } catch (IOException e2) {
            }
        }
    }

    public Vect3d getPoint(int pos) {
        if ((pos >= 0) && (pos < points.size())) {
            return points.get(pos);
        }
        return null;
    }

    public void addPoint(Vect3d p) {
        points.add(p);
    }

    AABB getAABB() {
        Vect3d minus = new Vect3d(99.0, 99.0, 99.0);
        Vect3d max = new Vect3d(-99.0, -99.0, -99.0);

        for (Vect3d v : points) {

            if (v.x < minus.x) {
                minus.setX(v.x);
            }
            if (v.y < minus.y) {
                minus.setY(v.y);
            }
            if (v.z < minus.z) {
                minus.setZ(v.z);
            }
            if (v.x > max.x) {
                max.setX(v.x);
            }
            if (v.y > max.y) {
                max.setY(v.y);
            }
            if (v.z > max.z) {
                max.setZ(v.z);
            }
        }

        return new AABB(minus, max);
    }
}
