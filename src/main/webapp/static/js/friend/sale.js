function saveSale() {
    var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
    if ($("#tel").val() == "") {
        $("#tel").tips({
            side: 3,
            msg: '输入手机号',
            bg: '#AE81FF',
            time: 3
        });
        $("#tel").focus();
        return false;
    } else if ($("#tel").val().length != 11 && !myreg.test($("#tel").val())) {
        $("#tel").tips({
            side: 3,
            msg: '手机号格式不正确',
            bg: '#AE81FF',
            time: 3
        });
        $("#tel").focus();
        return false;
    }
    if ($("#name").val() == "") {
        $("#name").tips({
            side: 3,
            msg: '输入姓名',
            bg: '#AE81FF',
            time: 3
        });
        $("#name").focus();
        return false;
    }
    if ($("#saleType").val() == -2) {
        $("#saleType").tips({
            side: 3,
            msg: '请选择职位',
            bg: '#AE81FF',
            time: 2
        });
        $("#saleType").focus();
        return false;
    }
    if ($("#upper_order").val() == -2) {
        $("#upper_order").tips({
            side: 3,
            msg: '请选择上级',
            bg: '#AE81FF',
            time: 2
        });
        $("#upper_order").focus();
        return false;
    }
    document.getElementById("saleForm").submit();
    $("#zhongxin").hide();
    $("#zhongxin2").show();
}

function showUpperOrder(obj) {
    var type = obj.value;
    $.ajax({
        "url": "sales/getUpper",
        "type": "GET",
        "dataType": "json",
        cache: false,
        data: {
            type: type
        },
        "contentType": "application/json",
        success: function (data) {
            if (data != null) {
                var upper_order = $("#upper_order");
                upper_order[0].options.length = 0;
                for (var i = 0; i < data.list.length; i++) {
                    var option = new Option(data.list[i].NAME,data.list[i].ORDER_NUM);
                    upper_order.append(option);
                }
            }
        }
    });
}

function search(){
    top.jzts();
    $("#sakesForm").submit();
}