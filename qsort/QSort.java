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

}

