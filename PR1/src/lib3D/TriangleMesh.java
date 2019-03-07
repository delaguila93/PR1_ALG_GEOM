package lib3D;

import Util.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public final class TriangleMesh {

    ArrayList<Face> faces;
    ArrayList<Vect3d> vertices;
    boolean setNormal;
    
/**
     * constructor from an obj file 
     */

    public TriangleMesh(String filename) throws IOException {
        faces = new ArrayList<Face>();
        vertices = new ArrayList<Vect3d>();
        setNormal = false;
        String obj = "obj";

        File file;
        FileReader fr;
        BufferedReader reader;

        file = new File(filename);
        fr = new FileReader(file);
        reader = new BufferedReader(fr);

        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals(obj)) {
            System.out.println("Only obj model sare supported ");
        } else {
            loadobject(reader);
            setNormals();
            setNormal = true;
        }
    }

    
    /**
     * get an array of triangles 
     */
    public ArrayList<Triangle3d> getTriangules() {
        ArrayList<Triangle3d> triangles = new ArrayList<Triangle3d>();
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            Vect3d b = vertices.get(f.v2 - 1);
            Vect3d c = vertices.get(f.v3 - 1);
            triangles.add(new Triangle3d(a, b, c));
        }
        return triangles;
    }

    /**
     * constructor from an obj file 
     */
    public double[] getVerticesTriangles() {
        double[] tri = new double[faces.size() * 9];
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            tri[i * 9] = a.x;
            tri[i * 9 + 1] = a.y;
            tri[i * 9 + 2] = a.z;
            Vect3d b = vertices.get(f.v2 - 1);
            tri[i * 9 + 3] = b.x;
            tri[i * 9 + 4] = b.y;
            tri[i * 9 + 5] = b.z;
            Vect3d c = vertices.get(f.v3 - 1);
            tri[i * 9 + 6] = c.x;
            tri[i * 9 + 7] = c.y;
            tri[i * 9 + 8] = c.z;
        }
        return tri;
    }
    
    /**
     * get all vertices  
     */

    public double[] getVertices() {
        double[] vertex = new double[3 * vertices.size()];
        int j = 0;
        for (int i = 0; i < vertices.size(); i++) {
            vertex[j++] = vertices.get(i).x;
            vertex[j++] = vertices.get(i).y;
            vertex[j++] = vertices.get(i).z;

        }
        return vertex;
    }

    /**
     * get the index of all faces 
     */

    public int[] getIndiceFaces() {
        int[] faces = new int[3 * this.faces.size()];
        int j = 0;
        for (int i = 0; i < this.faces.size(); i++) {
            faces[j++] = this.faces.get(i).v1;
            faces[j++] = this.faces.get(i).v2;
            faces[j++] = this.faces.get(i).v3;
        }
        return faces;
    }
    
    /**
     * get triangle in position i
     */

    public Triangle3d getTriangle(int i) {
        Face f = faces.get(i);
        Vect3d a = vertices.get(f.v1 - 1);
        Vect3d b = vertices.get(f.v2 - 1);
        Vect3d c = vertices.get(f.v3 - 1);
        return new Triangle3d(a, b, c);
    }


    /**
     * get the vertex in position i 
     */
    public Vect3d getVertice(int i) {
        return new Vect3d(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
    }

    /**
     * define all the normals, assuming the correct orientation of triangles 
     */

    public void setNormals() {
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            Vect3d b = vertices.get(f.v2 - 1);
            Vect3d c = vertices.get(f.v3 - 1);
            Triangle3d t = new Triangle3d(a, b, c);
            f.setNormal(t.normal());
        }
        setNormal = true;
    }

    
    /**
     * get array with all normal vectors 
     */
    public double[] getNormals() {
        double[] nor = new double[3 * faces.size()];
        if (!setNormal) {
            setNormals();
        }
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d b = f.getNormal();
            nor[i * 3] = b.x;
            nor[i * 3 + 1] = b.y;
            nor[i * 3 + 2] = b.z;
        }
        return nor;
    }

    
    /**
     * reader of obj model 
     *
     * @param br variable fichero
     */
    public void loadobject(BufferedReader br) {
        String line = "";

        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("  ", " ");
                if (line.length() > 0) {
                    if (line.startsWith("v ")) {
                        float[] vert = read3Floats(line);
                        Vect3d point = new Vect3d(vert[0], vert[1], vert[2]);
                        vertices.add(point);
                    } else if (line.startsWith("vt")) {

                        continue;

                    } else if (line.startsWith("vn")) {

                        continue;
                    } else if (line.startsWith("f ")) {
                        int[] faces = read3Integer(line);
                        this.faces.add(new Face(faces));
                    } else if (line.startsWith("g ")) {
                        continue;
                    } else if (line.startsWith("usemtl")) {
                        continue;
                    } else if (line.startsWith("mtllib")) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("GL_OBJ_Reader.loadObject() failed at line: " + line);
        }

        System.out.println("Obj loader: vertices " + vertices.size()
                + " faces " + faces.size());

    }

    public int getFacesSize() {
        return faces.size();
    }

    public int getVerticesSize() {
        return vertices.size();
    }

    private int[] read3Integer(String line) {
        try {
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            if (st.countTokens() == 2) {
                return new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0};
            } else {
                return new int[]{Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())};
            }
        } catch (NumberFormatException e) {
            System.out.println("GL_OBJ_Reader.read3Floats(): error on line '" + line + "', " + e);
            return null;
        }
    }

    private float[] read3Floats(String line) {
        try {
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            if (st.countTokens() == 2) {
                return new float[]{Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken()),
                    0};
            } else {
                return new float[]{Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken())};
            }
        } catch (NumberFormatException e) {
            System.out.println("GL_OBJ_Reader.read3Floats(): error on line '" + line + "', " + e);
            return null;
        }
    }

    
    /**
     * get the AABB of the model 
     */
    
    public AABB getAABB() {
        Vect3d minus = new Vect3d();
        Vect3d max = new Vect3d();

        for (Vect3d v : vertices) {
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
