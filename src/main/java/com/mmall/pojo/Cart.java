package com.mmall.pojo;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
 /**
  * @Description:
  * @author: zhenglongsu@163.com
  * @date: 2018.04.13 10:21
  */
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private Date createTime;

    private Date updateTime;

}