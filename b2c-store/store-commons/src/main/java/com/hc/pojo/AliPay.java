package com.hc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 何超
 * @date: 2022/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliPay {

    private String orderId;

    private double productPrice;

    private String productName;

    private String alipayTraceNo;
}
