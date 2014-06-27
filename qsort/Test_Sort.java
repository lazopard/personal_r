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
    public static void dbQSort(double[] v, int left, int right) {

        int pivot;
        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between first and last, Peto's remark

        //median(v[l], v[m], v[r])
        float lLEm = ((v[l] - v[m]) <= 0.0F) ? 0.0F -(v[l] - v[m]) : (v[l] - v[m]);
        float mLEr = ((v[m] - v[r]) <= 0.0F) ? 0.0F -(v[m] - v[r]) : (v[m] - v[r]);
        float lLEr = ((v[l] - v[r]) <= 0.0F) ? 0.0F -(v[l] - v[r]) : (v[l] - v[r]);
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
            dbQSort(v,l,j);

        if(i<r)       
            dbQSort(v,i,r);

    }

    public static void main(String [] args) {
        int []i = { 2, 3, 1, 10, 5, 89, 91, 32, 2, 4, 5,  78, 90, 100, 31 };
        intQSort(i, 0, i.length - 1);
        for(int j= 0; j < i.length; j++) System.out.println(i[j]);
    }
}
