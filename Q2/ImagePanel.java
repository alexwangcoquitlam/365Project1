package Q2;
import javax.swing.BorderFactory;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    private BufferedImage img, ditheredImg;
    private int dimensionX = 704, dimensionY = 576;

    ImagePanel(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(dimensionX, dimensionY));
    }

    public void repaint(BufferedImage input){
        img = input;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
