/** 
 * Copyright (c) 2005-2006 Xue Yong Zhi. All rights reserved.
 */

package com.xruby.runtime.lang;

import com.xruby.runtime.value.*;

/**
 * Hold all instances of the class
 */
public class RubyClass extends RubyModule {
	//private Set<RubyObject> instances_ = new HashSet<RubyObject>();
	private RubyClass superclass_;

	public RubyClass(String name, RubyClass superclass) {
		super(name);
		superclass_ = superclass;
	}

	boolean isMyParent(final RubyClass superclass) {
		return superclass_ == superclass;
	}

	boolean isKindOf(RubyClass value) {
		if (value == this) {
			return true;
		} else if (null != superclass_) {
			return superclass_.isKindOf(value);
		} else {
			return false;
		}
	}

	RubyMethod findSuperMethod(String method_name) {
		if (null != superclass_){
			return superclass_.findMethod(method_name);
		}
		
		return null;
	}
	
	protected RubyMethod findMethod(String method_name) {
		RubyMethod m = super.findMethod(method_name);
		if (null != m) {
			return m;
		} 
		
		if (null != superclass_){
			return superclass_.findMethod(method_name);
		}
		
		return null;
	}
	
	protected RubyMethod findPublicMethod(String method_name) {
		RubyMethod m = super.findPublicMethod(method_name);
		if (null != m) {
			return m;
		}
		
		if (null != superclass_){
			return superclass_.findPublicMethod(method_name);
		}
		
		return null;
	}

	void collectMethodNames(RubyArray a) {
		super.collectMethodNames(a);
		if (null != superclass_){
			superclass_.collectMethodNames(a);
		}
	}
}