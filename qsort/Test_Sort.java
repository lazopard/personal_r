import java.util.Arrays;


public class Test_Sort {

    // Note: helper functions embedded for efficiency
    private static final int CUTOFF = 90; //insertion sort thereshold
    private static final boolean INSERTIONENABLED = false; //enable insertion sort

    // Singleton's Qsort with Peto's remark
    public static void intQSort(int v[], int l, int r) {

        int tmp; //tmp variable for swapping

        if (INSERTIONENABLED) {
            //Use insertion sort for arrays smaller than CUTOFF
            if (r < CUTOFF) {

                for(int i = l; i < r; i++) {
                    for(int j = i; j > 0 && v[j - 1] > v[j]; j--) {
                        // swap(j, j -1) {
                        tmp=v[j - 1];
                        v[j - 1]=v[j];
                        v[j]=tmp;
                        // }
                    }
                }

                return;
            }
        }

        int pivot;
        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between first and last, Peto's remark

        //median(v[l], v[m], v[r]) {
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
        // }

        int i=l,j=r;

        //Partioning
        while(i<=j) {

            while(v[i]<pivot) i++;
            while(v[j]>pivot) j--;

            if(i<=j) {
                //swap(i, j) {
                tmp=v[i];
                v[i]=v[j];
                v[j]=tmp;
                // }

                i++;
                j--;
            }
        }

        if(l<j)       
            intQSort(v,l,j);

        if(i<r)       
            intQSort(v,i,r);
    }

    // Singleton qsort with peto remark for doubles.
    public static void dbQSort(double[] v, int l, int r) {

        double pivot;
        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between first and last, Peto's remark
    
        //median(v[l], v[m], v[r]) {
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
        // }

        int i=l,j=r;
        double tmp; //tmp variable for swapping

        //Partioning
        while(i<=j) {

            while(v[i]<pivot) i++;
            while(v[j]>pivot) j--;

            if(i<=j) {
                //swap(i, j) {
                tmp=v[i];
                v[i]=v[j];
                v[j]=tmp;
                // }

                i++;
                j--;
            }
        }

        if(l<j)       
            dbQSort(v,l,j);

        if(i<r)       
            dbQSort(v,i,r);

    }

    //checks if int array is sorted
    public static boolean isSorted(int[] a) {
        for(int i = 0; i < a.length-1; i ++) { 
            if (a[i] > a[i+1]) {
                return false; 
            }
        }
        return true;
    }

    //checks if double array is sorted
    public static boolean dIsSorted(double [] a) {

        for(int i = 0; i < a.length-1; i ++) { 
            if (a[i] >= a[i+1]) {
                return false; 
            }
        }
        return true;
    }

    //Testing both sorting functions 
    public static void main(String [] args) {
        int n = 10000000;
        int [] i = new int[n];
        double [] d = new double[n];

        for(int j = 0; j < n; j++) {
            i[j] = 0 + (int) (Math.random()*((n - 0)+ 1 ));
            d[j] = Math.random();
        }

        long iSortTimeStart = System.nanoTime();
        intQSort(i, 0, n - 1);
        long iSortTimeEnd = System.nanoTime();
        long iSortDuration = iSortTimeEnd - iSortTimeStart;

        long dSortTimeStart = System.nanoTime();
        dbQSort(d, 0, n - 1);
        long dSortTimeEnd = System.nanoTime();
        long dSortDuration = dSortTimeEnd - dSortTimeStart;

        System.out.println("iQsort time: " + iSortDuration + "\n");
        System.out.println("dbQSort time: " + dSortDuration + "\n");

        System.out.println("Is int array sorted: ?" + isSorted(i));
        System.out.println("Is double array sorted: ?" + dIsSorted(d));
    }
}

