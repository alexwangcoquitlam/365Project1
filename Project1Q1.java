import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Project1Q1 {
    public static void main(String[] args){
        JFrame frame = InitializeFrame();
    }

    private static JFrame InitializeFrame(){
        JFrame frame = new JFrame("CMPT 365 Project 1");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 26, 51));
        frame.setSize(500, 500);

        JButton fileButton = new JButton("Choose File");
        return frame;
    }
}
