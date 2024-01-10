package com.programlearning.learning.pay.wxPay;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * 支付可以使用IJPay进行支付功能的整合来快速进行支付模块的开发
 * https://javen205.gitee.io/ijpay/
 */
public class wxPay {

    public ResponseEntity<Object> wxXcxPay(HttpServletRequest request){
        return ResponseEntity.ok("");
    }
}
