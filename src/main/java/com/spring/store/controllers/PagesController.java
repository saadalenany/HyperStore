package com.spring.store.controllers;

import com.spring.store.config.WebConfig;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.models.CategoryModel;
import com.spring.store.dao.models.ProductModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PagesController {

    @Autowired
    private AdminController adminController;

    @Autowired
    private ProductController productController;

    @Autowired
    private CategoryController categoryController;

    @RequestMapping({"/home", "/"})
    public String home(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            map.put("user", user);
        }
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);

        //TODO Products from the last 7 days
        List<ProductModel> latestProducts = productController.listByLastWeek().getBody();
        map.put("latestProducts", latestProducts);

        //TODO Products with Discount of 10% or higher
        List<ProductModel> discountedProducts = productController.listByDiscount(10).getBody();
        map.put("discountedProducts", discountedProducts);

        //TODO Products with rate 4 or above
        List<ProductModel> recommendedProducts = productController.listByRate(4).getBody();
        map.put("recommendedProducts", recommendedProducts);

        response.setStatus(200);
        return render(map, "index.ftl");
    }

    @RequestMapping("/{username}")
    public String profile(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            AdminModel user = (AdminModel) request.getSession().getAttribute("user");
            map.put("user", user);
            if (!user.getName().equals(username)) {
                AdminModel visitedUser = adminController.getByName(username).getBody();
                map.put("visited", visitedUser);
            }
            return render(map, "profile.ftl");
        } else {
            AdminModel visitedUser = adminController.getByName(username).getBody();
            map.put("visited", visitedUser);
            return render(map, "profile.ftl");
        }
    }

    @RequestMapping("/products")
    public String products(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            map.put("user", user);
        }
        response.setStatus(200);
        return render(map, "products.ftl");
    }

    @RequestMapping("/by_product/{product_id}")
    public String product(@PathVariable String product_id, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            map.put("user", user);
        }
        ProductModel productModel = productController.get(product_id).getBody();
        map.put("product", productModel);
        response.setStatus(200);
        return render(map, "product.ftl");
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
