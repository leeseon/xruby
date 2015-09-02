## Introduction ##
Code Convention for XRuby project, to make our code more readable

This document based on Coding Guidelines of Globus
http://www-unix.globus.org/toolkit/docs/development/coding_guidelines.html

## Details ##
### Base coding conventions ###
Use the Sun Coding Conventions for the Java Programming Language:

http://java.sun.com/docs/codeconv/html/CodeConvTOC.doc.html
### Statements ###
#### Import Statements ####
All imports must be single class and explicit. In other words,
```
import org.xruby.runtime.value.*;
```
is not allowed.
```
import com.xruby.runtime.value.ObjectFactory;
import com.xruby.runtime.value.RubyArray;
```
is preferred.

### Variables ###
No acronyms or abbreviations should be used.



For example,
```
 a = b + mVarLen 
```
should be avoided. Use this instead:
```
totalLength = partLength + newLength
```

#### Instance Variables ####
Use this. as the prefix when referencing instance variables. For example,
```
public MyClass (ServicePropertiesInterface properties) {
     this.properties = properties;
}

public int foo () {
    int localInt = 3;
    return this.instanceInt + localInt;
}
```
Instance variables should never be declared public. If instance variables need to be accessed from outside of the class then use getters/setters.

We leave it to the implementers' discretion to choose between protected and private as appropriate.

#### Constant Variable ####
For constant variable(static final), Underscore Uppercase style is highly recommended.
```
private static final RubyValue OBJECT_SPACE_MODULE = ... ; // Uppercase and underscore
```

### Layout ###
#### Indentation ####

All indentation levels should be **four (4) spaces**.

No editor tabs are allowed unless they are converted to four spaces before saving the file.

  * AnyEdit plugin of Eclipse can expand tab to whitespace before saving a file
  * In Source Insight, open 'Options->Document Options', select 'Expand tabs', and set 'Tab width' = 4
  * In Scite, edit ruby.propertises: tab.indents = true tabsize=4 indent.size=4 use.tabs=0

#### Brackets ####
**Curly brackets {} are put on separate lines. This is never allowed:
```
      // Wrong style
      for (index = 0; index < length; index++)
      {
           <code>
      }
```** As defined in the Java Coding guidelines. This is highly recommended:
```
      for (index = 0; index < length; index++) {
           <code>
      }
```
**One-Liners**

Even single line statements should be inside brackets. E.g.
```
if (isEmpty) {
     return true;
}
```
And, this is not recommended:
```
if (isEmpty) 
    return true;
```

### Exceptions ###
We high recommend you should throw a customized Exception extending RubyException and RubyRuntimeException(We need create this Exception), please don't throw the built-in Java Exceptions.


## Sample Code ##

```

/**
 * Copyright 2006-2007 Yu Su, Ye Zheng
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.runtime.javasupport;

import com.xruby.runtime.lang.RubyBlock;
import com.xruby.runtime.lang.RubyClass;
import com.xruby.runtime.lang.RubyException;
import com.xruby.runtime.lang.RubyMethod;
import com.xruby.runtime.lang.RubyRuntime;
import com.xruby.runtime.lang.RubyValue;
import com.xruby.runtime.lang.RubyVarArgMethod;
import com.xruby.runtime.lang.RubyID;
import com.xruby.runtime.lang.StringMap;
import com.xruby.runtime.value.RubyArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper for Java Class
 *
 * @author Yu Su (beanworms@gmail.com), Ye Zheng(dreamhead.cn@gmail.com)
 */
public class JavaClass extends RubyClass {

    // ----------------------
    //      Const fields
    // ----------------------
    private static final String NEW_METHOD = "new";

    // ---------------------------------------
    //   Cache of methods (and constructors)
    // ---------------------------------------
    private Map<String, List<Method>> methodMap
            = new HashMap<String, List<Method>>();

    private Map<Integer, List<Constructor>> initMap
            = new HashMap<Integer, List<Constructor>>();

    private Map<Method, JavaMethod> javaMethods
            = new HashMap<Method, JavaMethod>();

    private Map<Constructor, JavaMethod> initMethods
            = new HashMap<Constructor, JavaMethod>();
    
    // Actual constructor
    private JavaClass(String name) {
        super(name, RubyRuntime.ObjectClass, RubyRuntime.GlobalScope);
    }

    /**
     * Constructor
     *
     * @param clazz Class instance
     */
    public JavaClass(Class clazz) {
        this(clazz.getName());

        // Initialize public constructors and methods
        initConstructors(clazz);
        initMethods(clazz);
    }

    ... ...

    // Collect public methods of given class
    private void initMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers)) {
                categoryByName(method);
            }
        }
    }

    ... ...
}

```