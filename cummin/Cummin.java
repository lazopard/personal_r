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
    public RIntVector cumMin(RIntSequence v) {
        controlVisibility();
        int[] cminV = new int[v.getLength()];

        if (v.getStride() > 0) { // all numbers are bigger than the first one
            Arrays.fill(cminV, v.getStart());
            return RDataFactory.createIntVector(cminV, RDataFactory.COMPLETE_VECTOR, v.getNames());
        } else {
            cminV[0] = v.getStart();
            for (int i = 1; i < v.getLength(); i++) {
                cminV[i] = cminV[i - 1] + v.getStride();
            }
            return RDataFactory.createIntVector(cminV, RDataFactory.COMPLETE_VECTOR, v.getNames());
        }
    }

    @Specialization
    public RDoubleVector cummin(RDoubleVector v) {
        controlVisibility();
        double[] cminV = new double[v.getLength()];
        double min = v.getDataAt(0);
        cminV[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) < min) {
                min = v.getDataAt(i);
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cminV[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cminV, i, cminV.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cminV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummin(RIntVector v) {
        controlVisibility();
        int[] cminV = new int[v.getLength()];
        int min = v.getDataAt(0);
        cminV[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) < min) {
                min = v.getDataAt(i);
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cminV[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cminV, i, cminV.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cminV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummin(RLogicalVector v) {
        controlVisibility();
        int[] cminV = new int[v.getLength()];
        int min = v.getDataAt(0);
        cminV[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) < min) {
                min = v.getDataAt(i);
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cminV[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cminV, i, cminV.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cminV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RDoubleVector cummin(RStringVector v) {

        controlVisibility();
        double[] cminV = new double[v.getLength()];
        double min = na.convertStringToDouble(v.getDataAt(0));
        cminV[0] = min;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            double value = na.convertStringToDouble(v.getDataAt(i));
            if (value < min) {
                min = value;
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cminV[i] = min;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cminV, i, cminV.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cminV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RComplexVector cummin(RComplexVector v) {
        controlVisibility();
        throw RError.error(getEncapsulatingSourceSection(), RError.Message.CUMMIN_UNDEFINED_FOR_COMPLEX);
    }

}
