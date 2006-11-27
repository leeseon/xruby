/** 
 * Copyright (c) 2005-2006 Xue Yong Zhi. All rights reserved.
 */

package com.xruby.runtime.builtin;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

class Object_operator_equal extends RubyMethod {
	public Object_operator_equal() {
		super(1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		if (receiver == args.get(0)) {
			return ObjectFactory.trueValue;
		} else {
			return ObjectFactory.falseValue;
		}
	}
}

class Object_alloc extends RubyMethod {
	public Object_alloc() {
		super(-1);
	}
	
	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		return new RubyObject((RubyClass)receiver);
	}
}

public class ObjectClassBuilder {
	
	public static void initialize() {
		RubyClass c = RubyRuntime.ObjectClass;
		c.defineMethod("==", new Object_operator_equal());
		c.defineAllocMethod(new Object_alloc());
	}
}
