$(top.hangge());

function showCustomerDetail(userId) {
    showCustomer(userId);
}

function showCustomer(userId) {
    $("#div2").removeClass("hide");
    add_mask_body();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    document.getElementById("customerDetail").style.display = "block";
    $.ajax({
        "url": "friend/customerInfo",
        "type": "GET",
        "dataType": "json",
        cache: false,
        data: {
            userId: userId,
            startDate: startDate,
            endDate: endDate
        },
        "contentType": "application/json",
        success: function (data) {
            if (data != null) {
                if (data.customerInfo.image != null && data.customerInfo.image != 0) {
                    document.getElementById("userImage").src = data.customerInfo.image;
                } else {
                    document.getElementById("userImage").src = 'static/images/user_tx.jpg';
                }
                document.getElementById("name").innerHTML = "姓名：" + data.customerInfo.name;
                document.getElementById("tel").innerHTML = "手机号：" + data.customerInfo.tel;
                document.getElementById("customerCount").innerHTML = "总客户数：" + data.customerInfo.customerCount;
                document.getElementById("investCustomerCount").innerHTML = "总投资客户数：" + data.customerInfo.investCustomerCount;
                document.getElementById("investMoney").innerHTML = "总投资金额：" + data.customerInfo.investMoney + " 元";
                document.getElementById("totalYearInvestMoney").innerHTML = "总年化折标业绩：" + data.customerInfo.totalYearInvestMoney + " 元";
                if (data.customerInfo.type != null && data.customerInfo.type != 4 && data.customerInfo.type != 5) {
                    document.getElementById("teamInvestMoney").innerHTML = "团队投资业绩：" + data.customerInfo.teamInvestMoney + " 元";
                    document.getElementById("teamYearInvestMoney").innerHTML = "团队年化折标业绩：" + data.customerInfo.teamYearInvestMoney + " 元";
                } else {
                    document.getElementById("teamInvestMoney").innerHTML = "";
                    document.getElementById("teamYearInvestMoney").innerHTML = "";
                }
                if(startDate != '' || endDate != ''){
                    document.getElementById("newCustomerCount").innerHTML = "新增客户数：" + data.customerInfo.newCustomerCount;
                }else{
                    document.getElementById("newCustomerCount").innerHTML = "";
                }
                if ((startDate != '' || endDate != '') && data.customerInfo.type != 4 && data.customerInfo.type != 5) {
                    document.getElementById("newTeamCustomerCount").innerHTML = "团队新增客户数：" + data.customerInfo.newTeamCustomerCount;
                } else {
                    document.getElementById("newTeamCustomerCount").innerHTML = "";
                }
                $("#currentUser").val(data.customerInfo.userId);
            }
            remove_mask_body();
        }
    });
    $("#userId").val(userId);
    document.getElementById("customerListIframe").src = "friend/listFriend.do?userId=" + userId + "&startDate=" + startDate + "&endDate=" + endDate;
}

function searchCustomer() {
    var obj = window.parent.document.getElementsByName("customerTr");
    for (var i = obj.length - 1; i >= 0; i--) {
        obj[i].parentNode.removeChild(obj[i]);
    }
    var userId = $("#currentUser").val();
    var name_phone = $("#name_phone").val();
    if ((name_phone == '' || name_phone == undefined) && userId == '') {
        alert("请选择要查询的员工！")
        return;
    }
    if (name_phone != '' && name_phone != undefined) {
        $.ajax({
            "url": "friend/getUserByNameOrPhone",
            "type": "GET",
            "dataType": "json",

            cache: false,
            data: {
                name_phone: encodeURI(name_phone)
            },
            "contentType": "application/json",
            success: function (data) {
                if (data.userId != '-1') {
                    userId = data.userId;
                } else {
                }
                showCustomer(userId);
            }
        });
    } else {
        showCustomer(userId);
    }
}

function showFriendOrder(userId) {
    var startDate = window.parent.document.getElementById("startDate").value;
    var endDate = window.parent.document.getElementById("endDate").value;
    if (startDate != '' && endDate != '' && startDate > endDate) {
        alert("查询的开始时间不能大于结束时间");
        return;
    }
    var obj = document.getElementsByName("billOrderTrr");
    for (var i = obj.length - 1; i >= 0; i--) {
        obj[i].parentNode.removeChild(obj[i]);
    }
    $("#user_id").val(userId);
    $("#start_date").val(startDate);
    $("#end_date").val(endDate);
    var currPage = 1;
    if ($("#currentPage").val() != '') {
        currPage = $("#currentPage").val();
    }
    $("#currPage").val(currPage);
    document.getElementById("ORDER_FORM").submit();
}

//给body添加透明遮罩，不能点击
function add_mask_body() {
    var height_ = $(window).height();
    var html_ = '<div class="mask" style=" height:' + height_ + 'px;"></div>';
    $('body,html').prepend(html_).height(height_);
}

//给body移除透明遮罩，不能点击
function remove_mask_body() {
    $('.mask').remove();
    $("#div2").addClass("hide");
}

