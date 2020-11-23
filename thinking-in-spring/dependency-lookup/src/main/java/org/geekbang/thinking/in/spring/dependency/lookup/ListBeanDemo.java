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
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过 {@link ObjectProvider} 进行依赖查找
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class ListBeanDemo
{ // @Configuration 是非必须注解

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(ListBeanDemo.class);
        // 启动应用上下文
        applicationContext.refresh();

        lookupByStreamOps(applicationContext);
        System.out.println("----------------");
        System.out.println(applicationContext.getBean("helloWorld", String.class));
        System.out.println(applicationContext.getBean("message", String.class));
        // 关闭应用上下文
        applicationContext.close();

    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        // Stream -> Method reference
        objectProvider.stream().forEach(System.out::println);
    }


    @Bean
    public String helloWorld() { // 方法名就是 Bean 名称 = "helloWorld"
        return "Hello,World";
    }

    @Bean
    public String message() {
        return "Message";
    }
}
