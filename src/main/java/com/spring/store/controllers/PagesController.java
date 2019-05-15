package com.spring.store.controllers;

import com.spring.store.config.WebConfig;
import com.spring.store.dao.models.AdminModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PagesController {

    @RequestMapping("/")
    public void autoRedirect(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("location", "/login");
        response.setStatus(301);
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession() == null || request.getSession().getAttribute("user") == null) {
            response.addHeader("location", "/login");
            response.setStatus(301);
            return "";
        } else {
            HttpSession session = request.getSession();
            AdminModel user = (AdminModel) session.getAttribute("user");
            HashMap<String, Object> map = new HashMap();
            map.put("user", user);
            response.setStatus(200);
            return render(map, "index.ftl");
        }
    }

    @RequestMapping("/profile")
    public String profile(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession() == null || request.getSession().getAttribute("user") == null) {
            response.setStatus(301);
            return login();
        } else {
            HttpSession session = request.getSession();
            AdminModel user = (AdminModel) session.getAttribute("user");
            HashMap<String, Object> map = new HashMap();
            map.put("user", user);
            return render(map, "profile.ftl");
        }
    }

    @RequestMapping("/login")
    public String login() {
        return render(new HashMap(), "login.ftl");
    }

    @RequestMapping("/404")
    @ExceptionHandler(Throwable.class)
    public String blank() {
        return render(new HashMap(), "blank.ftl");
    }

    public String render(Map map, String filename) {
        // write the freemarker output to a StringWriter
        StringWriter stringWriter = new StringWriter();
        try {
            Configuration cfg = WebConfig.getConfiguration();
            //Create Data Model
            Template template = cfg.getTemplate(filename);
            template.process(map, stringWriter);
            map.clear();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

}
