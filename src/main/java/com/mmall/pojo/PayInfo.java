package com.mmall.pojo;

import lombok.*;

import java.util.Date;

 /**
  * @Description:
  * @author: zhenglongsu@163.com
  * @date: 2018.04.13 11:16
  */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayInfo {
    private Integer id;

    private Integer userId;

    private Long orderNo;

    private Integer payPlatform;

    private String platformNumber;

    private String platformStatus;

    private Date createTime;

    private Date updateTime;

}