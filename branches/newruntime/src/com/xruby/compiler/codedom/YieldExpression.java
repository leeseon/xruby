/** 
 * Copyright (c) 2005-2006 Xue Yong Zhi. All rights reserved.
 */

package com.xruby.compiler.codedom;

public class YieldExpression extends Expression {

	private MethodCallArguments arguments_;

	public YieldExpression(MethodCallArguments arguments) {
		arguments_ = arguments;
	}

	public void accept(CodeVisitor visitor) {
		visitor.visitYieldBegin();

		if (null == arguments_) {
			visitor.visitNoParameter();
		} else {
			arguments_.accept(visitor);
		}

		visitor.visitYieldEnd();
	}
}
