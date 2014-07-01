public class Cummin {

    private Cummin() { }

    public static int [] cummin_int(int [] v) {
        int [] min_v = new int[v.length];
        int min = v[0];
        min_v[0] = min;
        for(int i = 1; i < v.length; i++) {
            if (v[i] < min)
                min = v[i];
            min_v[i] = min;
        }
        return min_v;
    }

    public static double [] cummin_double(double [] v) {
        double [] min_v = new double[v.length];
        double min = v[0];
        min_v[0] = min;
        for(int i = 0; i < v.length - 1; i++) {
            if (v[i] < min)
                min = v[i];
            min_v = min;
        }
        return min_v;
    }

    public static void main(String [] args) {
        int [] i = {2,1,1,1,5,4};
        int [] im = Cummin.cummin_int(i);
        for(int j = 0; j < im.length; j++) 
            System.out.println(im[j]);
    }
}

