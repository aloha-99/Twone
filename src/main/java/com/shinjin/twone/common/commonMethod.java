package com.shinjin.twone.common;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public class commonMethod {

    public static void setAttribute(Model model, String url, String msg) {
        model.addAttribute("url", url);
        model.addAttribute("msg", msg);
        return;
    }
    public static void setAttribute(HttpServletRequest request, String url, String msg) {
        request.setAttribute("url", url);
        request.setAttribute("msg", msg);
        return;
    }

}
