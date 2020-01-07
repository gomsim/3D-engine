import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class Engine {

    public static Engine instance;
    private Renderer renderer = new Renderer();
    private ArrayList<Artifact> artifacts = new ArrayList<>();
    public static final int SPATIAL_DIMENSIONS = 3;
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;
    public static final double[] ORIGIN = new double[] {0,0,0};
    private Timer movementTimer = new Timer(25, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ((Timer)e.getSource()).stop();
            render();
        }
    });

    private Engine(){
        movementTimer.setRepeats(false);
        new Window(renderer);
        //addFloor();
    }
    public static Engine initiate(){
        instance = new Engine();
        return instance;
    }
    public void start(){
        renderer.render();
    }

    private void addFloor(){
        int size = 500;
        int floorSize = 5000;
        Artifact floor = new Artifact(
                -2000,
                size/2,
                -size*3,
                1,
                1,
                floorSize,
                Color.PINK);
        for (int x = 0; x < 4000; x+=100){
            floor.addPolygon(
                    new Polygon(
                            new Vertex(x+0,0,0),
                            new Vertex(x+0,0,floorSize),
                            new Vertex(x+3,0,0)),
                    new Polygon(
                            new Vertex(x+0,0,0),
                            new Vertex(x+0,0,floorSize),
                            new Vertex(x+3,0,floorSize)),
                    new Polygon(
                            new Vertex(0,0,3+x),
                            new Vertex(floorSize,0,0+x),
                            new Vertex(0,0,0+x)),
                    new Polygon(
                            new Vertex(0,0,3+x),
                            new Vertex(floorSize,0,3+x),
                            new Vertex(floorSize,0,0+x)));
        }
        add(floor);
    }

    public void render(){
        renderer.render();
    }
    public boolean add(Artifact ... artifact){
        boolean success = artifacts.addAll(Arrays.asList(artifact));
        return success;
    }
    public ArrayList<Artifact> getArtifacts(){
        return artifacts;
    }

    public TreeMap<Double, Artifact> intersectingArtifacts(double[] dir){
        TreeMap<Double,Artifact> intersects = new TreeMap<>();
        for (Artifact artifact: artifacts){
            double intersectDistance = artifact.intersectDistance(dir);
            if (intersectDistance >= 0 && intersectDistance < Double.MAX_VALUE){
                intersects.put(intersectDistance,artifact);
            }
        }
        return intersects;
    }
    public void moveForward(double units){
        movementTimer.start();
        for (Artifact artifact: artifacts){
            artifact.translate(new double[] {0,0,-units});
        }
    }
    public void moveBackward(double units){
        movementTimer.start();
        for (Artifact artifact: artifacts){
            artifact.translate(new double[] {0,0,units});
        }
    }
    public void moveLeft(double units){
        movementTimer.start();
        for (Artifact artifact: artifacts){
            artifact.translate(new double[] {units,0,0});
        }
    }
    public void moveRight(double units){
        movementTimer.start();
        for (Artifact artifact: artifacts){
            artifact.translate(new double[] {-units,0,0});
        }
    }
    public void moveUp(double units){
        movementTimer.start();
        for (Artifact artifact: artifacts){
            artifact.translate(new double[] {0,units,0});
        }
    }
    public void moveDown(double units){
        movementTimer.start();
        for (Artifact artifact: artifacts){
            artifact.translate(new double[] {0,-units,0});
        }
    }
    public void rotateLeft(double deg){
        movementTimer.start();
        System.out.println();
        System.out.println("before");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
        for (Artifact artifact: artifacts){
            artifact.rotate(VectorUtil.Y,-deg);
        }
        System.out.println("after");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
    }
    public void rotateRight(double deg){
        movementTimer.start();
        System.out.println();
        System.out.println("before");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
        for (Artifact artifact: artifacts){
            artifact.rotate(VectorUtil.Y,deg);
        }
        System.out.println("after");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
    }
    public void rotateUp(double deg){
        movementTimer.start();
        System.out.println();
        System.out.println("before");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
        for (Artifact artifact: artifacts){
            artifact.rotate(VectorUtil.X,-deg);
        }
        System.out.println("after");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
    }
    public void rotateDown(double deg){
        movementTimer.start();
        System.out.println();
        System.out.println("before");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
        for (Artifact artifact: artifacts){
            artifact.rotate(VectorUtil.X,deg);
        }
        System.out.println("after");
        for (Artifact artifact: artifacts)
            System.out.println(artifact);
    }
}
