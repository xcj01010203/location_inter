(function ($) {
  var ms = {
    init: function (obj, args) {
      return (function () {
        ms.sendAjax(obj, args, function (pageCount, totalNum) {
          args.pageCount = pageCount;
          args.totalNum = totalNum;
          ms.fillHtml(obj, args);
          ms.bindEvent(obj, args);
        });
      })();
    },
    // 填充html
    fillHtml: function (obj, args) {
      return (function () {
        obj.empty();
        // 如果总页数为0把obj清空
        if (args.pageCount == 0) {
          return;
        }
        // 首页
        if (args.current != 1) {
          obj.append('<li class="shouye"><a href="javascript:void(0)">首页</a></li>');
        } else {

          obj.append('<li class="am-disabled shouye"><a href="javascript:void(0)">首页</a></li>');
        }
        // 上一页
        if (args.current > 1) {
          obj.append('<li class="prevPage"><a href="javascript:void(0)">&laquo;</a></li>');
        } else {
          obj.append('<li class="am-disabled prevPage"><a href="javascript:void(0)">&laquo;</a></li>');
        }
        // 中间页码
        if (args.current != 1 && args.current >= 4 && args.pageCount != 4) {
          obj.append('<li class="tcdNumber"><a href="javascript:void(0)">1</a></li>');
        }
        if (args.current - 2 > 2 && args.current <= args.pageCount && args.pageCount > 5) {
          obj.append('<li><span>...</span></li>');
        }
        var start = args.current - 2, end = args.current + 2;
        if ((start > 1 && args.current < 4) || args.current == 1) {
          end++;
        }
        if (args.current > args.pageCount - 4 && args.current >= args.pageCount) {
          start--;
        }
        for (; start <= end; start++) {
          if (start <= args.pageCount && start >= 1) {
            if (start != args.current) {
              obj.append('<li class="tcdNumber"><a href="javascript:void(0)">' + start + '</a></li>');
            } else {
              obj.append('<li class="am-active tcdNumber"><a href="javascript:void(0)">' + start + '</a></li>');
            }
          }
        }
        if (args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5) {
          obj.append('<li><span>...</span></li>');
        }
        if (args.current != args.pageCount && args.current < args.pageCount - 2 && args.pageCount != 4) {
          obj.append('<li class="tcdNumber"><a href="javascript:void(0)">' + args.pageCount + '</a></li>');
        }
        // 下一页
        if (args.current < args.pageCount) {
          obj.append('<li class="nextPage"><a href="javascript:void(0)">&raquo;</a></li>');
        } else {
          obj.append('<li class="am-disabled nextPage"><a href="javascript:void(0)">&raquo;</a></li>');
        }
        // 尾页
        if (args.current != args.pageCount) {
          obj.append('<li class="weiye"><a href="javascript:void(0)">尾页</a></li>');
        } else {
          obj.append('<li class="am-disabled weiye"><a href="javascript:void(0)">尾页</a></li>');
        }
        // 总条数
        if (args.totalNum) {
          obj.append('&nbsp;&nbsp;&nbsp;<span style="color:#333;">共:<span style="color:#EF772A;">&nbsp;' + args.totalNum + '&nbsp;</span>条</span>')
        }
      })();
    },
    // 绑定事件
    bindEvent: function (obj, args) {
      obj.off("click");
      return (function () {
        obj.on("click", "li.tcdNumber", function () {
          args.current = parseInt($(this).text());
          ms.init(obj, args)
        });
        // 首页
        obj.on("click", "li.shouye", function () {
          args.current = 1;
          ms.init(obj, args)
        });
        // 上一页
        obj.on("click", "li.prevPage", function () {
          --args.current;
          ms.init(obj, args)
        });
        // 下一页
        obj.on("click", "li.nextPage", function () {
          ++args.current;
          ms.init(obj, args)
        });
        // 尾页
        obj.on("click", "li.weiye", function () {
          args.current = args.pageCount;
          // ms.fillHtml(obj, {"current": args.current, "pageCount": args.pageCount, "totalNum": args.totalNum});
          ms.init(obj, args)
        });
      })();
    },
    // 发送ajax
    sendAjax: function (obj, args, callback) {
      var obj = {};
      obj['pageSize'] = args.pageSize;
      obj['currentPage'] = args.current;
      for (var x in args.data) {
        obj[x] = args.data[x];
      }
      if (args.contentType != 'application/x-www-form-urlencoded') {
        obj = JSON.stringify(obj)
      }
      return (function () {
        $.ajax({
          url: args.url,
          data: obj,
          type: "post",
          dataType: "json",
          // 发送信息至服务器时内容编码类型
          contentType: args.contentType,
          success: function (response) {
            var data = response.locationList;
            // 这一步是取得总页数跟总条数然后渲染分页，注册事件
            // 后端返回的总页数跟总条数
            if (args.pageKeyNameTotalPage && args.pageKeyNameTotalNum) {
              callback(data[args.pageKeyNameTotalPage], data[args.pageKeyNameTotalNum]);
            } else {
              alert("返回的总页数跟总条数不正确");
            }
            // 取得的数据，成功的回调
            args.successFn(response);
          }
        })
      })()
    }
  }
  $.fn.createPage = function (options) {
    var args = $.extend({
      url: '',
      data: {},
      // 发送信息至服务器时内容编码类型
      contentType: "application/x-www-form-urlencoded",
      // 总页数
      pageCount: 0,
      // 总条数
      totalNum: 0,
      // 当前页显示多少条数据
      pageSize: 7,
      // 当前页
      current: 1,
      // 后端返回来的总页数的参数
      pageKeyNameTotalPage: 'totalPage',
      // 后端返回来的总条数的参数
      pageKeyNameTotalNum: 'totalRows',
      // 成功的回调
      successFn: function () {

      }
    }, options);
    ms.init(this, args);
  }
})(jQuery);