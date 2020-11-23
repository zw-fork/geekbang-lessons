/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geekbang.thinking.in.spring.dependency.lookup;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 层次性依赖查找示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(ObjectProviderDemo.class);

        // 1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("1. 当前 BeanFactory 的 Parent BeanFactory ： " + beanFactory.getParentBeanFactory());

        // 2. 设置 Parent BeanFactory.并将xml解析的Bean[user,superUser,objectFactory]加载到该Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("2. 当前 BeanFactory 的 Parent BeanFactory ： " + beanFactory.getParentBeanFactory());

        displayContainsLocalBean(beanFactory, "user");
        displayContainsLocalBean(parentBeanFactory, "user");

        System.out.println("--------自定义递归查找---------");
        displayContainsBean(beanFactory, "user");
        System.out.println("-----------------");
        displayContainsBean(parentBeanFactory, "user");

        System.out.println("--------BeanFactoryUtils：递归查找---------");
        Map<String, User> userMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, User.class);
        System.out.println(userMap.toString());
        // 启动应用上下文
        applicationContext.refresh();

        // 关闭应用上下文
        applicationContext.close();

    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("B. 是否包含 Local Bean[name : %s] : %s. 当前 BeanFactory[%s]\n", beanName,
                containsBean(beanFactory, beanName), beanFactory);
    }

    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            System.out.println("--------递归查找Parent Factory---------");
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("A. 是否包含 Local Bean[name : %s] : %s. 当前 BeanFactory[%s]\n", beanName,
                beanFactory.containsLocalBean(beanName), beanFactory);
    }

    private static ConfigurableListableBeanFactory createParentBeanFactory() {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置文件 ClassPath 路径
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载配置
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }

}
