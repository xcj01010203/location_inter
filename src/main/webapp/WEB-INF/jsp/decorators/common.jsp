<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1">
  <title>华之讯</title>

  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">

  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>

  <link rel="icon" type="image/png" href="/static/component/amazeui/i/favicon.png">

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="icon" sizes="192x192" href="/static/component/amazeui/i/app-icon72x72@2x.png">

  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  <link rel="apple-touch-icon-precomposed" href="/static/component/amazeui/i/app-icon72x72@2x.png">

  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileColor" content="#0e90d2">

  <link rel="stylesheet" href="/static/component/amazeui/css/amazeui.min.css">
  <link rel="stylesheet" href="/static/css/common.css">

  <!--[if (gte IE 9)|!(IE)]><!-->
  <script src="/static/component/jquery/jquery.min.js"></script>
  <!--<![endif]-->
  <!--[if lte IE 8 ]>
  <script src="/static/component/http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
  <script src="/static/component/amazeui/js/amazeui.ie8polyfill.min.js"></script>
  <![endif]-->
  <script src="/static/component/amazeui/js/amazeui.min.js"></script>

  <!--moment-->
  <script src="/static/component/moment/min/moment.min.js"></script>
  <script src="/static/component/moment/locale/zh-cn.js"></script>

  <script src="/static/component/jquery.page.js"></script>

  <script src="/static/js/common.js"></script>

  <sitemesh:write property='head'/>
</head>
<body>
<div class="am-modal am-modal-alert" tabindex="-1" id="shyAlert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">Amaze UI</div>
    <div class="am-modal-bd" id="alertContent">
      Hello world！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>
<sitemesh:write property='body'/>
</body>
</html>