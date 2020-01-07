public class Vertex {

    public static final int X = 0, Y = 1, Z = 2;
    public double[] coordinates = new double[Engine.SPATIAL_DIMENSIONS];

    public Vertex(int x, int y, int z){
        //Should throw exception if any coordinate is negative, I think.
        coordinates[X] = x+1;
        coordinates[Y] = y+1;
        coordinates[Z] = z+1;
    }
    public double[] asVector(){
        return new double[] {coordinates[X],coordinates[Y],coordinates[Z]};
    }
    public String toString(){
        return "["+(int)coordinates[X]+" "+(int)coordinates[Y]+" "+(int)coordinates[Z]+"]";
    }

}
