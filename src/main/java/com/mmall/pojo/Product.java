package com.mmall.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
 /**
  * @Description:
  * @author: zhenglongsu@163.com
  * @date: 2018.04.13 10:24
  */
 @Setter
 @Getter
 @NoArgsConstructor
 @AllArgsConstructor
public class Product {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}