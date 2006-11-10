#Copyright (c) 2005-2006 Xue Yong Zhi. All rights reserved.
#
#Some built-in functions can be implemented in pure ruby, so they are implemented here.
#

$: = ["..", "./foobarlib", "."]

module Kernel
	def to_a
		[self]
	end

	alias require__ require

	def require(path)
# TODO: xruby BUG: return from block
=begin
		$:.length.times do |index|
			file_name = $:[index] + "/" + path + ".rb"
			next unless ::File.file?(file_name)
			begin
				content = ::IO.read(file_name)
				eval(content)
				return true
			rescue
				next
			end
		end
=end
		counter = 0
		while counter < $:.length
			file_name = $:[counter] + "/" + path + ".rb"
			counter += 1
			next unless ::File.file?(file_name)
			begin
				content = ::IO.read(file_name)
				eval(content)
				return true
			rescue
				return false
			end
		end
		require__(path)
	end
end

class Array
	def to_a
		self
	end
	
	alias join to_s
	alias to_ary to_a
	alias size length
end

class Symbol
	alias to_s id2name
end

def self.to_s
	return "main"
end

class String
	def to_s
		return self
	end
	
	alias to_str to_s
	alias inspect to_s
end

class Integer < Numeric

	def to_i
		return self
	end
	
	alias to_int to_i
	
	def times
		a = 0
		while a < self
			yield a
			a += 1;
		end
	end

	#Returns the Integer equal to int + 1
	def next
		self + 1
	end

	#Synonym for Integer#next
	def succ
		self + 1
	end
	
	#Always returns true
	def integer?
		true
	end

	def upto(to)
		a = self
		while a <= to
			yield a
			a += 1
		end
	end

	def downto(to)
		a = self
		while a >= to
			yield a
			a -= 1
		end
	end
end

class Fixnum < Integer
	def to_i
		self
	end
	
	alias inspect to_s
end

class Bignum < Integer
	def >=(value)
		compare = (self <=> value)
		return compare != -1
	end

	def ==(value)
		compare = (self <=> value)
		return compare == 0
	end

	def <=(value)
		compare = (self <=> value)
		return compare != 1
	end

	def >(value)
		compare = (self <=> value)
		return compare == 1
	end

	def <(value)
		compare = (self <=> value)
		return compare == -1
	end
end

class NilClass
	
	#Returns false
	def &(anObject)
		false
	end

	#Returns false if anObject is nil or false, true otherwise
	def ^(anObject)
		anObject ? true : false
	end
	
	#Returns false if anObject is nil or false, true otherwise
	def |(anObject)
		anObject ? true : false
	end
	
	#Always returns true
	def nil?
		true
	end
	
	#Always returns an empty array
	def to_a
		[]
	end
	
	#Always returns zero
	def to_i
		0
	end
	
	def to_f
		0.0
	end
	
	#Always returns the empty string
	def to_s
		""
	end

	def inspect
		"nil"
	end
end

class TrueClass
	#Returns false if anObject is nil or false, true otherwise
	def &(anObject)
		anObject ? true : false
	end
	
	#Returns true if anObject is nil or false, false otherwise
	def ^(anObject)
		anObject ? false : true
	end
	
	#Returns true
	def |(anObject)
		true
	end

	def to_s
		return "true"
	end
end

class FalseClass
	
	#Returns false
	def &(anObject)
		false
	end
	
	#If anObject is nil or false, returns false; otherwise, returns true
	def ^(anObject)
		anObject ? true : false
	end
	
	#Returns false if anObject is nil or false; true otherwise
	def |(anObject)
		anObject ? true : false
	end
	
	def to_s
		return "false"
	end
end
