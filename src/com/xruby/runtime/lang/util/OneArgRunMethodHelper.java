/**
 * Copyright 2007 Ye Zheng
 * Distributed under the Apache License
 */

package com.xruby.runtime.lang.util;

import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import com.xruby.compiler.codegen.CgUtil;
import com.xruby.compiler.codegen.Types;

class OneArgRunMethodHelper extends RunMethodHelper {
	private static final Method OneArgRunMethod = 
		CgUtil.getMethod("run", Types.RUBY_VALUE_TYPE, Types.RUBY_VALUE_TYPE,
			Types.RUBY_VALUE_TYPE, Types.RUBY_BLOCK_TYPE);
	
	protected Method getRunMethod() {
		return OneArgRunMethod;
	}
	
	protected void loadBlock(GeneratorAdapter mg) {
		mg.loadArg(2);
	}
	
	protected int rubyArgSize() {
		return 1;
	}
	
	protected void loadArgs(GeneratorAdapter mg) {
		mg.loadArg(1);
	}
}
