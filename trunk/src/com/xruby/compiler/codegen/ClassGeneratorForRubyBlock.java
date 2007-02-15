/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.compiler.codegen;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import com.xruby.runtime.lang.RubyBinding;
import java.util.*;

class ClassGeneratorForRubyBlock extends ClassGenerator {

	private final SymbolTable symbol_table_of_the_current_scope_;
	private final int argc_;
	private final boolean has_asterisk_parameter_;
	private final int default_argc_;
	private Set<String> fields_ = new HashSet<String>();//assigned fields are fields as well
	private Set<String> assigned_fields_ = new HashSet<String>();
	private boolean is_for_in_expression_;//for..in expression does not introduce new scope
	private RubyBinding binding_;

	public ClassGeneratorForRubyBlock(String name,
			int argc,
			boolean has_asterisk_parameter,
			int default_argc,
			SymbolTable symbol_table_of_the_current_scope,
			boolean is_for_in_expression,
			RubyBinding binding) {
		super(name);
		mg_for_run_method_ = visitRubyBlock();
		symbol_table_of_the_current_scope_ = symbol_table_of_the_current_scope;
		argc_ = argc;
		has_asterisk_parameter_ = has_asterisk_parameter;
		default_argc_ = default_argc;
		is_for_in_expression_ = is_for_in_expression;
		binding_ = binding;
	}
	
	protected Class getType() {
		return Types.RubyBlockClass;
	}

	private void loadField(String name) {
		mg_for_run_method_.loadThis();
		mg_for_run_method_.getField(Type.getType("L" + name_ + ";"), name, Type.getType(Types.RubyValueClass));
	}

	public void loadVariable(String name) {
		if (symbol_table_of_the_current_scope_.isDefinedInCurrentScope(name)) {
			fields_.add(name);
			loadField(name);
		} else {
			super.loadVariable(name);
		}
	}

	private void storeField(String name) {
		super.storeVariable("tmp$");
			
		getMethodGenerator().loadThis();
		loadVariable("tmp$");
		getMethodGenerator().putField(Type.getType("L" + name_ + ";"), name, Type.getType(Types.RubyValueClass));
	}

	public void storeVariable(String name) {
		if (is_for_in_expression_ || symbol_table_of_the_current_scope_.isDefinedInCurrentScope(name)) {
			fields_.add(name);
			assigned_fields_.add(name);
			storeField(name);
			if (is_for_in_expression_) {
				updateBinding(binding_, name);
			}
		} else {
			super.storeVariable(name);
		}
	}

	private void initialFiledUsingBlockParameter(String name) {
		if (symbol_table_of_the_current_scope_.isDefinedInCurrentScope(name)) {
			fields_.add(name);
			assigned_fields_.add(name);
			getMethodGenerator().loadThis();
			super.loadVariable(name);
			getMethodGenerator().putField(Type.getType("L" + name_ + ";"), name, Type.getType(Types.RubyValueClass));
		}
	}

	public void addParameter(String name) {
		super.addParameter(name);
		initialFiledUsingBlockParameter(name);
	}

	public void setAsteriskParameter(String name) {
		super.setAsteriskParameter(name);
		initialFiledUsingBlockParameter(name);
	}

	public void setBlockParameter(String name) {
		super.setBlockParameter(name);
		initialFiledUsingBlockParameter(name);
	}

	private MethodGenerator visitRubyBlock() {
		cv_.visit(Opcodes.V1_5,
				Opcodes.ACC_PUBLIC,		//need to modify fields when doing Proc#call, see RubyProc.java
				name_,	
				null,								// signature
				"com/xruby/runtime/lang/RubyBlock",	// superName
				null								// interface
				);
	
		return new MethodGenerator(Opcodes.ACC_PROTECTED,
				Method.getMethod("com.xruby.runtime.lang.RubyValue run(com.xruby.runtime.lang.RubyValue, com.xruby.runtime.value.RubyArray)"),
				null,
				null,
				cv_);
	}

	static String buildContructorSignature(int size) {
		StringBuilder method_name = new StringBuilder("void <init> (com.xruby.runtime.lang.RubyBlock, com.xruby.runtime.lang.RubyValue, com.xruby.runtime.lang.RubyBlock");
		for (int i = 0; i < size; ++i) {
			method_name.append(", ");
			method_name.append("com.xruby.runtime.lang.RubyValue");
		}
		method_name.append(")");
		return method_name.toString();
	}
	
	public String[] createFieldsAndConstructorOfRubyBlock() {
		String[] commons = fields_.toArray(new String[fields_.size()]);
		createConstructorOfRubyBlock(commons);
		createFields(commons);
		return commons;
	}

	public String[] getAssignedFields() {
		return assigned_fields_.toArray(new String[assigned_fields_.size()]);
	}

	private void createFields(final String[] commons) {
		for (String name : commons) {
			FieldVisitor fv = cv_.visitField(Opcodes.ACC_PUBLIC,
					name,
					Type.getDescriptor(Types.RubyValueClass),
					null,
					null
					);
			fv.visitEnd();
		}
	}
	
	private void createConstructorOfRubyBlock(final String[] commons) {
		
		MethodGenerator mg = new MethodGenerator(Opcodes.ACC_PUBLIC,
				Method.getMethod(buildContructorSignature(commons.length)),
				null,
				null,
				cv_);
		
		mg.loadThis();
		mg.push(argc_);
		mg.push(has_asterisk_parameter_);
		mg.push(default_argc_);
		mg.loadArg(0);
		mg.loadArg(1);
		mg.loadArg(2);
		mg.invokeConstructor(Type.getType(Types.RubyBlockClass),
						Method.getMethod("void <init> (int, boolean, int, com.xruby.runtime.lang.RubyBlock, com.xruby.runtime.lang.RubyValue, com.xruby.runtime.lang.RubyBlock)"));
		
		for (int i = 0; i < commons.length; ++i) {
			mg.loadThis();
			mg.loadArg(i + 3);
			mg.putField(Type.getType("L" + name_ + ";"), commons[i], Type.getType(Types.RubyValueClass));
		}
		
		mg.returnValue();
		mg.endMethod();
	}

	private void addVariableToBinding(String s) {
		getMethodGenerator().push(s);
		fields_.add(s);
		loadField(s);
		getMethodGenerator().invokeVirtual(Type.getType(Types.RubyBindingClass),
				Method.getMethod("com.xruby.runtime.lang.RubyBinding addVariable(String, com.xruby.runtime.lang.RubyValue)"));
	}

	public void createBinding(boolean isInSingletonMethod, boolean isInGlobalScope, boolean is_in_block) {
		super.createBinding(isInSingletonMethod, isInGlobalScope, is_in_block);

		Collection<String> vars = symbol_table_of_the_current_scope_.getLocalVariables();
		for (String s : vars) {
			addVariableToBinding(s);
		}
		
		Collection<String> params = symbol_table_of_the_current_scope_.getParameters();
		for (String s : params) {
			addVariableToBinding(s);
		}
	}

	public boolean isDefinedInCurrentScope(String name) {
		if (symbol_table_of_the_current_scope_.isDefinedInCurrentScope(name)) {
			return true;
		} else {
			return super.isDefinedInCurrentScope(name);
		}
	}
}
