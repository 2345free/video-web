<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<link rel="stylesheet" type="text/css" href="/css/ddsmoothmenu.css"/>
<script type="text/javascript" src="/js/ddsmoothmenu.js"></script>

<script type="text/javascript">

    ddsmoothmenu.init({
        mainmenuid: "svw_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    });

</script>

<div id="site_title"><h1><a href="/">Simplest Video Website</a></h1></div>
<div id="svw_menu" class="ddsmoothmenu">
    <ul>
        <li><a href="/" class="selected"><spring:message code="index.index"/></a></li>
        <li><a href="/video/list/0"><spring:message code="video.vod"/></a></li>
        <li><a href="/video/list/1"><spring:message code="video.live"/></a></li>
        <!--
            <ul>
                <li><span class="top"></span><span class="bottom"></span></li>
                <li><a href="...">Sub menu 1</a></li>
                <li><a href="...">Sub menu 2</a></li>
              </ul>
         -->
        <li><a href="/about"><spring:message code="about.about"/></a></li>
        <li><a href="/config/list"><spring:message code="configure.configure"/></a></li>
        <li><a href="#"><spring:message code="configure.language"/></a>
            <ul>
                <li><a href="?locale=en_US">English</a></li>
                <li><a href="?locale=zh_CN">简体中文</a></li>
            </ul>
        </li>
    </ul>
    <br style="clear: left"/>
</div>
<div class="cleaner"></div>