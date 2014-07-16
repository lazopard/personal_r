/*
 * Copyright (c) 2014, 2014, Oracle and/or its affiliates. All rights reserved.
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
import com.oracle.truffle.r.runtime.data.model.*;

@RBuiltin(name = "charmatch", kind = INTERNAL)
public abstract class CharMatch extends RBuiltinNode {

    @Specialization
    public RIntVector doCharMatch(String x, RAbstractStringVector table, RIntVector noMatch) {

        ArrayList<Integer> partialMatches = new ArrayList<>(table.getLength());

        // match or partially match strings
        for (int i = 0; i < table.getLength(); i++) {
            String tempString = table.getDataAt(i);
            int matchLength = 0;
            for (int j = 0; j < x.length(); j++) {
                if (x.charAt(j) == tempString.charAt(j)) {
                    matchLength++;
                } else {
                    break;
                }
            }
            if (matchLength == x.length()) {
                partialMatches.add(i + 1);
            }
        }

        if (partialMatches.size() == 0) { // no matches or partial matches
            return noMatch;
        } else if (partialMatches.size() == 1) { // one partial match
            return RDataFactory.createIntVectorFromScalar(partialMatches.get(0));
        } else if (partialMatches.size() > 0) { // many partial matches
            return RDataFactory.createIntVectorFromScalar(0);
        } else {
            throw RError.nyi(getEncapsulatingSourceSection(), "charmatch aspect");
        }
    }

    @Specialization
    public RIntVector doCharMatch(String x, RAbstractStringVector table) {

        ArrayList<Integer> partialMatches = new ArrayList<>(table.getLength());

        // match or partially match strings
        for (int i = 0; i < table.getLength(); i++) {
            String tempString = table.getDataAt(i);
            int matchLength = 0;
            for (int j = 0; j < x.length(); j++) {
                if (x.charAt(j) == tempString.charAt(j)) {
                    matchLength++;
                } else {
                    break;
                }
            }
            if (matchLength == x.length()) {
                partialMatches.add(i + 1);
            }
        }

        if (partialMatches.size() == 0) { // no matches or partial matches
            return RDataFactory.createIntVectorFromScalar(RRuntime.INT_NA);
        } else if (partialMatches.size() == 1) { // one partial match
            return RDataFactory.createIntVectorFromScalar(partialMatches.get(0));
        } else if (partialMatches.size() > 0) { // many partial matches
            return RDataFactory.createIntVectorFromScalar(0);
        } else {
            throw RError.nyi(getEncapsulatingSourceSection(), "charmatch aspect");
        }
    }
}
