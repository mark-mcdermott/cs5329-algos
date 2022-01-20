import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertionSortTests {
    private String testName = "Insertion Sort Tests";
    private List tests;

    public InsertionSortTests() {

        Test test1 = new Test(new int[]{4,3,2}, new int[]{2,3,4});
        test1.insertionSortTest();
        Test test2 = new Test(new int[]{4,2,3}, new int[]{2,3,4}).insertionSortTest();
        Test test3 = new Test(new int[]{1,100,50}, new int[]{1,50,100}).insertionSortTest();
        Test test4 = new Test(new int[]{5,0,1}, new int[]{0,1,5}).insertionSortTest();
        Test test5 = new Test(new int[]{5,0,-1}, new int[]{-1,0,5}).insertionSortTest();
        Test test6 = new Test(new int[]{2}, new int[]{2}).insertionSortTest();
        Test test7 = new Test(new int[]{2,1}, new int[]{1,2}).insertionSortTest();
        Test test8 = new Test(new int[]{2,1,10,9,3,5,4,8,7,6}, new int[]{1,2,3,4,5,6,7,8,9,10}).insertionSortTest();
        Test test9 = new Test(new int[]{1,2,3,4,5,6,7,8,9,10}, new int[]{1,2,3,4,5,6,7,8,9,10}).insertionSortTest();

        this.tests = Arrays.asList(
            test1,test2,test3,test4,test5,test6,test7,test8,test9
        );
    }

    public String getTestName() {
        return testName;
    }

    public List getTests() {
        return tests;
    }
}
