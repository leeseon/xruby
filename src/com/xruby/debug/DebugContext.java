/*
 * Copyright 2006-2007 Yu Su 
 * Distributed under the GNU General Public License 2.0
 */

package com.xruby.debug;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequestManager;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DebugContext for debug.
 *
 * @author Yu Su(beanworms@gmail.com)
 */
class DebugContext {

    private static JVMConnection jvmConnection;
    private static List<File> sourcePath;
    private static List<URL> classPath;
    private static JVMEventNotifier notifier;
    private static EventHandler handler;
    private static List<Instruction> deferredInsns;
    private static SmapMgr smapMgr;
    private static FrontEnd frontEnd;

    // Initiate
    static {
        sourcePath = new ArrayList<File>();
        notifier = new DefaultJVMEventNotifier();
        deferredInsns = new ArrayList<Instruction>();
    }

    /**
     * Initiate context
     *
     * @param traceFlag trace mode
     * @param arguments arguments
     */
    public static void initContext(int traceFlag, Map<String, String> arguments) {
        jvmConnection = new JVMConnection(traceFlag, arguments);
    }

    // -------------------------
    //   Readers and Writters
    // -------------------------
    public static void setNotifier(JVMEventNotifier notifier) {
        DebugContext.notifier = notifier;
    }

    public static JVMEventNotifier getNotifier() {
        return notifier;
    }


    public static EventHandler getHandler() {
        return handler;
    }

    public static void setHandler(EventHandler handler) {
        DebugContext.handler = handler;
    }

    public static JVMConnection getJvmConnection() {
        return jvmConnection;
    }

    public static VirtualMachine getJVM() {
        return jvmConnection.getJvm();
    }

    public static EventRequestManager getEventRequestManager() {
        return getJVM().eventRequestManager();
    }

    public static List<URL> getClassPath() {
        return classPath;
    }

    public static void setClassPath(List<URL> classPath) {
        DebugContext.classPath = classPath;

        // Initiatate smapMgr
        URLClassLoader loader = new URLClassLoader(classPath.toArray(new URL[]{}));
        smapMgr = new SmapMgr(loader);
    }

    public static List<File> getSourcePath() {
        return sourcePath;
    }

    public static SmapMgr getSmapMgr() {
        return smapMgr;
    }

    public static List<Instruction> getDeferredInsns() {
        return deferredInsns;
    }

    // If jvm is still a null value
    public static boolean isStarted() {
        return (getJVM() != null);
    }

    public static void registerFrontEnd(FrontEnd frontEnd) {
        DebugContext.frontEnd = frontEnd;
    }

    public static void shutdown() {
        if(DebugContext.frontEnd != null) {
            frontEnd.onVmShutdown();
        } 
    }

    // --------------------
    //    Helper Methods
    // --------------------

    public static void addSourcePath(String path) {
        File file = new File(path);

        if (file.isDirectory()) {
            sourcePath.add(file);
        } else if (path.endsWith(".zip")) {
            sourcePath.add(file);
        } else {
            // TODO: Output the warning here
        }
    }

    public static void addSourcePath(List<String> list) {
        for (String path : list) {
            addSourcePath(path);
        }
    }
    
    /**
     * Return the description of this context.
     *
     * @return description
     */
    public static String dumpContext() {
        return null; // TODO: implement it
    }

    public static void pushCommand(Instruction insn) {
        deferredInsns.add(insn);
    }
}
