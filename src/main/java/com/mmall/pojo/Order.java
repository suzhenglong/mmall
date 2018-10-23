package com.mmall.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
 /**
  * @Description:
  * @author: zhenglongsu@163.com
  * @date: 2018.04.13 10:21
  */
 @Setter
 @Getter
 @NoArgsConstructor
 @AllArgsConstructor
public class Order {
    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer shippingId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private Date updateTime;

   
}