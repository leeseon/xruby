package com.xruby.runtime.lang;

import java.io.*;
import com.xruby.runtime.value.*;

public class RubyAPI {

	public static boolean testTrueFalse(RubyValue value) {
		//only 'nil' and 'false' is false.
		if (value.getRubyClass() == RubyRuntime.FalseClassClass) {
			return false;
		}  else if (value.getRubyClass() == RubyRuntime.NilClassClass) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean testCaseEqual(RubyValue value1, RubyValue value2) {
		RubyValue r = RubyAPI.callPublicMethod(value1, value2, "===");
		return testTrueFalse(r);
	}

	public static boolean testEqual(RubyValue value1, RubyValue value2) {
		RubyValue r = RubyAPI.callPublicMethod(value1, value2, "==");
		return testTrueFalse(r);
	}

	public static boolean testExceptionType(RubyArray classes_to_compare, RubyException e) {
		RubyValue value = e.getRubyValue();
		for (RubyValue class_to_compare : classes_to_compare) {
			if (RubyAPI.isKindOf(class_to_compare, value)) {
				return true;
			}
		}
	
		return false;
	}

	public static boolean isKindOf(RubyValue class_to_compare, RubyValue value) {
		return value.getRubyClass().isKindOf((RubyClass)class_to_compare.getValue());
	}

	public static boolean isInstanceOf(RubyValue class_to_compare, RubyValue value) {
		return (value.getRubyClass() == class_to_compare.getValue());
	}

	//receiver is implicit self
	public static RubyValue callMethod(RubyValue receiver, RubyArray args, RubyBlock block, String method_name) {
		RubyMethod m = receiver.findMethod(method_name);
		if (null == m) {
			throw new RubyException(RubyRuntime.NameErrorClass, "method '" +  method_name + "' can not be found in 'Object'");
		}
	
		return m.invoke(receiver, args, block);
	}

	//method call with one argument and no block
	public static RubyValue callPublicMethod(RubyValue receiver, RubyValue arg, String method_name) {
		return RubyAPI.callPublicMethod(receiver, new RubyArray(arg), null, method_name);
	}

	public static RubyValue callPublicMethod(RubyValue receiver, RubyArray args, RubyBlock block, String method_name) {
		RubyMethod m = receiver.findPublicMethod(method_name);
		if (null == m) {
			throw new RubyException(RubyRuntime.NameErrorClass, "public method '" +  method_name + "' can not be found in '" + receiver.getRubyClass().getName() + "'");
		}
	
		return m.invoke(receiver, args, block);
	}

	public static RubyValue callSuperMethod(RubyValue receiver, RubyArray args, RubyBlock block, String method_name) {
		RubyMethod m = receiver.getRubyClass().findSuperMethod(method_name);
		if (null == m) {
			throw new RubyException(RubyRuntime.NameErrorClass, "super method '" +  method_name + "' can not be found in '" + receiver.getRubyClass().getName() + "'");
		}
	
		return m.invoke(receiver, args, block);
	}

	public static RubyValue operatorNot(RubyValue value) {
		if (testTrueFalse(value)) {
			return ObjectFactory.falseValue;
		} else {
			return ObjectFactory.trueValue;
		}
	}

	public static RubyValue runCommandAndCaptureOutput(String value) {
		try {
			Process p = Runtime.getRuntime().exec(value);
			StringBuilder output = new StringBuilder();
	
			BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line;
			while ((line = stderr.readLine()) != null) {
				output.append(line);
				output.append("\n");
			}
			stderr.close();
	
			BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				output.append(line);
				output.append("\n");
			}
			stdout.close();
	
			return ObjectFactory.createString(output.toString());
		} catch (IOException e) {
			throw new RubyException(RubyRuntime.ExceptionClass, e.toString());
		}
	}

	public static RubyValue expandArrayIfThereIsZeroOrOneValue(RubyArray a) {
		if (a.size() <= 1) {
			return a.get(0);
		} else {
			return ObjectFactory.createArray(a);
		}
	}

	public static RubyValue expandArrayIfThereIsZeroOrOneValue(RubyValue v) {
		if (v.getValue() instanceof RubyArray) {
			RubyArray a = (RubyArray)v.getValue();
			if (!a.isNotSingleAsterisk()) {
				return expandArrayIfThereIsZeroOrOneValue(a);
			}
		}
	
		return v;
	}

	public static RubyArray expandArrayIfThereIsOnlyOneRubyArray(RubyArray a) {
		if (a.size() == 1 &&
				a.get(0).getValue() instanceof RubyArray) {
			return (RubyArray)a.get(0).getValue();
		} else {
			return a;
		}
	}
	
	public static RubyArray convertToArrayIfNotYet(RubyValue v) {
		if (v.getValue() instanceof RubyArray) {
			return (RubyArray)v.getValue();
		} else {
			return new RubyArray(v);
		}
	}

}
