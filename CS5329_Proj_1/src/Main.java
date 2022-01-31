import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.String.valueOf;

public class Main {

    // init vars
    static String pdfOutputPathAndFile = "/Users/markmcdermott/Desktop/Project_1.pdf";
    static String[] tableHeaders = { "n", "Actual", "Worst Case" };
    // static String outputToScreenOrPdf = "screen"; // must be "screen" or "pdf"
    static String outputToScreenOrPdf = "pdf"; // must be "screen" or "pdf"
    static int numArrays=10;
    static int maxNumInArray = 20;
    static int lineCount;
    static int[] randomNValuesForArrays = new int[numArrays];
    static int[][] unsortedIntArrays = new int[numArrays][];
    static JFrame frame;


    public static void main(String[] args) {

        fillArrWithRandomNums(randomNValuesForArrays, maxNumInArray);  // get n values

        // get unsorted int arrays
        for (int i=0; i<numArrays; i++) {
            unsortedIntArrays[i] = new int[randomNValuesForArrays[i]];
        }
        fillArraysWithRandomNums(unsortedIntArrays, maxNumInArray);

        SortedActualAndWorstObj insertionSortResults = getSortedActualAndWorst("insertionSort",unsortedIntArrays);
        SortedActualAndWorstObj mergeSortResults = getSortedActualAndWorst("mergeSort",unsortedIntArrays);

        outputTheResults(insertionSortResults, mergeSortResults,outputToScreenOrPdf);

    }

    public static SortedActualAndWorstObj getSortedActualAndWorst(String sortType, int[][] arrays) {
        SortedActualAndWorstObj sortedActualAndWorst = getSortedArraysAndActualCost(sortType, arrays);
        sortedActualAndWorst.setWorstCases(getWorstCases(sortType,randomNValuesForArrays));
        return sortedActualAndWorst;
    }



    public static int[] getWorstCases(String sortType, int[] randomNValuesForArrays) {
        int[] worstCases = new int[numArrays];
        for (int i=0; i<numArrays; i++) {
            int n = randomNValuesForArrays[i];
            if (sortType.equals("insertionSort")) {
                worstCases[i] = (int) Math.pow(n,2);
            } else if (sortType.equals("mergeSort")) {
                worstCases[i] = n * log(n);
            }
        }
        return worstCases;
    }

    private static int log(int num) {
        return (int) (Math.log(num) / Math.log(2)); // binary log (log base 2)
    }


    /**
     * @param outputType    either "screen" or "pdf"
     */
    static void outputTheResults(SortedActualAndWorstObj insertionSortResults,
                                 SortedActualAndWorstObj mergeSortResults, String outputType) {

        if (outputType.equals("screen")) {
            printResultsToScreen(insertionSortResults, mergeSortResults);
        } else if (outputType.equals("pdf")) {
            printResultsToPDF(insertionSortResults, mergeSortResults);
        }

    }

    static void printResultsToScreen(SortedActualAndWorstObj insertionSortResults, SortedActualAndWorstObj mergeSortResults) {
        Frame frame = new JFrame("CS5329 Project 1");
        JPanel jPanel = createJPanelOutput(insertionSortResults, mergeSortResults);
        jPanel.setPreferredSize(new Dimension(2000,3000));
        frame.add(jPanel);
        frame.setSize(2000, 3000);
        frame.setVisible(true);
    }

    static void printResultsToPDF(SortedActualAndWorstObj insertionSortResults, SortedActualAndWorstObj mergeSortResults) {
        JPanel jPanel = createJPanelOutput(insertionSortResults, mergeSortResults);
        new ExportJPanelToPDF(jPanel, pdfOutputPathAndFile);
    }

    static void fillArrWithRandomNums(int[] arr, int maxNumInArray) {
        for (int i=0; i<arr.length; i++) {
            Random rand = new Random();
            arr[i] = rand.nextInt(maxNumInArray) + 1; // 1 to 100, inclusive
        }
    }

    static void fillArraysWithRandomNums(int[][] arrs, int maxNumInArray) {
        for (int i=0; i<arrs.length; i++) {
          fillArrWithRandomNums(arrs[i], maxNumInArray);
        }
    }

    static SortedActualAndWorstObj getSortedArraysAndActualCost(String sortType, int[][] arrays) {
        int[][] sortedArrays = new int[numArrays][];
        int[] actualCosts = new int[numArrays];
        for (int i=0; i<numArrays; i++) {
            int[] thisUnsortedArray = arrays[i];
            if (sortType.equals("insertionSort")) {
                insertionSort(thisUnsortedArray);
            } else if (sortType.equals("mergeSort")) {
                // mer
            }
            int[] thisSortedArray = thisUnsortedArray;
            sortedArrays[i] = thisSortedArray;
            actualCosts[i] = lineCount;
        }
        SortedActualAndWorstObj sortedActualAndWorstObj = new SortedActualAndWorstObj(sortedArrays, actualCosts);
        return sortedActualAndWorstObj;
    }

    static int[] insertionSort(int list[]) {
        lineCount=0;
        lineCount++; int len = list.length;
        lineCount++; for (int j=1; j<len; j++) {
            lineCount++; int key = list[j];
            lineCount++; int i=j-1;
            lineCount++; while (i>=0 && list[i]>key) {
                lineCount++; list[i+1] = list[i];
                lineCount++; i--;
            }
            lineCount++; list[i+1] = key;
        }
        return list;
    }

