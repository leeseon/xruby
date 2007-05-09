/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby;

import com.xruby.compiler.RubyCompiler;
import com.xruby.compiler.codegen.CompilationResults;
import com.xruby.runtime.lang.RubyProgram;
import com.xruby.runtime.lang.RubyRuntime;

import java.io.StringReader;

//This file serves as an example of running Ruby program in Java
public class Example {

	public static void main(String[] args) throws Exception {
		String program_text = 
			"def fibonacci(n)\n" +
			"    if n == 1 or n == 0\n" +
			"        n\n" +
			"    else\n" +
			"        fibonacci(n - 1) + fibonacci(n - 2)\n" +
			"    end    \n" +
			"end\n" +
			"\n" +
			"start_time = Time.new.to_f\n" +
			"puts start_time\n" +
			"puts fibonacci(30)\n" +
			"end_time = Time.now.to_f\n" +
			"puts start_time\n" +
			"puts end_time - start_time";
		
		RubyCompiler compiler = new RubyCompiler(null, false);
		CompilationResults codes = compiler.compile(new StringReader(program_text));
		RubyProgram p = codes.getRubyProgram();

		RubyRuntime.init(args);
		p.invoke();
		RubyRuntime.fini();
	}

}