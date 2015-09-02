## How to use debug ##
Now, as the debug branch has been merged into our trunk. Here's is a simple instruction how to use this tool

1.  Compile
  * Add the new option **g** to enable debug, e.g.
```
  E:\Projects\xruby_trunk>java -jar xruby-0.1.4.jar -c -g test_debug2.rb
  Added test_debug2/BLOCK_test_method$1.class
  Added test_debug2/BLOCK_test_method$2.class
  Added test_debug2/test_method$0.class
  Added test_debug2/BLOCK$3.class
  Added test_debug2/RubyIDContainer.class
  Added test_debug2/main.class
  Generated test_debug2.jar
```
2. Start debug
  * In windows environment，you could use debug.bat. There are two options: classpath and sourcepath for this command tool

> . classpath, class search path of directories separated by ';'

> . sourcepath, search path of source code separated by ';'

  * Sample, the command below show you how to use this tool:

```
  E:\Projects\xruby_trunk>debug.bat --classpath E:\Projects\xruby_trunk\xruby-0.1.3.jar;E:\Projects\xruby_trunk\test_debug2.jar   --sourcepath . test_debug2.main
```

3. Instructions in debugger

> . run, start running the program

> . stop, set a new break point
> `stop at|in <script_name>:<line> `
> e.g. stop in test\_debug2.rb:17

> . clear, list all active breakpoints

> . clear [index](index.md), remove a breakpoint, index is the that bp's position in breakpoints' list

> . list [range](range.md), list the source code

> . cont , continue execution from the breakpoint

> . next, step over, :( step into is not supported by now


## Introduction ##
TODO-LIST

A new branch named debugger has been created, which is located under the directory branch.
All the code about debug will be submitted to that branch.

### Details ###
1. Add line number and local variable info to the bytecode
  * How to deal with the loop structure
  * Modify corresponding codegen classes

2. How to connect the bytecode (the classes, blocks, variables) with souce code.
  * Maybe we need slightly modify the way we compile code, e.g. block naming mechanism

3. Implement the debug command: n(next), b(breakpoit), r(run), c(continue), l(list),   w(where) etc.
  * The commands of rdebug is a good example
  * Rough mannual, definition

4. (Optional) A gui tools or a plug-in for a IDE

### Document TODO list ###

#### Overview: ####
Components in the debugger: EventHandler, Instructions and EventRequestHandler, and SourceMgr. What's their roles.

#### Chapters/Sections ####

1. Sth about JVM's ThreadFrame, stack of the threads, this is important, because we use that to locate the current position.

> e.g. ThreadReference.frames()

2. Requests in JDI arch. especially ClassParepareRequest, ThreadStart/DeathRequest, JVMStartRequest, and BreakpointRequest(Locatable). How to we handle them: set, remove, enable, what's the roles of them?

3. Class diagrams showing the relationship among these classes.

4. State chart of Debugger

5. How to extend this debugger. Say, the plugin for some a IDE, or a standalone GUI debugger
6. Sequence of some important instructions, e.g. run, stop, list ...

7. Simple Manual

8. How do we record method and block, SMAP




