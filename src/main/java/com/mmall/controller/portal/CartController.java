package com.mmall.controller.portal;

import com.mmall.common.Common;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.02 18:29
 */
@RequestMapping("/cart/")
@Controller
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 购物车添加商品
     *
     * @param request
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer count, Integer productId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.add(user.getId(), productId, count);
    }

    /**
     * 更新购物车某个产品数量
     *
     * @param request
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer count, Integer productId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.update(user.getId(), productId, count);
    }

    /**
     * 移除购物车某个产品
     *
     * @param request
     * @param productIds
     * @return
     */
    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request, String productIds) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.deleteProduct(user.getId(), productIds);
    }

    /**
     * 购物车List列表
     *
     * @param request
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpServletRequest request) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.list(user.getId());
    }

    /**
     * 购物车全选
     *
     * @param request
     * @return
     */
    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.selectOrSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    /**
     * 购物车取消全选
     *
     * @param request
     * @return
     */
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> UnSelectAll(HttpServletRequest request) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.selectOrSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    /**
     * 购物车选中某个商品
     *
     * @param request
     * @param productId
     * @return
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVo> select(HttpServletRequest request, Integer productId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.selectOrSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    /**
     * 购物车取消选中某个商品
     *
     * @param request
     * @param productId
     * @return
     */
    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVo> UnSelect(HttpServletRequest request, Integer productId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.selectOrSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request, Integer productId) {
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            return serverResponse;
        }
        User user = (User) serverResponse.getData();
        return cartService.getCartProductCount(user.getId());
    }
}
