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

@RBuiltin(name = "cummax", kind = PRIMITIVE)
public abstract class CumMax extends RBuiltinNode {

    private final NACheck na = NACheck.create();

    private static boolean complex_gt(RComplex a, RComplex b) {
        if (a.getRealPart() >= b.getRealPart()) {
            return (a.getImaginaryPart() > b.getImaginaryPart());
        }
        return false;
    }

    @Specialization
    public double cummax(double arg) {
        controlVisibility();
        return arg;
    }

    @Specialization
    public int cummax(int arg) {
        controlVisibility();
        return arg;
    }

    @Specialization
    public int cummax(byte arg) {
        controlVisibility();
        na.enable(arg);
        if (na.check(arg)) {
            return RRuntime.INT_NA;
        }
        return arg;
    }

    @Specialization
    public RIntVector cummax(RIntSequence v) {

        controlVisibility();
        int[] cmax_v = new int[v.getLength()];
        int current = v.getStart();
        int max = 0;
        int i;
        na.enable(true);
        for (i = 0; i < v.getLength(); ++i) {

            if (current > max)
                max = current;
            if (na.check(max)) {
                break;
            }
            current = v.getStride();
            cmax_v[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmax_v, i, cmax_v.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmax_v, RDataFactory.COMPLETE_VECTOR, v.getNames());
    }

    @Specialization
    public RDoubleVector cummax(RDoubleVector v) {
        controlVisibility();
        double[] cmax_v = new double[v.getLength()];
        double max = v.getDataAt(0);
        cmax_v[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) > max)
                max = v.getDataAt(i);
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmax_v[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmax_v, i, cmax_v.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cmax_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummax(RIntVector v) {
        controlVisibility();
        int[] cmax_v = new int[v.getLength()];
        int max = v.getDataAt(0);
        cmax_v[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) > max)
                max = v.getDataAt(i);
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmax_v[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmax_v, i, cmax_v.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmax_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummax(RLogicalVector v) {
        controlVisibility();
        int[] cmax_v = new int[v.getLength()];
        int max = v.getDataAt(0);
        cmax_v[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) > max)
                max = v.getDataAt(i);
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmax_v[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmax_v, i, cmax_v.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmax_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RDoubleVector cummax(RStringVector v) {

        controlVisibility();
        double[] cmax_v = new double[v.getLength()];
        double max = na.convertStringToDouble(v.getDataAt(0));
        cmax_v[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            double value = na.convertStringToDouble(v.getDataAt(i));
            if (value > max)
                max = value;
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmax_v[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmax_v, i, cmax_v.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cmax_v, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RComplexVector cummax(RComplexVector v) {
        controlVisibility();
        String notDefinedError = "in cummax(%s) : 'cummin' not define for complex numbers";
        String.format(notDefinedError, v.toString());
        RError.error(null, notDefinedError);
        return null;
        /*
         * double[] cmax_v = new double[v.getLength() * 2]; RComplex max =
         * RDataFactory.createComplex(v.getDataAt(0).getRealPart(),
         * v.getDataAt(0).getImaginaryPart());
         * 
         * int i; na.enable(true); for (i = 0; i < v.getLength(); ++i) { RComplex value =
         * RDataFactory.createComplex(v.getDataAt(i).getRealPart(),
         * v.getDataAt(i).getImaginaryPart()); if (na.check(v.getDataAt(i))) { break; } if
         * (complex_gt(value, max)) max = value; cmax_v[2 * i] = max.getRealPart(); cmax_v[2 * i +
         * 1] = max.getImaginaryPart(); } if (!na.neverSeenNA()) { Arrays.fill(cmax_v, 2 * i,
         * cmax_v.length, RRuntime.DOUBLE_NA); } return RDataFactory.createComplexVector(cmax_v,
         * na.neverSeenNA(), v.getNames());
         */
    }

}
