package com.mmall.controller.backend;

import com.mmall.common.Common;
import com.mmall.common.ServerResponse;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
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
 * @date: 2018.04.11 15:02
 */
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse orderList(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return orderService.manageOrderList(pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse orderDetail(HttpServletRequest request, Long orderNo) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return orderService.manageOrderDetail(orderNo);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse orderSearch(HttpServletRequest request, Long orderNo,
                                      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return orderService.manageOrderSearch(orderNo, pageNum, pageSize);
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse sendGoods(HttpServletRequest request, Long orderNo) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return orderService.manageSendGoods(orderNo);
    }

}
