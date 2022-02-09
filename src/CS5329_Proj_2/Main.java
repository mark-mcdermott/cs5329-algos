package CS5329_Proj_2;// Mark McDermott
// CS5329
// Project 2
// 2/7/22

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
    static String pdfOutputPathAndFile = "/Users/markmcdermott/Desktop/Project_2.pdf";
    static String[] tableHeaders = {"","","","n", "Actual", "Worst Case","","",""}; // blank columns here just for formatting
    static String outputToScreenOrPdf = "screen"; // must be "screen" or "pdf"
    static int numArrays = 10;
    static int minNValue = 1;
    static int maxNValue = 20;
    static int minElemValue = -99;
    static int maxElemValue = 99;
    static long lineCount;
    static int[] randomNValuesForArrays = new int[numArrays];
    static int[][] unsortedIntArrays = new int[numArrays][];
    static int horizontalPaddingSmall = 5;
    static int horizontalPaddingLarge = 685;
    static int verticalPaddingSmall = 100;
    static int verticalPaddingMedium = 200;
    static int verticalPaddingLarge = 675;

    public static void main(String[] args) {

        fillArrWithRandomNums(randomNValuesForArrays, minNValue, maxNValue);  // get n values

        // get unsorted int arrays
        for (int i = 0; i < numArrays; i++) {
             unsortedIntArrays[i] = new int[randomNValuesForArrays[i]];
        }
        fillArraysWithRandomNums(unsortedIntArrays, minElemValue, maxElemValue);

        int[][] unsortedIntArraysCopy1 = copy2DArray(unsortedIntArrays);

        MaxSubarrayActualAndWorstObj maxSubarrayResults = getMaxSubarrayActualAndWorst(unsortedIntArraysCopy1);

        outputMaxSubarrayResults(maxSubarrayResults);

        int z=0;
        if (z==0) {
            int x=1;
        }

        // outputTheResults(insertionSortResults, mergeSortResults, outputToScreenOrPdf);

    }

    public static int[][] copy2DArray(int[][] arr) {
        int[][] arrCopy = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            arrCopy[i] = arr[i].clone();
        }
        return arrCopy;
    }

    public static SortedActualAndWorstObj getSortedActualAndWorst(String sortType, int[][] arrays) {
        SortedActualAndWorstObj sortedActualAndWorst = getSortedArraysAndActualCost(sortType, arrays); // gets sorted arrays & actual costs
        sortedActualAndWorst.setWorstCases(getWorstCases(sortType, randomNValuesForArrays));           // get worst cases
        return sortedActualAndWorst;
    }

    public static MaxSubarrayActualAndWorstObj getMaxSubarrayActualAndWorst(int[][] arrays) {
        SubarrayTuple[] maxSubarrays = new SubarrayTuple[numArrays];
        long[] actualCosts = new long[numArrays];
        int[] worstCases = new int[numArrays];
        for (int i=0; i<arrays.length; i++) {
            int n = arrays[i].length;
            lineCount=0;
            SubarrayTuple maxSubarray = maxSubarray(arrays[i],0, arrays[i].length - 1);
            maxSubarrays[i] = maxSubarray;
            actualCosts[i] = lineCount;
            worstCases[i] = (int) nLgN(n);
        }
        return new MaxSubarrayActualAndWorstObj(maxSubarrays,actualCosts,worstCases);
    }






    public static int[] getWorstCases(String sortType, int[] randomNValuesForArrays) {
        int[] worstCases = new int[numArrays];
        for (int i = 0; i < numArrays; i++) {
            int n = randomNValuesForArrays[i];
            if (sortType.equals("insertionSort")) {
                worstCases[i] = (int) Math.pow(n, 2);
            } else if (sortType.equals("mergeSort")) {
                double lgN = lg(n);
                double nLgN = n * lgN;
                worstCases[i] = (int) nLgN;
            }
        }
        return worstCases;
    }

    public static double nLgN(int n) {
        return n * lg(n);
    }

    private static double lg(int num) {
        return Math.log(num) / Math.log(2); // binary log (log base 2)
    }

    static void outputTheResults(SortedActualAndWorstObj insertionSortResults,
                                 SortedActualAndWorstObj mergeSortResults, String outputType) {

        if (outputType.equals("screen")) {
            printResultsToScreen(insertionSortResults, mergeSortResults);
        } else if (outputType.equals("pdf")) {
            printResultsToPDF(insertionSortResults, mergeSortResults);
        }

    }

    static void outputMaxSubarrayResults(MaxSubarrayActualAndWorstObj maxSubarrayActualAndWorstObj) {
        Frame frame = new JFrame("CS5329 Project 2");
        JPanel jPanel = createJPanelOutputMaxSubarray(maxSubarrayActualAndWorstObj);
        jPanel.setPreferredSize(new Dimension(2000, 3000));
        frame.add(jPanel);
        frame.setSize(2000, 3000);
        frame.setVisible(true);
    }

    static void printResultsToScreen(SortedActualAndWorstObj insertionSortResults, SortedActualAndWorstObj mergeSortResults) {
        Frame frame = new JFrame("CS5329 Project 1");
        JPanel jPanel = createJPanelOutput(insertionSortResults, mergeSortResults);
        jPanel.setPreferredSize(new Dimension(2000, 3000));
        frame.add(jPanel);
        frame.setSize(2000, 3000);
        frame.setVisible(true);
    }

    static void printResultsToPDF(SortedActualAndWorstObj insertionSortResults, SortedActualAndWorstObj mergeSortResults) {
        JPanel jPanel = createJPanelOutput(insertionSortResults, mergeSortResults);
        new CreatePDF(jPanel, pdfOutputPathAndFile);
    }

    static void fillArrWithRandomNums(int[] arr, int min, int max) {
        for (int i = 0; i < arr.length; i++) {
            Random rand = new Random();
            arr[i] = rand.nextInt(max + 1 - min) + min; // min to max, inclusive
        }
    }

    static void fillArrWithRandomPosOrNegNums(int[] arr, int min, int max) {
        for (int i = 0; i < arr.length; i++) {
            Random rand = new Random();
            arr[i] = rand.nextInt(max + 1 - min) + min; // min to max, inclusive
        }
    }

    static void fillArraysWithRandomNums(int[][] arrs, int min, int max) {
        for (int i = 0; i < arrs.length; i++) {
            fillArrWithRandomNums(arrs[i], minElemValue, maxElemValue);
        }
    }

    static SortedActualAndWorstObj getSortedArraysAndActualCost(String sortType, int[][] arrays) {
        int[][] sortedArrays = new int[numArrays][];
        long[] actualCosts = new long[numArrays];
        for (int i = 0; i < numArrays; i++) {
            int[] thisUnsortedArray = arrays[i];
            if (sortType.equals("maxSubarray")) {
                lineCount = 0;

            }
//            if (sortType.equals("insertionSort")) {
//                lineCount = 0;
//                insertionSort(thisUnsortedArray);
//            } else if (sortType.equals("mergeSort")) {
//                lineCount = 0;
//                mergeSort(thisUnsortedArray, 0, thisUnsortedArray.length - 1);
//            }
            int[] thisSortedArray = thisUnsortedArray;
            sortedArrays[i] = thisSortedArray;
            actualCosts[i] = lineCount;
        }
        SortedActualAndWorstObj sortedActualAndWorstObj = new SortedActualAndWorstObj(sortedArrays, actualCosts);
        return sortedActualAndWorstObj;
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

    public static SubarrayTuple maxCrossingSubarray(int list[], int low, int mid, int high) {
        lineCount++;
        int leftSum = Integer.MIN_VALUE;
        int rightSum;
        Integer maxLeft = null;
        Integer maxRight = null;
        int sum = 0;
        for (int i=mid; i>=low; i--) {
            sum = sum + list[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int j=mid+1; j<=high; j++) {
            sum = sum+list[j];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = j;
            }
        }
        return new SubarrayTuple(maxLeft,maxRight,leftSum+rightSum);
    }

    public static SubarrayTuple maxSubarray(int list[], int low, int high) {
        int mid, leftSum, rightSum, crossSum;
        SubarrayTuple leftLowLeftHighLeftSum, rightLowRightHighRightSum, crossLowCrossHighCrossSum;
        if (high == low) {
            lineCount++; return new SubarrayTuple(low, high, list[low]);
        } else {
            lineCount++; mid = (int) Math.floor((low + high)/2);
            leftLowLeftHighLeftSum = maxSubarray(list,low,mid);
            rightLowRightHighRightSum = maxSubarray(list,mid+1,high);
            crossLowCrossHighCrossSum = maxCrossingSubarray(list,low,mid,high);
            leftSum = leftLowLeftHighLeftSum.getSum();
            rightSum = rightLowRightHighRightSum.getSum();
            crossSum = crossLowCrossHighCrossSum.getSum();
            if (leftSum >= rightSum && leftSum >= crossSum) {
                return leftLowLeftHighLeftSum;
            } else if (rightSum >= leftSum && rightSum >= crossSum) {
                return rightLowRightHighRightSum;
            } else {
                return crossLowCrossHighCrossSum;
            }
        }
    }

    // Data to be displayed in the JTable
    static String[][] getTableData(String[] tableHeaders, int[] randomNValuesForArrays, long[] actualCosts, int[] worstCaseArr) {
        String[][] tableData = new String[numArrays][];
        tableData[0] = tableHeaders;
        for (int i = 1; i < numArrays; i++) {
            // blank columns here just for formatting
            tableData[i] = new String[]{"","","",valueOf(randomNValuesForArrays[i - 1]), valueOf(actualCosts[i - 1]), valueOf(worstCaseArr[i - 1]),"","",""};
        }
        return tableData;
    }

    static String sortedAndUnsortedArrsToString(int[][] unsortedArrs, int[][] sortedArrs) {
        String output = "";
        for (int i = 0; i < numArrays; i++) {
            int testNum = i + 1;
            output = output + "Test " + testNum + ":\n";
            output = output + "Unsorted: ";
            int[] unsorted = unsortedArrs[i];
            output = output + Arrays.toString(unsorted) + "\n";
            output = output + "Sorted: ";
            int[] sorted = sortedArrs[i];
            output = output + Arrays.toString(sorted) + "\n\n";
        }
        return output;
    }

    static String origArrayAndMaxSubarrayIntervalToString(int[][] unsortedArrs, SubarrayTuple[] subarrayTuples) {
        String output = "";
        for (int i = 0; i < numArrays; i++) {
            int[] unsorted = unsortedArrs[i];
            SubarrayTuple subarrayTuple = subarrayTuples[i];
            int[] maxSubarrayIntervalArr = getMaxSubarrayIntervalArr(unsorted,subarrayTuple);
            int sum = subarrayTuple.sum;
            int testNum = i + 1;
            output = output + "Test " + testNum + ":\n";
            output = output + "Unsorted: ";
            output = output + Arrays.toString(unsorted) + "\n";
            output = output + "Max Subarray: ";
            output = output + Arrays.toString(maxSubarrayIntervalArr) + "\n";
            output = output + "Max Subarray Sum: ";
            output = output + sum + "\n\n";
        }
        return output;
    }

    static int[] getMaxSubarrayIntervalArr(int[] unsortedArr, SubarrayTuple subarrayTuple) {
        int low = subarrayTuple.getLow();
        int high = subarrayTuple.getHigh();
        int maxSubarrayIntervalArrLen = high - low + 1;
        int[] maxSubarrayIntervalArr = new int[maxSubarrayIntervalArrLen];
        for (int i=0; i<maxSubarrayIntervalArrLen; i++) {
            int current = low + i;
            maxSubarrayIntervalArr[i] = unsortedArr[current];
        }
        return maxSubarrayIntervalArr;
    }

    private static XYDataset createLineGraphData(int[] randomNValuesForArrays, long[] actualArr, int[] worstCaseArr) {
        XYSeries actual = new XYSeries("Actual");
        XYSeries worstCase = new XYSeries("Worst Case");
        for (int i = 0; i < numArrays; i++) {
            actual.add(randomNValuesForArrays[i], actualArr[i]);
            worstCase.add(randomNValuesForArrays[i], worstCaseArr[i]);
        }

        XYSeriesCollection data = new XYSeriesCollection();
        data.addSeries(actual);
        data.addSeries(worstCase);

        return data;
    }

    static JPanel createJPanelOutput(SortedActualAndWorstObj insertionSortResults, SortedActualAndWorstObj mergeSortResults) {
        int[][] insertionSortedArrays = insertionSortResults.getSortedArrays();
        long[] insertionActualCosts = insertionSortResults.getActualCosts();
        int[] insertionWorstCases = insertionSortResults.getWorstCases();
        int[][] mergeSortedArrays = mergeSortResults.getSortedArrays();
        long[] mergeActualCosts = mergeSortResults.getActualCosts();
        int[] mergeWorstCases = mergeSortResults.getWorstCases();

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);
        JTextArea containerTitle = new JTextArea("Mark McDermott\nCS5329\nProject 1\n1/31/22");
        containerPanel.add(containerTitle, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        containerPanel.add(mainPanel);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout());
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
        JLabel label1 = new JLabel("Insertion Sort");
        leftPanel.add(label1);
        label1.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
        String text1 = sortedAndUnsortedArrsToString(unsortedIntArrays, insertionSortedArrays);
        JTextArea textArea1 = new JTextArea(text1);
        textArea1.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
        leftPanel.add(textArea1);
        String[][] tableData1 = getTableData(tableHeaders, randomNValuesForArrays, insertionActualCosts, insertionWorstCases);
        JTable jTable1 = new JTable(tableData1, tableHeaders);
        jTable1.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
        leftPanel.add(jTable1);
        XYDataset lineGraphData1 = createLineGraphData(randomNValuesForArrays, insertionActualCosts, insertionWorstCases);
        LineChart lineChart1 = new LineChart("Project 1 Part A Insertion Sort Graph", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData1);
        ChartPanel chartPanel1 = lineChart1.getChartPanel();
        leftPanel.add(chartPanel1);
        mainPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 50;
        gbc.ipady = 50;
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(true);
        rightPanel.setBackground(Color.WHITE);
        BoxLayout boxLayout2 = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
        rightPanel.setLayout(boxLayout2);
        JLabel rightTitleLabel = new JLabel(" ");
        rightPanel.add(rightTitleLabel);
        JLabel label2 = new JLabel("Merge Sort");
        label2.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
        rightPanel.add(label2);
        String text2 = sortedAndUnsortedArrsToString(unsortedIntArrays, mergeSortedArrays);
        JTextArea textArea2 = new JTextArea(text2);
        textArea2.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
        rightPanel.add(textArea2);
        String[][] tableData2 = getTableData(tableHeaders, randomNValuesForArrays, mergeActualCosts, mergeWorstCases);
        JTable jTable2 = new JTable(tableData2, tableHeaders);
        jTable2.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
        rightPanel.add(jTable2);
        XYDataset lineGraphData2 = createLineGraphData(randomNValuesForArrays, mergeActualCosts, mergeWorstCases);
        LineChart lineChart2 = new LineChart("Project 1 Part A Merge Sort Graph", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData2);
        ChartPanel chartPanel2 = lineChart2.getChartPanel();
        rightPanel.add(chartPanel2);
        mainPanel.add(rightPanel, gbc);

        return containerPanel;

    }

        static JPanel createJPanelOutputMaxSubarray(MaxSubarrayActualAndWorstObj maxSubarrayActualAndWorstObj) {
        SubarrayTuple[] subarrayTuples = maxSubarrayActualAndWorstObj.getSubarrayTuples();
        long[] insertionActualCosts = maxSubarrayActualAndWorstObj.getActualCosts();
        int[] insertionWorstCases = maxSubarrayActualAndWorstObj.getAverageCases();

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);
        JTextArea containerTitle = new JTextArea("Mark McDermott\nCS5329\nProject 1\n1/31/22");
        containerPanel.add(containerTitle, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        containerPanel.add(mainPanel);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout());
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
        JLabel label1 = new JLabel("Maximum Subarray Algo");
        leftPanel.add(label1);
        label1.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
        String text1 = origArrayAndMaxSubarrayIntervalToString(unsortedIntArrays, subarrayTuples);
        JTextArea textArea1 = new JTextArea(text1);
        textArea1.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
        leftPanel.add(textArea1);
        String[][] tableData1 = getTableData(tableHeaders, randomNValuesForArrays, insertionActualCosts, insertionWorstCases);
        JTable jTable1 = new JTable(tableData1, tableHeaders);
        jTable1.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
        leftPanel.add(jTable1);
        XYDataset lineGraphData1 = createLineGraphData(randomNValuesForArrays, insertionActualCosts, insertionWorstCases);
        LineChart lineChart1 = new LineChart("Project 2", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData1);
        ChartPanel chartPanel1 = lineChart1.getChartPanel();
        leftPanel.add(chartPanel1);
        mainPanel.add(leftPanel, gbc);

