<%--
  Created by IntelliJ IDEA.
  User: huawh.wang
  Date: 2018/8/13
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="/login.do" method="post">
    <fieldset>
        <table>
            <tr>
                <td><label for="name">用户名：</label></td>
                <td><input type="text" id="name" name="name" value="" required="true"></td>
            </tr>
            <tr>
                <td><label for="password">密码：</label></td>
                <td><input type="password" id="password" name="password" value="" required="true"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="submit" value="登录"></td>
            </tr>
        </table>
    </fieldset>

</form>

</body>
</html>