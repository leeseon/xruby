/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.compiler.codedom;

import java.util.ArrayList;

public class WhileExpression extends Expression {
	private final Expression condition_;
	private final CompoundStatement body_;
	boolean is_until_;
	
	public WhileExpression(Expression condition, CompoundStatement body, boolean is_until) {
		assert(null != condition);
		condition_ = condition;
		body_ = body;
		is_until_ = is_until;
	}
	
	boolean willNotBeExecuted() {
		if (!is_until_ && conditionIsAlwayFalse(condition_)) {
			//while (false)
			return true;
		} else if (is_until_ && conditionIsAlwayTrue(condition_)) {
			//while (true)
			return true;
		} else {
			return false;
		}
	}
	
	public void accept(CodeVisitor visitor) {
		//We used to generate code for body first, then condition (javac does this as well, it will help to create a little more efficient code).
		//But later we changed it back, because local variable may be first assigned in while expression's condition,  and we are replying
		//codegen backend to to maintain a symbol table -- so we should not change the consequence of visiting.

		if (null != body_) {
			body_.ensureVariablesAreInitialized(visitor);
		}

		//TODO apply this optimization to other places
		//If we have a  program like this:
		// i = 0; while i < 3000; i +=1; end
		//Without this optimization ObjectFactorycreateFixnum(3000) will be called 3000 times.
		ArrayList<Integer> integers = new ArrayList<Integer>();
		condition_.getFrequentlyUsedIntegers(integers);
		for (Integer i : integers) {
			visitor.visitFrequentlyUsedInteger(i);
		}
		
		visitor.visitWhileConditionBegin();
		condition_.accept(visitor);
		visitor.visitWhileConditionEnd(is_until_);

		if (null != body_) {
			body_.accept(visitor);
		}
		
		visitor.visitWhileBodyEnd(null != body_);
	}

}
