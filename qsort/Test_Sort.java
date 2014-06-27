import java.util.Arrays;

public class Test_Sort {

    private static final int CUTOFF = 0;

    // Singleton's Qsort with Peto's remark
    public static void intQSort(int v[], int l, int r) {

        int pivot;
        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between first and last, Peto's remark

        //median(v[l], v[m], v[r])
        if (v[l] <= v[m]) {
            if (v[m] <= v[r])
                pivot = v[m];
            else {
                if (v[l] <= v[r])
                    pivot = v[r];
                else
                    pivot = v[l];
            }
        } else {
            if (v[m] <= v[r]) {
                if (v[l] <= v[r])
                    pivot = v[l];
                else
                    pivot = v[r];
            } 
            else
                pivot = v[m];
        }

        int i=l,j=r;
        int tmp; //tmp variable for swapping

        //Partioning
        while(i<=j) {

            while(v[i]<pivot) i++;
            while(v[j]>pivot) j--;

            if(i<=j) {
                //swap(i, j)
                tmp=v[i];
                v[i]=v[j];
                v[j]=tmp;
                i++;
                j--;
            }
        }

        if(l<j)       
            intQSort(v,l,j);

        if(i<r)       
            intQSort(v,i,r);
    }

    private final static double EPSILON = 0.00001;

    // Singleton qsort with peto remark for doubles.
    public static void dbQSort(double[] v, int l, int r) {

        double pivot;
        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between first and last, Peto's remark

        //median(v[l], v[m], v[r])

        //float diffLM = ((v[l] - v[m]) <= 0.0F) ? 0.0F - (v[l] - v[m]) : (v[l] - v[m]);
        //float diffMR = ((v[m] - v[r]) <= 0.0F) ? 0.0F - (v[m] - v[r]) : (v[m] - v[r]);
        //float diffLR = ((v[l] - v[r]) <= 0.0F) ? 0.0F - (v[l] - v[r]) : (v[l] - v[r]);

        if (v[l] <= v[m]) {
            if (v[m] <= v[r])
                pivot = v[m];
            else {
                if (v[l] <= v[r])
                    pivot = v[r];
                else
                    pivot = v[l];
            }
        } else {
            if (v[m] <= v[r]) {
                if (v[l] <= v[r])
                    pivot = v[l];
                else
                    pivot = v[r];
            } 
            else
                pivot = v[m];
        }

        int i=l,j=r;
        double tmp; //tmp variable for swapping

        //Partioning
        while(i<=j) {

            while(v[i]<pivot) i++;
            while(v[j]>pivot) j--;

            if(i<=j) {
                //swap(i, j)
                tmp=v[i];
                v[i]=v[j];
                v[j]=tmp;
                i++;
                j--;
            }
        }

        if(l<j)       
            dbQSort(v,l,j);

        if(i<r)       
            dbQSort(v,i,r);

    }

    public static boolean isSorted(int[] a) {
        for(int i = 0; i < a.length-1; i ++) { 
            if (a[i] > a[i+1]) {
                return false; 
            }
        }
        return true;
    }

    public static boolean dIsSorted(double [] a) {

        for(int i = 0; i < a.length-1; i ++) { 
            System.out.println(a[i]);
            if (a[i] >= a[i+1]) {
                return false; 
            }
        }
        return true;
    }

    public static void main(String [] args) {
        int n = 10000000;
        int [] i = new int[n];
        double [] d = new double[n];
        for(int j = 0; j < n; j++) {
            //i[j] = 0 + (int) (Math.random()*((n - 0)+ 1 ));
            //d[j] = 0 + Math.random()*((n - 0)+ 1 );
            d[j] = Math.random();
        }

        //long iSortTimeStart = System.nanoTime();
        //intQSort(i, 0, n - 1);
        //long iSortTimeEnd = System.nanoTime();
        //long iSortDuration = iSortTimeEnd - iSortTimeStart;

        long dSortTimeStart = System.nanoTime();
        dbQSort(d, 0, n - 1);
        long dSortTimeEnd = System.nanoTime();
        long dSortDuration = dSortTimeEnd - dSortTimeStart;

        //System.out.println("iQsort time: " + iSortDuration + "\n");
        System.out.println("dbQSort time: " + dSortDuration + "\n");

        //System.out.println("Is int array sorted: ?" + isSorted(i));
        System.out.println("Is double array sorted: ?" + dIsSorted(d));
    }
}
