/**
 * Copyright 2005-2007 Xue Yong Zhi, Ye Zheng, Jie Li, Yu Su
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.runtime.builtin;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

class Array_new_with_given_objects extends RubyVarArgMethod {
    protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
        RubyArray a;
        if (null == args) {
            a = new RubyArray();
        } else {
            a = args.copy();
        }
        a.setRubyClass((RubyClass) receiver);
        return a;
    }
}

class Array_pack extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyArray array = (RubyArray) receiver;
        String format = ((RubyString) arg).toString();
        return ObjectFactory.createString(ArrayPacker.pack(array, format));
    }
}


public class ArrayClassBuilder {
    public static void initialize() {
        RubyClass c = RubyRuntime.ArrayClass;

        c.getSingletonClass().defineMethod("[]", new Array_new_with_given_objects());

        c.defineMethod("pack", new Array_pack());

    }
}