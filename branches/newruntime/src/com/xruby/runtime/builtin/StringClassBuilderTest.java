/** 
 * Copyright (c) 2005-2006 Xue Yong Zhi. All rights reserved.
 */

package com.xruby.runtime.builtin;

import junit.framework.TestCase;
import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

public class StringClassBuilderTest extends TestCase {

	public void test_to_i() throws RubyException {
		RubyValue str = ObjectFactory.createString("1234");
		StringValue value = (StringValue)str.getValue();
		assertEquals("1234", value.toString());
		RubyMethod m = str.findPublicMethod("to_i");
		RubyValue result = m.invoke(str, null, null);
		IntegerValue result_value = (IntegerValue)result.getValue();
		assertEquals(1234, result_value.intValue());
	}
	
	public void test_to_f() throws RubyException {
		RubyValue str = ObjectFactory.createString("0.1234");
		StringValue value = (StringValue)str.getValue();
		assertEquals("0.1234", value.toString());
		RubyMethod m = str.findPublicMethod("to_f");
		RubyValue result = m.invoke(str, null, null);
		FloatValue result_value = (FloatValue)result.getValue();
		assertEquals((double)0.1234, result_value.doubleValue());
	}
	
	public void test_upcase() throws RubyException {
		RubyValue str = ObjectFactory.createString("abcDe");
		StringValue value = (StringValue)str.getValue();
		assertEquals(value.toString(), "abcDe");
		RubyMethod m = str.findPublicMethod("upcase");
		RubyValue result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("abcDe", value.toString());
		StringValue result_value = (StringValue)result.getValue();
		assertEquals("ABCDE", result_value.toString());
		
		str = ObjectFactory.createString("abcDe");
		value = (StringValue)str.getValue();
		assertEquals(value.toString(), "abcDe");
		result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("abcDe", value.toString());
		result_value = (StringValue)result.getValue();
		assertEquals("ABCDE", result_value.toString());
	}
	
	public void test_upcase_dangers() throws RubyException {
		RubyValue str = ObjectFactory.createString("abcDe");
		StringValue value = (StringValue)str.getValue();
		assertEquals("abcDe", value.toString());
		RubyMethod m = str.findPublicMethod("upcase!");
		RubyValue result = m.invoke(str, null, null);
		assertTrue(result == str);
		StringValue result_value = (StringValue)result.getValue();
		assertEquals("ABCDE", result_value.toString());
		
		str = ObjectFactory.createString("ABC");
		value = (StringValue)str.getValue();
		assertEquals("ABC", value.toString());
		result = m.invoke(str, null, null);
		assertEquals("ABC", value.toString());
		assertEquals(ObjectFactory.nilValue, result);
	}

	public void test_downcase() throws RubyException {
		RubyValue str = ObjectFactory.createString("abcDe");
		StringValue value = (StringValue)str.getValue();
		assertEquals(value.toString(), "abcDe");
		RubyMethod m = str.findPublicMethod("downcase");
		RubyValue result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("abcDe", value.toString());
		StringValue result_value = (StringValue)result.getValue();
		assertEquals("abcde", result_value.toString());
		
		str = ObjectFactory.createString("abcDe");
		value = (StringValue)str.getValue();
		assertEquals(value.toString(), "abcDe");
		result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("abcDe", value.toString());
		result_value = (StringValue)result.getValue();
		assertEquals("abcde", result_value.toString());
	}
	
	public void test_downcase_dangers() throws RubyException {
		RubyValue str = ObjectFactory.createString("abcDe");
		StringValue value = (StringValue)str.getValue();
		assertEquals("abcDe", value.toString());
		RubyMethod m = str.findPublicMethod("downcase!");
		RubyValue result = m.invoke(str, null, null);
		assertTrue(result == str);
		StringValue result_value = (StringValue)result.getValue();
		assertEquals("abcde", result_value.toString());
		
		str = ObjectFactory.createString("abc");
		value = (StringValue)str.getValue();
		assertEquals("abc", value.toString());
		result = m.invoke(str, null, null);
		assertEquals("abc", value.toString());
		assertEquals(ObjectFactory.nilValue, result);
	}

	public void test_capitalize() throws RubyException {
		RubyValue str = ObjectFactory.createString("abc");
		RubyMethod m = str.findPublicMethod("capitalize");
		RubyValue result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("Abc", ((StringValue)result.getValue()).toString());
		
		str = ObjectFactory.createString("HELLO");
		result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("Hello", ((StringValue)result.getValue()).toString());

		str = ObjectFactory.createString("123ABC");
		result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("123abc", ((StringValue)result.getValue()).toString());
		
		str = ObjectFactory.createString("Hello");
		result = m.invoke(str, null, null);
		assertTrue(result != str);
		assertEquals("Hello", ((StringValue)result.getValue()).toString());
	}
	
	public void test_capitalize_danger() throws RubyException {
		RubyValue str = ObjectFactory.createString("abc");
		RubyMethod m = str.findPublicMethod("capitalize!");
		RubyValue result = m.invoke(str, null, null);
		assertTrue(result == str);
		assertEquals("Abc", ((StringValue)result.getValue()).toString());
		
		str = ObjectFactory.createString("HELLO");
		result = m.invoke(str, null, null);
		assertTrue(result == str);
		assertEquals("Hello", ((StringValue)result.getValue()).toString());

		str = ObjectFactory.createString("123ABC");
		result = m.invoke(str, null, null);
		assertTrue(result == str);
		assertEquals("123abc", ((StringValue)result.getValue()).toString());
		
		str = ObjectFactory.createString("Hello");
		result = m.invoke(str, null, null);
		assertEquals(ObjectFactory.nilValue, result);
		assertEquals("Hello", ((StringValue)str.getValue()).toString());
	}
	
	public void test_operator_compare() throws RubyException {
		RubyValue str1 = ObjectFactory.createString("abc");
		RubyValue str2 = ObjectFactory.createString("abd");
		RubyArray args = new RubyArray();
		args.add(str2);
		RubyMethod m = str1.findPublicMethod("<=>");
		RubyValue result = m.invoke(str1, args, null);
		assertEquals(result, ObjectFactory.createFixnum(-1));
		
		str2 = ObjectFactory.createString("abb");
		args = new RubyArray();
		args.add(str2);
		result = m.invoke(str1, args, null);
		assertEquals(result, ObjectFactory.createFixnum(1));
		
		str2 = ObjectFactory.createString("abc");
		args = new RubyArray();
		args.add(str2);
		result = m.invoke(str1, args, null);
		assertEquals(result, ObjectFactory.createFixnum(0));
		
		str2 = ObjectFactory.createString("a");
		args = new RubyArray();
		args.add(str2);
		result = m.invoke(str1, args, null);
		assertEquals(result, ObjectFactory.createFixnum(1));
		
		str2 = ObjectFactory.createString("b");
		args = new RubyArray();
		args.add(str2);
		result = m.invoke(str1, args, null);
		assertEquals(result, ObjectFactory.createFixnum(-1));
		
		args = new RubyArray();
		args.add(ObjectFactory.createFixnum(1));
		result = m.invoke(str1, args, null);
		assertTrue(result == ObjectFactory.nilValue);
	}
}
