/*
 * Copyright 2006-2007 Yu Su
 * Distributed under the GNU General Public License 2.0
 */
package com.xruby.debug;

/**
 * XRubyDebugException
 */
public class XRubyDebugException extends Exception {

    public XRubyDebugException() {
    }

    public XRubyDebugException(String message) {
        super(message);
    }

    public XRubyDebugException(String message, Throwable cause) {
        super(message, cause);
    }
}
