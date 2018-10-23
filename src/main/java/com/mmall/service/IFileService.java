package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.02 9:21
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

}
