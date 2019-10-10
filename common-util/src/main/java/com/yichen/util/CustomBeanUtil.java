package com.yichen.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dengbojing
 */
public class CustomBeanUtil {

    /**
     * 获取对象中为值空的属性名称集合
     * @param source 操作对象
     * @param additionalProperties 直接指定为空值得属性
     * @return 属性集合
     * @see CustomBeanUtil#getNullPropertyNames(Object, Collection)
     */
    public static List<String> getNullPropertyNames(Object source,String...additionalProperties) {
        List<String> ignoreList = getNullPropertyNames(source);
        if(additionalProperties != null){
            ignoreList.addAll(Arrays.asList(additionalProperties));
        }
        return ignoreList;
    }

    /**
     * 获取对象中为值空的属性名称集合
     * @param source 操作对象
     * @param additionalProperties 直接指定为空值得属性
     * @return 属性集合
     * @see CustomBeanUtil#getNullPropertyNames(Object, String...)
     */
    public static List<String> getNullPropertyNames(Object source, Collection<String> additionalProperties) {
        List<String> ignoreList = getNullPropertyNames(source);
        if(additionalProperties != null){
            ignoreList.addAll(additionalProperties);
        }
        return ignoreList;
    }


    /**
     * 获取对象中为值空的属性名称集合
     * @param source 操作对象
     * @return 属性集合
     */
    public static List<String> getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .collect(Collectors.toList());
    }
}
