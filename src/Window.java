import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {

    private static final int MOVEMENT_SPEED = 250;
    private static final int TURNING_SPEED = 6;

    public Window(Renderer renderer){
        setSize(Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT);//Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        addKeyListener(new MoveListener());
        //setOpacity(0.3f);//FOR TESTING

        renderer.setBounds(getX(),getY(),getWidth(),getHeight());
        add(renderer);

        setVisible(true);
    }

    private class MoveListener extends KeyAdapter {
        public void keyPressed(KeyEvent event){
            int keyPress = event.getKeyCode();
            switch (keyPress){
                case KeyEvent.VK_A:
                    System.out.println("A");
                    Engine.instance.moveLeft(MOVEMENT_SPEED);
                    break;
                case KeyEvent.VK_D:
                    System.out.println("D");
                    Engine.instance.moveRight(MOVEMENT_SPEED);
                    break;
                case KeyEvent.VK_W:
                    System.out.println("W");
                    Engine.instance.moveForward(MOVEMENT_SPEED);
                    break;
                case KeyEvent.VK_S:
                    System.out.println("S");
                    Engine.instance.moveBackward(MOVEMENT_SPEED);
                    break;
                case KeyEvent.VK_SHIFT:
                    System.out.println("Shift");
                    Engine.instance.moveUp(MOVEMENT_SPEED);
                    break;
                case KeyEvent.VK_CONTROL:
                    System.out.println("Ctrl");
                    Engine.instance.moveDown(MOVEMENT_SPEED);
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("Right");
                    Engine.instance.rotateRight(TURNING_SPEED);
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("Left");
                    Engine.instance.rotateLeft(TURNING_SPEED);
                    break;
                case KeyEvent.VK_UP:
                    System.out.println("Up");
                    Engine.instance.rotateUp(TURNING_SPEED);
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("Down");
                    Engine.instance.rotateDown(TURNING_SPEED);
                    break;
            }
        }
    }

}
