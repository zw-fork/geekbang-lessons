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
package org.geekbang.thinking.in.spring.ioc.overview.domain;

import org.geekbang.thinking.in.spring.ioc.overview.annotation.Super;

import java.util.Arrays;
import java.util.List;

/**
 * 超级用户
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
@Super
public class SuperUser extends User {

    private String address;

    private List<User> userList;

    private User[] users;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserList(List<User> userList)
    {
        this.userList = userList;
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public void setUsers(User[] users)
    {
        this.users = users;
    }

    public User[] getUsers()
    {
        return users;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address +
                ", users=" + Arrays.toString(users) +
                ", userList=" + userList + '\'' +
                "} " + super.toString();
    }
}