//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        gbc.ipadx = 50;
//        gbc.ipady = 50;
//        JPanel rightPanel = new JPanel();
//        rightPanel.setOpaque(true);
//        rightPanel.setBackground(Color.WHITE);
//        BoxLayout boxLayout2 = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
//        rightPanel.setLayout(boxLayout2);
//        JLabel rightTitleLabel = new JLabel(" ");
//        rightPanel.add(rightTitleLabel);
//        JLabel label2 = new JLabel("Merge Sort");
//        label2.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingSmall));
//        rightPanel.add(label2);
//        String text2 = sortedAndUnsortedArrsToString(unsortedIntArrays, mergeSortedArrays);
//        JTextArea textArea2 = new JTextArea(text2);
//        textArea2.setPreferredSize(new Dimension(horizontalPaddingLarge, verticalPaddingLarge));
//        rightPanel.add(textArea2);
//        String[][] tableData2 = getTableData(tableHeaders, randomNValuesForArrays, mergeActualCosts, mergeWorstCases);
//        JTable jTable2 = new JTable(tableData2, tableHeaders);
//        jTable2.setPreferredSize(new Dimension(horizontalPaddingSmall, verticalPaddingMedium));
//        rightPanel.add(jTable2);
//        XYDataset lineGraphData2 = createLineGraphData(randomNValuesForArrays, mergeActualCosts, mergeWorstCases);
//        LineChart lineChart2 = new LineChart("Project 1 Part A Merge Sort Graph", "", "N value", "Theoretic total cost T(N) and Actual Count", lineGraphData2);
//        ChartPanel chartPanel2 = lineChart2.getChartPanel();
//        rightPanel.add(chartPanel2);
//        mainPanel.add(rightPanel, gbc);

        return containerPanel;

    }

}

