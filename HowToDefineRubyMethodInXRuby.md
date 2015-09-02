# Authors #
  * Ye Zheng(dreamhead.cn@gmail.com)

# Introduction #
The goal of this article is to help users/deverlopers understand how to define Ruby method in XRuby.


# Define a Ruby Method #
You've got an idea which implements **`clear`** method for **`Array`**. Here's your code:
```
public class RubyArray extends RubyBasic {
    ...

    public RubyArray clear() {
        array_.clear();
        return this;
    }

    ...
}
```

Now, you have the Java-level code. Of course, you want the code to work in Ruby level. In XRuby, you must provide a method wrapper for the Ruby method like this:

```
public class ArrayClear extends RubyNoArgMethod {
    protected RubyValue run(RubyValue receiver, RubyBlock block) {
        RubyArray array = (RubyArray)receiver;
        return array.clear();
    }
}
```

The rest is very simple. Add this wrapper into XRuby runtime.
```
RubyClass c = RubyRuntime.ArrayClass;
c.defineMethod("clear", new RubyClear());
```

# Another Way of Definition #
But you finally find that it's very boring to write a wrapper for every Ruby method which has a similar structure. On the other hand, it's a little confusing where the code should be. Actually, we could implement the method both in Java-level method and wrapper.

Yes, you're right. Actually, we have another way to Ruby method which could reduce this confusion. You can define Ruby method like this:
```
MethodFactory factory = MethodFactory.createMethodFactory(RubyArray.class);
c.defineMethod("clear", factory.getMethod("clear", MethodFactory.NO_ARG));
```

The code means you create method wrapper for **`clear`** method of **`RubyArray`**. With this way, method wrapper could be generated dynamically, so a lot of trivial and confusing code could be saved.

# Annotation #
You're still thinking. **`MethodFactory`** makes the job easier, but you still have to create wrapper with **`MethodFactory`** by which you **`getMethod`** with method name. That's a problem when you want to change your method name. Method name is just a String, so it is difficult for tools such as IDE to find the relationship between method name and it. Furthermore, it would be better if **`defineMethod`** could be saved.

Now, it's time for Java 5. XRuby provides Annotation for you to make this job further simpler as well.
```
@RubyLevelClass(name="Array")
public class RubyArray extends RubyBasic {
    ...

    @RubyLevelMethod(name="clear")
    public RubyArray clear() {
        array_.clear();
        return this;
    }

    ...
}
```

With **`@RubyLevelClass`**, you tell XRuby this class is mapped to Ruby level class. A Ruby level method is defined with **`@RubyLevelMethod`**. Now, you don't need to care the string name and **`defineMethod`** since XRuby can do that well for you.











