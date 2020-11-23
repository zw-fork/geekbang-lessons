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

import org.geekbang.thinking.in.spring.ioc.overview.container.AnnotationApplicationContextAsIoCContainerDemo;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * {@link BeanNameGenerator}
 *
 * Bean 别名示例：基于注解实现
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
@Configuration
public class BeanAliasByAnnotationDemo
{

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 将当前类 AnnotationApplicationContextAsIoCContainerDemo 作为配置类（Configuration Class）
        applicationContext.register(BeanAliasByAnnotationDemo.class);

        // 启动应用上下文
        applicationContext.refresh();
        User user1 = applicationContext.getBean("user1", User.class);
        User user2 = applicationContext.getBean("user2", User.class);
        System.out.println("user1 是否与 user2 相同：" + (user1 == user2));
        // 关闭应用上下文
        applicationContext.close();

    }

    /**
     * 通过 Java 注解的方式，定义了一个 Bean
     *
     * bean别名(value)和name是相互依赖关联的，value,name如果都使用的话值必须要一致
     */
    @Bean(value={"user1", "user2"}, name={"user1", "user2"})
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("小马哥AAA");
        return user;
    }

}