class MaxSubarrayActualAndWorstObj {
    SubarrayTuple[] subarrayTuples;
    long[] actualCosts;
    int[] averageCases;

    public MaxSubarrayActualAndWorstObj(SubarrayTuple[] subArrayTuples, long[] actualCosts, int[] averageCases) {
        this.subarrayTuples = subArrayTuples;
        this.actualCosts = actualCosts;
        this.averageCases = averageCases;
    }

    public MaxSubarrayActualAndWorstObj(SubarrayTuple[] subArrayTuples, long[] actualCosts) {
        this.subarrayTuples = subArrayTuples;
        this.actualCosts = actualCosts;
    }



    public long[] getActualCosts() {
        return actualCosts;
    }

    public int[] getAverageCases() {
        return averageCases;
    }

    public SubarrayTuple[] getSubarrayTuples() {
        return subarrayTuples;
    }

}

class SortedActualAndWorstObj {
    int[][] sortedArrays;
    long[] actualCosts;
    int[] worstCases;

    public SortedActualAndWorstObj(int[][] sortedArrays, long[] actualCosts) {
        this.sortedArrays = sortedArrays;
        this.actualCosts = actualCosts;
    }

    public void setActualCosts(long[] actualCosts) {
        this.actualCosts = actualCosts;
    }

    public void setSortedArrays(int[][] sortedArrays) {
        this.sortedArrays = sortedArrays;
    }

    public void setWorstCases(int[] worstCases) {
        this.worstCases = worstCases;
    }

    public long[] getActualCosts() {
        return actualCosts;
    }

    public int[] getWorstCases() {
        return worstCases;
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

class SubarrayTuple {
    int low;
    int high;
    int sum;
    int oneBasedLow;
    int oneBasedHigh;
    public SubarrayTuple(int low, int high, int sum) {
        this.low = low;
        this.high = high;
        this.sum = sum;
        this.oneBasedHigh = low + 1;
        this.oneBasedHigh = high + 1;
    }
    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public int getOneBasedHigh() {
        return oneBasedHigh;
    }

    public int getOneBasedLow() {
        return oneBasedLow;
    }

    public int getSum() {
        return sum;
    }

}
