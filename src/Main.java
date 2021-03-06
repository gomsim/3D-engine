import java.awt.*;

public class Main {

    public static void main(String[] args){
        Engine engine = Engine.initiate();

        int size = 500;
        int walllength = 2000;

        //Triangles behind each other
        /*engine.add(
                new Artifact(
                -size/2,
                -size/2,
                size*1,
                size,
                size,
                0,
                Color.RED,
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size/2,0,0),
                        new Vertex(size,size,0))
        ),      new Artifact(
                -size/2,
                -size/2,
                size*2,
                size,
                size,
                0,
                Color.YELLOW,
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size/2,0,0),
                        new Vertex(size,size,0))
        ),      new Artifact(
                size,
                -size/2,
                size*1,
                1,
                size,
                size,
                Color.GREEN,
                new Polygon(
                        new Vertex(0,size,walllength),
                        new Vertex(0,0,0),
                        new Vertex(0,size,0)),
                new Polygon(
                        new Vertex(0,0,walllength),
                        new Vertex(0,0,0),
                        new Vertex(0,size,walllength))
        ));*/

        int yellowPos = 200;
        //Intersecting triangles
        /*engine.add(
                new Artifact(
                        -size/2,
                        -size/2,
                        500,
                        size,
                        size,
                        size,
                        Color.RED,
                        new Polygon(
                                new Vertex(0,size,0),
                                new Vertex(size/2,0,size),
                                new Vertex(size,size,0)),
                        new Polygon(
                                new Vertex(0,size,yellowPos ),
                                new Vertex(size/2,0,yellowPos ),
                                new Vertex(size,size,yellowPos ))
                ));*/

        //Triangles side by side
        /*engine.add(new Artifact(
                -size/2,
                -size/2,
                500,
                size,
                size,
                size,
                Color.RED,
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size/2,0,0),
                        new Vertex(size,size,0))
        ),new Artifact(
                size/2,
                -size/2,
                400,
                size,
                size,
                size,
                Color.YELLOW,
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size/2,0,0),
                        new Vertex(size,size,0))
        ));*/

        //Intersecting triangles
        /*engine.add(new Artifact(
                -size/2,
                -size/2,
                500,
                size,
                size,
                size,
                Color.RED,
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size/2,0,size),
                        new Vertex(size,size,0))
        ),new Artifact(
                size/2,
                -size/2,
                600,
                size,
                size,
                size,
                Color.YELLOW,
                new Polygon(
                        new Vertex(0,size,size),
                        new Vertex(size/2,0,0),
                        new Vertex(size,size,size))
        ));*/

        //Tetrahedron
        /*engine.add(new Artifact(
                -size/2,
                -size/2,
                1000,
                size,
                size,
                size,
                Color.RED,
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size/2,0,size/2),
                        new Vertex(size,size,0)),
                new Polygon(
                        new Vertex(size,size,0),
                        new Vertex(size/2,0,size/2),
                        new Vertex(size/2,size,size)),
                new Polygon(
                        new Vertex(size/2,size,size),
                        new Vertex(size/2,0,size/2),
                        new Vertex(0,size,0)),
                new Polygon(
                        new Vertex(0,size,0),
                        new Vertex(size,size,0),
                        new Vertex(size/2,size,size))
        ));*/

        int bigSize = size * 5;
        //Large Tetrahedron
        /*engine.add(new Artifact(
                -bigSize/2,
                -bigSize/2,
                -bigSize/2,
                bigSize,
                bigSize,
                bigSize,
                Color.RED,
                new Polygon(
                        new Vertex(0,bigSize,0),
                        new Vertex(bigSize/2,0,bigSize/2),
                        new Vertex(bigSize,bigSize,0)),
                new Polygon(
                        new Vertex(bigSize,bigSize,0),
                        new Vertex(bigSize/2,0,bigSize/2),
                        new Vertex(bigSize/2,bigSize,bigSize)),
                new Polygon(
                        new Vertex(bigSize/2,bigSize,bigSize),
                        new Vertex(bigSize/2,0,bigSize/2),
                        new Vertex(0,bigSize,0)),
                new Polygon(
                        new Vertex(0,bigSize,0),
                        new Vertex(bigSize,bigSize,0),
                        new Vertex(bigSize/2,bigSize,bigSize))
        ));*/

        int cielingHeight = size*2;
        //Large Room
        /*engine.add(new Artifact(
                -bigSize/2,
                -cielingHeight + size/2,
                -size/2,
                bigSize,
                bigSize,
                bigSize,
                Color.ORANGE,
                //FRONT
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(bigSize,0,0),
                        new Vertex(0,cielingHeight,0)),
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(0,cielingHeight,0),
                        new Vertex(bigSize,cielingHeight,0)),
                //BACK
                new Polygon(
                        new Vertex(0,0,bigSize),
                        new Vertex(bigSize,0,bigSize),
                        new Vertex(0,cielingHeight,bigSize)),
                new Polygon(
                        new Vertex(bigSize,0,bigSize),
                        new Vertex(0,cielingHeight,bigSize),
                        new Vertex(bigSize,cielingHeight,bigSize)),
                //LEFT
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(0,cielingHeight,0),
                        new Vertex(0,cielingHeight,bigSize)),
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(0,cielingHeight,bigSize),
                        new Vertex(0,0,bigSize)),
                //RIGHT
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(bigSize,cielingHeight,0),
                        new Vertex(bigSize,cielingHeight,bigSize)),
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(bigSize,cielingHeight,bigSize),
                        new Vertex(bigSize,0,bigSize)),
                //UP
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(bigSize,0,0),
                        new Vertex(0,0,bigSize)),
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(bigSize,0,bigSize),
                        new Vertex(0,0,bigSize)),
                //DOWN
                new Polygon(
                        new Vertex(0,cielingHeight,0),
                        new Vertex(bigSize,cielingHeight,0),
                        new Vertex(0,cielingHeight,bigSize)),
                new Polygon(
                        new Vertex(bigSize,cielingHeight,0),
                        new Vertex(bigSize,cielingHeight,bigSize),
                        new Vertex(0,cielingHeight,bigSize))
        ));*/

        //LARGE CUBE ROOM
        engine.add(new Artifact(
                -bigSize/2,
                -bigSize + size/2,
                -size/2,
                bigSize,
                bigSize,
                bigSize,
                Color.ORANGE,
                //FRONT
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(bigSize,0,0),
                        new Vertex(0,bigSize,0)),
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(0,bigSize,0),
                        new Vertex(bigSize,bigSize,0)),
                //BACK
                new Polygon(
                        new Vertex(0,0,bigSize),
                        new Vertex(bigSize,0,bigSize),
                        new Vertex(0,bigSize,bigSize)),
                new Polygon(
                        new Vertex(bigSize,0,bigSize),
                        new Vertex(0,bigSize,bigSize),
                        new Vertex(bigSize,bigSize,bigSize)),
                //LEFT
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(0,bigSize,0),
                        new Vertex(0,bigSize,bigSize)),
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(0,bigSize,bigSize),
                        new Vertex(0,0,bigSize)),
                //RIGHT
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(bigSize,bigSize,0),
                        new Vertex(bigSize,bigSize,bigSize)),
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(bigSize,bigSize,bigSize),
                        new Vertex(bigSize,0,bigSize)),
                //UP
                new Polygon(
                        new Vertex(0,0,0),
                        new Vertex(bigSize,0,0),
                        new Vertex(0,0,bigSize)),
                new Polygon(
                        new Vertex(bigSize,0,0),
                        new Vertex(bigSize,0,bigSize),
                        new Vertex(0,0,bigSize)),
                //DOWN
                new Polygon(
                        new Vertex(0,bigSize,0),
                        new Vertex(bigSize,bigSize,0),
                        new Vertex(0,bigSize,bigSize)),
                new Polygon(
                        new Vertex(bigSize,bigSize,0),
                        new Vertex(bigSize,bigSize,bigSize),
                        new Vertex(0,bigSize,bigSize))
        ));
        engine.start();
    }
}
