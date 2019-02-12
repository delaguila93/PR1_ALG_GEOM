package lib2D;

/**
 * Class that represents a polygon vertex
 */
public class Vertex extends Point {

    protected int position;
    protected Polygon polig;

    /**
     * Vertex no asociated to any polygon (position = -1)
     */
    public Vertex() {
        x = 0;
        y = 0;
        position = 0;
        polig = null;
    }

    /**
     * Vertex no asociated to any polygon (position = -1)
     */
    public Vertex(Point p) {
        x = p.x;
        y = p.y;
        position = -1;
        polig = null;
    }

    public Vertex(Point p, Polygon pol) {
        x = p.x;
        y = p.y;
        position = -1;
        polig = pol;
    }

    public Vertex(Point p, Polygon pol, int pos) {
        x = p.x;
        y = p.y;
        position = pos;
        polig = pol;
    }

    public Vertex(double xx, double yy, Polygon pol) {
        x = xx;
        y = yy;
        position = -1;
        polig = pol;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public int getPositionInPolygon() {
        return position;
    }

    public Polygon getPolygon() {
        return polig;
    }

    public void setPoint(Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * This method has been declared as protected because inconsistencies can be produced by error
     */
    protected void setPosition(int pos) {
        position = pos;
    }

    /**
     * This method has been declared as protected because inconsistencies can be produced by error
     */
    protected void setPolygon(Polygon pl) {
        polig = pl;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    boolean convex() {

        Vertex iPlus1 = new Vertex(next());
        Vertex iMinus1 = new Vertex(previous());
        return iPlus1.left(iMinus1, this);
    }

    boolean concave() {
        return !convex();
    }

    /**
     * Next vertex in counter-clockwise order
     *
     */
    public Vertex next() {

        int posPlus1 = (position + 1) % polig.nVertexs;
        return polig.getVertexAt(posPlus1);
    }

    /**
     * Next vertex in clockwise order
     */
    public Vertex previous() {
        int sizePolig = polig.vertexSize();
        int posPlus = (position - 1 + sizePolig) % sizePolig;
        return polig.getVertexAt(posPlus);
    }

    /**
     * Next edge in counter-clockwise order
     */
    public SegmentLine nextEdge() {
        int posPlus1 = (position + 1) % polig.nVertexs;
        return polig.getEdge(posPlus1);
    }

    /**
     * Next edge in clockwise order
     */
    public SegmentLine previousEdge() {
        int sizePolig = polig.vertexSize();
        int posPlus = (position - 1 + sizePolig) % sizePolig;
        return polig.getEdge(posPlus);
    }

    @Override
    public void out() {
        System.out.print("Coordinate x: ");
        System.out.println(x);
        System.out.print("Coordinate y: ");
        System.out.println(y);

        System.out.print("Position: ");
        System.out.println(position);
    }

}
