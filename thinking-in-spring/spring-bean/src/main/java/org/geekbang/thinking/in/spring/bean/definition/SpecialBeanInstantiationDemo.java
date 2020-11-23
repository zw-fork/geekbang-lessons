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
package org.geekbang.thinking.in.spring.bean.definition;

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 特殊的 Bean 实例化示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {

        System.out.println("---------方法一：ServiceLoader----------");
        demoServiceLoader();

        System.out.println("-------方法二：ServiceLoaderFactoryBean------------");
        // 使用Spring中的ServiceLoaderFactoryBean类。(org.geekbang.thinking.in.spring.bean.factory.UserFactory文件与方法一一样，都需要定义)
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
        // 通过 ApplicationContext 获取 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);

        displayServiceLoader(serviceLoader);

        System.out.println("--------方法三：DefaultUserFactory-----------");
        // 创建 UserFactory 对象，通过 AutowireCapableBeanFactory
        UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
        System.out.println(userFactory.createUser());

        System.out.println("---------方法四：User----------");
        User user = beanFactory.createBean(User.class);
        System.out.println(user);


    }

    public static void demoServiceLoader() {
        //通过ServiceLoader，加载所有定义的UserFactory对象
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        displayServiceLoader(serviceLoader);
    }

    private static void displayServiceLoader(ServiceLoader<UserFactory> serviceLoader) {
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            // 创建实例对象
            System.out.println(userFactory.createUser());
        }
    }
}
