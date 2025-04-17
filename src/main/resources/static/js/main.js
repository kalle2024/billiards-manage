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