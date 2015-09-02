# Introduction #

We always welcome contributors.

# Guidelines #

We appreciate **clean code with tests**. When you fix a bug or add a new feature, we encourage you to write a test (test driven is better). Before commiting a change, it is best to run the all tests to ensure nothing is broken. There are two sets of test, one is written or embedded in Java, and you can invoke them by doing:

>**build test**

The other set of tests are written in Ruby, and you run them by doing:

>**java -jar xruby-0.3.3.jar sample\test.rb**

While it is impossible to implement something perfectly at the first attempt, we encourage refactoring. If the change may affect the work of other contributors, an email to xruby-devel malilist would be great.

Other than the above general guidelines, feel free to make changes. The most important thing is members should be able to learn and have fun.

# License #
Contributors own the copyright of their contributions. If you made significant contributions in a source file, please add your name to the Copyright block.

By contributing your work to XRuby project, you agreed to release the source code under GPL v2 license.

# GetStarted #

Please read [XRubyHackingGuide](XRubyHackingGuide.md) first, it gives you an overview of the implementation.

The easiest way to jump start the process is to implement a few builtin libraries. And almost all of our members started this way. By implementing builtin libraries, it helps you understand how the runtime/compiler works. And it does not require too much knowledge of compiler to get started.

Our current focus is to get major Ruby libraries work under XRuby. Some known issues can be found at http://code.google.com/p/xruby/issues/list and http://code.google.com/p/xruby/wiki/TodoList

**NOTE**: We perfer to write builtin in pure ruby (builtin.rb) unless it is impossible or **too** bad for performance.

# Eclipse #
If you want to develope XRuby with eclipse, please do the following:

  1. Run 'build' in the command line, this will generate parser with ANTLR and compile builtin written in ruby
  1. Bring up eclipse, open 'File->New->Project...' from the menu, select 'Java Project from Existing Ant Buildfile', click 'Next', click 'Browse...', find xruby's build.xml file, then click 'Finish'
  1. Right click the project, choose 'Properties'
  1. Go to 'Java Build Path' option, In 'libraries' tab, click 'Add JARs...', add 'builtin.jar', 'lib/junit/junit-3.8.1.jar'. Click 'Add external JARs', add JDK's 'tools.jar'
  1. Go to 'Java Compiler' option, make sure the 'Compiler Compliance Level' is 5.0.
  1. Click 'Finish', that is it!

# Note #

Please just choose an area that you are interested in most. Enjoy!