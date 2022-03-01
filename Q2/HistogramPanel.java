package Q2;
import javax.swing.BorderFactory;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class HistogramPanel extends JPanel {
    private int dimensionX, dimensionY;
    private int[][] histogramData;

    HistogramPanel(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(dimensionX, dimensionY));
    }

    public void repaint(int[][] input, int w, int h){
        dimensionX = w;
        dimensionY = h;
        histogramData = input;
        setPreferredSize(new Dimension(dimensionX, dimensionY));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int offset = 0;
        double normalizationFactorX = (double) dimensionX/ 3 / 255;

        for(int i = 0; i < histogramData.length; i++){
            offset = i*dimensionX/3;
            g.drawLine(offset, 0, offset, dimensionY);
            if (i == 0)
                g.setColor(Color.RED);
            else if (i == 1)
                g.setColor(Color.GREEN);
            else if (i == 2)
                g.setColor(Color.BLUE);

            double normalizationFactorY = (double) (dimensionY-10) / (double) GetMax(histogramData[i]);
            for(int j = 0; j < histogramData[0].length; j++){
                int x = (int) Math.ceil(j * normalizationFactorX) + offset;
                int y = (int) Math.ceil(dimensionY - histogramData[i][j] * normalizationFactorY);
                g.drawLine(x, dimensionY, x, y);
            }
            g.setColor(Color.BLACK);
        }
    }

    private int GetMax(int[] input) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (input[i] > max)
                max = input[i];
        }
        return max;
    }
}
