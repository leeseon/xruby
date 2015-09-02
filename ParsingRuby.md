# Introduction #

A note for people who are working on ANRLTv3 Ruby parser.


# Details #

Ruby, like most real world programming languages, its grammar can not be fully expressed by BNF.

Some challenges include:

  * ID/function ambiguity

  * expression substitution inside string

  * heredoc

# ANTLR v3 vs v2 #

ANTLR v2 produces a limited LL(k) lexer/parser (with 'linear approximate lookahead'). Linear approximate lookahead is a reasonable optimization technique, but it gives warnings for legitimate LL(k) grammar. To avoid this annoying behavior, xruby's ANTLR v2 parser uses k=1, and adds prediction manually if we need k > 1.

ANTLR v3 is more powerful as it produces full LL(`*`) lexer/parser, not only linear approximate lookahead is gone, LL(`*`) is better than LL(k) as well. So we should be able to clean up some hack used in our v2 lexer/parser.

# Advise #

Read the source code that ANTLR generates and use a debugger to step through it. The best part of ANTLR is it produces human readable code -- take advantage of it!

# Coupling between Lexer and Parser #

Of course it is very desirable to have independent lexer and parser, which is great for testing purpose. But for ruby, this is not always possible: see examples in http://seclib.blogspot.com/2006/04/rubyfront-020-released.html

I tried my best to make lexer and parser independent by introducing lexer state in lexer.

# References #

Some old notes on the blog:
http://del.icio.us/yawl/blog/ANTLR

# Questions #
  * Does JRuby still uses Jay(Yacc port to Java) as their parsers now?

> Yes, Their grammar has not changed much since Stephen Matthias Aust ported the Ruby grammar from C ruby to Jay.

  * Does c ruby's parser have a 'trace' function?

> Yes, there is a secret "-y" option in c ruby: 'ruby -y test.rb'. You have to remove "RUBYOPT=rubygems" environment variable first to get readable output.

  * Is there an 'official' grammar for ruby?

> No. The only rerference is the parse.y file.  There is a simpilefied 'Pseudo BNF Syntax of Ruby' at http://www.ruby-doc.org/docs/ruby-doc-bundle/Manual/man-1.4/yacc.html, but it can  only help you to get started.

# Findings #
  * When def a ID both as local variable and method,
ruby takes advantage of variable:
```
test =1
x=1
def x

end

puts x
```
--outputs 1

It explains the behavior when ruby meets <<
when left of << is var, it treats << as shift operator,
otherwise, it's a heredoc.