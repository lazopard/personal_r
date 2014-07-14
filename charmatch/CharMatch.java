package com.oracle.truffle.r.nodes.builtin.base;

import static com.oracle.truffle.r.runtime.RBuiltinKind.*;

import java.util.*;

import com.oracle.truffle.api.dsl.*;
import com.oracle.truffle.r.nodes.builtin.*;
import com.oracle.truffle.r.runtime.*;
import com.oracle.truffle.r.runtime.data.*;
import com.oracle.truffle.r.runtime.data.model.*;

@RBuiltin(name = "pmatch", kind = INTERNAL)
public abstract class CharMatch extends RBuiltinNode {

    @Specialization
    public RIntVector doCharMatch(String x, RAbstractStringVector table) {

        ArrayList<Integer> matches = new ArrayList<>(table.getLength());
        for (int i = 0; i < table.getLength(); i++) {
            if (x.equals(table.getDataAt(i))) {
                matches.add(i + 1);
            }
        }
        int size = matches.size();
        if (size == 0) {
            return RDataFactory.createIntVectorFromScalar(RRuntime.INT_NA);
        } else if (size == 1) {
            return RDataFactory.createIntVectorFromScalar(matches.get(0));
        } else {
            throw RError.nyi(getEncapsulatingSourceSection(), "pmatch aspect");
        }
    }
}
