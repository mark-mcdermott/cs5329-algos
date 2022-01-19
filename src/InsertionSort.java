import java.util.List;

public class InsertionSort {
    private List sorted;

    public InsertionSort(List list) {
        int len = list.size();
        for (int j=1; j<len; j++) {
            int key= (int) list.get(j);
            int i=j-1;
            while (i>=0 && (int) list.get(i)>key) {
                list.set(i+1,list.get(i));
                i--;
            }
            list.set(i+1,key);
        }
        this.sorted = list;
    }

    public List getSorted() {
        return sorted;
    }
}