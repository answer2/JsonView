package com.answer.library.JsonView.bean;

import java.lang.reflect.*;
import java.util.List;
import com.answer.library.JsonView.enums.ReflectType;
import com.answer.library.JsonView.JsonManager;
import com.answer.library.JsonView.utils.StringUtil;
import com.answer.library.JsonView.debug.JsonLog;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;
import com.answer.library.JsonView.manager.VarManager;
import com.answer.library.JsonView.utils.EmptyUtil;

public class ReflectBean {

    public static final String TAG = "ReflectBean";

    private String className;

    private String type;
    // Field Method
    private String name;

    private List<String> parameterTypes;

    private List<String> invokeArgs;
    // 将Field,Methid或Constructor保存起来
    private String Var;

    private String VarVlaue;

    private String VarInstance;

    private String VarInvoke;

    private String instance;

    private String value;

    private boolean isUseHide = true;

    private boolean isOnClick = false;

    private ReflectBean reflect;

    public void setReflect(ReflectBean reflect) {
        this.reflect = reflect;
    }

    public ReflectBean getReflect() {
        return reflect;
    }

    public void setIsOnClick(boolean isOnClick) {
        this.isOnClick = isOnClick;
    }

    public boolean isOnClick() {
        return isOnClick;
    }

    public void setInvokeArgs(List<String> invokeArgs) {
        this.invokeArgs = invokeArgs;
    }

    public List<String> getInvokeArgs() {
        return invokeArgs;
    }

    public void setVarInvoke(String varInvoke) {
        VarInvoke = varInvoke;
    }

    public String getVarInvoke() {
        return VarInvoke;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getInstance() {
        return instance;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ReflectType getType() {
        return ReflectType.valueOf(type);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setVar(String var) {
        Var = var;
    }

    public String getVar() {
        return Var;
    }

    public void setVarVlaue(String varVlaue) {
        VarVlaue = varVlaue;
    }

    public String getVarVlaue() {
        return VarVlaue;
    }

    public void setVarInstance(String varInstance) {
        VarInstance = varInstance;
    }

    public String getVarInstance() {
        return VarInstance;
    }

    public boolean isUseHide() {
        return isUseHide;
    }

    public void start() {
        try {
            Class<?> reflectClass = Class.forName(getClassName());

            VarManager.add(getVarInstance(), (Object) reflectClass.newInstance());

            Object Instance = EmptyUtil.isNotNull(getInstance()) ? StringUtil.varObjectOne(getInstance()) : reflectClass.newInstance();
            if (getType().equals(ReflectType.method)) {
                Method reflectMethod = reflectClass.getMethod(getName(), StringListToClassArray(getParameterTypes()));
                reflectMethod.setAccessible(isUseHide());
                if (EmptyUtil.isNotNull(getVarInvoke())) 
                    VarManager.add(getVarInvoke(), reflectMethod.invoke(Instance, StringUtil.invokeArgs(getInvokeArgs())));
                else 
                    reflectMethod.invoke(Instance, StringUtil.invokeArgs(getInvokeArgs()));
                
            } else if (getType().equals(ReflectType.field)) {
                Field reflectField = reflectClass.getField(getName());
                reflectField.setAccessible(isUseHide());
                if (EmptyUtil.isNotNull(getVarVlaue())) VarManager.add(getVarVlaue(), reflectField.get(Instance));
                if (EmptyUtil.isNotNull(getValue())) reflectField.set(Instance, StringUtil.invoke(getValue()));
            } else if (getType().equals(ReflectType.constructor)) {
                Constructor<?> reflectConstructor = reflectClass.getConstructor(StringListToClassArray(getParameterTypes()));
                reflectConstructor.newInstance(StringUtil.invokeArgs(getInvokeArgs()));
            }

            if (EmptyUtil.isNotNull(getReflect())) getReflect().start();
            

        } catch (Throwable e) {
            JsonLog.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    private Class<?>[] StringListToClassArray(List<String> list) throws ClassNotFoundException {
        Class<?>[] classArray = new Class<?>[list.size()];
        for (int i = 0; list.size() > i; i++) {
            classArray[i] = Class.forName(list.get(i));
        }
        return classArray;
    }
}
