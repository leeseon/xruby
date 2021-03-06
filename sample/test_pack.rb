require 'common'

test_check "pack"

test_ok([1].pack('c')[0] == 1)
test_ok([2].pack('c')[0] == 2)
test_ok([256].pack('c')[0] == 0)
test_ok([257].pack('c')[0] == 1)

test_ok([1].pack('C')[0] == 1)
test_ok([2].pack('C')[0] == 2)
test_ok([256].pack('C')[0] == 0)
test_ok([257].pack('C')[0] == 1)

test_ok([1].pack('s')[0] == 1 && [1].pack('s')[1] == 0)
test_ok([2].pack('s')[0] == 2 && [2].pack('s')[1] == 0)
test_ok([256].pack('s')[0] == 0 && [257].pack('s')[1] == 1)
test_ok([257].pack('s')[0] == 1 && [257].pack('s')[1] == 1)

test_ok([1].pack('S')[0] == 1 && [1].pack('S')[1] == 0)
test_ok([2].pack('S')[0] == 2 && [2].pack('S')[1] == 0)
test_ok([256].pack('S')[0] == 0 && [257].pack('S')[1] == 1)
test_ok([257].pack('S')[0] == 1 && [257].pack('S')[1] == 1)

test_ok([1].pack('i')[0] == 1)
test_ok([1].pack('i')[1] == 0)
test_ok([1].pack('i')[2] == 0)
test_ok([1].pack('i')[3] == 0)
test_ok([2].pack('i')[0] == 2)
test_ok([2].pack('i')[1] == 0)
test_ok([2].pack('i')[2] == 0)
test_ok([2].pack('i')[3] == 0)
test_ok([256].pack('i')[0] == 0)
test_ok([256].pack('i')[1] == 1)
test_ok([256].pack('i')[2] == 0)
test_ok([256].pack('i')[3] == 0)
test_ok([257].pack('i')[0] == 1)
test_ok([257].pack('i')[1] == 1)
test_ok([257].pack('i')[2] == 0)
test_ok([257].pack('i')[3] == 0)

test_ok([1].pack('I')[0] == 1)
test_ok([1].pack('I')[1] == 0)
test_ok([1].pack('I')[2] == 0)
test_ok([1].pack('I')[3] == 0)
test_ok([2].pack('I')[0] == 2)
test_ok([2].pack('I')[1] == 0)
test_ok([2].pack('I')[2] == 0)
test_ok([2].pack('I')[3] == 0)
test_ok([256].pack('I')[0] == 0)
test_ok([256].pack('I')[1] == 1)
test_ok([256].pack('I')[2] == 0)
test_ok([256].pack('I')[3] == 0)
test_ok([257].pack('I')[0] == 1)
test_ok([257].pack('I')[1] == 1)
test_ok([257].pack('I')[2] == 0)
test_ok([257].pack('I')[3] == 0)


test_ok([1].pack('l')[0] == 1)
test_ok([1].pack('l')[1] == 0)
test_ok([1].pack('l')[2] == 0)
test_ok([1].pack('l')[3] == 0)
test_ok([2].pack('l')[0] == 2)
test_ok([2].pack('l')[1] == 0)
test_ok([2].pack('l')[2] == 0)
test_ok([2].pack('l')[3] == 0)
test_ok([256].pack('l')[0] == 0)
test_ok([256].pack('l')[1] == 1)
test_ok([256].pack('l')[2] == 0)
test_ok([256].pack('l')[3] == 0)
test_ok([257].pack('l')[0] == 1)
test_ok([257].pack('l')[1] == 1)
test_ok([257].pack('l')[2] == 0)
test_ok([257].pack('l')[3] == 0)


test_ok([1].pack('L')[0] == 1)
test_ok([1].pack('L')[1] == 0)
test_ok([1].pack('L')[2] == 0)
test_ok([1].pack('L')[3] == 0)
test_ok([2].pack('L')[0] == 2)
test_ok([2].pack('L')[1] == 0)
test_ok([2].pack('L')[2] == 0)
test_ok([2].pack('L')[3] == 0)
test_ok([256].pack('L')[0] == 0)
test_ok([256].pack('L')[1] == 1)
test_ok([256].pack('L')[2] == 0)
test_ok([256].pack('L')[3] == 0)
test_ok([257].pack('L')[0] == 1)
test_ok([257].pack('L')[1] == 1)
test_ok([257].pack('L')[2] == 0)
test_ok([257].pack('L')[3] == 0)

