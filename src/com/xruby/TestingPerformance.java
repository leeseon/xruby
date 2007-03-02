/** 
 * Copyright 2005-2007 Xue Yong Zhi
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.value.*;

/*
 * A simple console program that test XRuby runtime's performance 
 */
public class TestingPerformance {

	private static final int LOOP = 30000000;
	
	private static long test_createFixnum() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			ObjectFactory.createFixnum(i);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_createString() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			ObjectFactory.createString("xxxxx");
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_createArray() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			new RubyArray(ObjectFactory.fixnum1);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_cast() {
		RubyValue v = new RubyArray(ObjectFactory.fixnum1);
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			@SuppressWarnings("unused")
			RubyArray a = (RubyArray)v;
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_nothing() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			//Do nothing
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_findMethod() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			ObjectFactory.fixnum1.findMethod("+");
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_invokeMethod() {
		RubyMethod m = ObjectFactory.fixnum1.findMethod("+");
		RubyArray args = new RubyArray(ObjectFactory.fixnum1);
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < LOOP; ++i) {
			m.invoke(ObjectFactory.fixnum1, args, null);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	private static long test_callMethod() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 30000000; ++i) {
			RubyAPI.callMethod(ObjectFactory.fixnum1, ObjectFactory.fixnum1, "+");
		}
		long end = System.currentTimeMillis();
		return end - start;
	}
	
	/* Sample result:
	Fixnum Creation: 895
	String Creation: 3273
	Array Creation: 2556
	Type Casting: 93
	Do nothing: 27
	Method Finding: 3979
	Method Invoking: 1675
	Method Calling: 10250
	*/
	public static void main(String[] args) {
		System.out.println("Fixnum Creation: " + test_createFixnum());
		System.out.println("String Creation: " + test_createString());
		System.out.println("Array Creation: " + test_createArray());
		System.out.println("Type Casting: " + test_cast());
		System.out.println("Do nothing: " + test_nothing());
		System.out.println("Method Finding: " + test_findMethod());
		System.out.println("Method Invoking: " + test_invokeMethod());
		System.out.println("Method Calling: " + test_callMethod());
	}

}