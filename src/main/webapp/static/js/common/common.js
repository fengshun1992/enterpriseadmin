/**
 * 初始化时间区间控件
 * @param $dateRange JQ对象
 * @param clearCall clear按钮触发
 */
function initDateRangePicker($dateRange, clearCall) {
    $dateRange.daterangepicker({
        timePicker: true,
        timePickerIncrement: 30,
        format: 'YYYY/MM/DD H:mm:ss',
        locale: {
            cancelLabel: 'Clear'
        }
    });
    $dateRange.on('cancel.daterangepicker', function () {
        $(this).val('');
        if (clearCall) {
            clearCall();
        }
    });
}


/**
 * 禁止按钮
 */
function forbidButton(id) {
    $("#" + id).removeAttr("onclick"); //禁用按钮
}

/**
 * 按钮还原
 */
function recoverybutton(id,method){
    $("#"+id).attr("onclick",method);
}


function addColorOdd(obj) {
    obj.find('tr').each(function (i) {
        if (i % 2 != 0) {
            $(this).css("background-color", "#f9f9f9");
        }
    })
}


/**
 * 行增加点击事件变换色差
 */
$(function () {
    var size = $('table').length;
    $('table').each(function (i) {
        var obj;
        var objColor
        if (size > 1) {
            if (i > 0) {
                $(this).removeClass("table-hover");
                $(this).removeClass("table-striped");
                addColorOdd($(this));
                $(this).find('tr').bind("click", function () {
                    if (obj != null && obj != $(this)) {
                        obj.css("background-color", objColor);
                    }
                    objColor = $(this).css("background-color");
                    obj = $(this);
                    $(this).css("background-color", "#ddd");
                });
            }
        } else {
            $(this).removeClass("table-hover");
            $(this).removeClass("table-striped");
            addColorOdd($(this));
            $(this).find('tr').bind("click", function () {
                if (obj != null && obj != $(this)) {
                    obj.css("background-color", objColor);
                }
                objColor = $(this).css("background-color");
                obj = $(this);
                $(this).css("background-color", "#ddd");
            });
        }
    });

    $('table').hover(function(){
        $('table').css("cursor","pointer");
    },function(){
        $('table').css("cursor","default");
    });
})