test_ok([1].pack('q')[0] == 1)
test_ok([1].pack('q')[1] == 0)
test_ok([1].pack('q')[2] == 0)
test_ok([1].pack('q')[3] == 0)
test_ok([1].pack('q')[4] == 0)
test_ok([1].pack('q')[5] == 0)
test_ok([1].pack('q')[6] == 0)
test_ok([1].pack('q')[7] == 0)
test_ok([2].pack('q')[0] == 2)
test_ok([2].pack('q')[1] == 0)
test_ok([2].pack('q')[2] == 0)
test_ok([2].pack('q')[3] == 0)
test_ok([2].pack('q')[4] == 0)
test_ok([2].pack('q')[5] == 0)
test_ok([2].pack('q')[6] == 0)
test_ok([2].pack('q')[7] == 0)
test_ok([256].pack('q')[0] == 0)
test_ok([256].pack('q')[1] == 1)
test_ok([256].pack('q')[2] == 0)
test_ok([256].pack('q')[3] == 0)
test_ok([256].pack('q')[4] == 0)
test_ok([256].pack('q')[5] == 0)
test_ok([256].pack('q')[6] == 0)
test_ok([256].pack('q')[7] == 0)
test_ok([257].pack('q')[0] == 1)
test_ok([257].pack('q')[1] == 1)
test_ok([257].pack('q')[2] == 0)
test_ok([257].pack('q')[3] == 0)
test_ok([257].pack('q')[4] == 0)
test_ok([257].pack('q')[5] == 0)
test_ok([257].pack('q')[6] == 0)
test_ok([257].pack('q')[7] == 0)


test_ok([1].pack('c') == "\001")
test_ok([2].pack('c') == "\002")
test_ok([256].pack('c') == "\000")
test_ok([257].pack('c') == "\001")

test_ok([1].pack('C') == "\001")
test_ok([2].pack('C') == "\002")
test_ok([256].pack('C') == "\000")
test_ok([257].pack('C') == "\001")


test_ok([1].pack('s') == "\001\000")
test_ok([2].pack('s') == "\002\000")
test_ok([256].pack('s') == "\000\001")
test_ok([257].pack('s') == "\001\001")

test_ok([1].pack('S') == "\001\000")
test_ok([2].pack('S') == "\002\000")
test_ok([256].pack('S') == "\000\001")
test_ok([257].pack('S') == "\001\001")

test_ok([1].pack('i') == "\001\000\000\000")
test_ok([2].pack('i') == "\002\000\000\000")
test_ok([256].pack('i') == "\000\001\000\000")
test_ok([257].pack('i') == "\001\001\000\000")

test_ok([1].pack('I') == "\001\000\000\000")
test_ok([2].pack('I') == "\002\000\000\000")
test_ok([256].pack('I') == "\000\001\000\000")
test_ok([257].pack('I') == "\001\001\000\000")

test_ok([1].pack('l') == "\001\000\000\000")
test_ok([2].pack('l') == "\002\000\000\000")
test_ok([256].pack('l') == "\000\001\000\000")
test_ok([257].pack('l') == "\001\001\000\000")


test_ok([1].pack('L') == "\001\000\000\000")
test_ok([2].pack('L') == "\002\000\000\000")
test_ok([256].pack('L') == "\000\001\000\000")
test_ok([257].pack('L') == "\001\001\000\000")

=begin
1,-100,   c2   \001\234
          x5   \000\000\000\000\000
127,      C    \177
128,      C    \200
          x    \000
32767     s    \377\177
987.654321098 / 100.0,  d   >a\221E\312\300\#@
12345     i    90\000\000
123456    l    @\342\001\000
-32767    s_   \001\200
-123456   l_   \300\035\376\377
"abcdef"  a6   abcdef
=end
$format = "c2x5CCxsdils_l_a6";
# Need the expression in here to force ary[5] to be numeric.  This avoids
# test2 failing because ary2 goes str->numeric->str and ary does not.
ary = [1,-100,127,128,32767,987.654321098 / 100.0,12345,123456,-32767,-123456,"abcdef"]
$x = ary.pack($format)
ary2 = $x.unpack($format)

test_ok(ary.length == ary2.length)
test_ok(ary.join(':') == ary2.join(':'))
test_ok($x =~ /def/)
$x = [-1073741825]
test_ok($x.pack("q").unpack("q") == $x)


