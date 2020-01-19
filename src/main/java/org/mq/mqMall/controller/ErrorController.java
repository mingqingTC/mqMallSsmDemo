package org.mq.mqMall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    // GET /mqMall/404
    @GetMapping("/404")
    public String notFound(HttpServletRequest request, Model model) {
        model.addAttribute("basePath", ControllerUtil.basePath(request));
        return "404";
    }

    // GET /mqMall/500
    @GetMapping("/500")
    public String serverError(HttpServletRequest request, Model model) {
        model.addAttribute("basePath", ControllerUtil.basePath(request));
        return "500";
    }
}
