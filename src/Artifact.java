import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Artifact {

    //Dimensions of containing box
    private double x, y, z;
    public double width, height, depth;

    //shape
    private HashSet<Polygon> polygons = new HashSet<>();
    private Color color;

    //Will have a colour and a smoothness/diffuse value giving the strength of reflected light based on the light angle an view angle

    public Artifact(int x, int y, int z, int width, int height, int depth, Color color, Polygon ... polygons){
        this.x = x;
        this.y = y;
        this.z = z;
        //this.width = width;
        //this.height = height;
        //this.depth = depth;
        this.polygons.addAll(Arrays.asList(polygons));
        for (Polygon polygon:polygons)
            polygon.setParent(this);
        this.color = color;
        setBounds();
    }
    public void addPolygon(Polygon ... polygons){
        this.polygons.addAll(Arrays.asList(polygons));
        for (Polygon polygon:polygons)
            polygon.setParent(this);
        setBounds();
    }

    public HashSet<Polygon> getPolygons(){
        return polygons;
    }
    public boolean insideBox(double[] point){
        int marginal = 1;
        return point[Vertex.X] >= x-marginal &&
                point[Vertex.X] <= x + width+marginal &&
                point[Vertex.Y] >= y-marginal &&
                point[Vertex.Y] <= y + height+marginal &&
                point[Vertex.Z] >= z-marginal &&
                point[Vertex.Z] <= z + depth+marginal;
    }
    public double intersectDistance(double[] dir) {
        double[] lineSource = new double[]{0, 0, 0};
        double intersectDistance = Double.MAX_VALUE;
        final int V = 0;
        final int N = 1;

        double[] v1 = new double[]{x, y, z};
        double[] v2 = new double[]{x + width, y, z};
        double[] v3 = new double[]{x, y + height, z};
        double[] v4 = new double[]{x + width, y + height, z};
        double[] v5 = new double[]{x, y, z + depth};
        double[] v6 = new double[]{x + width, y, z + depth};
        double[] v7 = new double[]{x, y + height, z + depth};
        double[] v8 = new double[]{x + width, y + height, z + depth};

        double[][][] planes = new double[][][]{
                {v1, VectorUtil.getNormal(VectorUtil.subtract(v2, v1), VectorUtil.subtract(v3, v1))},//front
                {v2, VectorUtil.getNormal(VectorUtil.subtract(v4, v2), VectorUtil.subtract(v6, v2))},//right
                {v5, VectorUtil.getNormal(VectorUtil.subtract(v1, v5), VectorUtil.subtract(v7, v5))},//left
                {v2, VectorUtil.getNormal(VectorUtil.subtract(v1, v2), VectorUtil.subtract(v6, v2))},//up
                {v3, VectorUtil.getNormal(VectorUtil.subtract(v7, v3), VectorUtil.subtract(v4, v3))},//down
                {v7, VectorUtil.getNormal(VectorUtil.subtract(v5, v7), VectorUtil.subtract(v8, v7))},//back
        };
        for (double[][] plane : planes) {
            double[] n = plane[N];
            double[] v = plane[V];
            double s = (VectorUtil.dotProduct(n, VectorUtil.subtract(v, lineSource))) /
                    (VectorUtil.dotProduct(n, VectorUtil.subtract(dir, lineSource)));
            if (s < intersectDistance && s >= 0 && insideBox(VectorUtil.multiply(dir, s)))
                intersectDistance = s;
        }
        return intersectDistance;
    }

    public PolyRayHitInfo getHitInfo(double[] lineSource,double[] dir){
        //double[] lineSource = new double[] {0,0,0};
        double[] localPos = globalPointToLocal(lineSource);
        PolyRayHitInfo hitInfo = new PolyRayHitInfo(null,Double.MAX_VALUE,null,null);
        //Should be for connectedPolygons, not all raw polygons
        for (Polygon polygon: polygons){
            double intersectDistance = polygon.intersectDistance(localPos, dir);
            if (intersectDistance < hitInfo.distance && intersectDistance >= 0) {
                double[] hitPoint = VectorUtil.add(lineSource, VectorUtil.multiply(dir, intersectDistance));
                hitInfo = new PolyRayHitInfo(hitPoint,
                        VectorUtil.length(hitPoint),
                        color,
                        null); // null as for now. I don't need the normal unless I'm calculating light bounces
            }
        }
        return hitInfo;
    }

    private double[] globalPointToLocal(double[] global){
        double[] local = new double[Engine.SPATIAL_DIMENSIONS];
        local[VectorUtil.X] = global[VectorUtil.X] - x;
        local[VectorUtil.Y] = global[VectorUtil.Y] - y;
        local[VectorUtil.Z] = global[VectorUtil.Z] - z;
        return local;
    }
    private double[] localPointToGlobal(double[] local){
        double[] global = new double[Engine.SPATIAL_DIMENSIONS];
        global[VectorUtil.X] = global[VectorUtil.X] + x;
        global[VectorUtil.Y] = global[VectorUtil.Y] + y;
        global[VectorUtil.Z] = global[VectorUtil.Z] + z;
        return global;
    }

    //Equals
    //hashcode


    public void translate(double[] dir){
        x += dir[VectorUtil.X];
        y += dir[VectorUtil.Y];
        z += dir[VectorUtil.Z];
    }
    public void scale(/*Some parameters*/){

    }
    public void rotate(int axis, double deg){
        deg /= 30;
        switch (axis){
            case VectorUtil.X: {
                double y1 = (Math.cos(deg) * y - Math.sin(deg) * z);
                double z1 = (Math.sin(deg) * y + Math.cos(deg) * z);
                y = y1;
                z = z1;
            }
                break;
            case VectorUtil.Y:
                double x1 = (Math.cos(deg)*x - Math.sin(deg)*z);
                double z1 = (Math.sin(deg)*x + Math.cos(deg)*z);
                x = x1;
                z = z1;
                break;
        }
        for (Polygon polygon: polygons){
            polygon.rotate(axis,deg);
        }
        setBounds();
    }
    private void setBounds(){
        if (polygons.isEmpty())
            return;
        final int MARGIN = 1;
        double[] polygonsXMax = new double[polygons.size()];
        double[] polygonsXMin = new double[polygons.size()];
        double[] polygonsYMax = new double[polygons.size()];
        double[] polygonsYMin = new double[polygons.size()];
        double[] polygonsZMax = new double[polygons.size()];
        double[] polygonsZMin = new double[polygons.size()];
        int i = 0;
        for (Polygon polygon: polygons){
            polygonsXMax[i] = polygon.maxCoordinate(VectorUtil.X);
            polygonsXMin[i] = polygon.minCoordinate(VectorUtil.X);
            polygonsYMax[i] = polygon.maxCoordinate(VectorUtil.Y);
            polygonsYMin[i] = polygon.minCoordinate(VectorUtil.Y);
            polygonsZMax[i] = polygon.maxCoordinate(VectorUtil.Z);
            polygonsZMin[i] = polygon.minCoordinate(VectorUtil.Z);
            i++;
        }
        Arrays.sort(polygonsXMax);
        Arrays.sort(polygonsXMin);
        Arrays.sort(polygonsYMax);
        Arrays.sort(polygonsYMin);
        Arrays.sort(polygonsZMax);
        Arrays.sort(polygonsZMin);
        x += polygonsXMin[0];
        y += polygonsYMin[0];
        z += polygonsZMin[0];
        width = (polygonsXMax[polygonsXMax.length-1] - polygonsXMin[0]+MARGIN*2);
        height = (polygonsYMax[polygonsYMax.length-1] - polygonsYMin[0]+MARGIN*2);
        depth = (polygonsZMax[polygonsZMax.length-1] - polygonsZMin[0]+MARGIN*2);
        for (Polygon polygon: polygons){
            polygon.translate(new double[] {-polygonsXMin[0]+MARGIN,-polygonsYMin[0]+MARGIN,-polygonsZMin[0]+MARGIN});
        }
    }
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(color+" ["+x+" "+y+" "+z+"] ("+width+" "+height+" "+depth+")");
        builder.append("\n");
        for (Polygon polygon: polygons){
            builder.append(polygon);
            builder.append("\n");
        }
        return builder.toString();
    }
}
