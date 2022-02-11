// Mark McDermott
// CS5329
// Project 3
// 2/9/22

package CS5329_Proj_3;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.*;
import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.String.valueOf;

public class Main {

    // init vars
    static String pdfOutputPathAndFile = "/Users/markmcdermott/Desktop/Project_3.pdf";
    static String[] tableHeadersWorstCase = {"","n", "Actual", "Worst Case",""}; // blank columns here just for formatting
    static String[] tableHeadersGeneralCase = {"","n", "Actual", "General Case",""}; // blank columns here just for formatting
    static String outputToScreenOrPdf = "pdf"; // must be "screen" or "pdf"
    static int numArrays = 10;
    static int minNValue = 1;
    static int maxNValue = 10;
    static int minElemValue = 1;
    static int maxElemValue = 99;
    static long lineCount;
    static int[] randomNValuesForArrays = new int[numArrays];
    static int[][] unsortedIntArrays = new int[numArrays][];
    static int horizontalPaddingSmall = 5;
    static int horizontalPaddingLarge = 5;
    static int verticalPaddingSmall = 100;
    static int verticalPaddingMedium = 200;
    static int verticalPaddingLarge = 750;

    public static void main(String[] args) {

        fillArrWithRandomNumsNoRepeats(randomNValuesForArrays, minNValue, maxNValue);  // get n values

        // get unsorted int arrays
        for (int i = 0; i < numArrays; i++) {
            unsortedIntArrays[i] = new int[randomNValuesForArrays[i]];
        }
        fillArraysWithRandomNums(unsortedIntArrays, minElemValue, maxElemValue);

        int[][] unsortedIntArraysCopy1 = copy2DArray(unsortedIntArrays);
        int[][] unsortedIntArraysCopy2 = copy2DArray(unsortedIntArrays);
        int[][] unsortedIntArraysCopy3 = copy2DArray(unsortedIntArrays);


        int[][] sortedIntArraysCopy1 = mergeSortArrays(unsortedIntArraysCopy1); // worst case input must be pre-sorted
        SortedActualAndTheoreticalObj quickSortWorst = getSortedActualAndWorst("quickSortWorst", "partitionModern", sortedIntArraysCopy1);
        SortedActualAndTheoreticalObj quickSortGeneralModern = getSortedActualAndGeneral("quickSortGeneral", "partitionModern", unsortedIntArraysCopy2);
        SortedActualAndTheoreticalObj quickSortGeneralHoare = getSortedActualAndGeneral("quickSortGeneral", "partitionHoare", unsortedIntArraysCopy3);


        outputTheResults(quickSortWorst, quickSortGeneralModern, quickSortGeneralHoare, outputToScreenOrPdf);

    }

