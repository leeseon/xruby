注意：本文尚在写作中。最近修改于2006年10月15日

# 作者 #
  * Xue Yong Zhi (zhixueyong AT hotmail DOT com)
  * Ye Zheng (dreamhead.cn AT gmail DOT com)

# 翻译 #
  * dennis\_zane (killme2008 AT gmail.com)

# 介绍 #
本文的目标是帮助用户/开发者理解XRuby的实现。

# 如何编译Ruby? #
怎么将Ruby编译成Java字节码呢？首先，你不必成为一名字节码方面的专家来考虑这个问题，Java的字节码是对原生机器指令的一个较高层次的抽象，非常接近Java源代码。因此，你就可以这样考虑这个问题：如何用Java表示Ruby程序？

两种语言有很多的相同之处：Ruby是一门OO语言，它有类、方法、变量等，Java也是如此。这是否意味着我们可以将Ruby类映射为Java类，Ruby方法作为Java方法呢？除了这些相同之处外，它们之间有足够的不同点让你打消这个主意：首先，Ruby是一种动态类型语言，因此方法可以接受不同类型的参数，而在Java中，参数类型是方法签名（signature）的一部分。其次，在Ruby中，可以动态增删类中的方法；但是目前的JVM并不能很好的支持这样的行为。需要指出的是，上述问题也许会在未来版本的JVM中得到解决，请参考Gilad Bracha在[JSR 292](http://www.jcp.org/en/jsr/detail?id=292)上的工作。

一种方法是我们自己维护一个类型系统，这正是XRuby目前采用的办法（Ruby.net好像也是如此）。从JVM的角度看，Ruby类只是个Object，其中包含其它Object，用以表示方法等。对此，我们后面会进行更多的讨论。

另一种方法是动态地编译(Ruby)源代码。在运行时获得类型信息，将代码编译成高效代码是可能的（一些方法由于duck typing的特性将被编译成好几个版本）。

TODO：将比较这两种方法。

# 例子 #
让我们通过一个例子来更多的理解XRuby：

```
def say_hello_three_times
	3.times {puts 'hello'}
end

say_hello_three_times
```

我们将上面的代码存为test.rb，使用XRuby编译：
java -jar xruby-0.1.4.jar -c test.rb

然后，我们得到一个test.jar文件，执行下面的命令运行这个程序：
java -jar test.jar

当然，你会看到下面的输出：

hello
hello
hello

如果你查看test.jar文件，你会看到以下3个class文件：
test/BLOCK$1.class
test/say\_hello\_three\_times$0.class
test/main.class
这些class文件等价于下面这段java程序：

```
//test/main.class
public class main
    implements RubyProgram
{

    public main()
    {
    }

    public static void main(String args[])
    {
    	RubyRuntime.init(args);
        (new main()).run();
        RubyRuntime.fini();
    }

    public RubyValue run()
    {
        RubyRuntime.ObjectClass.defineMethod("say_hello_three_times", new say_hello_three_times._cls0());
        return RubyAPI.callMethod(ObjectFactory.topLevelSelfValue, null, null, "say_hello_three_times");
    }
}


//say_hello_three_times$0.class
class say_hello_three_times$0 extends RubyMethod
{

    protected RubyValue run(RubyValue rubyvalue, RubyArray arrayvalue, RubyBlock rubyblock)
    {
        return RubyAPI.callPublicMethod(ObjectFactory.createFixnum(3), null, new BLOCK._cls1(), "times");
    }

    public say_hello_three_times$0()
    {
        super(0, false);
    }
}


//test/BLOCK$1.class
class BLOCK$1 extends RubyBlock
{

    protected RubyValue run(RubyValue rubyvalue, RubyArray arrayvalue)
    {
        RubyArray arrayvalue1 = new RubyArray(1);
        arrayvalue1.add(ObjectFactory.createString("hello"));
        return RubyAPI.callMethod(rubyvalue, arrayvalue1, null, "puts");
    }

    public BLOCK$1()
    {
        super(0, false);
    }
}
```


main类表示程序：首先在"Object"类中定义了一个私有的方法“say\_hello\_three\_times”，然后调用这个方法，没有参数，没有block，以顶层的“self”作为接收者。

“say\_hello\_three\_times$0”类表示say\_hello\_three\_times方法的实现（参考command模式）。在代码中，我们可以看到Fixnum “3”(接收者）调用"timer"这个方法，仍然没有parameter（null），但是传进去一个block。

BLOCK$1类表示传给"3.times"方法中的block，其主体是“puts 'hello'”的实现。

# 代码结构 #

**com.xruby.compiler.parser** 提供了一个compiler前端(parser和tree parser)。 parser将Ruby脚本转换成AST（抽象语法树，Abstract Syntax Tree），然后tree parser将AST转换为内部结构（internal structure）。
编译器前端使用 Antlr 作为语法分析器的生成器。将这个前端分为两部分（parser和tree parser）是一种好的实践；parser 解析脚本，tree parser生成内部结构（internal structure）。

**com.xruby.compiler.codedom** 定义了描述Ruby脚本结构的内部结构（internal structure）。内部结构作为前端和后端的接口，这对于XRuby来说是非常重要的。

**com.xruby.compiler.codegen** 实现了编译器的后端（代码生成）。后端将前端生成的内部结构转换为Java字节码。代码生成是通过ASM实现的，它简化了对字节码的操作。

**com.xruby.runtime** 实现了XRuby运行时（runtime），它维护着运行Ruby脚本必需的类型系统。

**com.xruby.runtime.lang** 描述了ruby类型的运行时结构，一些标准库实现在 com.xruby.runtime.builtin中。

# 内建库 #
通往xuby hacking之路最简便的办法就是学习'com.xruby.runtime.builtin'包的源代码。

下面的代码片段表明如何实现Fixnum::+方法：

```
class Fixnum_operator_plus extends RubyMethod {
	public Fixnum_operator_plus() {
		super(1);
	}

	protected RubyValue run(RubyValue receiver, RubyArray args, RubyBlock block) {
		RubyFixnum value1 = (RubyFixnum)receiver.getValue();
		RubyFixnum value2 = (RubyFixnum)args.get(0).getValue();
		return ObjectFactory.createFixnum(value1.intValue() + value2.intValue());
	}
}

...
RubyClass c = RubyAPI.defineClass("Fixnum", RubyRuntime.IntegerClass);
c.defineMethod("+", new Fixnum_operator_plus());
...
```

# XRuby的解析器 #
XRuby的解析器使用 Antlr 作为解析器的生成器。 这是目前除C Ruby之外唯一可选的Ruby语法。

对于大部分编程语言来说，词法分析（lexing）和语法解析（parsing）是两个不同的步骤：首先词法分析器将输入的字符组织成记号（token），然后解析器将记号组织成句法单元。但是在Ruby（和Perl）中，词法分析器和语法解析器是紧紧地耦合在一起的：有时候词法分析器需要从语法分析器中获取上下文信息（比如，双引号字符串中的表达式替换）！

# 疑难解决 #
作为XRuby的开发者，我们的修改都可能导致编译器出错，生成有问题的字节码。发生这种情况时，我们有三种工具可以依赖：javap、ASM和你所喜欢的Java反编译器(这里推荐jad）

如果生成的class文件格式正确但是运行结果不是预期的，我们可以简单地用反编译工具将字节码转换成可读的Java源代码，以便查找错误。

如果你遇到是一个验证错误（verifier error），大部分的反编译器都不能正常工作（jad在这种情况也许会简单崩溃）。我们不得不使用javap来研读字节码。多数情况下，JVM class验证器（verifier）给出的信息没什么用处，但是我们可以通过ASM快速地定位错误。( 见ASM FAQ: ["Why do I get the xxx verifier error?"](http://asm.objectweb.org/doc/faq.html#Q4) ).






