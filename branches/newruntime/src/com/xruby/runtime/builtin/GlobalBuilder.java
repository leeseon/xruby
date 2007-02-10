package com.xruby.runtime.builtin;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

public class GlobalBuilder implements ExtensionBuilder {
	public void initialize() {
		RubyAPI.defineGlobalMethod("raise", GlobalMethod.raise, -1);
		RubyAPI.defineGlobalMethod("fail", GlobalMethod.raise, -1);
		
		RubyAPI.defineGlobalMethod("proc", GlobalMethod.lambda, 0);
		RubyAPI.defineGlobalMethod("lambda", GlobalMethod.lambda, 0);
		
		RubyAPI.defineGlobalMethod("puts", GlobalMethod.puts, -1);
		RubyAPI.defineGlobalMethod("print", GlobalMethod.print, -1);
		RubyAPI.defineGlobalMethod("p", GlobalMethod.p, -1);
		
	}	
}

class GlobalMethod {
	public static RubyMethod raise = new RubyMethod() {
		protected RubyValue run(RubyValue receiver, RubyArray args,
				RubyBlock block) {
			
			int argc = args.size();
			switch (argc) {
			case 0:
				RubyAPI.raise(RubyRuntime.runtimeError, " not implemented");
				break;
			case 1:				
				if (args.get(0) instanceof RubyString) {
					RubyAPI.raise(RubyRuntime.runtimeError, ((RubyString)args.get(0)).getString());
					break;
				}
				
				break;
			case 2:
			case 3:
				RubyAPI.raise(RubyRuntime.runtimeError, " not implemented");
				break;
			default: 
				RubyAPI.raise(RubyRuntime.runtimeError, " not implemented");
			}
			
			return RubyConstant.QNIL; // not reach
		}		
	};
	
	public static RubyMethod lambda = new RubyNoArgMethod() {
		protected RubyValue run(RubyValue receiver) {
			return null;
		}		
	};
	
	public static RubyMethod puts = new RubyMethod() {
		protected RubyValue run(RubyValue receiver, RubyArray args,
				RubyBlock block) {			
			return RubyIO.puts(GlobalVariables.STDOUT, args);
		}		
	};
	
	public static RubyMethod print = new RubyMethod() {
		protected RubyValue run(RubyValue receiver, RubyArray args,
				RubyBlock block) {			
			return RubyIO.print(GlobalVariables.STDOUT, args);
		}		
	};
	
	public static RubyMethod p = new RubyMethod() {
		protected RubyValue run(RubyValue receiver, RubyArray args,
				RubyBlock block) {			
			return RubyIO.p(GlobalVariables.STDOUT, args);
		}		
	};
}