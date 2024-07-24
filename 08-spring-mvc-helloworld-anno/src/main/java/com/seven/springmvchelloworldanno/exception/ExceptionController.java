package com.seven.springmvchelloworldanno.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Seven
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ArithmeticException.class, NullPointerException.class})
    public String test(Exception e, Model model) {
        model.addAttribute("exception", e); // 将异常类共享到request域中
        return "error";	// 转发到error视图
    }
}
