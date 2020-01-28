import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer extends JPanel {

    private Camera camera = new Camera();
    private BufferedImage img = new BufferedImage(Engine.SCREEN_WIDTH,Engine.SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();

    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(img,0,0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,this);
    }

    public void render(){
        renderRaycast();
    }

    private void renderRaycast(){
        int numProcessors = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numProcessors];
        System.out.println("Renderer: rendering");
        for (int startY = 0; startY < numProcessors; startY++){
            threads[startY] = new Thread(new RenderingThread(startY,numProcessors));
            threads[startY].start();
        }
        try {
            for (Thread thread : threads)
                thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Renderer: successfully rendered");
    }
    private class RenderingThread implements Runnable {

        int start, increment;

        public RenderingThread(int start, int increment) {
            this.start = start;
            this.increment = increment;
        }

        public void run() {
            for (int x = 0; x < Engine.SCREEN_WIDTH; x++){
                for (int y = start; y < Engine.SCREEN_HEIGHT; y += increment){
                    pixels[y * Engine.SCREEN_WIDTH + x] = camera.getPixelColor(x, y).getRGB();
                }
            }
            repaint();
        }
    }
}
