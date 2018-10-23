package com.mmall.controller.portal;

import com.mmall.common.Common;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.03 16:16
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    /**
     * 添加地址
     *
     * @param request
     * @param shipping
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest request, Shipping shipping) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return shippingService.add(user.getId(), shipping);
    }

    /**
     * 删除地址
     *
     * @param request
     * @param shippingId
     * @return
     */
    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpServletRequest request, Integer shippingId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return shippingService.del(user.getId(), shippingId);
    }

    /**
     * 登录状态更新地址
     *
     * @param request
     * @param shipping
     * @return
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpServletRequest request, Shipping shipping) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return shippingService.update(user.getId(), shipping);
    }

    /**
     * 选中查看具体的地址
     *
     * @param request
     * @param shippingId
     * @return
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(HttpServletRequest request, Integer shippingId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return shippingService.select(user.getId(), shippingId);

    }

    /**
     * 地址列表
     *
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return shippingService.list(user.getId(), pageNum, pageSize);

    }
}
