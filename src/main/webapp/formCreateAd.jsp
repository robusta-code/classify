<%--
  ~ Copyright (c) 2014. by Robusta Code and individual contributors
  ~  as indicated by the @authors tag. See the copyright.txt in the
  ~ distribution for a full listing of individual contributors.
  ~
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements.  See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership.  The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License.  You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: dev
  Date: 01/09/14
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Ad</title>
</head>
<body>

<form method="post" action="api/ad/add">

    <table>
        <td>
            <label>id</label></td>
        <td>
            <input type="text" name="id" value="3"/>
        </td>
        <tr>
            <td><label>title</label></td>
            <td>
                <input type="text" name="title" value="title"/></td>
        </tr>

        <tr>
            <td><label>content</label></td>
            <td>
                <input type="text" name="content" value="content"/></td>
        </tr>

        <tr>
            <td><label>price</label></td>
            <td>
                <input type="text" name="price" value="12"/></td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="submit" value="Valider"/></td>
        </tr>
    </table>
</form>

</body>
</html>
