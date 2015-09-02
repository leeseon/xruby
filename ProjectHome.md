XRuby is the first Ruby to Java compiler which compiles Ruby source code (.rb) to Java bytecode (.class). The parser is implemented in ANTLR, and we use ASM to emit java bytecode.

The current stable version is 0.3.3, released on Mar. 24, 2008. It is able to pass all tests in samples/test.rb and most unit tests in test/ruby/ - a decent test suite that comes with ruby installation. In most benchmark tests, XRuby runs faster than Ruby 1.8.5.

Right now we are working hard to make more ruby libraries work under XRuby (Test::Unit is supported now, while Ruby On Rails is not). We always welcome contributors.




[![](http://www.jetbrains.com/img/linklogos/idea88x31_2.gif)](http://www.jetbrains.com/) [![](http://asm.objectweb.org/images/poweredbyasm.gif)](http://asm.objectweb.org) [![](http://www.antlr.org/images/antlr-link.gif)](http://www.antlr.org)










