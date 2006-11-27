package com.xruby.runtime.builtin;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

class Method_call extends RubyMethod {
	public Method_call() {
		super(-1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		MethodValue m = (MethodValue)receiver;
		return m.call(args, block);
	}
}

class Method_to_s extends RubyMethod {
	public Method_to_s() {
		super(0);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		MethodValue m = (MethodValue)receiver;
		return ObjectFactory.createString(m.toString());
	}
}

public class MethodClassBuilder {
	
	public static void initialize() {
		RubyClass c = RubyRuntime.MethodClass;
		c.defineMethod("call", new Method_call());
		RubyMethod to_s = new Method_to_s();
		c.defineMethod("to_s", to_s);
		c.defineMethod("inspect", to_s);
		
	}
}
