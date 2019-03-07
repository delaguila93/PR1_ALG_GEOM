package lib3D;



public class AABB {

    Vect3d min; //menor x,y,z
    Vect3d max; //max x, y, z

    public void setMin(Vect3d min) {
        this.min = min;
    }

    public void setMax(Vect3d max) {
        this.max = max;
    }

    public AABB(Vect3d min, Vect3d max) {
        this.min = min;
        this.max = max;
    }

    /**
     * get the extension vector 
     */
    public Vect3d getExtent (){
        
        Vect3d extent = new Vect3d();
        extent.x = (max.x - min.x) / 2;
        extent.y = (max.y - min.y) / 2;
        extent.z = (max.y - min.z) / 2;
        return extent;
    }
    
    /**
     * get the cube center  
     */
    public Vect3d getCenter (){
        Vect3d v = new Vect3d();
        v.x = (min.x + max.x) / 2;
        v.y = (min.y + max.y) / 2;
        v.z = (min.z + max.z) / 2;
        return v;
    }

    public Vect3d getMin() {
        return min;
    }

    public Vect3d getMax() {
        return max;
    }

    
    

}
