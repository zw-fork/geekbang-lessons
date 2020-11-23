package org.geekbang.thinking.in.spring.resource;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author zw
 * @date 2020/11/16
 */
public class ProtocolHandlerTest
{
    public static void main(String[] args) throws IOException
    {

        System.out.println("----方法一：将协议handler放到sun.net.www.protocol.${protocol}目录下");

        //java.net.URL.getURLStreamHandler(protocol)：根据协议类型protocol，获取处理的Handler对象

        // 指定协议类型为x；该协议将会由sun.net.www.protocol.x 包下的Handler类处理
        URL url = new URL("x:///META-INF/default.properties"); // 类似于 classpath:/META-INF/default.properties

        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")));



        System.out.println("\n\n----方法二：使用环境变量指定协议handler处理器路径-----");

        /**
         * -Djava.protocol.handler.pkgs=org.geekbang.thinking.in.spring.resource
         *
         * 该环境变量，表示协议路径在org.geekbang.thinking.in.spring.resource包下，
         * 即使用org.geekbang.thinking.in.spring.resource.springx包下的Hander处理springx协议
         * @param args
         * @throws IOException
         */
        // springx 协议
        url = new URL("springx:///META-INF/production.properties"); // 类似于 classpath:/META-INF/default.properties
        inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")));
    }
}
