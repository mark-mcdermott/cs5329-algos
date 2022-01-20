
public class RunTests {

    public void insertionSort() {
        InsertionSortTests insertion = new InsertionSortTests();
        TestHarness insertionSortTestResults = new TestHarness(insertion.getTestName(), insertion.getTests());
        System.out.println(insertionSortTestResults);
    }

    public void mergeSort() {
        MergeSortTests merge = new MergeSortTests();
        TestHarness mergeSortTestResults = new TestHarness(merge.getTestName(), merge.getTests());
        System.out.println(mergeSortTestResults);
    }

}
