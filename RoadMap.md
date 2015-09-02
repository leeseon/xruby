# Introduction #

Here is a list of things we are going to do

# XRuby 0.4.0 #
  * Support yaml
  * Support rubygems

# XRuby 0.5.0 #
  * Pass all c ruby unit tests (Milestone)
  * ~~~Change compiler's command line interface to compile multiple ruby scripts into one jar~~~ (DONE)
  * ~~~Improve performance (optimize block with one argument as we did for method)~~~ (DONE)
  * Implement more builtin libraries
  * Improve the startup speed (now there is noticeable delay at startup)
  * Should use CommonRubyID more aggressively for preformance
  * Refactoring is always welcome

# XRuby 0.6.0 #
  * Support RSpec
  * Upgrade Antlr to 3.0
  * Decouple runtime and compiler

# XRuby 1.0 and beyond #
  * Make Ruby on Rails work
  * Make the compiled code debuggable
  * Support JSR 223: https://scripting.dev.java.net/
  * Support YARV instruction
  * Complete Java support
  * Make C extension work (?)

# Wild Ideas? #
  * Compile code that uses JRuby's runtime and lib
  * Bring better concurrency to ruby (Borrow ideas from Erlang)
  * Functional programming in ruby.(Lazy,Single assignment,Pattern matching, etc..)
  * Prototype based programming in ruby.(Borrow ideas from Io,JS)
  * Declarative ruby
  * Aggressive type inference (Borrow ideas from Strongtalk)
  * Rewrite compiler in pure ruby
  * Run under JDK 1.4 or lower.