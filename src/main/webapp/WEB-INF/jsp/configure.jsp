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

    <!-- validationEngine -->
    <link rel="stylesheet" href="/css/validationEngine.jquery.css" type="text/css"/>
    <!-- <script src="js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script> -->
    <script src="/js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
    <script src="/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>


    <script type="text/javascript">
        $(document).ready(function () {
            $("#configform").validationEngine();

            $("#transcoder_keepaspectratio").val('${transcoder_keepaspectratio}');
            $("#transcoder_watermarkuse").val('${transcoder_watermarkuse}');
        });
    </script>
</head>

<body id="subpage">

<div id="svw_header_wrapper">
    <%@ include file="cheader.jsp" %>
</div>    <!-- END of svw_header_wrapper -->

<div id="svw_main">
    <div id="content">
        <div class="post">

            <div id="contact_form">
                <form id="configform" method="post" name="update" action="/config/update"
                      enctype="multipart/form-data">
                    <h2>配置</h2>
                    <label for="transcoder_vcodec">视频编码器:</label> <input type="text"
                                                                         id="transcoder_vcodec"
                                                                         name="transcoder_vcodec"
                                                                         value="${transcoder_vcodec}"
                                                                         class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_bv">视频码率 (bps):</label>
                    <input type="text" id="transcoder_bv" name="transcoder_bv" value="${transcoder_bv}"
                           class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_framerate">视频帧率 (fps):</label>
                    <input type="text" id="transcoder_framerate" name="transcoder_framerate"
                           value="${transcoder_framerate}"
                           class="validate[required,custom[integer]] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_acodec">音频编码器:</label> <input type="text"
                                                                         id="transcoder_acodec"
                                                                         name="transcoder_acodec"
                                                                         value="${transcoder_acodec}"
                                                                         class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_ar">音频采样率 (Hz):</label>
                    <input type="text" id="transcoder_ar" name="transcoder_ar" value="${transcoder_ar}"
                           class="validate[required,custom[integer]] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_ba">音频码率 (bps):</label>
                    <input type="text" id="transcoder_ba" name="transcoder_ba" value="${transcoder_ba}"
                           class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_scale_w">视频宽 (pixel):</label> <input type="text"
                                                                                id="transcoder_scale_w"
                                                                                name="transcoder_scale_w"
                                                                                value="${transcoder_scale_w}"
                                                                                class="validate[required,custom[integer]] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_scale_h">视频高 (pixel):</label> <input type="text"
                                                                                id="transcoder_scale_h"
                                                                                name="transcoder_scale_h"
                                                                                value="${transcoder_scale_h}"
                                                                                class="validate[required,custom[integer]] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_watermarkuse">使用水印:</label>
                    <select id="transcoder_watermarkuse" name="transcoder_watermarkuse" class="required input_field">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_watermark_url">水印文件路径:</label> <input type="text"
                                                                                 id="transcoder_watermark_url"
                                                                                 name="transcoder_watermark_url"
                                                                                 value="${transcoder_watermark_url}"
                                                                                 class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_watermark_x">水印位置-x坐标 (pixel):</label> <input type="text"
                                                                                         id="transcoder_watermark_x"
                                                                                         name="transcoder_watermark_x"
                                                                                         value="${transcoder_watermark_x}"
                                                                                         class="validate[required,custom[integer]] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_watermark_y">水印坐标-y坐标 (pixel):</label> <input type="text"
                                                                                         id="transcoder_watermark_y"
                                                                                         name="transcoder_watermark_y"
                                                                                         value="${transcoder_watermark_y}"
                                                                                         class="validate[required,custom[integer]] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_keepaspectratio">保持宽高比并且填充黑边:</label>
                    <select id="transcoder_keepaspectratio" name="transcoder_keepaspectratio"
                            class="required input_field">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                    <div class="cleaner h10"></div>
                    <label for="transcoder_outfmt">输出视频封装格式:</label> <input type="text"
                                                                            id="transcoder_outfmt"
                                                                            name="transcoder_outfmt"
                                                                            value="${transcoder_outfmt}"
                                                                            class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="thumbnail_ss">视频截图位置 (sec):</label> <input
                        type="text" id="thumbnail_ss" name="thumbnail_ss" value="${thumbnail_ss}"
                        class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="folder_videoori">上传视频存放文件夹:</label>
                    <input type="text" id="folder_videoori" name="folder_videoori" value="${folder_videoori}"
                           class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="folder_video">转码视频存放文件夹:</label> <input
                        type="text" id="folder_video" name="folder_video" value="${folder_video}"
                        class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <label for="folder_thumbnail">视频截图存放文件夹:</label> <input type="text"
                                                                            id="folder_thumbnail"
                                                                            name="folder_thumbnail"
                                                                            value="${folder_thumbnail}"
                                                                            class="validate[required] required input_field"/>
                    <div class="cleaner h10"></div>
                    <input type="submit" value="提交" id="submit"
                           name="submit" class="submit_btn float_l"/>
                    <input type="reset" value="重置" id="reset" name="reset"
                           class="submit_btn float_r"/>

                </form>
            </div>
        </div>
        <div class="cleaner"></div>
    </div>

    <div id="sidebar">
        <s:action name="SidebarRecent" executeResult="true">
            <s:param name="num">5</s:param>
        </s:action>
    </div> <!-- end of sidebar -->
    <div class="cleaner"></div>
</div> <!-- END of svw_main -->

<!-- END of svw_bottom_wrapper -->

<div id="svw_footer_wrapper">
    <%@ include file="cfooter.jsp" %>
</div> <!-- END of svw_footer -->

</body>
</html>