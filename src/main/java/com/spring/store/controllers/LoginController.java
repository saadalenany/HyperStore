package com.spring.store.controllers;

import com.spring.store.dao.models.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class LoginController {

    @Autowired
    private AdminController adminController;

    @Autowired
    private PagesController pageController;

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        AdminModel user = adminController.getByEmailAndPassword(email, pass).getBody();

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
    public void checkSignUp(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image") MultipartFile image) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String phone = request.getParameter("phone");

        String location = request.getParameter("region") + ", " + request.getParameter("place");

        AdminModel user = new AdminModel();
        user.setName(name);
        user.setPassword(pass);
        user.setEmail(email);
        user.setPhone(phone);
        try {
            user.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setAddress(location);
        user.setEmailVerifiedAt(LocalDateTime.now());

        user = adminController.post(user).getBody();

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
