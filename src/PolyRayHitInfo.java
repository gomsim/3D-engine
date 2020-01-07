import java.awt.*;

public class PolyRayHitInfo {

    public double[] pos;
    public Color color;
    public double[] normal;
    public double distance;
    //public double diffuse;

    public PolyRayHitInfo(double[] pos, double distance, Color color, double[] normal){
        this.pos = pos;
        this.distance = distance;
        this.color = color;
        this.normal = normal;
    }

    public String toString(){
        return
                "{\nHitPos: " + VectorUtil.asString(pos)+
                "\nDistance from origo: " + distance+
                "\nColor: " + color +"\n}";
    }
}
