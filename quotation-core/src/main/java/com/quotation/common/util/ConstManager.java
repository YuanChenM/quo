/**
 * ConstManager.java
 * 
 * @screen core
 * @author yu_jiong
 */
package com.quotation.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * UseContantsTag.
 * </p>
 * 
 * @author yu_jiong
 */
public class ConstManager {

    /** messages */
    private static final String ERROR_MISSING_CLASS = "Cannot find type '%s' in classpath.";
    private static final String ERROR_FIELD_ACCESS = "Cannot access constant field '%s' of type '%s'.";

    /** logger */
    private static Logger logger = LoggerFactory.getLogger(ConstManager.class);

    /** the instance of ConstManager */
    private static ConstManager constManager;

    /** the map of all constants define */
    private Map<String, Map<String, Object>> constCache = new HashMap<String, Map<String, Object>>();

    // /** class's name */
    // private String type;
    //
    // /** var */
    // private String var;

    /**
     * 
     * <p>
     * The Constructors Method.
     * </p>
     * 
     */
    private ConstManager() {

    }

    /**
     * Get the instance of ConstManager.
     * 
     * @return the instance of ConstManager
     */
    public static ConstManager getInstance() {
        if (constManager == null) {
            constManager = new ConstManager();
        }
        return constManager;
    }

    /**
     * <p>
     * add a constants define to cache.
     * </p>
     * 
     * @param type the constant type
     */
    public void initConst(Class<?> type) {

        Map<String, Object> typeConstCache = constCache.get(type.getName());

        if (typeConstCache == null) {
            typeConstCache = collectConstants(type);
            constCache.put(type.getName(), typeConstCache);
            logger.info("initialize constant define: [{}]", type.getName());
        }
    }

    /**
     * get the constant define by a type.
     * 
     * @param type the constant define type
     * @return the type's constant define
     */
    public Map<String, Object> getConst(Class<?> type) {
        // Iterator<Entry<String, Map<String, Object>>> iterator = constCache.entrySet().iterator();
        // Map<String, Map<String, Object>> constants = new HashMap<String, Map<String, Object>>();
        //
        // while (iterator.hasNext()) {
        // Entry<String, Map<String, Object>> entry = iterator.next();
        // constants.put(getClassName(entry.getKey()), getAllConstants(entry.getValue()));
        // }
        //
        // return Collections.unmodifiableMap(constants);
        return constCache.get(type.getName());
    }

    /**
     * get all constant define.
     * 
     * @return all constant define
     */
    public Map<String, Map<String, Object>> getAllConsts() {
        return constCache;
    }

    // /**
    // *
    // * <p>
    // * get all constants
    // * </p>
    // *
    // * @param constMap Map<String, Object>
    // * @return constants
    // */
    // @SuppressWarnings("unchecked")
    // public Map<String, Object> getAllConstants(Map<String, Object> constMap) {
    //
    // Iterator<Entry<String, Object>> iterator = constMap.entrySet().iterator();
    //
    // Map<String, Object> constants = new HashMap<String, Object>();
    // while (iterator.hasNext()) {
    // Entry<String, Object> entry = iterator.next();
    //
    // Object value = entry.getValue();
    // if (value instanceof Map) {
    // constants.put(getClassName(entry.getKey()), getAllConstants((Map<String, Object>) entry.getValue()));
    // } else {
    // constants.put(getClassName(entry.getKey()), entry.getValue());
    // }
    //
    // }
    // return Collections.unmodifiableMap(constants);
    //
    // }

    // /**
    // *
    // * <p>
    // * transfer class's name to class object
    // * </p>
    // * <p>
    // * class' name must be qualified
    // * </p>
    // *
    // * @param type className
    // * @return Class
    // */
    // private static Class<?> toClass(String type) {
    // try {
    // return Class.forName(type);
    // } catch (ClassNotFoundException e) {
    // throw new IllegalArgumentException(String.format(ERROR_MISSING_CLASS, type));
    // }
    // }

    /**
     * 
     * <p>
     * get name of the class
     * </p>
     * 
     * @param type className
     * @return class name
     */
    private static String getClassName(String type) {
        if (type == null || type.trim().length() == 0) {
            throw new IllegalArgumentException(String.format(ERROR_MISSING_CLASS, type));
        }

        int innerClassIdx = type.lastIndexOf("$");
        if (innerClassIdx > 0) {
            return type.substring(innerClassIdx + 1);
        }
        return type.substring(type.lastIndexOf('.') + 1);
    }

    /**
     * Collect constants of the given type. That are, all public static final fields of the given type.
     * 
     * @param type The fully qualified name of the type to collect constants for.
     * @return Constants of the given type.
     */
    private Map<String, Object> collectConstants(final Class<?> type) {
        Map<String, Object> constants = new HashMap<String, Object>();

        Class<?> fieldClzz = type;
        for (Field field : fieldClzz.getFields()) {
            Class<?> fieldType = field.getType();
            if (fieldType.equals(Class.class)) {
                constants.put(getClassName(field.getName()), collectConstants(fieldType));
            } else if (isPublicStaticFinal(field)) {
                try {
                    constants.put(getClassName(field.getName()), field.get(null));
                } catch (Exception e) {
                    throw new RuntimeException(String.format(ERROR_FIELD_ACCESS, type, field.getName()));
                }
            }

        }

        for (Class<?> clzz : fieldClzz.getClasses()) {
            constants.put(getClassName(clzz.getName()), collectConstants(clzz));
        }

        return Collections.unmodifiableMap(constants);
    }

    /**
     * Returns whether the given field is a constant field, that is when it is public, static and final.
     * 
     * @param field The field to be checked.
     * @return <code>true</code> if the given field is a constant field, otherwise <code>false</code>.
     */
    private static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }

    // /**
    // * <p>
    // * get the type.
    // * </p>
    // *
    // * @return type
    // */
    // public String getType() {
    // return this.type;
    // }
    //
    // /**
    // * <p>
    // * set the type.
    // * </p>
    // *
    // * @param type type
    // */
    // public void setType(String type) {
    // this.type = type;
    // }
    //
    // /**
    // * <p>
    // * get the var.
    // * </p>
    // *
    // * @return var
    // */
    // public String getVar() {
    // return this.var;
    // }
    //
    // /**
    // * <p>
    // * set the var.
    // * </p>
    // *
    // * @param var var
    // */
    // public void setVar(String var) {
    // this.var = var;
    // }

}
