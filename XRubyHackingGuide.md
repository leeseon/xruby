_NOTE: this article is still under development. Last significant update: Dec 20. 2006._

_Please add your name to the Thanks list if you changed this page. Or add your name to the Authors list if you made significant contribution (e.g. started a new paragraph). Thank you for participating!_

# Authors #
  * Xue Yong Zhi (zhixueyong AT hotmail DOT com)
  * Ye Zheng (dreamhead.cn AT gmail DOT com)

# Thanks #

# Introduction #

The goal of the article is to help users/developers understand XRuby's implementation.

# How to Compile Ruby? #

So how to compile Ruby to Java bytecode? First, you do not have to be a bytecode expert to think of this problem. Java's bytecode is a very high level abstraction on top of native machine instructions, and is very close to the Java source code. So you can just think of the problem as: how to represent a Ruby program in Java?

The two languages have lots of things in common: Ruby is a OO language, it has classes, methods, variables etc, Java has them as well. Does it mean we can map Ruby's class as a Java class, Ruby's method as a Java method? Well, besides all the similarities, there are enough differences to make the naive idea infeasible: first, Ruby is a Dynamic Typing language, so a method can take parameters of different types, while in Java, parameters' type are part of the signature. Second, in Ruby, method can be added and removed dynamically from a class, and current JVM does not support this behavior very well. It worth pointing out the above problems may be addressed in future version JVM, please check out Gilad Bracha's work at [JSR 292](http://www.jcp.org/en/jsr/detail?id=292).

So one solution is to maintain a type system ourselves, and this is the approach that XRuby is using (Ruby.NET seems to use this as well). So from the JVM's point of view, a Ruby class is just an object, which contains other objects that represent methods etc. We will talk more about this later.

Another approach is to compile the source code dynamically. Given type information is known at run time, it is possible to compile the code into very efficient code (same methods may be compiled to several different versions due to the duck typing nature).

TODO compare the two approaches.


# Example #
Let's understand more about XRuby through an example:

```
class MyClass
	def say_hello_three_times
		3.times {puts "hello"}
	end
end

MyClass.new.say_hello_three_times
```

we save the above code as test.rb, and then compile it using XRuby:

```
java -jar xruby-0.1.4.jar -c test.rb
```

Then we get test.jar file, we can run the program with the following command:

```
java -jar test.jar
```

Of course you will see the following output:

```
hello
hello
hello
```

If you look at the test.jar file, you will find three class files:
  * `test/BLOCK$2.class`
  * `test/say_hello_three_times$1.class`
  * `test/main.class`

Which are equivalent to the following Java program:

```
//test/main.class
public class main implements RubyProgram
{

    public main()
    {
    }

    public static void main(String args[]) throws RubyException
    {
        RubyRuntime.init(args);
        (new main()).run();
        RubyRuntime.fini();
    }

    public RubyValue run() throws RubyException
    {
        RubyValue rubyvalue;
        RubyValue rubyvalue1;
        MyClass$0(rubyvalue1 = rubyvalue = RubyAPI.defineClass("MyClass", RubyRuntime.ObjectClass), (RubyModule)rubyvalue1.getValue());
        return RubyAPI.callPublicMethod(RubyAPI.callPublicMethod(RubyModule.getTopLevelConstant("MyClass"), null, null, "new"), null, null, "say_hello_three_times");
    }

    private RubyValue MyClass$0(RubyValue rubyvalue, RubyModule rubymodule) throws RubyException
    {
        rubymodule.defineMethod("say_hello_three_times", new say_hello_three_times._cls1());
        return rubyvalue;
    }
}


//say_hello_three_times$1.class
class say_hello_three_times$1 extends RubyMethod
{

    protected RubyValue run(RubyValue rubyvalue, ArrayValue arrayvalue, RubyBlock rubyblock) throws RubyException
    {
        return RubyAPI.callPublicMethod(ObjectFactory.createFixnum(3), null, new BLOCK._cls2(rubyblock), "times");
    }

    public say_hello_three_times$1()
    {
        super(0, false, 0);
    }
}


//test/BLOCK$2.class
class BLOCK$2 extends RubyBlock
{

    protected RubyValue run(RubyValue rubyvalue, ArrayValue arrayvalue) throws RubyException
    {
        ArrayValue arrayvalue1 = new ArrayValue(1, true);
        arrayvalue1.add(ObjectFactory.createString("hello"));
        return RubyAPI.callMethod(rubyvalue, arrayvalue1, null, "puts");
    }

    public BLOCK$2(RubyBlock rubyblock)
    {
        super(0, false, 0, rubyblock);
    }
}

```

Class "main" represents the program: run() contains the code for the top level in the script. MyClass$0() is the "class builder" method, which represents the execution of code in the class body.

Class "say\_hello\_three\_times$1" represent the implementation of  the "say\_hello\_three\_times" method (think of the command pattern). In its body, we can see "time" method is called on Fixnum "3"(receiver). No parameter(null), but a block is passed in.

Class BLOCK$2 represents the blocks passed to "3.times", and its body is the implementation of "puts 'hello'".


Unknown end tag for &lt;/p&gt;



# Code Organization #

**com.xruby.compiler.parser** provides the compiler front end (parser and tree parser). Parser translates ruby scripts into AST (Abstract Syntax Tree), and then, tree parser translates AST into an internal structure.

The front end uses Antlr as the parser generator. It's a good practice to separate front end into two parts: parser and tree parser: parser parses scripts and tree parser generates internal structure.

**com.xruby.compiler.codedom** defines internal structure which describes structure of Ruby scripts. As the interface between front and back end, internal structure is very important in Xruby.

**com.xruby.compiler.codegen** implements compiler's back end (code generation). Back end translates internal structure generated by font end into Java bytecode. Code generation is implemented by ASM which simplifies bytecode manipulation.

**com.xruby.runtime** implements XRuby runtime. It maintains a type system which is required to run ruby scripts. <b>com.xruby.runtime.lang</b> describes the runtime structure of Ruby types. Some standard libraries are implemented in <b>com.xruby.runtime.builtin</b>.

# Builtin Libraries #
The easiest way to jump start XRuby hacking is to study the source code in the 'com.xruby.runtime.builtin' packages.

The following code snippet illustrates how Fixnm::+ is implemented:

```

class Fixnum_operator_plus extends RubyOneArgMethod {
	protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
		RubyFixnum value1 = (RubyFixnum)receiver;
		RubyFixnum value2 = (RubyFixnum)arg;
		return ObjectFactory.createFixnum(value1.intValue() + value2.intValue());
	}
}

...
RubyClass c = RubyAPI.defineClass("Fixnum", RubyRuntime.IntegerClass);
c.defineMethod("+", new Fixnum_operator_plus());
...
```


# XRuby's Parser #
XRuby's parser used [Antlr](http://www.antlr.org) as the parser generator. This is so far the only alternative ruby grammar other than the one in the c ruby.

For lots of programming languages, lexing and parsing are two seperated steps: first lexer groups input characters into tokens, then parser groups tokens into syntactical units. But in Ruby (and Perl), lexer and parser are more tightly coupled: sometimes the lexer need context information from the passer ( e.g.  expression substitutions in a double quote string)!


# Trouble Shooting #
As XRuby developers, our changes may break the compiler and make bad bytecodes get generated. When this happens, there are three tools we can rely on: javap, ASM, and your favorite Java decompiler (I recommend [jad](http://www.kpdus.com/jad.html)).

If the generated  class file is well formed but does not work as expected, we can simply use decompiler to translate bytecodes to very readable Java source code, then spot errors.

If what you get is a verifier error, then most decompilers do not work (jad may simply crash in this situation). We have to go back to javap and read at bytecode level. Most of the time the message given by the JVM class verifier is not helpful, but we can use ASM to get to the error location quicker (see ASM FAQ: ["Why do I get the xxx verifier error?"](http://asm.objectweb.org/doc/faq.html#Q4)).


