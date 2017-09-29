<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title></title>
  <link rel="stylesheet" href="/static/css/index.css">
  <script src="/static/js/index.js"></script>
</head>
<body>
<div class="amz-banner am-margin-bottom">
  <div class="am-container am-animation-scale-up">
    <h1>坐标值</h1>
    <p>定时获取坐标值</p>
  </div>
</div>
<div class="am-container">
  <div class="am-panel am-panel-default" id="conditionPanel">
    <div class="am-panel-hd" data-am-collapse="{target: '#conditionForm'}">
      条件设置
    </div>
    <div id="conditionForm" class="am-collapse">
      <div class="am-panel-bd">
        <form class="am-form am-form-horizontal am-g-collapse">
          <div class="am-form-group">
            <label class="am-u-md-2 am-form-label">maxage：</label>
            <div class="am-u-md-10 am-animation-slide-top">
              <input type="text" value="100" id="maxAge">
            </div>
          </div>
          <div class="am-form-group">
            <label class="am-u-md-2 am-form-label">轮询时间（毫秒）：</label>
            <div class="am-u-md-10 am-animation-slide-top" id="preValueDiv">
              <div class="am-btn-group am-margin-bottom am-btn-group-justify" id="interBtnList">
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">
                  100
                </a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">200</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">300</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">400</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">500</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">600</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">700</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">800</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">900</a>
                <a type="button" class="am-btn am-btn-default" onclick="selectTime(this)">1000</a>
                <a type="button" class="am-btn am-btn-default" onclick="showCustomDiv(this)">自定义</a>
              </div>
            </div>

            <div class="am-input-group am-u-md-10 am-animation-slide-top am-hide" id="customTimeDiv">
              <input type="text" class="am-form-field">
              <a class="am-input-group-label am-btn am-btn-default" href="javascript:showPreValueDiv()">返回</a>
            </div>
          </div>
          <div class="am-form-group">
            <div class="am-u-md-5 am-u-md-offset-2">
              <button type="button" class="am-btn am-btn-primary" onclick="adjustQuartzTime()">确定</button>
            </div>
            <div class="am-u-md-5">
              <button type="button" class="am-btn am-btn-default am-fr am-animation-fade" id="pauseBtn" onclick="pauseQuartz()">暂停</button>
              <button type="button" class="am-btn am-btn-success am-fr am-hide am-animation-fade" id="startBtn" onclick="resumeQuartz()">开始</button>
            </div>
          </div>
        </form>
      </div>
    </div>

  </div>

  <div class="am-panel am-panel-primary">
    <div class="am-panel-hd">
      <button type="button" class="am-btn am-btn-default am-btn-xs" onclick="loadData()">
        刷新
      </button>
    </div>
    <div class="am-scrollable-horizontal">
      <table class="am-table am-table-bordered am-table-striped am-table-hover am-text-nowrap">
        <thead>
        <tr>
          <th>标签ID</th>
          <th>横坐标</th>
          <th>纵坐标</th>
          <th>横坐标（滤）</th>
          <th>纵坐标（滤）</th>
          <th>请求时间</th>
          <th>读取时间</th>
          <th>系统延时</th>
        </tr>
        </thead>
        <tbody id="table_tbody">

        </tbody>
      </table>
    </div>

    <ul id="pageUl" class="am-pagination am-pagination-centered">
      <li><a href="#">首页</a></li>
      <li class="am-disabled"><a href="#">&laquo;</a></li>
      <li class="am-active"><a href="#">1</a></li>
      <li><a href="#">2</a></li>
      <li><a href="#">3</a></li>
      <li><a href="#">4</a></li>
      <li><a href="#">5</a></li>
      <li><a href="#">&raquo;</a></li>
      <li><a href="#">尾页</a></li>
    </ul>
  </div>
</div>
</body>
</html>