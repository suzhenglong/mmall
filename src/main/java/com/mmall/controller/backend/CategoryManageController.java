package com.mmall.controller.backend;

import com.mmall.common.Common;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.03.30 15:32
 */
@RequestMapping("/manage/category")
@Controller
public class CategoryManageController {


    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;

    /**
     * 增加节点
     *
     * @param request
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {

        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        return categoryService.addCategory(categoryName, parentId);
    }

    /**
     * 修改品类名字
     *
     * @param request
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }

        return categoryService.setCategoryName(categoryId, categoryName);
    }

    /**
     * 获取品类子节点(平级) 不递归 保持平级
     *
     * @param request
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        return categoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 查询当期前节点的id及递归子节点的id
     *
     * @param request
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        return categoryService.selectCategoryAndChildrenById(categoryId);
    }

}
