<%--
  Created by IntelliJ IDEA.
  User: huawh.wang
  Date: 2018/8/13
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <title>对比文件</title>
</head>
<body>
<c:choose>
    <c:when test="${username != null}">
        <td><strong>用户:</strong>${username}</td>
        <input type="button" value="退出" onclick="location.href='/logout'">
    </c:when>
    <c:otherwise>
        <td>
            <strong>用户:</strong><input type="button" value="登录" onclick="location.href='/login'">
        </td>
        <td> </td>
    </c:otherwise>
</c:choose>

<form action="/add" method="post" enctype="multipart/form-data">
    源文件: <input type="file" required="true" name="source" >
    目标文件: <input type="file" required="true" name="target" >
    <input type="submit" value="上传文件">
</form>
<p>最近5条历史对比结果</p>
<table  width="1000" border="1" cellspacing="0">
    <tr>
        <td align="center">对比时间</td>
        <td align="center">源文件内容</td>
        <td align="center">目标文件内容</td>
        <td align="center">差异</td>
        <td align="center">操作</td>
    </tr>
    <c:forEach items="${diffs}" var="diff">
        <tr>
            <td align="center">
                    ${diff.time}
            </td>
            <td align="center">${diff.sourceContent}</td>
            <td align="center">${diff.targetContent}</td>
            <td align="center">${diff.difference}</td>
            <td align="center">

                <form action="/delete" method="post">
                    <input type="hidden" name="id"  value="${diff.id}">
                    <input type="submit" <c:if test="${username==null}">disabled="disabled"</c:if> value="删除">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
<c:if test="${page!=0}">
    <a href="/index?page=${page-1}">上一页</a>
</c:if>

<c:if test="${page*2+2 < diffcount}">
    <a href="/index?page=${page+1}">下一页</a>
</c:if>
</body>
</html>