function showFriend(obj) {
    add_mask_body();
    var id = obj.name;
    $("#" + id + "Ul").html("");
    $.ajax({
        "url": "friend/getFriends",
        "type": "GET",
        "dataType": "json",
        cache: false,
        data: {
            id: id
        },
        "contentType": "application/json",
        success: function (data) {
            if (data != null) {
                var html = '';
                for (var i = 0; i < data.list.length; i++) {
                    html += '<li style="line-height: 42px;position: relative;z-index: 2;">';
                    html += '<a href="javascript:;" onclick="showCustomerDetail(' + data.list[i].USER_ID + ')">';
                    html += data.list[i].NAME;
                    if (data.list[i].TYPE == 5) {
                        html += "(兼职)";
                    }
                    html += '</a>';
                    if (data.list[i].TYPE != 5) {
                        html += '<a href="javascript:;" id="' + data.list[i].ID + 'span1" name="' + data.list[i].ID + '" style="position: absolute;z-index: 999;right: 8px;top: 0;display: block;" class="fa fa-angle-left pull-right" onclick="showFriend(this)">';
                        html += '<img style="width: 36px;height: 22px;" src="static/images/down.jpg">';
                        html += '</a>';
                        html += '<a href="javascript:;" id="' + data.list[i].ID + 'span2" name="' + data.list[i].ID + '" style="position: absolute;z-index: 999;right: 8px;top: 0;display: none;"  class="fa fa-angle-left pull-right" onclick="disappear(this)">';
                        html += '<img style="width: 36px;height: 22px;" src="static/images/up.jpg">';
                        html += '  </a><ul style="display: none" id="' + data.list[i].ID + 'Ul"></ul>';
                        html += '</li>';
                    }
                }
                $("#" + id + "Ul").append(html);
                remove_mask_body();
            }
        }
    });
    document.getElementById(id + 'Ul').style.display = "block";
    document.getElementById(id + "span1").style.display = "none";
    document.getElementById(id + "span2").style.display = "block";
}

function disappear(obj) {
    var id = obj.name;
    document.getElementById(id + "Ul").style.display = "none";
    document.getElementById(id + "span1").style.display = "block";
    document.getElementById(id + "span2").style.display = "none";
}

function showForm() {
    if ($("#logo").val() == 100) {
        var form = $("form")[0];
        document.body.removeChild(form);
        document.getElementById("ORDER_FORM").style.display = 'block';
    }
    var userId = window.parent.document.getElementById('userId').value;
    var startDate = window.parent.document.getElementById('startDate').value;
    var endDate = window.parent.document.getElementById('endDate').value;
    $("#UserId").val(userId);
    $("#startDate").val(startDate);
    $("#endDate").val(endDate);
}

function backToFriends() {
    var currPage = $("#currPage").val();
    var userId = window.parent.document.getElementById('currentUser').value;
    var startDate = window.parent.document.getElementById('startDate').value;
    var endDate = window.parent.document.getElementById('endDate').value;
    var name_phone = window.parent.document.getElementById('name_phone').value;
    if (name_phone.trim() != '') {
        searchFriends();
    } else {
        window.parent.document.getElementById("customerListIframe").src = "friend/listFriend.do?userId=" + userId + "&startDate=" + startDate + "&endDate=" + endDate + "&currentPage=" + currPage;
    }
}

function clearDate() {
    $("#startDate").val("");
    $("#endDate").val("");
    $("#name_phone").val("");
}

function searchFriends() {
    var user_id = $("#UserId").val();
    var name_phone = $("#name_phone").val();
    if (name_phone.trim() == '') {
        alert("请输入要查询的姓名或手机号");
        return;
    }
    add_mask_body();
    var startDate = window.parent.document.getElementById('startDate').value;
    var endDate = window.parent.document.getElementById('endDate').value;
    $.ajax({
        "url": "friend/getFriendByNameOrPhone",
        "type": "GET",
        "dataType": "json",
        cache: false,
        data: {
            user_id: user_id,
            name_phone: encodeURI(name_phone),
            startDate: startDate,
            endDate: endDate
        },
        "contentType": "application/json",
        success: function (data) {
            var tr = $("#customerTable").find("tr");
            var seePhone = $("#seePhone").val();
            for (var i = 1; i < tr.length; i++) {
                tr[i].remove();
            }
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html += '<tr name="customerTr">';
                html += '<td><a style="color:#ff9103" href="javascript:;"';
                html += 'onclick="showFriendOrder(' + data[i].userId + ')">' + data[i].name + '</a></td><td>';
                if (seePhone == 1) {
                    html += data[i].tel;
                }
                if (seePhone == -1 && data[i].tel.length == 11) {
                    html += data[i].tel.substring(0, 3) + "****" + data[i].tel.substring(8, 12);
                }
                html += '</td><td>' + data[i].investMoney + '</td><td width="30%">' + data[i].investCount + '<td>' + data[i].yearInvestMoney + '</td></tr>';
            }
            $("#customerDetailList").after(html);
            $("#page").html("");
            remove_mask_body();
        }
    });
}

function clearPhone() {
    $("#name_phone").val("");
}

function downAll() {
    var startDate = window.parent.document.getElementById('startDate').value;
    var endDate = window.parent.document.getElementById('endDate').value;
    window.location = '/friend/downAll.do?startDate=' + startDate + "&endDate=" + endDate;
}

function downPerson() {
    var startDate = window.parent.document.getElementById('startDate').value;
    var endDate = window.parent.document.getElementById('endDate').value;
    var name_phone = $("#name_phone").val();
    if(name_phone.trim() == ''){
        alert("请输入你要下载的员工姓名或者手机号！")
        return;
    }else{
        name_phone = encodeURI(encodeURI(name_phone));
    }
    window.location = '/friend/downPerson.do?startDate=' + startDate + "&endDate=" + endDate + "&name_phone=" + name_phone;
}