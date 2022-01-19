import java.util.Arrays;
import java.util.List;

public class InsertionSortTests {
    private String testName = "Insertion Sort Tests";
    private List tests;

    public InsertionSortTests() {
        this.tests = Arrays.asList(
            new Test(Arrays.asList(4,3,2), Arrays.asList(2,3,4)),
            new Test(Arrays.asList(4,2,3), Arrays.asList(2,3,4)),
            new Test(Arrays.asList(1,100,50), Arrays.asList(1,50,100)),
            new Test(Arrays.asList(5,0,1), Arrays.asList(0,1,5)),
            new Test(Arrays.asList(5,0,-1), Arrays.asList(-1,0,5)),
            new Test(Arrays.asList(2), Arrays.asList(2)),
            new Test(Arrays.asList(2,1), Arrays.asList(1,2)),
            new Test(Arrays.asList(2,1,10,9,3,5,4,8,7,6), Arrays.asList(1,2,3,4,5,6,7,8,9,10)),
            new Test(Arrays.asList(1,2,3,4,5,6,7,8,9,10), Arrays.asList(1,2,3,4,5,6,7,8,9,10))
        );
    }

    public String getTestName() {
        return testName;
    }

    public List getTests() {
        return tests;
    }
}
