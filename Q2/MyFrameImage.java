package Q2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MyFrameImage extends JFrame implements ActionListener {

    private JPanel panel;
    private JButton fileButton;
    private JLabel fileLabel;
    private ImagePanel imgPanel;

    MyFrameImage() {
        this.setTitle("CMPT 365 Project 1 Question 2");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0, 26, 51));

        fileLabel = new JLabel("No File Selected");
        fileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        fileButton = new JButton("Choose File");
        fileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileButton.addActionListener(this);

        imgPanel = new ImagePanel();
        imgPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(fileButton);
        panel.add(fileLabel);
        panel.add(imgPanel);

        this.add(panel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String fileName = file.getName();
                int i = fileName.lastIndexOf(".");

                if (i != -1) {
                    String extension = fileName.substring(i);
                    if (!extension.equals(".png")) {
                        fileLabel.setText("Not a .png file.");
                        fileLabel.setForeground(Color.RED);
                        Timer timer = new Timer(2000, event -> {
                            fileLabel.setText("No File Selected");
                            fileLabel.setForeground(Color.BLACK);
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        fileLabel.setText(fileName);
                        try {
                            BufferedImage img = ImageIO.read(file);
                            GetRGBArray(img);
                            imgPanel.repaint(img);
                            
                        } catch (Exception ex) {
                            fileLabel.setText("Error reading .png.");
                            fileLabel.setForeground(Color.RED);
                            Timer timer = new Timer(2000, event -> {
                                fileLabel.setText("No File Selected");
                                fileLabel.setForeground(Color.BLACK);
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    }
                }
            }
        }
    }

    private int[][] GetRGBArray(BufferedImage image){
        int w = image.getWidth(), h = image.getHeight();
        int[][] output = new int[w][h];
        
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                output[x][y] = image.getRGB(x, y);
                int R = (output[x][y] >> 16) & 0xff;
                int G = (output[x][y] >> 8) & 0xff;
                int B = (output[x][y]) & 0xff;
                System.out.println("RGB: [" + R + "," + G + "," + B + "]");
            }
        }

        return output;
    }
}
