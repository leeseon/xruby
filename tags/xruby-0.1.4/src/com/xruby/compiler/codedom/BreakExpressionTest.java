/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.compiler.codedom;

public class BreakExpressionTest extends TestingAstTestCase {
	public void test_break_1() {
		String program_text = "break 1";
		
		String expected_result = 
			"1\n" +
			"break\n" +
			"EOF";
		assertAstOutput(program_text, expected_result);
	}
}