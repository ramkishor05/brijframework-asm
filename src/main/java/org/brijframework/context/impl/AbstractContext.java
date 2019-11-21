package org.brijframework.context.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.brijframework.container.Container;
import org.brijframework.context.Context;
import org.brijframework.env.Environment;
import org.brijframework.env.impl.EnvironmentImpl;
import org.brijframework.support.config.Assignable;

public abstract class AbstractContext implements Context{

	private Context context;
	
	private Environment environment;
	
	private Stages stages;
	
	public Stages getStages() {
		if(stages==null) {
			stages=Stages.INIT;
		}
		return stages;
	}
	
	protected void setStages(Stages stages) {
		this.stages = stages;
	}
	
	@Override
	public Environment getEnvironment() {
		if(environment==null) {
			load();
		}
		return environment;
	}

	@Override
	public Context getParent() {
		return context;
	}

	@Override
	public void initialize(Context context) {
		this.context = context;
	}

	protected void load() {
		environment = new EnvironmentImpl();
		environment.init();
	}
	
	protected Method findFactoryMethod(Class<? extends Container> contextClass) {
		for (Method method : contextClass.getMethods()) {
			if (Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Assignable.class)) {
				return method;
			}
		}
		return null;
	}
}
