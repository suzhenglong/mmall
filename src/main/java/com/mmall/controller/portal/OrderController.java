package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.mmall.common.Common;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.09 16:37
 */
@Controller
@RequestMapping("/order/")
@Slf4j
public class OrderController {


    @Autowired
    private IOrderService orderService;

    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(Long orderNo, HttpServletRequest request) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        String path = request.getSession().getServletContext().getRealPath("upload");

        return orderService.pay(orderNo, user.getId(), path);
    }

    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        for (Iterator iterator = requestParameterMap.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = requestParameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("支付宝回调,sign:{}.trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());
        //非常重要:验证回调的正确性,是不是支付宝发送的,并且还要避免重复通知
        params.remove("sign_type");
        try {
            boolean rsaCheckV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!rsaCheckV2) {
                return ServerResponse.createByErrorMessage("非法请求,不予通过,请速速离去");
            }

        } catch (AlipayApiException e) {
            log.error("支付宝回调验证异常", e);
        }
        // 验证这个数据
        ServerResponse serverResponse = orderService.aliCallBack(params);
        if (serverResponse.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }

        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(HttpServletRequest request, Long orderNo) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        serverResponse = orderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSuccess()) {
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }


    @RequestMapping("create.do")
    @ResponseBody
    public ServerResponse create(HttpServletRequest request, Integer shippingId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return orderService.createOrder(user.getId(), shippingId);
    }

    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpServletRequest request, Long orderNo) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return orderService.cancel(user.getId(), orderNo);
    }

    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpServletRequest request) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return orderService.getOrderCartProduct(user.getId());
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpServletRequest request, Long orderNo) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return orderService.getOrderDetail(user.getId(), orderNo);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return orderService.getOrderList(user.getId(), pageNum, pageSize);
    }


}
