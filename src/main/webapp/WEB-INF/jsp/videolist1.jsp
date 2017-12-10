<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="Video, FFmpeg, JavaEE"/>
    <meta name="author" content="Lei Xiaohua"/>
    <meta name="description" content="The simplest video website based on JavaEE and FFmpeg"/>

    <title>Simplest Video Website</title>
    <%@include file="JsAndCss.jsp" %>
    <script type="text/JavaScript" src="/js/slimbox2.js"></script>

</head>

<body id="subpage">

<div id="svw_header_wrapper">
    <%@ include file="cheader.jsp" %>
</div>    <!-- END of svw_header_wrapper -->

<div id="svw_main">

    <h2>
        <c:choose>
            <c:when test="${isLive==1}">
                <spring:message code="video.livelist"/>
            </c:when>
            <c:otherwise>
                <spring:message code="video.vodlist"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <div class="post">
        <div class="meta">
            <span><b><spring:message code="video.listmanage"/></b></span>
            <span class="add"><a href="/video/toAdd?isLive=${isLive}"><spring:message
                    code="video.add"/></a></span>
        </div>
    </div>
    <c:if test="${empty videos}">
        <p><spring:message code="video.listempty"/></p>
        <div style="height:300px;"></div>
    </c:if>
    <c:forEach items="${videos}" var="video">
        <div class="col one_fourth gallery_box" style="${video.videostate.cssstyle}">
            <a href="/video/get/${video.id}"><img src="/${video.thumbnailurl}" alt="thumbnail"
                                                  class="image_frame"/></a>
            <h5><a href="/video/get/${video.id}">${video.name}</a></h5>
            <p><spring:message code="video.edittime"/>:${video.edittime}</p>
            <p>
                <a href="/video/get/${video.id}"><spring:message code="video.content"/></a>|
                <a href="/video/edit/${video.id}"><spring:message code="video.edit"/></a>|
                <a href="javascript:if(confirm('Are you sure to Delete?'))location='/video/delete/${video.id}'"><spring:message
                        code="video.delete"/></a>
            </p>
        </div>
    </c:forEach>


    <div class="cleaner"></div>
    <!--
    <div class="pagging">
        <ul>
            <li><a href="http://xxx/1" target="_parent">Previous</a></li>
            <li><a href="http://xxx/1" target="_parent">1</a></li>
            <li><a href="http://xxx/1" target="_parent">2</a></li>
            <li><a href="http://xxx/1" target="_parent">Next</a></li>
        </ul>
    </div>
     -->
    <div class="cleaner"></div>
</div> <!-- END of svw_main -->

<!-- END of svw_bottom_wrapper -->

<div id="svw_footer_wrapper">
    <%@ include file="cfooter.jsp" %>
</div> <!-- END of svw_footer -->

</body>
</html>