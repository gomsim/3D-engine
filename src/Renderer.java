import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.TreeMap;

public class Renderer extends JPanel {

    private Camera camera = new Camera();
    private BufferedImage img = new BufferedImage(Engine.SCREEN_WIDTH,Engine.SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    private TreeMap<Double,ColoredPolygon> polygons2D = new TreeMap<>();
    private boolean realTimeRender;

    public Renderer(){
        addMouseListener(new DebugPointerListener());
    }

    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if (realTimeRender){
            for (ColoredPolygon polygon: polygons2D.values()){
                graphics.setColor(polygon.color);
                graphics.drawPolygon(polygon.xpoints,polygon.ypoints,polygon.npoints);
            }
        }else{
            graphics.drawImage(img,0,0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,this);
        }
    }

    public void render(){
        if (realTimeRender)
            renderRealTime();
        else
            renderRaycast();
    }
    private void renderRealTime(){
        for (Artifact artifact: Engine.instance.getArtifacts()){
            for (Polygon polygon: artifact.getPolygons()){
                //I know how to get X and Y values down to lens distance values. But how do I paint all pixels inside polygons??
                ColoredPolygon polyInfo = camera.getFlatPolygon(polygon);
                polygons2D.put(polyInfo.distance, polyInfo);
            }
        }
    }
    private void renderRaycast(){
        System.out.println("Renderer: rendering");
        for (int y = 0; y < Engine.SCREEN_HEIGHT; y++){
            new Thread(new RenderingThread(y)).start();
        }
        System.out.println("Renderer: successfully rendered");
        //for each pixel ask camera for color of pixel and save in vector, then renderRaycast to screen
    }
    public void setRealTimeRender(boolean flag){
        realTimeRender = flag;
    }
    private class RenderingThread implements Runnable {

        int y;

        public RenderingThread(int y) {
            this.y = y;
        }

        public void run() {
            for (int x = 0; x < Engine.SCREEN_WIDTH; x++){
                pixels[y * Engine.SCREEN_WIDTH + x] = camera.getPixelColor(x, y).getRGB();
            }
            repaint();
        }
    }

    public TreeMap<Double, Artifact> getIntersectingArtifactsTest(int x, int y){//FOR TESTING
        return camera.getIntersectingArtifactsTest(x,y);
    }
    public Color getRayColorTest(int x, int y){//FOR TESTING
        return camera.getRayColorTest(x,y);
    }
    public PolyRayHitInfo getHitInfoTest(int x, int y){
        return camera.getHitInfoTest(x,y);
    }
    public static class ColoredPolygon extends java.awt.Polygon {

        public Color color;
        public double distance;

        public ColoredPolygon(int[] x, int[] y, int n, Color color, double distance){
            super(x,y,n);
            this.color = color;
            this.distance = distance;
        }
    }
    private class DebugPointerListener extends MouseAdapter {
        public void mouseClicked(MouseEvent event){
            int x = (int)(((double)1920/1535)*(double)event.getXOnScreen());
            int y = (int)(((double)1080/863)*(double)event.getYOnScreen());
            switch(event.getButton()){
                case MouseEvent.BUTTON1:
                    System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.err.println(getHitInfoTest(x,y));
                    break;
                case MouseEvent.BUTTON3:
                    System.err.println(getRayColorTest(x,y));
                    break;
            }
        }
    }
}
