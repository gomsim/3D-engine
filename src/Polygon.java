public class Polygon {

    private static final int A = 0, B = 1, C = 2;
    private Vertex[] vertices = new Vertex[Engine.SPATIAL_DIMENSIONS];
    private Artifact parent;

    public Polygon(Vertex a, Vertex b, Vertex c){
        vertices[A] = a;
        vertices[B] = b;
        vertices[C] = c;
    }

    //equals
    //hashcode

    public double minCoordinate(int axis){
        double min = Integer.MAX_VALUE;
        for (Vertex vertex: vertices) {
            double coordinate = vertex.coordinates[axis];
            if (coordinate < min)
                min = coordinate;
        }
        return min;
    }
    public double maxCoordinate(int axis){
        double max = Integer.MIN_VALUE;
        for (Vertex vertex: vertices) {
            double coordinate = vertex.coordinates[axis];
            if (coordinate > max)
                max = coordinate;
        }
        return max;
    }
    public void setParent(Artifact parent){
        this.parent = parent;
    }
    public double intersectDistance(double[] lineSource, double[] dir){
        double[] n = getNormal();
        double[] u = dir;
        double[] w = VectorUtil.subtract(vertices[A].asVector(),lineSource);
        boolean parallel = VectorUtil.dotProduct(n,u) == 0;
        if (parallel)
            return -1;
        double s = (VectorUtil.dotProduct(n,w)) / (VectorUtil.dotProduct(n,u));
        if (intersectionInsidePolygon(VectorUtil.add(lineSource,VectorUtil.multiply(u,s))))
            return s;
        else
            return -1;
    }
    public Vertex[] getVertices(){
        return vertices;
    }
    private boolean intersectionInsidePolygon(double[] i){
        double[] u = VectorUtil.subtract(vertices[B].asVector(),vertices[A].asVector());
        double[] v = VectorUtil.subtract(vertices[C].asVector(),vertices[A].asVector());
        double[] w = VectorUtil.subtract(i, vertices[A].asVector());
        double uv = VectorUtil.dotProduct(u,v);
        double wv = VectorUtil.dotProduct(w,v);
        double wu = VectorUtil.dotProduct(w,u);
        double vv = VectorUtil.dotProduct(v);
        double uu = VectorUtil.dotProduct(u);
        double commonDenominator = (Math.pow(uv,2) - uu * vv);
        double s = (uv * wv - vv * wu) / commonDenominator;
        double t = (uv * wu - uu * wv) / commonDenominator;
        return s >= 0 && t >= 0 && t+s <= 1;
    }
    public double[] getNormal(){
        return VectorUtil.getNormal(VectorUtil.subtract(vertices[B].asVector(),vertices[A].asVector()),
                VectorUtil.subtract(vertices[C].asVector(),vertices[A].asVector()));
    }
    public void rotate(int axis, double deg){
        //System.out.println(parent);
        double cX = (double)parent.width/2;
        double cY = (double)parent.height/2;
        double cZ = (double)parent.depth/2;

        switch (axis){
            case VectorUtil.X:
                for (Vertex vertex: vertices) {
                    //vertex.coordinates[Vertex.Y] -= cY; //Kontroversiell
                    //vertex.coordinates[Vertex.Z] -= cZ; //Kontroversiell
                    double y1 = (Math.cos(deg) * vertex.coordinates[Vertex.Y] - Math.sin(deg) * vertex.coordinates[Vertex.Z]);
                    double z1 = (Math.sin(deg) * vertex.coordinates[Vertex.Y] + Math.cos(deg) * vertex.coordinates[Vertex.Z]);
                    vertex.coordinates[Vertex.Y] = y1;
                    vertex.coordinates[Vertex.Z] = z1;
                    //vertex.coordinates[Vertex.Y] += cY; //Kontroversiell
                    //vertex.coordinates[Vertex.Z] += cZ; //Kontroversiell
                }
            break;
            case VectorUtil.Y:
                for (Vertex vertex: vertices) {
                    //vertex.coordinates[Vertex.X] -= cX; //Kontroversiell
                    //vertex.coordinates[Vertex.Z] -= cZ; //Kontroversiell
                    double x1 = (Math.cos(deg)*vertex.coordinates[Vertex.X] - Math.sin(deg)*vertex.coordinates[Vertex.Z]);
                    double z1 = (Math.sin(deg)*vertex.coordinates[Vertex.X] + Math.cos(deg)*vertex.coordinates[Vertex.Z]);
                    vertex.coordinates[Vertex.X] = x1;
                    vertex.coordinates[Vertex.Z] = z1;
                    //vertex.coordinates[Vertex.X] += cX; //Kontroversiell
                    //vertex.coordinates[Vertex.Z] += cZ; //Kontroversiell
                }
                break;

        }
    }
    public void translate(double[] dir){
        for (Vertex vertex: vertices){
            vertex.coordinates[Vertex.X] += dir[VectorUtil.X];
            vertex.coordinates[Vertex.Y] += dir[VectorUtil.Y];
            vertex.coordinates[Vertex.Z] += dir[VectorUtil.Z];
        }
    }
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex: vertices){
            builder.append(vertex);
            builder.append("\n");
        }
        builder.append("   ---   ");
        return builder.toString();
    }
}
