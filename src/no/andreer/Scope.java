package no.andreer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Scope extends JPanel {

    private BufferedImage image = new BufferedImage(400, 256, BufferedImage.TYPE_INT_RGB);
    private final JFrame jframe;

    Scope() {
        jframe = new JFrame();
        jframe.add(this);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setSize(450, 320);
        jframe.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }

    public void update(byte[] b) {
        image = new BufferedImage(400, 256, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < b.length; i++) {
            image.setRGB(i, b[i]+127, 0xffffff);
        }
        image.flush();
        this.repaint();
    }

    public void close() {
        jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
    }
}
