package com.oracle.truffle.r.nodes.builtin.base;

import com.oracle.graal.nodes.*;
import com.oracle.truffle.api.dsl.*;
import com.oracle.truffle.r.nodes.*;
import com.oracle.truffle.r.nodes.unary.*;
import com.oracle.truffle.r.runtime.*;
import com.oracle.truffle.r.runtime.data.*;
import com.oracle.truffle.r.runtime.ops.*;

@RBuiltin(name = "qsort", kind = Internal)
public abstract class Qsort extends RBuiltinNode {

    @Child static CastDoubleNode castDoubleNode;

    @Child protected BooleanOperation eq = BinaryCompare.EQUAL.create();
    @Child protected BooleanOperation lt = BinaryCompare.LESS_THAN.create();
    @Child protected BooleanOperation le = BinaryCompare.LESS_EQUAL.create();
    @Child protected BooleanOperation ge = BinaryCompare.GREATER_EQUAL.create();
    @Child protected BooleanOperation gt = BinaryCompare.GREATER_THAN.create();

    private static final Object[] PARAMETER_NAMES = new Object[]{"x", "ind"};

    @Override
    public Object[] getParameterNames() {
        return PARAMETER_NAMES;
    }

    @Override
    public RNode[] getParameterValues() {
        return new RNode[]{ConstantNode.create(RMissing.instance), ConstantNode.create(RMissing.instance)};
    }

    @Specialization(order = 10)
        public RDoubleVector do_psort(RDoubleVector x) {
            controlVisibility();
            System.out.println("entering do_psort");
            double[] v = x.getDataCopy();
            int n = x.getLength();
            dbQSort(v, 0, n);
            return RDataFactory.createDoubleVector(v, RDataFactory.COMPLETE_VECTOR);
        }

    @Specialization(order = 20)
        public RIntVector do_qsort(RIntVector x) {
            int[] v = x.getDataCopy();
            int n = x.getLength();
            intQSort(v, 0, n);
            return RDataFactory.createIntVector(v, RDataFactory.COMPLETE_VECTOR);
        }

    /*
     * //CLRS deterministic partition, Theta(n^2) when v is completely sorted private int
     *      dPartition(int[] v, int left, int right) { int tmp; //for swapping int x = v[right]; int i =
     *                 left -1; for(int j = left; j < right - 1; j++) { if (v[j] <= x) { i++; tmp = v[i]; v[i] =
     *                      v[j]; v[j] = i; } } tmp = v[i + 1]; v[i + 1] = right; v[right] = tmp;
     *                           
     *                                return i + 1; }
     */

    /*
     *       //CLRS quicksort public void intQSort(int [] v, int left, int right) { if (left < right) {
     *            int q = dPartition(v, left, right); intQSort(v, left, q -1); intQSort(v, q + 1, right); } }
     */

    /*
     *      //helper insertion sort from Bell Lab's Engineering a sort function private static void
     *            iisort(int [] v, int len) { int i, j, tmp; for(i = 1; i < len; i++) { for(j = i;j > 0 &&
     *                 v[j-1] > v[j]; j--) { tmp = v[j]; v[j] = v[j - 1]; v[j - 1] = tmp; } } }
     */

    private static final int CUTOFF = 20;

    //Median of first, last and middle random number between the two.(Peto's remark)
    private static int iMedian3(int[] v, int l, int r) {

            int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between the two, Peto's
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

    // Singleton's Qsort with Peto's remark
    public void intQSort(int[] v, int left, int right) {
        int i, j, tmp;// tmp variable for swapping
        int pivot;
        if (left + CUTOFF <= right) {

            pivot = iMedian3(v, left, right);
            i = left;
            j = right - 1;

            for (;;) {
                while (v[++i] < v[pivot])
                    ;
                while (v[j--] > v[pivot])
                    ;
                if (i < j) {

                    // swap(i, j)
                    tmp = v[i];
                    v[i] = v[j];
                    v[j] = tmp;
                } else
                    break;
            }
        }
        // Swap(i, right -1)
        tmp = v[i];
        v[i] = v[right - 1];
        v[right - 1] = tmp;

        intQSort(v, left, i - 1);
        intQSort(v, i + 1, right);
    }

    private final static double EPSILON = 0.00001;

    // median of three with double array
    private static int dMedian3(double v[], int l, int r) {

        int m = l + (int) (Math.random() * ((r - l) + 1)); // random number between the two, Peto's
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
    public void dbQSort(double[] v, int left, int right) {

        int i, j, pivot;
        double tmp; // tmp variable for swapping

        if (left + CUTOFF <= right) {

            pivot = dMedian3(v, left, right);
            i = left;
            j = right - 1;

            for (;;) {
                while (v[++i] < v[pivot])
                    ;
                while (v[j--] > v[pivot])
                    ;
                if (i < j) {

                    // swap(i, j)
                    tmp = v[i];
                    v[i] = v[j];
                    v[j] = tmp;
                } else
                    break;
            }
        }

        // Swap(i, right -1)
        tmp = v[i];
        v[i] = v[right - 1];
        v[right - 1] = tmp;

        dbQSort(v, left, i - 1);
        dbQSort(v, i + 1, right);
    }
}
