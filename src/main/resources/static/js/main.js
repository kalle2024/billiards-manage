function jsonPost(api, jsonData, callback) {
    $.post({
        url: api,
        contentType: 'application/json',
        data: JSON.stringify(jsonData),
        success: function(response) {
            if (response.code == 200) {
                callback(response);
            } else if (response.code == 401) {
                layer.msg('当前未登录，请登录后再试', function() {
                    location.href='login.html'
                });
            } else {
                layer.msg(response.msg, {icon: 5});
            }
        },
        error: function (error) {
            layer.msg('系统异常', {icon: 5});
        }
    });
}

function jsonGet(api, callback) {
    $.get({
        url: api,
        contentType: 'application/json',
        success: function(response) {
            if (response.code == 200) {
                callback(response);
            } else if (response.code == 401) {
                layer.msg('当前未登录，请登录后再试', function() {
                    location.href='login.html'
                });
            } else {
                layer.msg(response.msg, {icon: 5});
            }
        },
        error: function (error) {
            layer.msg('系统异常', {icon: 5});
        }
    });
}

// 全局设置的cookie
function userInfo(callback) {
    jsonGet("/api/user/user-info", callback);
}

function deduceUserType(userType) {
    switch(userType) {
        case 0: return "普通用户";
        case 1: return "会员";
        case 2: return "员工";
        case -1: return "超级管理员";
    }
}

function formatDatetime(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}