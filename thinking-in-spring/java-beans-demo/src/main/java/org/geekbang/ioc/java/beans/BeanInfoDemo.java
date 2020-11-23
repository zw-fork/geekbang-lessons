package org.geekbang.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * @author zw
 * @date 2020/9/5
 */
public class BeanInfoDemo
{
    public static void main(String[] args) throws IntrospectionException
    {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);

        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor ->
                System.out.println(propertyDescriptor.toString()));


        System.out.println("------------------------");
        // 获取当前类(排除父类)中定义的Bean属性信息。此处，不获取父类Object中定义的属性信息.
        beanInfo = Introspector.getBeanInfo(Person.class, Object.class);

        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor ->
                System.out.println(propertyDescriptor.toString()));
    }


    /**
     * String转Integer
     */
    static class StringToIntegerPropertyEditor extends PropertyEditorSupport
    {
        @Override
        public void setAsText(String text) throws IllegalArgumentException
        {
            if (text instanceof String) {
                Integer value = Integer.valueOf(text);
                setValue(value);
                return;
            }
            throw new java.lang.IllegalArgumentException(text);
        }
    }
}
