import java.util.Arrays;

public class Test_Sort {

    private static final int CUTOFF = 0;

    //Median of first, last and middle random number between the two.(Peto's remark)
    private static int iMedian3(int[] v, int l, int r) {

        //int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between the two, Peto's remark
        int m = (l + r)/2;

        System.out.println("median of " + l + " " +  m + " " + r );

        if (v[l] <= v[m]) {
            if (v[m] <= v[r])
                return v[m];
            else {
                if (v[l] <= v[r])
                    return v[r];
                else
                    return v[l];
            }
        } else {
            if (v[m] <= v[r]) {
                if (v[l] <= v[r])
                    return v[l];
                else
                    return v[r];
            } else
                return v[m];
        }
    }

    // Singleton's Qsort with Peto's remark
    public static void intQSort(int[] v, int left, int right) {


        if (left + CUTOFF <= right) {

            int i, j, tmp;// tmp variable for swapping
            int pivot;

            pivot = iMedian3(v, left, right);
            i = left;
            j = right - 1;

            for (;;) {
                while (v[++i] < pivot);
                while (v[j--] > pivot);

                if (i < j) {
                    // swap(i, j)
                    tmp = v[i];
                    v[i] = v[j];
                    v[j] = tmp;
                } else
                    break;
            }

            // Swap(i, right -1)
            tmp = v[i];
            v[i] = v[right - 1];
            v[right - 1] = tmp;

            intQSort(v, left, i - 1);
            intQSort(v, i + 1, right);
        }        
    }

    private final static double EPSILON = 0.00001;

    // median of three with double array
    private static int dMedian3(double v[], int l, int r) {

        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between the two, Peto's

        System.out.println(m);
        // remark

        if (v[l] <= v[m]) {
            if (v[m] <= v[r])
                return m;
            else {
                if (v[l] <= v[r])
                    return r;
                else
                    return l;
            }
        } else {
            if (v[m] <= v[r]) {
                if (v[l] <= v[r])
                    return l;
                else
                    return r;
            } else
                return m;
        }
    }

    // Singleton qsort with peto remark for doubles.
    public static void dbQSort(double[] v, int left, int right) {

        int i, j, pivot;

        double tmp; // tmp variable for swapping

        if (left + CUTOFF <= right) {

            pivot = dMedian3(v, left, right);
            i = left;
            j = right - 1;

            for (;;) {

                while (v[++i] < v[pivot]);
                while (v[j--] > v[pivot]);

                if (i < j) {
                    // swap(i, j)
                    tmp = v[i];
                    v[i] = v[j];
                    v[j] = tmp;
                } else
                    break;
            }

            // Swap(i, right -1)
            tmp = v[i];
            v[i] = v[right - 1];
            v[right - 1] = tmp;

            dbQSort(v, left, i - 1);
            dbQSort(v, i + 1, right);
        }
    }

    public static void main(String [] args) {
        int []i = { 2, 3, 1, 10, 5, 89, 91, 32, 2, 4, 5,  78, 90, 100, 31 };
        intQSort(i, 0, i.length - 1);
        for(int j= 0; j < i.length; j++) System.out.println(i[j]);
   }
}
