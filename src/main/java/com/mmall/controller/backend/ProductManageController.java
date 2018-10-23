package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Common;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.01 14:09
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IFileService fileService;

    /**
     * 新增OR更新产品
     *
     * @param request
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 产品上下架
     *
     * @param request
     * @param productId 产品id
     * @param status    状态
     * @return
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return productService.setSaleStatus(productId, status);
    }

    /**
     * 产品详情
     *
     * @param request
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest request, Integer productId) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return productService.manageProductDetail(productId);
    }


    /**
     * 产品list
     *
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getlist(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return productService.getProductList(pageNum, pageSize);
    }

    /**
     * 产品搜索
     *
     * @param request
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        return productService.SearchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        ServerResponse serverResponse = Common.judgePermission(request, userService);
        if (serverResponse != null) {
            return serverResponse;
        }
        //处理我们的业务逻辑
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        Map fileMap = new HashMap<>();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        //富文本中对于返回值有自己的要求,使用simditor,所以按照simditor的要求返回
        // {
        //     "file_path": "http://img.happymmall.com/5fb239f2-0007-40c1-b8e6-0dc11b22779c.jpg",
        //         "msg": "上传成功",
        //         "success": true
        // }
        Map resultMap = Maps.newHashMap();
        ServerResponse serverResponse = Common.getUserServerResponse(request);
        if (!(serverResponse != null && serverResponse.isSuccess())) {
            resultMap.put("success", false);
            resultMap.put("msg", "户未登录,无法获取用户");
            return resultMap;
        }
        User user = (User) serverResponse.getData();
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请管理员登录");
            return resultMap;
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.setHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }
}
