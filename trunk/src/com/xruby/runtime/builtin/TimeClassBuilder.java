package com.xruby.runtime.builtin;

import java.util.Date;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

class Time_new extends RubyMethod {
	public Time_new() {
		super(0);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		return ObjectFactory.createTime(new RubyTime());
	}
}

class Time_to_f extends RubyMethod {
	public Time_to_f() {
		super(0);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		RubyTime t = (RubyTime)receiver.getValue();
		return ObjectFactory.createFloat((double)t.getTime() / 1000);
	}
}

class Time_to_i extends RubyMethod {
	public Time_to_i() {
		super(0);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		RubyTime t = (RubyTime)receiver.getValue();
		return ObjectFactory.createFixnum((int)(t.getTime() / 1000));
	}
}

class Time_to_s extends RubyMethod {
	public Time_to_s() {
		super(0);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		RubyTime t = (RubyTime)receiver.getValue();
		return ObjectFactory.createString(t.toString());
	}
}

class Time_at extends RubyMethod {
	public Time_at() {
		super(1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		Object value = args.get(0).getValue();
		long time = 0;
		if (value instanceof RubyFixnum){
			time = ((RubyFixnum)value).intValue();
		}else if (value instanceof RubyBignum){
			time = ((RubyBignum)value).getValue().longValue();
		}else if (value instanceof RubyFloat){
			time = (long)((RubyFloat)value).doubleValue();
		}else{
			throw new RubyException(RubyRuntime.TypeErrorClass, "can't convert " + args.get(0).getRubyClass().getName() + " into time");
		}
		return ObjectFactory.createTime(new RubyTime(new Date(time * 1000)));
	}
}

class Time_plus extends RubyMethod {
	public Time_plus() {
		super(1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		RubyTime time = (RubyTime)receiver.getValue();
		Object value = args.get(0).getValue();
		long timeAdd = 0;
		if (value instanceof RubyFixnum){
			timeAdd = ((RubyFixnum)value).intValue();
		}else if (value instanceof RubyBignum){
			timeAdd = ((RubyBignum)value).getValue().longValue();
		}else if (value instanceof RubyFloat){
			double add = (long)((RubyFloat)value).doubleValue();
			return ObjectFactory.createTime(new RubyTime(new Date(time.getTime() + (long)(add * 1000))));
		}else{
			throw new RubyException(RubyRuntime.TypeErrorClass, "can't convert " + args.get(0).getRubyClass().getName() + " into Float");
		}
		return ObjectFactory.createTime(new RubyTime(new Date(time.getTime() + timeAdd * 1000)));
	}
}

class Time_minus extends RubyMethod {
	public Time_minus() {
		super(1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		RubyTime time1 = (RubyTime)receiver.getValue();
		RubyTime time2 = RubyTypesUtil.convertToTime(args.get(0));
		long timeInteval = time1.getTime() - time2.getTime();
		return ObjectFactory.createFloat((double)timeInteval / 1000);
	}
}

class Time_operator_compare extends RubyMethod {
	public Time_operator_compare() {
		super(1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		long time1 = ((RubyTime)receiver.getValue()).getTime();
		long time2 = RubyTypesUtil.convertToTime(args.get(0)).getTime();
		if (time1 < time2){
			return ObjectFactory.createFixnum(-1);
		}else if (time1 > time2) {
			return ObjectFactory.createFixnum(1);
		}
		return ObjectFactory.createFixnum(0);
	}
}

public class TimeClassBuilder {
	public static RubyClass create() {
		RubyClass c = RubyRuntime.GlobalScope.defineNewClass("Time", RubyRuntime.ObjectClass);
		c.defineMethod("to_f", new Time_to_f());
		c.defineMethod("to_i", new Time_to_i());
		c.defineMethod("to_s", new Time_to_s());
		c.defineMethod("+", new Time_plus());
		c.defineMethod("-", new Time_minus());
		c.defineMethod("<=>", new Time_operator_compare());
		return c;
	}

	//Do not call this method in create(), otherwise it will cause initialization probelem.
	public static void initSingletonMethods() {
		RubyMethod m = new Time_new();
		ObjectFactory.TimeClassValue.defineMethod("new", m);
		ObjectFactory.TimeClassValue.defineMethod("now", m);
		ObjectFactory.TimeClassValue.defineMethod("at", new Time_at());
	}
}

