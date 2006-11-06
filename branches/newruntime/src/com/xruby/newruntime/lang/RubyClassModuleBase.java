package com.xruby.newruntime.lang;

import java.util.HashMap;
import java.util.Map;

public abstract class RubyClassModuleBase extends RubyIvBase {
	private static RubyID ID_ALLOCATOR = RubyID.ID_ALLOCATOR;
	
	protected RubyClass superclass;	
	protected Map<RubyID, RubyMethodWrapper> methodTable;
	
	public RubyClassModuleBase() {
		this.methodTable = new HashMap<RubyID, RubyMethodWrapper>();
	}
	
	// super
	public void setSuper(RubyClass superclass) {
		this.superclass = superclass;		
	}
	
	public RubyClass getSuper() {
		return this.superclass;
	}
	
	// method
	public void defineAllocMethod(RubyMethod method) {
		RubyClass metaClass = RubyUtil.classof(this);
		metaClass.addMethod(ID_ALLOCATOR, method, 0, RubyMethodAttr.PRIVATE);
	}
	
	public void defineMethod(String name, RubyMethod method, int argc) {
		RubyID id = StringMap.intern(name);
		this.addMethod(id, method, argc, RubyMethodAttr.PUBLIC);
	}
	
	// package access
	public void undefAllocMethod() {
		RubyClass metaClass = RubyUtil.classof(this);
		metaClass.addMethod(ID_ALLOCATOR, null, 0, RubyMethodAttr.PRIVATE);
	}
	
	public void undefMethod(String name) {
		// FIXME: check existence
		RubyID id = StringMap.intern(name);
		this.addMethod(id, null, 0, RubyMethodAttr.UNDEF);
	}
	
	public void aliasMethod(String newName, String oldName) {
		RubyID newId = StringMap.intern(newName);
		RubyID oldId = StringMap.intern(oldName);
		this.aliasMethod(newId, oldId);
	}
	
	void definePrivateMethod(String name, RubyMethod method, int argc) {
		RubyID id = StringMap.intern(name);
		this.addMethod(id, method, argc, RubyMethodAttr.PRIVATE);
	}
	
	protected void addMethod(RubyID id, RubyMethod method, int argc, RubyMethodAttr attr) {	
		RubyMethodWrapper wrapper = new RubyMethodWrapper(method, argc, attr);
		this.methodTable.put(id, wrapper);
	}
	
	private void aliasMethod(RubyID newId, RubyID oldId) {
		RubyMethodWrapper method = this.methodTable.get(oldId);
		if (method == null) {
			// FIXME: throw exception
		}
		
		this.methodTable.put(newId, method);
	}
	
	RubyValue callMethod(RubyValue receiver, RubyID name, RubyValue[] args, RubyBlock block) {
		RubyMethod method = this.findMethod(name);
		if (method != null) {
			return method.invoke(receiver, args, block);
		}
		
		return null;
	}
	
	private RubyMethod findMethod(RubyID id) {
		RubyClassModuleBase klass = this;
		
		while (klass != null) {	
			RubyMethodWrapper method = klass.methodTable.get(id);
			if (method != null) {
				return method.getMethod();
			}
			
			klass = klass.superclass;
		}
		
		return null;		
	}
	
	// const
	public void defineConst(String name, RubyValue value) {
		RubyID id = StringMap.intern(name);
		// FIXME: check validity
		this.setConst(id, value);
	}
	
	public void setConst(RubyID id, RubyValue value) {
		ensureIvTableNotNull();
		
		RubyValue innerValue = this.ivTable.get(id);
		if (innerValue != null) {
			// FIXME: add handler for undef
		}
		
		this.ivTable.put(id, value);
	}
	
	public boolean isDefinedConst(RubyID id) {
		RubyClassModuleBase tempClass = this;
		
		while (tempClass != null) {
			if (this.ivTable != null && this.ivTable.containsKey(id)) {
				// FIXME: add judgement for nil && undef etc.
				
				return true;
			}
			
			// FIXME: add judegement for recursion and object
			
			tempClass = tempClass.superclass;
		}
		
		// FIXME: add judgement for retry
		
		return false;
	}
	
	public RubyValue getConst(RubyID id) {
		RubyClassModuleBase tempClass = this;
		RubyValue value;
		
		while (tempClass != null) {
			if (this.ivTable != null && ((value = this.ivTable.get(id)) != null)) {
				// FIXME: add judgement for nil && undef etc.				
				
				return value;
			}
			
			// FIXME: add judegement for recursion and object
			
			tempClass = tempClass.superclass;
		}
		
		// FIXME: add judgement for retry
		
		// FIXME: call const_missing
		return RubyConstant.QNIL;		
	}
	
	// instance variable
		
	// module
	private RubyClass newIncludeClass(RubyModule module, RubyClass superclass) {
		RubyIncludeClass klass = new RubyIncludeClass();
		klass.superclass = superclass;
		klass.ivTable = module.ivTable;
		klass.methodTable = module.methodTable;
		klass.superclass = superclass;
		return klass;
	}
	
	public void includeModule(RubyModule module) {
		if (RubyConstant.isNil(module)) {
			return;
		}
		
		RubyClassModuleBase c = this;
		
		// ignore if the module included already in superclasses
		
		// include module
		RubyClass includeClass = newIncludeClass(module, c.superclass);
		c.superclass = includeClass;
	}
	
	// instance
	public boolean isInstanceOf(RubyValue value) {
		return RubyUtil.classof(value) == this;
	}
	
	public boolean isKindOf(RubyValue value) {
		RubyClass klass = RubyUtil.classof(value);
		while (klass != null) {
			if (klass == this || klass.methodTable == this.methodTable) {
				return true;
			}
			
			klass = klass.superclass;
		}
		
		return false;
	}
}
