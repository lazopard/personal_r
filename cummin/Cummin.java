/*
 * Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.truffle.r.nodes.builtin.base;

import static com.oracle.truffle.r.runtime.RBuiltinKind.*;

import java.util.*;

import com.oracle.truffle.api.dsl.*;
import com.oracle.truffle.r.nodes.builtin.*;
import com.oracle.truffle.r.runtime.*;
import com.oracle.truffle.r.runtime.data.*;
import com.oracle.truffle.r.runtime.ops.na.*;

@RBuiltin(name = "cummin", kind = PRIMITIVE)
public abstract class CumMin extends RBuiltinNode {

    private final NACheck na = NACheck.create();

    private static boolean complex_lt(RComplex a, RComplex b) {
        if (a.getRealPart() <= b.getRealPart()) {
            return (a.getImaginaryPart() < b.getImaginaryPart());
        }
        return false;
    }

    @Specialization
    public double cummin(double arg) {
        controlVisibility();
        return arg;
    }

    @Specialization
    public int cummin(int arg) {
        controlVisibility();
        return arg;
    }

    @Specialization
    public int cummin(byte arg) {
        controlVisibility();
        na.enable(arg);
        if (na.check(arg)) {
            return RRuntime.INT_NA;
        }
        return arg;
    }

    @Specialization
    public RIntVector cummin(RIntSequence arg) {
        return null;
    }

    @Specialization
    public RDoubleVector cummin(RDoubleVector v) {
        controlVisibility();
        double[] cmin_v = new double[v.getLength()];
        double min = v.getDataAt(0);
        cmin_v[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) < min)
                min = v.getDataAt(i);
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmin_v[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmin_v, i, cmin_v.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cmin_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummin(RIntVector v) {
        controlVisibility();
        int[] cmin_v = new int[v.getLength()];
        int min = v.getDataAt(0);
        cmin_v[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) < min)
                min = v.getDataAt(i);
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmin_v[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmin_v, i, cmin_v.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmin_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummin(RLogicalVector v) {
        controlVisibility();
        int[] cmin_v = new int[v.getLength()];
        int min = v.getDataAt(0);
        cmin_v[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) < min)
                min = v.getDataAt(i);
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmin_v[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmin_v, i, cmin_v.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmin_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RDoubleVector cummin(RStringVector v) {

        controlVisibility();
        double[] cmin_v = new double[v.getLength()];
        double min = na.convertStringToDouble(v.getDataAt(0));
        cmin_v[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            double value = na.convertStringToDouble(v.getDataAt(i));
            if (value < min)
                min = value;
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmin_v[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmin_v, i, cmin_v.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cmin_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RComplexVector cummin(RComplexVector v) {
        controlVisibility();
        String notDefinedError = "in cummin(%s) : 'cummax' not define for complex numbers";
        String.format(notDefinedError, v.toString());
        RError.error(null, notDefinedError);
        return null;
        /*
         * double[] cmin_v = new double[v.getLength() * 2]; RComplex min =
         * RDataFactory.createComplex(v.getDataAt(0).getRealPart(),
         * v.getDataAt(0).getImaginaryPart());
         * 
         * int i; na.enable(true); for (i = 0; i < v.getLength(); ++i) { RComplex value =
         * RDataFactory.createComplex(v.getDataAt(i).getRealPart(),
         * v.getDataAt(i).getImaginaryPart()); if (na.check(v.getDataAt(i))) { break; } if
         * (complex_lt(value, min)) min = value; cmin_v[2 * i] = min.getRealPart(); cmin_v[2 * i +
         * 1] = min.getImaginaryPart(); } if (!na.neverSeenNA()) { Arrays.fill(cmin_v, 2 * i,
         * cmin_v.length, RRuntime.DOUBLE_NA); } return RDataFactory.createComplexVector(cmin_v,
         * na.neverSeenNA(), v.getNames());
         */
    }

}
