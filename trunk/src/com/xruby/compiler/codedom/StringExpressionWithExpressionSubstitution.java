/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.compiler.codedom;

public class StringExpressionWithExpressionSubstitution extends ExpressionWithExpressionSubstitution {
	
	public StringExpressionWithExpressionSubstitution(String s) {
		addString(s);
	}
	
	void merge(StringExpressionWithExpressionSubstitution another) {
		stmts_.addAll(another.stmts_);
	}

	public void accept(CodeVisitor visitor) {
		
		visitor.visitStringExpressionWithExpressionSubstitutionBegin();
		
		for (Object o : stmts_) {
			if (o instanceof String) {
				visitor.visitStringExpressionWithExpressionSubstitution((String)o);
			} else if (o instanceof InstanceVariableExpression) {
				((InstanceVariableExpression)o).accept(visitor);
				visitor.visitStringExpressionWithExpressionSubstitution();
			} else if (o instanceof ClassVariableExpression) {
				((ClassVariableExpression)o).accept(visitor);
				visitor.visitStringExpressionWithExpressionSubstitution();
			} else if (o instanceof GlobalVariableExpression) {
				((GlobalVariableExpression)o).accept(visitor);
				visitor.visitStringExpressionWithExpressionSubstitution();
			} else if (o instanceof CompoundStatement){
				((CompoundStatement)o).accept(visitor);
				visitor.visitStringExpressionWithExpressionSubstitution();
			} else {
				assert(false);
			}
		}
		
		visitor.visitStringExpressionWithExpressionSubstitutionEnd();
	}
	
}
