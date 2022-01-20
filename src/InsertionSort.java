
public class InsertionSort {

    public int[] sort(int list[]) {
        int len = list.length;
        for (int j=1; j<len; j++) {
            int key = list[j];
            int i=j-1;
            while (i>=0 && list[i]>key) {
                list[i+1] = list[i];
                i--;
            }
            list[i+1] = key;
        }
        return list;
    }

}