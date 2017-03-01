package com.gourd.erwa.util.conver;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.gourd.erwa.annotation.NotNull;
import com.gourd.erwa.annotation.PreBeta;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 提供方法:
 * JSON 转换 string       {@link #parseJsonToString(Object)}
 * ES Source Map 转 Obj  {@link #convertESSourceMap2Object(Map, TransDescriptor)} )}，{@link #convertESSourceMap2Object(List, TransDescriptor)}
 *
 * @author wei.Li
 */
public final class ConvertUtils0 {

    /**
     * json 对象转成 string
     *
     * @param object 待转化为 JSON字符串 对象
     * @return json 串 or null
     */
    public static String parseJsonToString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * Parse json to map map.
     *
     * @param json the json
     * @return the map
     */
    public static Map parseJsonToMap(String json) {
        return JSON.parseObject(json, Map.class);
    }

    /**
     * Parse json to class t.
     *
     * @param <T>    the type parameter
     * @param json   the json
     * @param tClass the t class
     * @return the t
     */
    public static <T> T parseJsonToClass(String json, Class<? extends T> tClass) {
        return JSON.parseObject(json, tClass);
    }

    /**
     * Convert es source map 2 object list.
     *
     * @param <T>             the type parameter
     * @param mapList         the map list
     * @param transDescriptor the trans descriptor
     * @return the list
     * @throws Exception the exception
     * @see #convertESSourceMap2Object(Map, TransDescriptor) #convertESSourceMap2Object(Map, TransDescriptor)#convertESSourceMap2Object(Map, TransDescriptor)
     */
    @PreBeta(author = "wei.Li")
    public static
    @NotNull
    <T> List<T> convertESSourceMap2Object(@NotNull List<Map<String, Object>> mapList, @NotNull TransDescriptor<T> transDescriptor) throws Exception {

        final List<T> ts = Lists.newArrayListWithCapacity(mapList.size());
        for (Map<String, Object> map : mapList) {
            ts.add(convertESSourceMap2Object(map, transDescriptor));
        }
        return ts;
    }

    /**
     * ES Source Map String,Object 转换为 T 对象 , T 对象必须提供无参构造函数 , 字段提供 set 方法
     *
     * @param <T>             T
     * @param map             map
     * @param transDescriptor 转换参数说明
     * @return T t
     * @throws Exception 转换异常
     */
    @PreBeta(author = "wei.Li")
    public static
    @NotNull
    <T> T convertESSourceMap2Object(@NotNull Map<String, Object> map, @NotNull TransDescriptor<T> transDescriptor) throws Exception {

        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(transDescriptor.getBeanClass());
        } catch (IntrospectionException e) {
            if (transDescriptor.isDebug()) {
                System.err.println(e.getMessage());
            }
            throw e;
        }

        T t;
        try {
            final Constructor<T> constructor = transDescriptor.getBeanClass().getConstructor();
            t = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            if (transDescriptor.isDebug()) {
                System.err.println(e.getMessage());
            }
            throw e;
        }

        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            final String key = property.getName();
            final Class<?> propertyType = property.getPropertyType();
            boolean isMathBeanNestClass = false;
            for (Class<?> beanNest : transDescriptor.getBeanNestClass()) {
                if (propertyType.equals(beanNest)) {
                    isMathBeanNestClass = true;
                    break;
                }
            }
            Object value = map.get(key);
            if (value == null) {
                continue;
            }

            //key 类型为嵌套其他 class 数据类型进行解析
            if (isMathBeanNestClass && (value instanceof Map)) {
                @SuppressWarnings("unchecked") final Map<String, Object> nestClassMap = (Map<String, Object>) value;
                value = ConvertUtils0.convertESSourceMap2Object(
                        nestClassMap,
                        TransDescriptor.createTransDescriptor(propertyType, transDescriptor.getBeanNestClass(), transDescriptor
                                .getEnumNestClass(), transDescriptor.isDebug())
                );
                if (value == null) {
                    continue;
                }
            }

            //set 方法设置
            final Method setter = property.getWriteMethod();
            try {
                setter.invoke(t, value);
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {

                //异常情况下进行特殊类型转换 , 此情况发生较少
                final String valueStr = value.toString();
                if (propertyType.equals(Integer.class) || propertyType.equals(int.class)) {
                    value = Integer.parseInt(valueStr);
                } else if (propertyType.equals(Long.class) || propertyType.equals(long.class)) {
                    value = Long.parseLong(valueStr);
                } else if (propertyType.equals(Double.class) || propertyType.equals(double.class)) {
                    value = Double.parseDouble(valueStr);
                } else if (propertyType.equals(Float.class) || propertyType.equals(float.class)) {
                    value = Float.parseFloat(valueStr);
                } else if (propertyType.equals(Short.class) || propertyType.equals(short.class)) {
                    value = Short.parseShort(valueStr);
                } else if (propertyType.equals(Byte.class) || propertyType.equals(byte.class)) {
                    value = Byte.parseByte(valueStr);
                } else if (propertyType.equals(Boolean.class) || propertyType.equals(boolean.class)) {
                    value = Boolean.parseBoolean(valueStr);
                } else {
                    //基本数据类型不匹配时匹配枚举
                    for (Class<? extends Enum> anEnum : transDescriptor.getEnumNestClass()) {
                        if (anEnum.equals(propertyType)) {
                            value = Enum.valueOf(anEnum, valueStr);
                            break;
                        }
                    }
                }
                try {
                    setter.invoke(t, value);
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    if (transDescriptor.isDebug()) {
                        System.err.println(key + " \tvalue = " + value + "\t , [" + value.getClass().getSimpleName() +
                                "] convert [" + propertyType.getSimpleName() + "] , error = " + e.getMessage());
                    }
                    throw e;
                }
            }
        }
        return t;
    }

    /**
     * The type Trans descriptor.
     *
     * @param <T> 转换目标 class 类型
     */
    public static class TransDescriptor<T> {
        /**
         * 解析为目标 class 类型 , 必须包含无参构造函数
         */
        private final Class<T> beanClass;
        /**
         * 目标 class 类型中嵌套其他 class 数据类型
         */
        private final List<Class<?>> beanNestClass;
        /**
         * 目标 class 类型中包含枚举类型
         */
        private final List<Class<? extends Enum>> enumNestClass;

        /**
         * 是否 debug 模式进行打印错误信息
         */
        private boolean isDebug;

        private TransDescriptor(Class<T> beanClass, List<Class<?>> beanNestClass,
                                List<Class<? extends Enum>> enumNestClass, Boolean isDebug) {

            this.beanClass = beanClass;
            this.beanNestClass = beanNestClass == null ? Lists.<Class<?>>newArrayList() : beanNestClass;
            this.enumNestClass = enumNestClass == null ? Lists.<Class<? extends Enum>>newArrayList() : enumNestClass;
            this.isDebug = isDebug == null ? false : isDebug;
        }

        /**
         * Create trans descriptor trans descriptor.
         *
         * @param <T>       the type parameter
         * @param beanClass the bean class
         * @return the trans descriptor
         */
        public static <T> TransDescriptor<T> createTransDescriptor(Class<T> beanClass) {

            return new TransDescriptor<>(beanClass, null, null, null);
        }

        /**
         * Create trans descriptor trans descriptor.
         *
         * @param <T>           the type parameter
         * @param beanClass     the bean class
         * @param beanNestClass the bean nest class
         * @param enumNestClass the enum nest class
         * @return the trans descriptor
         */
        public static <T> TransDescriptor<T> createTransDescriptor(Class<T> beanClass, List<Class<?>> beanNestClass,
                                                                   List<Class<? extends Enum>> enumNestClass) {

            return new TransDescriptor<>(beanClass, beanNestClass, enumNestClass, null);
        }

        /**
         * Create trans descriptor trans descriptor.
         *
         * @param <T>           the type parameter
         * @param beanClass     the bean class
         * @param beanNestClass the bean nest class
         * @param enumNestClass the enum nest class
         * @param isDebug       the is debug
         * @return the trans descriptor
         */
        public static <T> TransDescriptor<T> createTransDescriptor(Class<T> beanClass, List<Class<?>> beanNestClass,
                                                                   List<Class<? extends Enum>> enumNestClass, Boolean isDebug) {

            return new TransDescriptor<>(beanClass, beanNestClass, enumNestClass, isDebug);
        }

        /**
         * Gets bean class.
         *
         * @return the bean class
         */
        Class<T> getBeanClass() {
            return beanClass;
        }

        /**
         * Gets bean nest class.
         *
         * @return the bean nest class
         */
        List<Class<?>> getBeanNestClass() {
            return beanNestClass;
        }

        /**
         * Gets enum nest class.
         *
         * @return the enum nest class
         */
        List<Class<? extends Enum>> getEnumNestClass() {
            return enumNestClass;
        }

        /**
         * Is debug boolean.
         *
         * @return the boolean
         */
        boolean isDebug() {
            return isDebug;
        }
    }
}
