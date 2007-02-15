/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.runtime.lang;

import java.util.*;
import com.xruby.runtime.value.*;

abstract class MethodCollectionWithMixin extends MethodCollection {
	private ArrayList<RubyModule> mixins_ = new ArrayList<RubyModule>();

	MethodCollectionWithMixin(RubyClass c) {
		super(c);
	}
	
	public void includeModule(RubyModule m) {
		if (!mixins_.contains(m)) {
			mixins_.add(0, m);
		}
	}

	protected RubyMethod findOwnMethod(String method_name) {
		RubyMethod m = super.findOwnMethod(method_name);
		if (null != m) {
			return m;
		}
		
		for (RubyModule module : mixins_) {
			m = module.findOwnMethod(method_name);
			if (null != m) {
				return m;
			}
		}
		
		return null;
	}

	protected RubyMethod findOwnPublicMethod(String method_name) {
		RubyMethod m = super.findOwnMethod(method_name);
		if (null != m && m.isPublic()) {
			return m;
		}
		
		for (RubyModule module : mixins_) {
			m = module.findOwnMethod(method_name);
			if (null != m && m.isPublic()) {
				return m;
			}
		}
		
		return null;
	}

	void collectOwnMethodNames(RubyArray a) {
		super.collectOwnMethodNames(a);
		for (RubyModule module : mixins_) {
			module.collectOwnMethodNames(a);
		}
	}

	public final void collectIncludedModuleNames(RubyArray a) {
		a.add(this);
		for (RubyModule module : mixins_) {
			module.collectIncludedModuleNames(a);
		}
	}

	protected RubyValue findClassVariable(String name) {
		RubyValue v = super.findClassVariable(name);
		if (null != v) {
			return v;
		}

		for (RubyModule module : mixins_) {
			v = module.findClassVariable(name);
			if (null != v) {
				return v;
			}
		}

		return null;
	}

	RubyValue getConstant(String name) {
		RubyValue v = super.getConstant(name);
		if (null != v) {
			return v;
		}
		
		for (RubyModule module : mixins_) {
			v = module.getConstant(name);
			if (null != v) {
				return v;
			}
		}

		return null;
	}
	
	public RubyValue getCurrentNamespaceConstant(String name) {
		RubyValue v = getConstant(name);
		if (null != v) {
			return v;
		}

		v = RubyRuntime.GlobalScope.getConstant(name);
		if (null == v) {
			throw new RubyException(RubyRuntime.NameErrorClass, "uninitialized constant " + ((null == name_) ? "" : (name_ + "::")) + name);
		}
		return v;
	}
}