    public static int[][] copy2DArray(int[][] arr) {
        int[][] arrCopy = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            arrCopy[i] = arr[i].clone();
        }
        return arrCopy;
    }

    public static SortedActualAndTheoreticalObj getSortedActualAndWorst(String sortType, String partitionType, int[][] arrays) {
        SortedActualAndTheoreticalObj sortedActualAndWorst = getSortedArraysAndActualCost(sortType, partitionType, arrays); // gets sorted arrays & actual costs
        sortedActualAndWorst.setTheoreticalCases(getWorstCases(sortType, randomNValuesForArrays));            // get worst cases
        return sortedActualAndWorst;
    }

    public static SortedActualAndTheoreticalObj getSortedActualAndGeneral(String sortType, String partitionType, int[][] arrays) {
        SortedActualAndTheoreticalObj sortedActualAndWorst = getSortedArraysAndActualCost(sortType, partitionType, arrays); // gets sorted arrays & actual costs
        sortedActualAndWorst.setTheoreticalCases(getWorstCases(sortType, randomNValuesForArrays));            // get worst cases
        return sortedActualAndWorst;
    }


    public static int[] getWorstCases(String sortType, int[] randomNValuesForArrays) {
        int[] worstCases = new int[numArrays];
        for (int i = 0; i < numArrays; i++) {
            int n = randomNValuesForArrays[i];
            if (sortType.equals("quickSortWorst") || sortType.equals("mergeSort")) {
                worstCases[i] = (int) Math.pow(n, 2);
            } else if (sortType.equals("quickSortGeneral")) {
                double lgN = lg(n);
                double nLgN = n * lgN;
                worstCases[i] = (int) nLgN;
            }
        }
        return worstCases;
    }

        public static int[] getGeneralCases(String sortType, int[] randomNValuesForArrays) {
        int[] worstCases = new int[numArrays];
        for (int i = 0; i < numArrays; i++) {
            int n = randomNValuesForArrays[i];
            if (sortType.equals("mergeSort")) {
                double lgN = lg(n);
                double nLgN = n * lgN;
                worstCases[i] = (int) nLgN;
            }
        }
        return worstCases;
    }

    private static double lg(int num) {
        return Math.log(num) / Math.log(2); // binary log (log base 2)
    }

    static void outputTheResults(SortedActualAndTheoreticalObj quickSortWorst,
                                 SortedActualAndTheoreticalObj quickSortGeneralModern,
                                 SortedActualAndTheoreticalObj quickSortGeneralHoare,
                                 String outputType) {

        if (outputType.equals("screen")) {
            printResultsToScreen(quickSortWorst, quickSortGeneralModern, quickSortGeneralHoare);
        } else if (outputType.equals("pdf")) {
            printResultsToPDF(quickSortWorst, quickSortGeneralModern, quickSortGeneralHoare);
        }

    }

    static void printResultsToScreen(
            SortedActualAndTheoreticalObj quickSortWorst,
            SortedActualAndTheoreticalObj quickSortGeneralModern,
            SortedActualAndTheoreticalObj quickSortGeneralHoare) {
        Frame frame = new JFrame("CS5329 Project 1");
        JPanel jPanel = createJPanelOutput(quickSortWorst, quickSortGeneralModern, quickSortGeneralHoare);
        jPanel.setPreferredSize(new Dimension(2000, 3000));
        frame.add(jPanel);
        frame.setSize(2000, 3000);
        frame.setVisible(true);
    }

    static void printResultsToPDF(
            SortedActualAndTheoreticalObj quickSortWorst,
            SortedActualAndTheoreticalObj quickSortGeneralModern,
            SortedActualAndTheoreticalObj quickSortGeneralHoare) {
        JPanel jPanel = createJPanelOutput(quickSortWorst, quickSortGeneralModern, quickSortGeneralHoare);
        new CreatePDF(jPanel, pdfOutputPathAndFile);
    }

    static void fillArrWithRandomNums(int[] arr, int min, int max) {
        for (int i = 0; i < arr.length; i++) {
            Random rand = new Random();
            arr[i] = rand.nextInt(max + 1 - min) + min; // min to max, inclusive
        }
    }

    static void fillArrWithRandomNumsNoRepeats(int[] arr, int min, int max) {
        int thisRandomNum;
        for (int i = 0; i < arr.length; i++) {
            Random rand = new Random();
            thisRandomNum = rand.nextInt(max + 1 - min) + min; // min to max, inclusive

            // make sure no nums are repeated
            while (isInArray(thisRandomNum,arr)) {
                thisRandomNum = rand.nextInt(max + 1 - min) + min;
            }

            arr[i] = thisRandomNum;
        }
    }

    static Boolean isInArray(int num, int[] arr) {
        Integer[] integerArr = intArrToIntegerArr(arr);
        for (int i=0; i<arr.length; i++) {
            if (integerArr[i] != null && arr[i] == num) {
                return true;
            }
        }
        return false;
    }

    static Integer[] intArrToIntegerArr(int[] intArr) {
        Integer[] integerArr = new Integer[intArr.length];
        for (int i=0; i<intArr.length; i++) {
            integerArr[i] = intArr[i];
        }
        return integerArr;
    }


    static void fillArraysWithRandomNums(int[][] arrs, int min, int max) {
        for (int i = 0; i < arrs.length; i++) {
            fillArrWithRandomNums(arrs[i], minElemValue, maxElemValue);
        }
    }

    static SortedActualAndTheoreticalObj getSortedArraysAndActualCost(String sortType, String partitionType, int[][] arrays) {
        int[][] sortedArrays = new int[numArrays][];
        long[] actualCosts = new long[numArrays];
        for (int i = 0; i < numArrays; i++) {
            int[] thisUnsortedArray = arrays[i];
            if (sortType.equals("quickSortWorst") || sortType.equals("quickSortGeneral")) {
                lineCount = 0;
                quickSort(thisUnsortedArray, 0, thisUnsortedArray.length - 1, partitionType);
            }
            int[] thisSortedArray = thisUnsortedArray;
            sortedArrays[i] = thisSortedArray;
            actualCosts[i] = lineCount;
        }
        SortedActualAndTheoreticalObj sortedActualAndTheoreticalObj = new SortedActualAndTheoreticalObj(sortedArrays, actualCosts);
        return sortedActualAndTheoreticalObj;
    }

    static int[] quickSort(int list[], int p, int r, String partitionType) {
        int q;
        lineCount++; if (p<r) {
            if (partitionType.equals("partitionModern")) {
                q = partitionModern(list,p,r);
            } else {
                q = partitionHoare(list,p,r);
            }
            quickSort(list,p,q-1, partitionType);
            quickSort(list,q+1,r, partitionType);
        }
        return list;
    }

    static int partitionModern(int[] list, int p, int r) {
        int x = list[r];
        int i = p-1;
        for (int j=p; j<r; j++) {
            lineCount++;
            if (list[j] <= x) {
                i = i + 1;
                int temp = list[i];
                list[i] = list[j];
                list[j] = temp;
            }
        }
        int temp = list[i+1];
        list[i+1] = list[r];
        list[r] = temp;
        return i+1;
    }

        static int partitionHoare(int[] list, int p, int r) {
        int x = list[p];
        int i = p - 1;
        int j = r + 1;
        while (true) {
            do {
                lineCount++;
                j = j - 1;
            }
            while (list[j] > x);
            do {
                lineCount++;
                i = i + 1;
            } while (list[i] < x);

            lineCount++;
            if (i<j) {
                int temp = list[i];
                list[i] = list[j];
                list[j] = temp;
            } else {
                return j;
            }
        }
    }

    static int[] insertionSort(int list[]) {
        lineCount++;
        for (int j = 1; j < list.length; j++) {
            lineCount++;
            int key = list[j];
            lineCount++;
            int i = j - 1;
            lineCount++;
            while (i >= 0 && list[i] > key) {
                lineCount++;
                list[i + 1] = list[i];
                lineCount++;
                i--;
            }
            lineCount++;
            list[i + 1] = key;
        }
        return list;
    }

    public static int[][] mergeSortArrays(int[][] arrays) {
        for (int i=0; i<arrays.length; i++) {
            int[] thisArr = arrays[i];
            mergeSort(thisArr, 0, thisArr.length-1);
        }
        return arrays;
    }

    public static int[] mergeSort(int list[], int p, int r) {
        if (p < r) {
            int q = (int) Math.floor((p + r - 1) / 2);
            mergeSort(list, p, q);
            mergeSort(list, q + 1, r);
            merge(list, p, q, r);
        }
        return list;
    }

    public static void merge(int list[], int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int list1[] = new int[n1 + 1];
        int list2[] = new int[n2 + 1];
        for (int i = 0; i < n1; i++) {
            list1[i] = list[p + i];
        }
        for (int i = 0; i < n2; i++) {
            list2[i] = list[q + i + 1];
        }
        list1[n1] = Integer.MAX_VALUE;
        list2[n2] = Integer.MAX_VALUE;

        int j = 0;
        int k = 0;
        int l = p;
        for (int i = 0; i < n1 + n2; i++) {
            lineCount++;
            int list1Num = list1[j];
            int list2Num = list2[k];
            if (list1Num <= list2Num) {
                list[l] = list1Num;
                j++;
            } else {
                list[l] = list2Num;
                k++;
            }
            l++;
        }
    }

    // Data to be displayed in the JTable
    static String[][] getTableData(String[] tableHeaders, int[] randomNValuesForArrays, long[] actualCosts, int[] worstCaseArr) {
        String[][] tableData = new String[numArrays][];
        tableData[0] = tableHeaders;
        for (int i = 1; i < numArrays; i++) {
            // blank columns here just for formatting
            tableData[i] = new String[]{"",valueOf(randomNValuesForArrays[i - 1]), valueOf(actualCosts[i - 1]), valueOf(worstCaseArr[i - 1]),""};
        }
        return tableData;
    }

    static String sortedAndUnsortedArrsToString(int[][] unsortedArrs, int[][] sortedArrs) {
        String output = "";
        for (int i = 0; i < numArrays; i++) {
            int testNum = i + 1;
            output = output + "\tTest " + testNum + ":\n";
            output = output + "\tUnsorted: ";
            int[] unsorted = unsortedArrs[i];
            output = output + Arrays.toString(unsorted) + "\n";
            output = output + "\tSorted: ";
            int[] sorted = sortedArrs[i];
            output = output + Arrays.toString(sorted) + "\n\n";
        }
        return output;
    }

        private static XYDataset createLineGraphData(int[] randomNValuesForArrays, long[] actualArr, int[] theoreticalCaseArr, String typeTheoretical) {
        XYSeries actual = new XYSeries("Actual");
        XYSeries worstCase = new XYSeries(typeTheoretical + " Case");
        for (int i = 0; i < numArrays; i++) {
            actual.add(randomNValuesForArrays[i], actualArr[i]);
            worstCase.add(randomNValuesForArrays[i], theoreticalCaseArr[i]);
        }

        XYSeriesCollection data = new XYSeriesCollection();
        data.addSeries(actual);
        data.addSeries(worstCase);

        return data;
    }

    private static XYDataset createLineGraphDataWorstCase(int[] randomNValuesForArrays, long[] actualArr, int[] worstCaseArr) {
        return createLineGraphData(randomNValuesForArrays, actualArr, worstCaseArr, "Worst");
    }

    private static XYDataset createLineGraphDataGeneralCase(int[] randomNValuesForArrays, long[] actualArr, int[] worstCaseArr) {
        return createLineGraphData(randomNValuesForArrays, actualArr, worstCaseArr, "General");
    }



    static JPanel createJPanelOutput(SortedActualAndTheoreticalObj quickSortWorst,
                                     SortedActualAndTheoreticalObj quickSortGeneral,
                                     SortedActualAndTheoreticalObj quickSortHoare) {
        int[][] quickSortWorstSortedArrays = quickSortWorst.getSortedArrays();
        long[] quickSortWorstActualCosts = quickSortWorst.getActualCosts();
        int[] quickSortWorstTheoreticalCases = quickSortWorst.getTheoreticalCases();
        int[][] quickSortGeneralSortedArrays = quickSortGeneral.getSortedArrays();
        long[] quickSortGeneralActualCosts = quickSortGeneral.getActualCosts();
        int[] quickSortGeneralTheoreticalCases = quickSortGeneral.getTheoreticalCases();
        int[][] quickSortHoareSortedArrays = quickSortHoare.getSortedArrays();
        long[] quickSortHoareActualCosts = quickSortHoare.getActualCosts();
        int[] quickSortHoareTheoreticalCases = quickSortHoare.getTheoreticalCases();

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);
        JTextArea containerTitle = new JTextArea("\n\nMark McDermott\nCS5329\nProject 3\n2/9/22\n\n");
        containerPanel.add(containerTitle, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        containerPanel.add(mainPanel);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout());
        mainPanel.setPreferredSize(new Dimension(1300,1200)); // necessary for 3 columns to look right (only affects the pdf)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
         gbc.ipadx = 50;
         gbc.ipady = 50;
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(true);
        leftPanel.setBackground(Color.WHITE);
        BoxLayout boxLayout1 = new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS);
        leftPanel.setLayout(boxLayout1);
        // JLabel label1 = new JLabel("Quick Sort Worst Case");
        // leftPanel.add(label1);
        // label1.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
        String text1 = "\tQuick Sort Worst Case\n\n\n\n";
        text1 = text1 + sortedAndUnsortedArrsToString(unsortedIntArrays, quickSortWorstSortedArrays);
        JTextArea textArea1 = new JTextArea(text1);
        textArea1.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
        leftPanel.add(textArea1);
        String[][] tableData1 = getTableData(tableHeadersWorstCase, randomNValuesForArrays, quickSortWorstActualCosts, quickSortWorstTheoreticalCases);
        JTable jTable1 = new JTable(tableData1, tableHeadersWorstCase);
        jTable1.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
        leftPanel.add(jTable1);
        XYDataset lineGraphData1 = createLineGraphDataWorstCase(randomNValuesForArrays, quickSortWorstActualCosts, quickSortWorstTheoreticalCases);
        LineChart lineChart1 = new LineChart("Project 1 Part A Insertion Sort Graph", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData1);
        ChartPanel chartPanel1 = lineChart1.getChartPanel();
        leftPanel.add(chartPanel1);
        mainPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
         gbc.ipadx = 50;
         gbc.ipady = 50;
        JPanel middlePanel = new JPanel();
        middlePanel.setOpaque(true);
        middlePanel.setBackground(Color.WHITE);
        BoxLayout boxLayout2 = new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS);
        middlePanel.setLayout(boxLayout2);
        JLabel middleTitleLabel = new JLabel(" ");
        // middlePanel.add(middleTitleLabel);
        // JLabel label2 = new JLabel("Quick Sort General Case");
        // label2.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
        // middlePanel.add(label2);
        String text2 = "\tQuick Sort General Case\n\n\n\n";
        text2 = text2 + sortedAndUnsortedArrsToString(unsortedIntArrays, quickSortGeneralSortedArrays);
        JTextArea textArea2 = new JTextArea(text2);
        textArea2.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
        middlePanel.add(textArea2);
        String[][] tableData2 = getTableData(tableHeadersGeneralCase, randomNValuesForArrays, quickSortGeneralActualCosts, quickSortGeneralTheoreticalCases);
        JTable jTable2 = new JTable(tableData2, tableHeadersGeneralCase);
        jTable2.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
        middlePanel.add(jTable2);
        XYDataset lineGraphData2 = createLineGraphDataGeneralCase(randomNValuesForArrays, quickSortGeneralActualCosts, quickSortGeneralTheoreticalCases);
        LineChart lineChart2 = new LineChart("Project 1 Part A Merge Sort Graph", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData2);
        ChartPanel chartPanel2 = lineChart2.getChartPanel();
        middlePanel.add(chartPanel2);
        mainPanel.add(middlePanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx = 50;
        gbc.ipady = 50;
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(true);
        rightPanel.setBackground(Color.WHITE);
        BoxLayout boxLayout3 = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
        rightPanel.setLayout(boxLayout3);
        // JLabel rightTitleLabel = new JLabel(" ");
        // rightPanel.add(rightTitleLabel);
        // JLabel label3 = new JLabel("Original 1962 Hoare Quick Sort");
        // label3.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
        // rightPanel.add(label3);
        String text3 = "\tOriginal 1962 Hoare Quick Sort\n\n\n\n";
        text3 = text3 + sortedAndUnsortedArrsToString(unsortedIntArrays, quickSortHoareSortedArrays);
        JTextArea textArea3 = new JTextArea(text3);
        textArea3.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
        rightPanel.add(textArea3);
        String[][] tableData3 = getTableData(tableHeadersGeneralCase, randomNValuesForArrays, quickSortHoareActualCosts, quickSortHoareTheoreticalCases);
        JTable jTable3 = new JTable(tableData3, tableHeadersGeneralCase);
        jTable3.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
        rightPanel.add(jTable3);
        XYDataset lineGraphData3 = createLineGraphDataGeneralCase(randomNValuesForArrays, quickSortHoareActualCosts, quickSortHoareTheoreticalCases);
        LineChart lineChart3 = new LineChart("Project 1 Part A Merge Sort Graph", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData3);
        ChartPanel chartPanel3 = lineChart3.getChartPanel();
        rightPanel.add(chartPanel3);
        mainPanel.add(rightPanel, gbc);

        return containerPanel;

    }
}

