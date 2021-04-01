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
    private int rowsRemaining;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;
    private JButton resetButton = new JButton("Reset");
    private JButton saveImage = new JButton("Save Image");
    private Choice Button1 = new Choice();

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



        Button1.add("Mandelbrot");
        Button1.add("Tricorn");
        Button1.add("Burning Ship");

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);
        panel.add(header);
        panel.add(Button1);


        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);


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
      enableUI(false);

      rowsRemaining = displaySize;

      /** Loop through every row in the display */
      for (int x = 0; x < displaySize; x++) {
          FractalWorker drawRow = new FractalWorker(x);
          drawRow.execute();
      }
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
    private class FractalWorker extends SwingWorker<Object, Object>
    {

        int yCoordinate;

        int[] computedRGBValues;

        private FractalWorker(int row) {
            yCoordinate = row;
        }


        protected Object doInBackground() {

            computedRGBValues = new int[displaySize];

            for (int i = 0; i < computedRGBValues.length; i++) {

                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, i);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, yCoordinate);


                int iteration = fractal.numIterations(xCoord, yCoord);


                if (iteration == -1){
                    computedRGBValues[i] = 0;
                }

                else {

                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);


                    computedRGBValues[i] = rgbColor;
                }
            }
            return null;

        }


        protected void done() {
            for (int i = 0; i < computedRGBValues.length; i++) {
                display.drawPixel(i, yCoordinate, computedRGBValues[i]);
            }

            display.repaint(0, 0, yCoordinate, displaySize, 1);
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }
    private void enableUI(boolean val)
     {  Button1.setEnabled(val);
        resetButton.setEnabled(val);
        saveImage.setEnabled(val);
    }
}
