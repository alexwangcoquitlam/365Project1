package Q1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MyFrameAudio extends JFrame implements ActionListener {
    JButton fileButton;
    JLabel fileLabel, totalSamplesLabel, samplingRateLabel;
    WavePanel wavePanel;

    MyFrameAudio() {
        this.setTitle("CMPT 365 Project 1 Question 1");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0, 26, 51));

        fileLabel = new JLabel("No File Selected");
        fileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        fileButton = new JButton("Choose File");
        fileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileButton.addActionListener(this);

        wavePanel = new WavePanel();
        wavePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = new JPanel();
        totalSamplesLabel = new JLabel("Total Samples: ");
        samplingRateLabel = new JLabel("Sampling Rate: ");
        infoPanel.add(totalSamplesLabel);
        infoPanel.add(samplingRateLabel);

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(fileButton);
        panel.add(fileLabel);
        panel.add(infoPanel);
        panel.add(wavePanel);

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
                    if (!extension.equals(".wav")) {
                        fileLabel.setText("Not a .wav file.");
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
                            AudioInputStream fileStream = AudioSystem.getAudioInputStream(file);
                            byte[] data = new byte[fileStream.available()];
                            fileStream.read(data);

                            int channels = fileStream.getFormat().getChannels();
                            int frameLength = (int) fileStream.getFrameLength();
                            int bitsPerSample = fileStream.getFormat().getSampleSizeInBits();

                            int sampleRate = (int) fileStream.getFormat().getSampleRate();
                            int totalSamples = data.length/(channels*bitsPerSample/8);
                            int[][] finalAudio = new int[channels][frameLength];
                            
                            if (bitsPerSample == 16) {
                                int index = 0;
                                for (int d = 0; d < data.length;) {
                                    for (int c = 0; c < channels; c++) {
                                        int low = (int) data[d];
                                        d++;
                                        int high = (int) data[d];
                                        d++;
                                        finalAudio[c][index] = (high << 8) + (low & 0x00ff);
                                    }
                                    index++;
                                }
                            }
                            else{
                                int index = 0;
                                for (int d = 0; d < data.length;) {
                                    for (int c = 0; c < channels; c++) {
                                        finalAudio[c][index] = (int)data[d];
                                        d++;
                                    }
                                    index++;
                                }                   
                            }
                            totalSamplesLabel.setText("Total Samples: " + totalSamples);
                            samplingRateLabel.setText("Sampling Rate: " + sampleRate +"hz");
                            wavePanel.repaint(finalAudio, channels);
                        } catch (Exception ex) {
                            fileLabel.setText("Error reading file." + ex);
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
}