class SortedActualAndTheoreticalObj {
    int[][] sortedArrays;
    long[] actualCosts;
    int[] theoreticalCases;

    public SortedActualAndTheoreticalObj(int[][] sortedArrays, long[] actualCosts) {
        this.sortedArrays = sortedArrays;
        this.actualCosts = actualCosts;
    }

    public void setActualCosts(long[] actualCosts) {
        this.actualCosts = actualCosts;
    }

    public void setSortedArrays(int[][] sortedArrays) {
        this.sortedArrays = sortedArrays;
    }

    public void setTheoreticalCases(int[] theoreticalCases) {
        this.theoreticalCases = theoreticalCases;
    }

    public long[] getActualCosts() {
        return actualCosts;
    }

    public int[] getTheoreticalCases() {
        return theoreticalCases;
    }

    public int[][] getSortedArrays() {
        return sortedArrays;
    }

}



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
class CreatePDF {

    /**
     * @param panel a java swing JPanel
     * @param pathAndFilename mac example: "/Users/markmcdermott/Desktop/newfile.pdf", windows example: "C://newfile.pdf"
     */
    public CreatePDF(JPanel panel, String pathAndFilename) {
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
            com.itextpdf.text.Image iTextImage = com.itextpdf.text.Image.getInstance(writer, awtImage, 1);
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


/*******************************************************************************************************
 * This JFreeChart Line Chart Code is originally from tutorialspoint.com, but I have modified it slightly
 * Url: https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
 * Accessed: 1/25/21
 *******************************************************************************************************/
class LineChart extends ApplicationFrame {
   ChartPanel chartPanel;

   public LineChart(String applicationTitle, String chartTitle, String xAxisName, String yAxisName, XYDataset xyDataset ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle,
         xAxisName,
         yAxisName,
         xyDataset,
         PlotOrientation.VERTICAL,
         true , true , false);

      chartPanel = new ChartPanel(xylineChart);
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ));
      XYPlot plot = xylineChart.getXYPlot();

      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.YELLOW );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer );
      setContentPane( chartPanel );

   }

   public ChartPanel getChartPanel() {
      return chartPanel;
   }
}
