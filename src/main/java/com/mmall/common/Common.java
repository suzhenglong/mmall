package com.mmall.common;

import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.01 14:12
 */
public class Common {

    public static ServerResponse judgePermission(HttpServletRequest request, IUserService userService) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        //校验是否是管理员
        if (!userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            return ServerResponse.createByErrorMessage("无权限操作,需管理员权限");
        }
        return null;
    }

    public static ServerResponse<User> getUserServerResponse(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return null;
    }

}