    public static int[] mergeSort(int list[], int p, int r) {
        if (p<r) {
            int q = (int) Math.floor((p+r-1)/2);
            mergeSort(list,p,q);
            mergeSort(list,q+1,r);
            merge(list,p,q,r);
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
            if (i == list2.length || i == list.length) {
                int z = 0;
            }
            if (q + i + 1 == list.length) {
                int z = 1;
                System.out.println(z);
            }
            list2[i] = list[q + i + 1];
        }
        list1[n1] = Integer.MAX_VALUE;
        list2[n2] = Integer.MAX_VALUE;

        int j = 0;
        int k = 0;
        int l = p;
        for (int i = 0; i < n1 + n2; i++) {
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

//    static int[] getWorseCaseValues(String sortType, int n) {
//        int[] worstCase =
//        for (int i=0; i<randomNValuesForArrays.length; i++) {
//            if (sortType.equals("insertionSort")) {
//                worstCaseArr[i] = (int) Math.pow(randomNValuesForArrays[i], 2);
//            } else if (sortType.equals("mergeSort")) {
//                //
//            }
//        }
//    }

    // Data to be displayed in the JTable
    static String[][] getTableData(String[] tableHeaders, int[] randomNValuesForArrays, int[] actualCosts, int[] worstCaseArr) {
        String[][] tableData = new String[numArrays][];
        tableData[0] = tableHeaders;
        for (int i=1; i<numArrays; i++) {
            tableData[i] = new String[] { valueOf(randomNValuesForArrays[i-1]), valueOf(actualCosts[i-1]), valueOf(worstCaseArr[i-1]) };
        }
        return tableData;
    }

    static String sortedAndUnsortedArrsToString(int[][] unsortedArrs, int[][] sortedArrs) {
        String output = "";
        for (int i=0; i<numArrays; i++) {
            int testNum = i+1;
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

    private static XYDataset createLineGraphData(int[] randomNValuesForArrays, int[] actualArr, int[] worstCaseArr) {
        XYSeries actual = new XYSeries( "Actual" );
        XYSeries worstCase = new XYSeries( "Worst Case" );
        for (int i=0; i<numArrays; i++) {
            actual.add(randomNValuesForArrays[i], actualArr[i]);
            worstCase.add(randomNValuesForArrays[i], worstCaseArr[i]);
        }

        XYSeriesCollection data = new XYSeriesCollection( );
        data.addSeries(actual);
        data.addSeries(worstCase);

        return data;
   }

   static int[] integerArrToIntArr(Integer[] integerArr) {
        return Arrays.stream((Integer[]) integerArr).mapToInt(Integer::intValue).toArray();
   }

    static JPanel createJPanelOutput(SortedActualAndWorstObj insertionSortResults, SortedActualAndWorstObj mergeSortResults) {
        int[][] insertionSortedArrays = insertionSortResults.getSortedArrays();
        int[] insertionActualCosts = insertionSortResults.getActualCosts();
        int[] insertionWorstCases = insertionSortResults.getWorstCases();
        int[][] mergeSortedArrays = mergeSortResults.getSortedArrays();
        int[] mergeActualCosts = mergeSortResults.getActualCosts();
        int[] mergeWorstCases = mergeSortResults.getWorstCases();

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);
        JTextArea containerTitle = new JTextArea("Mark McDermott\nCS5329\nProject 1\n1/31/22");
        containerPanel.add(containerTitle,BorderLayout.NORTH);
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
        String text1 = sortedAndUnsortedArrsToString(unsortedIntArrays, insertionSortedArrays);
        JTextArea textArea1 = new JTextArea(text1);
        leftPanel.add(textArea1);
        String[][] tableData1 = getTableData(tableHeaders,randomNValuesForArrays,insertionActualCosts,insertionWorstCases);
        JTable jTable1 = new JTable(tableData1,tableHeaders);
        leftPanel.add(jTable1);
        XYDataset lineGraphData1 = createLineGraphData(randomNValuesForArrays,insertionActualCosts,insertionWorstCases);
        LineChart lineChart1 = new LineChart("Project 1 Part A Insertion Sort Graph", "Theoretic total cost T(N), Actual Count vs. N value","N value","Theoretic total cost T(N) and Actual Count",lineGraphData1);
        ChartPanel chartPanel1 = lineChart1.getChartPanel();
        leftPanel.add(chartPanel1);
        mainPanel.add(leftPanel,gbc);

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
        rightPanel.add(label2);
        String text2 = sortedAndUnsortedArrsToString(unsortedIntArrays, mergeSortedArrays);
        JTextArea textArea2 = new JTextArea(text2);
        rightPanel.add(textArea2);
        String[][] tableData2 = getTableData(tableHeaders,randomNValuesForArrays,mergeActualCosts,mergeWorstCases);
        JTable jTable2 = new JTable(tableData2,tableHeaders);
        rightPanel.add(jTable2);
        XYDataset lineGraphData2 = createLineGraphData(randomNValuesForArrays,mergeActualCosts,mergeWorstCases);
        LineChart lineChart2 = new LineChart("Project 1 Part A Insertion Sort Graph", "Theoretic total cost T(N), Actual Count vs. N value","N value","Theoretic total cost T(N) and Actual Count",lineGraphData2);
        ChartPanel chartPanel2 = lineChart2.getChartPanel();
        rightPanel.add(chartPanel2);
        mainPanel.add(rightPanel,gbc);

        return containerPanel;

    }

}
