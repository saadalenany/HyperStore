package com.spring.store.controllers;

import com.spring.store.dao.models.AdminModel;
import com.spring.store.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PagesController pageController;

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        AdminModel user = adminService.getByEmailAndPassword(email, pass);

        if (user == null) {
            response.addHeader("location", "/login");
            response.setStatus(301);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.addHeader("location", "/home");
            response.setStatus(301);
        }
    }

    @RequestMapping(value = "/checkSignUp", method = RequestMethod.POST)
    public void checkSignUp(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String phone = request.getParameter("phone");

        String location = request.getParameter("region") + ", " + request.getParameter("place");
        String date = request.getParameter("date");

        AdminModel user = new AdminModel();
        user.setName(name);
        user.setPassword(pass);
        user.setEmail(email);
        user.setPhone(phone);

        user = adminService.save(user);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.addHeader("location", "/home");
        response.setStatus(301);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView("/404");
        mav.addObject("exception", e);
        //mav.addObject("errorcode", "404");
        return mav;
    }
}
