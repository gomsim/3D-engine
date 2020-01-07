public class VectorUtil {

    public static final int X = 0, Y = 1, Z = 2;

    public static double[] unitVector(int x, int y, int z){
        double length = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
        return new double[] {x/length,y/length,z/length};
    }
    public static double[] unitVector(double[] vector){
        if (vector.length != Engine.SPATIAL_DIMENSIONS)
            throw new IllegalArgumentException("Vector not of length 3");
        double length = Math.sqrt(Math.pow(vector[X],2) + Math.pow(vector[Y],2) + Math.pow(vector[Z],2));
        return new double[] {vector[X]/length,vector[Y]/length,vector[Z]/length};
    }
    public static double[] subtract(double[] vec, double[] by){
        double[] result = new double[Engine.SPATIAL_DIMENSIONS];
        for (int i = 0; i < vec.length; i++)
            result[i] = vec[i] - by[i];
        return result;
    }
    public static double[] add(double[] vec, double[] by){
        double[] result = new double[Engine.SPATIAL_DIMENSIONS];
        for (int i = 0; i < vec.length; i++)
            result[i] = vec[i] + by[i];
        return result;
    }
    public static double[] add(double[] vec, double by){
        double[] result = new double[Engine.SPATIAL_DIMENSIONS];
        for (int i = 0; i < vec.length; i++)
            result[i] = vec[i] + by;
        return result;
    }
    public static double dotProduct(double[] a, double[] b){
        double dot = 0;
        for (int i = 0; i < a.length; i++)
            dot += a[i] * b[i];
        return dot;
    }
    public static double dotProduct(double[] vec){
        double dot = 0;
        for (int i = 0; i < vec.length; i++)
            dot += vec[i] * vec[i];
        return dot;
    }
    public static double[] multiply(double[] vec, double multiplier){
        double[] result = new double[Engine.SPATIAL_DIMENSIONS];
        for (int i = 0; i < vec.length; i++)
            result[i] = vec[i] * multiplier;
        return result;
    }
    public static double[] copy(double[] vec){
        double[] copy = new double[Engine.SPATIAL_DIMENSIONS];
        for (int i = 0; i < vec.length; i++)
            copy[i] = vec[i];
        return copy;
    }
    public static double[] getNormal(double[] a, double[] b){
        double[] normal = new double[Engine.SPATIAL_DIMENSIONS];
        normal[X] = a[Y] * b[Z] - a[Z] * b[Y];
        normal[Y] = a[Z] * b[X] - a[X] * b[Z];
        normal[Z] = a[X] * b[Y] - a[Y] * b[X];
        return unitVector(normal);
    }
    public static String asString(double[] vec){
        if (vec == null)
            return null;
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < vec.length; i++){
            builder.append(vec[i]);
            if (i < vec.length-1)
                builder.append(",");
            }
        builder.append("]");
        return builder.toString();
    }
    public static double length(double[] vec){
        double length = 0;
        for (double d: vec)
            length += Math.pow(d,2);
        return Math.sqrt(length);
    }
    public static int normalAxis(double[] normal){
        for (int d = 0; d < normal.length; d++){
            if (normal[d] != 0)
                return d;
        }
        return -1;
    }
}
