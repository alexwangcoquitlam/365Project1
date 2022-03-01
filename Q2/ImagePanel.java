package Q2;
import javax.swing.BorderFactory;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    private Color[][] colorArray;
    private int imgW, imgH;
    private int dimensionX, dimensionY;
    int step;

    ImagePanel(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(dimensionX, dimensionY));
    }

    public void repaint(Color[][] input, int w, int h){
        imgW = w;
        imgH = h;
        dimensionX = w;
        dimensionY = h;
        colorArray = input;
        setPreferredSize(new Dimension(dimensionX, dimensionY));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int x = 0; x < imgW; x++){
            for(int y = 0; y < imgH; y++){
                g.setColor(colorArray[x][y]);
                g.drawLine(x, y, x, y);
            }
        }
    }
}
