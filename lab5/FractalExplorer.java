import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO.*;
import java.awt.image.*;

public class FractalExplorer implements ItemListener
{
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        fractal = new Mandelbrot();
        displaySize = size;
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);

    }
    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());


        JFrame frame = new JFrame("Fractal");
        frame.add(display, BorderLayout.CENTER);


        JLabel header = new JLabel("Fractal:");

        Choice Button1 = new Choice();

        Button1.add("Mandelbrot");
        Button1.add("Tricorn");
        Button1.add("Burning Ship");

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);
        panel.add(header);
        panel.add(Button1);


        JButton resetButton = new JButton("Reset");
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);

        JButton saveImage = new JButton("Save Image");
        SaveHandler save = new SaveHandler();
        saveImage.addActionListener(save);

        JPanel down  = new JPanel();
        frame.add(down, BorderLayout.SOUTH);
        down.add(resetButton);
        down.add(saveImage);

        Button1.addItemListener(this);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

    }


    public void itemStateChanged(ItemEvent ie)
    {
        if (ie.getItem() == "Mandelbrot") fractal = new Mandelbrot();
        //System.out.println (ie.getItem());
        if (ie.getItem() == "Tricorn") fractal = new Tricorn();
        if (ie.getItem() == "Burning Ship") fractal = new Burning_Ship();
        drawFractal();
    }
    private void drawFractal()
    {
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                double xCoord = fractal.getCoord(
                    range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(
                    range.y, range.y + range.height, displaySize, y);
                int iteration = fractal.numIterations(xCoord, yCoord);
                if (iteration == -1) {
                    display.drawPixel(x, y, 0);
                }
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        display.repaint();
    }
    private class ResetHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    private class SaveHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            JFileChooser myFileChooser = new JFileChooser();
                FileFilter extensionFilter = new FileNameExtensionFilter(
                    "PNG Images", "png");

                myFileChooser.setFileFilter(extensionFilter);
                myFileChooser.setAcceptAllFileFilterUsed(false);

                int userSelection = myFileChooser.showSaveDialog(display);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();

                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }

                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(
                            display, exception.getMessage(),
                            "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
    }

    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            double xCoord = fractal.getCoord(
                range.x, range.x + range.width, displaySize, x);
            int y = e.getY();
            double yCoord = fractal.getCoord(
                range.y, range.y + range.height, displaySize, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
