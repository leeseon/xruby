Currently xruby provides some ways to make use of the many Java libraries available.You can import Java packages,create a Java object,implement a Java interface,etc.We will list all available features as follows.
  * Importing Java packages and classes
    * Use require\_java keywords,of course also can use import with which most Java programmers are familiar.
    * When not ensure that whether class name collision will occur or not,access the Java class prefixing with a 'J' char.
```
#import the specific class
require_java 'javax.swing.JFrame'

#import the Java packages
require_java 'javax.swing.*'
```
  * Creating a Java object
```
require_java 'javax.swing.JFrame'
f = JFrame.new('hello')
```
  * Calling instance methods
    * Not only the methods defined in the own class are available,the methods inherited from the parent also are available.
```
require_java 'javax.swing.JFrame'
f = JFrame.new('hello')
f.pack
f.setVisible(true)
#invoke the method defined in Object class
f.toString
```
  * Calling static methods
```
import 'java.lang.System'
System.exit(0)

require_java 'java.lang.Math'
JMath.cos(0)
```
  * Accessing static fields
```
require_java 'java.lang.System'
out = System::out
out.println('ok!')

require_java 'java.lang.Math'
puts JMath.PI
```
  * Java overloaded resolution
    * Select the first applicable method which is enumerated by the JVM.This means that as long as the ruby arguments can be converted to corresponding Java type listed in the method's signature,this method will be chosen.
```
require_java 'java.lang.System'
out = System::out
out.println('ok!')
out.println(123)
```
  * JavaBean support
```
import 'javax.swing.JFrame'
f = JFrame.new("hello");
# calls setVisible
f.visible= true
# calls getTitle
puts f.title
```
  * Creating Java Arrays and accessing Java Arrays
```
import 'com.xruby.runtime.javasupport.AssistantTester'             
import 'java.lang.reflect.Array'
import 'java.lang.String'

s = JArray.newInstance(JString, 2)
s[0]='Hello '
s[1]='World!'
AssistantTester.echo(s)

#Suppose that the code of AssistantTester is as follows:
class AssistantTester{
    public static void echo(String[] str){
        for(String s : str){
            System.out.print(s);
        }
        System.out.println();
    }
}
```
  * Handling Java exceptions
```
import 'java.io.FileInputStream'
import 'java.io.FileNotFoundException'
import 'java.io.IOException'

begin
    f = FileInputStream.new('myfile')
rescue FileNotFoundException=>fnfe
    puts fnfe.getMessage
rescue IOException=>ioe
    puts ioe
end
```
  * instanceof check
```
require_java 'javax.swing.JFrame'
f = JFrame.new("hello");
# print true
puts f.kind_of?(JFrame)
# print true
puts f.kind_of?(Frame)
```
  * Implementing a Java interface
```
import 'java.lang.Runnable'
import 'java.lang.Thread'

class MyRunnable < Runnable
    def run
        puts 'ok'
    end
end

r = MyRunnable.new
#prefix a 'J' char in order to escape from name collision 
thread = JThread.new(r)
thread.start()
```

### Reference ###
  1. [Java Integration: JavaScript, Groovy and JRuby](http://blogs.sun.com/sundararajan/entry/java_integration_javascript_groovy_and)
  1. [Scripting Java](http://www.mozilla.org/rhino/ScriptingJava.html)