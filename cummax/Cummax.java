public class Cummax {

    private Cummax() { }

    public static int [] cummax_int(int [] v) {
        int [] max_v = new int[v.length];
        int max = v[0];
        max_v[0] = max;
        for(int i = 1; i < v.length; i++) {
            if (v[i] > max)
                max = v[i];
            max_v[i] = max;
        }
        return max_v;
    }

    public static double [] cummax_double(double [] v) {
        double [] max_v = new double[v.length];
        for(int i = 0; i < v.length - 1; i++) {
        }
        return max_v;
    }

    public static void main(String [] args) {
        int [] i = {2,1,1,1,5,4};
        int [] im = Cummax.cummax_int(i);
        for(int j = 0; j < im.length; j++) 
            System.out.println(im[j]);
    }
}

