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
        int[] cmaxV = new int[v.getLength()];

        if (v.getStride() < 0) { // all numbers are bigger than the first one
            Arrays.fill(cmaxV, v.getStart());
            return RDataFactory.createIntVector(cmaxV, RDataFactory.COMPLETE_VECTOR, v.getNames());
        } else {
            cmaxV[0] = v.getStart();
            for (int i = 1; i < v.getLength(); i++) {
                cmaxV[i] = cmaxV[i - 1] + v.getStride();
            }
            return RDataFactory.createIntVector(cmaxV, RDataFactory.COMPLETE_VECTOR, v.getNames());
        }
    }

    @Specialization
    public RDoubleVector cummax(RDoubleVector v) {
        controlVisibility();
        double[] cmaxV = new double[v.getLength()];
        double max = v.getDataAt(0);
        cmaxV[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) > max) {
                max = v.getDataAt(i);
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmaxV[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmaxV, i, cmaxV.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cmaxV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummax(RIntVector v) {
        controlVisibility();
        int[] cmaxV = new int[v.getLength()];
        int max = v.getDataAt(0);
        cmaxV[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) > max) {
                max = v.getDataAt(i);
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmaxV[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmaxV, i, cmaxV.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmaxV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RIntVector cummax(RLogicalVector v) {
        controlVisibility();
        int[] cmaxV = new int[v.getLength()];
        int max = v.getDataAt(0);
        cmaxV[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            if (v.getDataAt(i) > max) {
                max = v.getDataAt(i);
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmaxV[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmaxV, i, cmaxV.length, RRuntime.INT_NA);
        }
        return RDataFactory.createIntVector(cmaxV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RDoubleVector cummax(RStringVector v) {

        controlVisibility();
        double[] cmaxV = new double[v.getLength()];
        double max = na.convertStringToDouble(v.getDataAt(0));
        cmaxV[0] = max;
        na.enable(v);

        int i;
        for (i = 1; i < v.getLength(); ++i) {
            double value = na.convertStringToDouble(v.getDataAt(i));
            if (value > max) {
                max = value;
            }
            if (na.check(v.getDataAt(i))) {
                break;
            }
            cmaxV[i] = max;
        }
        if (!na.neverSeenNA()) {
            Arrays.fill(cmaxV, i, cmaxV.length, RRuntime.DOUBLE_NA);
        }
        return RDataFactory.createDoubleVector(cmaxV, na.neverSeenNA(), v.getNames());
    }

    @Specialization
    public RComplexVector cummax(RComplexVector v) {
        controlVisibility();
        throw RError.error(getSourceSection(), RError.Message.CUMMAX_UNDEFINED_FOR_COMPLEX);
    }

}
