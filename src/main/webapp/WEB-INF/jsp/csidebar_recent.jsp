<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>最近添加</h3>
<ul class="recent_post">
    <c:forEach items="${resultvideo}" var="video">
        <li><a href="VideoReadByID.action?videoid=${video.id}">${video.name}</a><br/>${video.edittime}</li>
    </c:forEach>
</ul>