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
package org.geekbang.thinking.in.spring.ioc.overview.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        // 依赖注入来源一：自定义的Bean
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository.getUsers());

        //依赖来源二：依赖注入（內建依赖）
        System.out.println(userRepository.getBeanFactory());  //获取注入的BeanFactory对象


        /**
         * ApplicationContext实现了BeanFactory接口
         *
         * 同时，通过组合的方式注入了一个BeanFactory的实现类：DefaultListableBeanFactory
         *      org.springframework.context.support.AbstractRefreshableApplicationContext
         */
        System.out.println(userRepository.getBeanFactory() == beanFactory);
        ClassPathXmlApplicationContext classPathXmlApplicationContext = (ClassPathXmlApplicationContext)beanFactory;
        System.out.println(classPathXmlApplicationContext.getBeanFactory() == userRepository.getBeanFactory());

        System.out.println("-----------------");

        //依赖查找（错误）. 查找BeanFactory对象(抛异常<没有定义该Bean>) TODO
        // System.out.println(beanFactory.getBean(BeanFactory.class));

        System.out.println(userRepository.getUserObjectFactory().getObject());
        System.out.println(userRepository.getObjectFactory().getObject());
        System.out.println(userRepository.getObjectFactory().getObject() == beanFactory);
        System.out.println("-----------------");
        // 依赖来源三：容器內部自建的Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);
    }

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {


        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory

        // ConfigurableApplicationContext#getBeanFactory()


        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        // ApplicationContext is BeanFactory

    }

}
