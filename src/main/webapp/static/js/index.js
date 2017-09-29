$(function () {

  // adjustQuartzTime(100);
  loadData();
  loadQuartz();
})

/**
 * 选择时间
 * @param _this
 */
function selectTime(_this) {
  $(_this).siblings("a").removeClass("am-active");
  $(_this).addClass("am-active");
}

/**
 * 设置条件
 */
function adjustQuartzTime() {
  var maxAge = $("#maxAge").val();
  if (!maxAge || isNaN(maxAge)) {
    alert("请填写maxAge");
    return;
  }
  var interTime = null;
  if (!$("#preValueDiv").hasClass("am-hide")) {
    interTime = $("#interBtnList").find("a.am-active").text();
  }
  if (!$("#customTimeDiv").hasClass("am-hide")) {
    interTime = $("#customTimeDiv").find("input").val();
  }
  if (!interTime) {
    alert("请选择或填写轮询时间");
    return;
  }

  $.ajax({
    url: "/location/adjustQuartzTime",
    data: {maxAge: maxAge, time: interTime},
    type: "post",
    dataType: "json",
    success: function (response) {
      if (!response.success) {
        alert(response.message);
      } else {
        showSuccessMessage("设置成功，已按照新的方式执行");
      }
    }
  });
}

/**
 * 加载坐标列表
 */
function loadData() {
  $("#pageUl").createPage({
    url: "/location/queryLocationData",
    pageSize: "10",
    successFn: function (response) {
      var trArray = [];
      $.each(response.locationList.result, function (index, item) {
        trArray.push("<tr>");
        trArray.push("  <td>" + item.tagId + "</td>");
        trArray.push("  <td>" + item.positionX + "</td>");
        trArray.push("  <td>" + item.positionY + "</td>");
        trArray.push("  <td>" + item.smoothedPositionX + "</td>");
        trArray.push("  <td>" + item.smoothedPositionY + "</td>");
        trArray.push("  <td>" + moment(item.responseTimestampEpoch).format("YYYY-MM-DD hh:mm:ss | SSS") + "</td>");
        trArray.push("  <td>" + moment(item.positionTimestampEpoch).format("YYYY-MM-DD hh:mm:ss | SSS")+ "</td>");
        trArray.push("  <td>" + (item.responseTimestampEpoch - item.positionTimestampEpoch) + "</td>");
        trArray.push("</tr>");
      });

      $("#table_tbody").html(trArray.join(""));
    }
  });
}

/**
 * 加载定时器信息
 */
function loadQuartz() {
  $.ajax({
    url: "/location/queryQuartz",
    type: "get",
    dataType: "json",
    success: function (response) {
      if (!response.success) {
        alert(response.message);
        return;
      }

      var time = response.time;
      var maxAge = response.maxAge;
      var status = response.status;

      $("#maxAge").val(maxAge);

      //时间间隔的初始化
      var matchPreValue = false;
      $.each($("#interBtnList").find("a"), function (index, item) {
        if ($(this).text() == time) {
          $(this).click();
          matchPreValue = true;
        }
      });
      if (!matchPreValue) {
        $("#customTimeDiv").find("input").val(time);
        $("#customTimeDiv").removeClass("am-hide");
        $("#preValueDiv").addClass("am-hide");
      }



      //开始暂停按钮的初始化
      if (status == 1) {
        $("#pauseBtn").removeClass("am-hide");
        $("#startBtn").addClass("am-hide");
      } else {
        $("#pauseBtn").addClass("am-hide");
        $("#startBtn").removeClass("am-hide");
      }

    }
  })
}

/**
 * 显示自定义时间面板
 * @param _this
 */
function showCustomDiv(_this) {
  // selectTime(_this);

  $("#preValueDiv").addClass("am-hide");
  $("#customTimeDiv").removeClass("am-hide");

}

/**
 * 显示预设值面板
 */
function showPreValueDiv() {
  $("#preValueDiv").removeClass("am-hide");
  $("#customTimeDiv").addClass("am-hide");
}

/**
 * 暂停任务
 */
function pauseQuartz() {
  $.ajax({
    url: "/location/pauseQuartz",
    type: "post",
    dataType: "json",
    success: function (response) {
      if (!response.success) {
        alert(response.message);
        return;
      }

      $("#startBtn").removeClass("am-hide");
      $("#pauseBtn").addClass("am-hide")
    }
  });
}

/**
 * 开始任务
 */
function resumeQuartz() {
  $.ajax({
    url: "/location/resumeQuartz",
    type: "post",
    dataType: "json",
    success: function (response) {
      if (!response.success) {
        alert(response.message);
        return;
      }

      $("#startBtn").addClass("am-hide");
      $("#pauseBtn").removeClass("am-hide")
    }
  });
}