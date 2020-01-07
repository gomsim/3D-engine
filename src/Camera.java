import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class    Camera {

    private final int LENS_DISTANCE = 1000;//(int)Math.sqrt(Math.pow(Engine.SCREEN_WIDTH,2)+Math.pow(Engine.SCREEN_HEIGHT,2));
    private final int FOCAL_X = Engine.SCREEN_WIDTH/2;
    private final int FOCAL_Y = Engine.SCREEN_HEIGHT/2;
    private final Color EMPTY_COLOR = Color.BLACK;
    private double[][][] rayVectors;

    public Camera(){
        rayVectors = createRayVectorMatrix();
    }
    private double[][][] createRayVectorMatrix(){
        double[][][] rayVectors = new double[Engine.SCREEN_WIDTH][Engine.SCREEN_HEIGHT][Engine.SPATIAL_DIMENSIONS];
        for (int x = 0; x < Engine.SCREEN_WIDTH; x++){
            for (int y = 0; y < Engine.SCREEN_HEIGHT; y++){
                rayVectors[x][y] = VectorUtil.unitVector(x-FOCAL_X,y-FOCAL_Y,LENS_DISTANCE);
            }
        }
        return rayVectors;
    }

    public Renderer.ColoredPolygon getFlatPolygon(Polygon polygon){
        Vertex[] vertices = polygon.getVertices();
        double[] a = vertices[0].asVector();
        double[] b = vertices[1].asVector();
        double[] c = vertices[2].asVector();
        return null;
    }
    public Color getPixelColor(int x, int y){
        return getFirstColor(rayVectors[x][y]);
    }
    private Color getFirstColor(double[] dir) {
        TreeMap<Double, Artifact> intersects = Engine.instance.intersectingArtifacts(dir);
        for (Map.Entry<Double, Artifact> entry : intersects.entrySet()) {
            double distance = entry.getKey();
            Artifact artifact = entry.getValue();
            PolyRayHitInfo hitInfo = artifact.getHitInfo(Engine.ORIGIN,dir);

            if (hitInfo.color != null) {
                int red = (int) (hitInfo.color.getRed() / (VectorUtil.length(hitInfo.pos)/800));
                int green = (int) (hitInfo.color.getGreen() / (VectorUtil.length(hitInfo.pos)/800));
                int blue = (int) (hitInfo.color.getBlue() / (VectorUtil.length(hitInfo.pos)/800));
                red = red <= 0 ? 0 : red >= 255 ? 255 : red;
                green = green <= 0 ? 0 : green >= 255 ? 255 : green;
                blue = blue <= 0 ? 0 : blue >= 255 ? 255 : blue;
                return new Color(red, green, blue);
            }
        }
        return EMPTY_COLOR;
    }
    public TreeMap<Double, Artifact> getIntersectingArtifactsTest(int x, int y){
        return Engine.instance.intersectingArtifacts(rayVectors[x][y]);
    }
    public Color getRayColorTest(int x, int y){
        return getFirstColor(rayVectors[x][y]);
    }
    public PolyRayHitInfo getHitInfoTest(int x, int y){
        double[] dir= rayVectors[x][y];
        TreeMap<Double, Artifact> intersects = Engine.instance.intersectingArtifacts(rayVectors[x][y]);
        PolyRayHitInfo hitInfo = null;
        for (Map.Entry<Double, Artifact> entry : intersects.entrySet()) {
            double distance = entry.getKey();
            Artifact artifact = entry.getValue();
            return hitInfo = artifact.getHitInfo(Engine.ORIGIN,dir);
        }
        return null;
    }

}
