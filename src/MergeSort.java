
public class MergeSort {

    public int[] sort(int list[], int p, int r) {
        if (p<r) {
            int q = (int) Math.floor((p+r-1)/2);
            sort(list,p,q);
            sort(list,q+1,r);
            merge(list,p,q,r);
        }
        return list;
    }

    public void merge(int list[], int p, int q, int r) {
        int n1 = q-p+1;
        int n2 = r-q;
        int list1[] = new int[n1 + 1];
        int list2[] = new int[n2 + 1];
        for (int i=0; i<n1; i++) {
            list1[i] = list[p+i];
        }
        for (int i=0; i<n2; i++) {
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

}
