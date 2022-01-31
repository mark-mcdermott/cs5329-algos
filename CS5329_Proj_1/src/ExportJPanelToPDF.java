/**
 * Code originally found at https://stackoverflow.com/a/25580014, accessed 1/30/22
 * I have made some slight modifications. -Mark McDermott 1/30/22
 * This uses iText 5.5.9
 * iText 5.5.9 documentation: https://www.javadoc.io/doc/com.itextpdf/itextpdf/5.5.9/index.html
 * iText 5.5.9 jar: https://jar-download.com/artifacts/com.itextpdf/itextpdf/5.5.9/source-code
 * apparently the iText is on the maven repo as below, although I used the jar directly
 *      <dependency>
 *          <groupId>com.itextpdf</groupId>
 *          <artifactId>itextpdf</artifactId>
 *          <version>5.5.2</version>
 *      </dependency>
 */

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportJPanelToPDF {

    /**
     * @param panel a java swing JPanel
     * @param pathAndFilename mac example: "/Users/markmcdermott/Desktop/newfile.pdf", windows example: "C://newfile.pdf"
     */
    public ExportJPanelToPDF(JPanel panel, String pathAndFilename) {
        JFrame frame = new JFrame();
        frame.add(new JScrollPane(panel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        final java.awt.Image image = getImageFromPanel(panel);
        printToPDF(image, pathAndFilename);
    }

        public void printToPDF(java.awt.Image awtImage, String fileName) {
        try {
            Document d = new Document();
            PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(fileName));
            d.open();
            Image iTextImage = Image.getInstance(writer, awtImage, 1);
            iTextImage.scalePercent(40);
            d.add(iTextImage);
            d.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static java.awt.Image getImageFromPanel(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(),component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
    }

}
