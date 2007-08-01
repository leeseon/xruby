/**
 * Copyright 2005-2007 Xue Yong Zhi, Jie Li, Ye Zheng
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.runtime.builtin;

import com.xruby.runtime.lang.*;
import com.xruby.runtime.lang.annotation.MethodType;
import com.xruby.runtime.lang.util.MethodFactory;
import com.xruby.runtime.value.*;

import java.math.BigDecimal;
import java.math.BigInteger;


//C Ruby does not need this function since it can compare integer directly
//class Fixnum_operator_case_equal extends Fixnum_operator_equal {
//}

class Fixnum_operator_less_or_equal extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        boolean result = false;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            int sign = bigValue2.signum();
            assert sign != 0;
            result = (sign > 0);
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum fixnumValue2 = (RubyFixnum) arg;
            result = (value1.intValue() <= fixnumValue2.intValue());
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            result = (floatValue1 <= floatValue2);
        } else {
            throw new RubyException(RubyRuntime.ArgumentErrorClass, "comparison of Fixnum with " + arg.getRubyClass().getName() + " failed");
        }

        if (result) {
            return ObjectFactory.TRUE_VALUE;
        }

        return ObjectFactory.FALSE_VALUE;
    }
}


class Fixnum_operator_less_than extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        boolean result = false;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            int sign = bigValue2.signum();
            assert sign != 0;
            result = (sign > 0);
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum fixnumValue2 = (RubyFixnum) arg;
            result = (value1.intValue() < fixnumValue2.intValue());
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            result = (floatValue1 < floatValue2);
        } else {
            throw new RubyException(RubyRuntime.ArgumentErrorClass, "comparison of Fixnum with " + arg.getRubyClass().getName() + " failed");
        }

        if (result) {
            return ObjectFactory.TRUE_VALUE;
        }

        return ObjectFactory.FALSE_VALUE;
    }
}

class Fixnum_operator_compare extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        int result = 0;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            int sign = bigValue2.signum();
            assert sign != 0;
            if (sign > 0) {
                result = -1;
            } else if (sign < 0) {
                result = 1;
            }
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum fixnumValue2 = (RubyFixnum) arg;
            result = (value1.intValue() - fixnumValue2.intValue());
            if (result < 0) {
                result = -1;
            } else if (result > 0) {
                result = 1;
            }
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            if (floatValue1 < floatValue2) {
                result = -1;
            } else if (floatValue1 > floatValue2) {
                result = 1;
            }
        } else {
            return ObjectFactory.NIL_VALUE;
        }
        return ObjectFactory.createFixnum(result);
    }
}


class Fixnum_operator_greater_than extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        boolean result = false;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            int sign = bigValue2.signum();
            assert sign != 0;
            result = (sign < 0);
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum fixnumValue2 = (RubyFixnum) arg;
            result = (value1.intValue() > fixnumValue2.intValue());
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            if (Double.isNaN(floatValue2)) {
                result = false;
            } else {
                result = (floatValue1 > floatValue2);
            }
        } else {
            throw new RubyException(RubyRuntime.ArgumentErrorClass, "comparison of Fixnum with " + arg.getRubyClass().getName() + " failed");
        }

        if (result) {
            return ObjectFactory.TRUE_VALUE;
        }

        return ObjectFactory.FALSE_VALUE;
    }
}

class Fixnum_operator_greater_or_equal extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        Object value2 = arg;
        boolean result = false;
        if (value2 instanceof RubyBignum) {
            BigInteger bigValue2 = ((RubyBignum) value2).getInternal();
            int sign = bigValue2.signum();
            assert sign != 0;
            result = (sign < 0);
        } else if (value2 instanceof RubyFixnum) {
            RubyFixnum fixnumValue2 = (RubyFixnum) value2;
            result = (value1.intValue() >= fixnumValue2.intValue());
        } else if (value2 instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) value2).doubleValue();
            result = (floatValue1 >= floatValue2);
        } else {
            throw new RubyException(RubyRuntime.ArgumentErrorClass, "comparison of Fixnum with " + arg.getRubyClass().getName() + " failed");
        }

        if (result) {
            return ObjectFactory.TRUE_VALUE;
        }

        return ObjectFactory.FALSE_VALUE;
    }
}

class Fixnum_operator_minus extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue1 = BigInteger.valueOf(value1.intValue());
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            return RubyBignum.bignorm(bigValue1.subtract(bigValue2));
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum fixnumValue2 = (RubyFixnum) arg;
            long result = (long) value1.intValue() - (long) fixnumValue2.intValue();
            return RubyBignum.bignorm(result);
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            return ObjectFactory.createFloat(floatValue1 - floatValue2);
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, arg.getRubyClass().getName() + " can't be coersed into Fixnum");
    }
}

class Fixnum_operator_div extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue1 = BigInteger.valueOf(value1.intValue());
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            return RubyBignum.bignorm(bigValue1.divide(bigValue2));
        } else if (arg instanceof RubyFixnum) {
            int intValue1 = value1.intValue();
            int intValue2 = ((RubyFixnum) arg).intValue();
            int div = intValue1 / intValue2;
            int mod = intValue1 - div * intValue2;
            if (mod != 0 && div < 0) {
                --div;
            }
            return RubyBignum.bignorm(div);
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            return ObjectFactory.createFloat(floatValue1 / floatValue2);
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, arg.getRubyClass().getName() + " can't be coersed into Fixnum");
    }
}

class Fixnum_operator_mod extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        if (arg instanceof RubyBignum) {
            BigInteger bigValue1 = BigInteger.valueOf(value1.intValue());
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            return RubyBignum.bignorm(bigValue1.mod(bigValue2));
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum intValue2 = (RubyFixnum) arg;
            return ObjectFactory.createFixnum(value1.intValue() % intValue2.intValue());
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1.intValue();
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            return ObjectFactory.createFloat(floatValue1 % floatValue2);
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, arg.getRubyClass().getName() + " can't be coersed into Fixnum");
    }
}

class Fixnum_operator_star_star extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        int value1 = ((RubyFixnum) receiver).intValue();
        if (arg instanceof RubyBignum) {
            BigInteger bigValue2 = ((RubyBignum) arg).getInternal();
            assert(bigValue2.signum() != 0);
            if (value1 == 1) {
                if (bigValue2.signum() > 0) {
                    return ObjectFactory.FIXNUM1;
                } else {
                    // TODO: return Rational
                    throw new RubyException("return Rational value not implemented");
                }
            } else if (value1 == 0) {
                if (bigValue2.signum() > 0) {
                    return ObjectFactory.FIXNUM0;
                } else {
                    // TODO: return Rational
                    throw new RubyException("return Rational value not implemented");
                }
            } else if (value1 == -1) {
                if (bigValue2.signum() > 0) {
                    if (bigValue2.intValue() % 2 == 0) {
                        return ObjectFactory.FIXNUM1;
                    } else {
                        return ObjectFactory.FIXNUM_NEGATIVE_ONE;
                    }
                } else {
                    // TODO: return Rational
                    throw new RubyException("return Rational value not implemented");
                }
            }
        } else if (arg instanceof RubyFixnum) {
            RubyFixnum intValue2 = (RubyFixnum) arg;
            BigInteger bigValue1 = BigInteger.valueOf(value1);
            BigInteger result = bigValue1.pow(intValue2.intValue());
            return RubyBignum.bignorm(result);
        } else if (arg instanceof RubyFloat) {
            double floatValue1 = value1;
            double floatValue2 = ((RubyFloat) arg).doubleValue();
            return ObjectFactory.createFloat(Math.pow(floatValue1, floatValue2));
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, arg.getRubyClass().getName() + " can't coerced into Fixnum");
    }
}

class Fixnum_operator_bor extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        if (arg instanceof RubyBignum) {
            return ((RubyBignum) arg).op_bor(receiver);
        } else if (arg instanceof RubyFixnum) {
            return ObjectFactory.createFixnum(value1.intValue() | ((RubyFixnum) arg).intValue());
        } else if (arg instanceof RubyFloat) {
            RubyFloat floatValue2 = (RubyFloat) arg;
            if (floatValue2.doubleValue() > Integer.MAX_VALUE ||
                    floatValue2.doubleValue() < Integer.MIN_VALUE) {
                int intValue2 = (int) floatValue2.doubleValue();
                return ObjectFactory.createFixnum(value1.intValue() | intValue2);
            } else {
                BigInteger bigValue2 = BigDecimal.valueOf(floatValue2.doubleValue()).toBigInteger();
                return RubyBignum.bignorm(BigInteger.valueOf(value1.intValue()).or(bigValue2));
            }
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, "Can't convert " + arg.getRubyClass().getName() + " to Integer");
    }
}

class Fixnum_operator_band extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        if (arg instanceof RubyBignum) {
            return ((RubyBignum) arg).op_band(receiver);
        } else if (arg instanceof RubyFixnum) {
            return ObjectFactory.createFixnum(value1.intValue() & ((RubyFixnum) arg).intValue());
        } else if (arg instanceof RubyFloat) {
            RubyFloat floatValue2 = (RubyFloat) arg;
            if (floatValue2.doubleValue() > Integer.MAX_VALUE ||
                    floatValue2.doubleValue() < Integer.MIN_VALUE) {
                int intValue2 = (int) floatValue2.doubleValue();
                return ObjectFactory.createFixnum(value1.intValue() & intValue2);
            } else {
                BigInteger bigValue2 = BigDecimal.valueOf(floatValue2.doubleValue()).toBigInteger();
                return RubyBignum.bignorm(BigInteger.valueOf(value1.intValue()).and(bigValue2));
            }
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, "Can't convert " + arg.getRubyClass().getName() + " to Integer");
    }
}

class Fixnum_operator_bxor extends RubyOneArgMethod {
    protected RubyValue run(RubyValue receiver, RubyValue arg, RubyBlock block) {
        RubyFixnum value1 = (RubyFixnum) receiver;
        if (arg instanceof RubyBignum) {
            return ((RubyBignum) arg).op_bxor(receiver);
        } else if (arg instanceof RubyFixnum) {
            return ObjectFactory.createFixnum(value1.intValue() ^ ((RubyFixnum) arg).intValue());
        } else if (arg instanceof RubyFloat) {
            RubyFloat floatValue2 = (RubyFloat) arg;
            if (floatValue2.doubleValue() > Integer.MAX_VALUE ||
                    floatValue2.doubleValue() < Integer.MIN_VALUE) {
                int intValue2 = (int) floatValue2.doubleValue();
                return ObjectFactory.createFixnum(value1.intValue() ^ intValue2);
            } else {
                BigInteger bigValue2 = BigDecimal.valueOf(floatValue2.doubleValue()).toBigInteger();
                return RubyBignum.bignorm(BigInteger.valueOf(value1.intValue()).xor(bigValue2));
            }
        }
        throw new RubyException(RubyRuntime.TypeErrorClass, "Can't convert " + arg.getRubyClass().getName() + " to Integer");
    }
}

public class FixnumClassBuilder {
    public static void initialize() {
        RubyClass c = RubyRuntime.FixnumClass;
        MethodFactory factory = MethodFactory.createMethodFactory(RubyFixnum.class);
        
        c.defineMethod(">>", factory.getMethod("rshift", MethodType.ONE_ARG));//new Fixnum_operator_right_shift());
        c.defineMethod("<<", factory.getMethod("lshift", MethodType.ONE_ARG));//new Fixnum_operator_left_shift());
        c.defineMethod("==", factory.getMethod("opEqual", MethodType.ONE_ARG));//type)new Fixnum_operator_equal());
        c.defineMethod("===", factory.getMethod("opEqual", MethodType.ONE_ARG));
        c.defineMethod(RubyID.plusID, factory.getMethod("opPlus", MethodType.ONE_ARG));
        c.defineMethod("<=", new Fixnum_operator_less_or_equal());
        c.defineMethod(RubyID.subID, factory.getMethod("opMinus", MethodType.ONE_ARG));
        c.defineMethod("/", new Fixnum_operator_div());
        c.defineMethod(RubyID.toSID, factory.getMethod("to_s", MethodType.NO_OR_ONE_ARG));
        c.defineMethod("%", new Fixnum_operator_mod());
        c.defineMethod("|", new Fixnum_operator_bor());
        c.defineMethod("&", new Fixnum_operator_band());
        c.defineMethod("<", new Fixnum_operator_less_than());
        c.defineMethod("div", new Fixnum_operator_div());
        c.defineMethod("<=>", new Fixnum_operator_compare());
        c.defineMethod("^", new Fixnum_operator_bxor());
        c.defineMethod(">", new Fixnum_operator_greater_than());
        c.defineMethod(">=", new Fixnum_operator_greater_or_equal());
        c.defineMethod("*", factory.getMethod("opMul", MethodType.ONE_ARG));
        c.defineMethod("to_f", factory.getMethod("convertToFloat", MethodType.NO_ARG));
        c.defineMethod("**", new Fixnum_operator_star_star());
        c.defineMethod("~", factory.getMethod("opRev", MethodType.NO_ARG));
        c.defineMethod("quo", factory.getMethod("quo", MethodType.ONE_ARG));//new Fixnum_quo());
    }
}
