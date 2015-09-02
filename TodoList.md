# Implementing #
  * yaml support

# Things to be fixed to support Ruby on Rails #

  * yaml: need to implement it with JvYAML
  * active\_support: some runtime errors when doing:
```
$:.unshift("lib/rails/activesupport/lib")
require 'active_support'
```
  * erb: has issues with Binding
  * activerecord: need jdbc bridge
  * ...

# Library Method To be Implemented #

If you find a method not implemented,feel free to report it here. Of course,we welcome that you implement them and share with us.

_Rubinius has lots builtin methods written in pure ruby, we could use some of their implementations. To keep their copyright, better put all these migrated code in a single file and then load it at runtime._

# Passed Tests #
```
ruby\test_alias.rb
ruby\test_assignment.rb
ruby\test_array.rb
ruby\test_bignum.rb
ruby\test_case.rb
ruby\test_call.rb
ruby\test_clone.rb
ruby\test_condition.rb
ruby\test_const.rb
ruby\test_dir.rb
ruby\test_defined.rb
ruby\test_env.rb
ruby\test_exception.rb
ruby\test_float.rb
ruby\test_gc.rb
ruby\test_hash.rb
ruby\test_ifunless.rb
ruby\test_io.rb
ruby\test_pack.rb
ruby\test_process.rb
ruby\test_math.rb
ruby\test_method.rb
ruby\test_range.rb
ruby\test_readpartial.rb	
ruby\test_signal.rb
ruby\test_struct.rb
ruby\test_string.rb
ruby\test_stringchar.rb
ruby\test_super.rb
ruby\test_symbol.rb
ruby\test_system.rb
ruby\test_time.rb
ruby\test_trace.rb
ruby\test_variable.rb
ruby\test_whileuntil.rb

testunit\test_error.rb
testunit\test_failure.rb
testunit\test_testresult.rb
testunit\test_testsuite.rb
testunit\util\test_backtracefilter.rb
testunit\util\test_observable.rb
testunit\util\test_procwrapper.rb
```

# Failed Tests #
```
ruby\test_beginendblock.rb	#need to popen(), system() methods
ruby\test_eval.rb			#Kernel_instance_eval ClassCastException
ruby\test_file.rb			#need add/fix some IO methods
ruby\test_iterator.rb		
ruby\test_marshal.rb		#need to improve Marshal
ruby\test_objectspace.rb	#need to improve ObjectSpace
ruby\test_path.rb			#only work on unix
ruby\test_pipe.rb			#need to fix eof?()
ruby\test_proc.rb			#http://code.google.com/p/xruby/issues/detail?id=37
ruby\test_rand.rb			#need to fix some Rand related methods
ruby\test_settracefunc.rb	#need to implement set_trace_func()


testunit\test_assertions.rb
testunit\test_testcase.rb
testunit\collector\test_dir.rb
testunit\collector\test_objectspace.rb
```

# Reference #
http://www.ruby-doc.org/docs/ProgrammingRuby/html/builtins.html