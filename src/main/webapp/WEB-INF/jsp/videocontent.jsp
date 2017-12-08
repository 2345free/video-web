<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="Video, FFmpeg, JavaEE"/>
    <meta name="author" content="Lei Xiaohua"/>
    <meta name="description" content="The simplest video website based on JavaEE and FFmpeg"/>

    <title>Simplest Video Website</title>

    <link href="/css/svw_style.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/js/showhide.js"></script>
    <script type="text/JavaScript" src="/js/jquery.mousewheel.js"></script>

    <!-- Flow Player -->
    <script type="text/javascript" src="/videoplayer/flowplayer-3.2.8.min.js"></script>
</head>

<body id="subpage">

<div id="svw_header_wrapper">
    <%@ include file="cheader.jsp" %>
</div>    <!-- END of svw_header_wrapper -->

<div id="svw_main">
    <div id="content">
        <div class="post">
            <h2>
                <c:choose>
                    <c:when test="${video.islive==1}">[直播]</c:when>
                    <c:otherwise>[点播]</c:otherwise>
                </c:choose>
                ${video.name}
            </h2>
            <div id="player_window">
                <c:choose>
                    <c:when test="${video.islive==1}">
                        <a id="player">
                        </a>
                        <!-- Parse RTMP URL -->
                        <script>
                            str = '${video.url}';
                            arr = str.split('/');
                            //rtmp://server/app/playpath
                            protocol = arr[0];
                            server = arr[2];
                            app = arr[3];
                            playpath = arr[4];
                            flowplayer("player", "/videoplayer/flowplayer-3.2.8.swf", {
                                clip: {
                                    //scaling:'orig',
                                    url: playpath,
                                    provider: 'rtmp',
                                    live: 'true'
                                },
                                plugins: {
                                    rtmp: {
                                        url: '/videoplayer/flowplayer.rtmp-3.2.8.swf',
                                        netConnectionUrl: protocol + '//' + server + '/' + app
                                    }
                                }
                            });

                        </script>

                    </c:when>
                    <c:otherwise>
                        <a id="player"
                           href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/${video.url}">
                        </a>
                        <script>
                            flowplayer("player", "/videoplayer/flowplayer-3.2.8.swf");
                        </script>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="meta">
                <span class="content"><a href="/video/get/${video.id}">内容</a></span>
                <span class="edit"><a href="/video/edit/${video.id}">编辑</a></span>
                <span class="delete"><a
                        href="javascript:if(confirm('Are you sure to delete?'))location='/video/delete/${video.id}'">删除</a></span>

                <div class="cleaner"></div>
            </div>
        </div>

        <div class="cleaner h20"></div>
        <h3>简介</h3>
        <div id="comment_section">
            <c:choose>
                <c:when test="${empty video.intro}">
                    <p>暂无简介</p>
                </c:when>
                <c:otherwise>
                    <p>${video.intro}</p>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="cleaner h20"></div>
        <!-- Default: Do not use MediaInfo  -->
        <!-- 
        <c:if test="0">
            <h3>媒体信息</h3>
              	<div id="comment_section">
                	<ol class="comments first_level">
                        
                        <li>
                        <h4>上传的源文件</h4>
                            <div class="comment_box commentbox1">
                                <div class="comment_text">
                                    ${original_videoinfo}
                                </div>
                                <div class="cleaner"></div>
                            </div> 
                        </li>
                        <li>
                        <h4>转码后的文件</h4>
                            <div class="comment_box commentbox1">
                                <div class="comment_text">
                                    ${convert_videoinfo}
                                </div>
                                <div class="cleaner"></div>
                            </div> 
                        </li>
                        
	                </ol>
	                <div class="cleaner h20"></div>    
                    
                </div>
                
                <div class="cleaner h20"></div>
         </c:if>
          -->
    </div>

    <div id="sidebar">
        <ul class="svw_list">
            <li><a href="/video/list/${video.islive}">返回</a></li>
            <li><a href="/video/get/${video.id}">内容</a></li>
            <li><a href="/video/edit/${video.id}">编辑</a></li>
            <li>
                <a href="javascript:if(confirm('Are you sure to Delete?'))location='/video/delete/${video.id}'">删除</a>
            </li>

        </ul>

        <div class="cleaner h30"></div>

        <%--        <s:action name="SidebarRecent" executeResult="true">
                    <s:param name="num">5</s:param>
                </s:action>--%>
    </div> <!-- end of sidebar -->
    <div class="cleaner"></div>
</div> <!-- END of svw_main -->

<div id="svw_footer_wrapper">
    <%@ include file="cfooter.jsp" %>
</div> <!-- END of svw_footer -->

</body>
</html>