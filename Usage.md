# Play with xruby #

Generally, there are two ways to use xruby. If you are a java geek, you must be familiar with that, which is,

  * java -jar xruby-0.3.3.jar
  * java -cp xruby-0.3.3.jar com.xruby.Main


# Details #

## Command line options ##
The supported command line options:
> | **Option** | **Meaning** | **Example** |
|:-----------|:------------|:------------|
> |   c        | compile only | java -jar xruby-0.3.3.jar -c test.rb |
> |   e        | eval one line | java -jar xruby-0.3.3.jar -e "puts 'xruby'" |
> |   s        | enable some switch parsing for switches after script name |             |
> |   x        | strip off text before #!ruby line |             |
> |   g        | enable debug |             |
> |   v        | print version number, then turn on verbose mode |             |
> |   h        | display help |             |

## Basic Usage ##
If you just want to use xruby with your native ruby code, run as follows
```
>java -jar xruby-0.3.3.jar -c test.rb
>java -jar test.jar

Or simpler

> java -jar xruby-0.3.3.jar test.rb
```

## Advanced Usage ##
1. If xruby.jar and test.jar are not in the same folder, then should be:
```
>java -cp xruby-0.3.3.jar:test.jar xruby.test.main
```

2. Some ruby scripts require large amount of memory, if you see OutOfMemoryError, try to increase the maximum heap size with -Xmx parameter:
```
>java -Xmx512m -jar xruby-0.3.3.jar test.rb
```

3. Want more advanced features, such as java integration more than the java standard library?  For example, you need jvyaml.jar.
```
>java -cp xruby-0.3.3.jar:jvyaml.jar com.xruby.Main test.rb
  
Or

>java -jar xruby-0.3.3.jar test.rb
>java -cp xruby-0.3.3.jar:test.jar:jvyaml.jar xruby.test.main
```