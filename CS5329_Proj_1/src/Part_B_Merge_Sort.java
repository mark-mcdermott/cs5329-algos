import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.Random;
import static java.lang.String.valueOf;

public class Part_B_Merge_Sort {

    static int numArrays=10;
    private static int lineCount;
    public static int[] lineCounts = new int[numArrays];
    public static int[] randomNValues = new int[numArrays];
    public static int[] worstCaseArr = new int[numArrays];


    public Part_B_Merge_Sort() {

        arrayFillRandom(randomNValues);

        int[] arr1 = new int[randomNValues[0]];
        int[] arr2 = new int[randomNValues[1]];
        int[] arr3 = new int[randomNValues[2]];
        int[] arr4 = new int[randomNValues[3]];
        int[] arr5 = new int[randomNValues[4]];
        int[] arr6 = new int[randomNValues[5]];
        int[] arr7 = new int[randomNValues[6]];
        int[] arr8 = new int[randomNValues[7]];
        int[] arr9 = new int[randomNValues[8]];
        int[] arr10 = new int[randomNValues[9]];

        int[][] arraysOriginal = new int[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10 };

        arraysFillRandom(arraysOriginal);

        int[][] arraysSortedAscending = new int[][] { arr1.clone(),arr2.clone(),arr3.clone(),arr4.clone(),arr5.clone(),
                arr6.clone(),arr7.clone(),arr8.clone(),arr9.clone(),arr10.clone() };

        // TODO this will be different for merge sort - look it up
//        int[][] arraysSortedDescending = new int[][] { arr1.clone(),arr2.clone(),arr3.clone(),arr4.clone(),arr5.clone(),
//                arr6.clone(),arr7.clone(),arr8.clone(),arr9.clone(),arr10.clone() };

        sortArraysAscending(arraysSortedAscending);
        // sortArraysDescending(arraysSortedDescending);
        // getWorstCases(arraysSortedDescending);
        getWorstCases(randomNValues);

        XYDataset lineGraphData = createLineGraphData(randomNValues,lineCounts,worstCaseArr);

        printResults(arraysOriginal,arraysSortedAscending);
        printTable(lineCounts,worstCaseArr);
        new LineChart("Project 1 Part B Merge Sort Graph", "Theoretic total cost T(N), Actual Count vs. N value","N value","Theoretic total cost T(N) and Actual Count",lineGraphData);
    }

    private static XYDataset createLineGraphData(int[] randomNValues, int[] lineCounts, int[] worstCaseArr) {
        XYSeries actual = new XYSeries( "Actual" );
        XYSeries worstCase = new XYSeries( "Worst Case" );
        for (int i=0; i<numArrays; i++) {
            actual.add(randomNValues[i], lineCounts[i]);
            worstCase.add(randomNValues[i], worstCaseArr[i]);
        }

        XYSeriesCollection data = new XYSeriesCollection( );
        data.addSeries(actual);
        data.addSeries(worstCase);

        return data;
   }

   // TODO this will be different for merge sort - look it up
//    private static void getWorstCases(int[][] arraysSortedDescending) {
//        for (int i=0; i<arraysSortedDescending.length; i++) {
//            mergeSort(arraysSortedDescending[i],0,arraysSortedDescending.length);
//            worstCaseArr[i] = lineCount;
//        }
//    }

    private static int lg(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    private static void getWorstCases(int[] randomNValues) {
        for (int i=0; i<randomNValues.length; i++) {
            int n = randomNValues[i];
            int nLogNPlusN = n * lg(n) + n;
            worstCaseArr[i] = nLogNPlusN;
        }
    }

    private static void printTable(int[] lineCounts, int[] worstCaseArr) {
        Object[][] table = new String[numArrays][];
        for (int i = 0; i< numArrays; i++) {
            table[i] = new String[] { valueOf(randomNValues[i]), valueOf(lineCounts[i]), valueOf(worstCaseArr[i]) };
        }
        System.out.format("%3s%8s%12s%n", new String[] {"n","actual","worst case"});
        for (Object[] row : table) {
            System.out.format("%3s%8s%12s%n", row);
        }
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
        int n1 = q-p+1;
        int n2 = r-q;
        int list1[] = new int[n1 + 1];
        int list2[] = new int[n2 + 1];
        for (int i=0; i<n1; i++) {
            list1[i] = list[p+i];
        }
        for (int i=0; i<n2; i++) {
            if (i == list2.length || i == list.length) {
                int z=0;
            }
            if (q+i+1 == list.length) {
                int z=1;
                System.out.println(z);
            }
            list2[i] = list[q+i+1];
        }
        list1[n1] = Integer.MAX_VALUE;
        list2[n2] = Integer.MAX_VALUE;

        int j=0;
        int k=0;
        int l=p;
        for (int i=0; i<n1+n2; i++) {
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

    private static int[] sortDescending(int list[]) {
        lineCount=0;
        lineCount++; int len = list.length;
        lineCount++; for (int j=1; j<len; j++) {
            lineCount++; int key = list[j];
            lineCount++; int i=j-1;
            lineCount++; while (i>=0 && list[i]<key) {
                lineCount++; list[i+1] = list[i];
                lineCount++; i--;
            }
            lineCount++; list[i+1] = key;
        }
        return list;
    }

    private static void sortArraysAscending(int[][] arrs) {
        for (int i=0; i<arrs.length; i++) {
            mergeSort(arrs[i],0,arrs[i].length-1);
            lineCounts[i] = lineCount;
        }
    }

        private static void sortArraysDescending(int[][] arrs) {
        for (int i=0; i<arrs.length; i++) {
            sortDescending(arrs[i]);
            lineCounts[i] = lineCount;
        }
    }


    private static void printResults(int[][] unsorted, int[][] sorted) {
        for (int i=0; i<unsorted.length; i++) {
            System.out.print("Test " + i + ". Unsorted: ");
            for (int j=0; j<unsorted[i].length; j++) {
                System.out.print(unsorted[i][j]);
                if (j != unsorted[i].length-1) {
                    System.out.print(",");
                }
            };
            System.out.print("\n");
            System.out.print("          Sorted: ");
            for (int j=0; j<sorted[i].length; j++) {
                System.out.print(sorted[i][j]);
                if (j != sorted[i].length-1) {
                    System.out.print(",");
                }
            };
            System.out.print("\n\n");
        }
    }

    private static void arrayFillRandom(int[] arr) {
        for (int i=0; i<arr.length; i++) {
            Random rand = new Random();
            arr[i] = rand.nextInt(99) + 1; // 1 to 100, inclusive
        }
    }

    private static void arraysFillRandom(int[][] arrs) {
        for (int i=0; i<arrs.length; i++) {
            arrayFillRandom(arrs[i]);
        }
    }

}
