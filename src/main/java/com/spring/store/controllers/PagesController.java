package com.spring.store.controllers;

import com.spring.store.config.WebConfig;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.models.CategoryModel;
import com.spring.store.dao.models.ProductModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @RequestMapping("/profile/{username}")
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
            if (visitedUser == null) {
                return blank(new HashMap());
            }
            map.put("visited", visitedUser);
            return render(map, "profile.ftl");
        }
    }

    @RequestMapping("/profile/{username}/settings")
    public String settings(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            AdminModel user = (AdminModel) request.getSession().getAttribute("user");
            map.put("user", user);
            if (!user.getName().equals(username)) {
                return blank(map);
            }
            return render(map, "settings.ftl");
        } else {
            return blank(new HashMap());
        }
    }

    @RequestMapping(value = "/profile/save", method = RequestMethod.POST)
    public String saveAdmin(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image") MultipartFile image) {

        AdminModel user;
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            user = (AdminModel) request.getSession().getAttribute("user");
        } else {
            response.addHeader("location", "/404");
            return blank(new HashMap());
        }

        if (request.getParameter("email") != null && !request.getParameter("email").isEmpty()) {
            user.setEmail(request.getParameter("email"));
        }
        if (request.getParameter("pass") != null && !request.getParameter("pass").isEmpty()) {
            user.setPassword(request.getParameter("pass"));
        }
        if (request.getParameter("phone") != null && !request.getParameter("phone").isEmpty()) {
            user.setPhone(request.getParameter("phone"));
        }
        if (request.getParameter("address") != null && !request.getParameter("address").isEmpty()) {
            user.setAddress(request.getParameter("address"));
        }
        if (!image.isEmpty()) {
            try {
                if (image.getBytes().length != 0) {
                    user.setImage(image.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        user.setEmailVerifiedAt(LocalDateTime.now());

        user = adminController.put(user).getBody();

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.addHeader("location", "/profile/" + user.getName());
        return profile(user.getName(), request, response);
    }

    @RequestMapping("/create_product")
    public String createProduct(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            AdminModel user = (AdminModel) request.getSession().getAttribute("user");
            map.put("user", user);
            return render(map, "create_product.ftl");
        } else {
            return blank(new HashMap());
        }
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String saveProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image") MultipartFile image) {

        AdminModel user;
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            user = (AdminModel) request.getSession().getAttribute("user");
        } else {
            response.addHeader("location", "/404");
            return blank(new HashMap());
        }

        ProductModel product = new ProductModel();
        if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
            product.setName(request.getParameter("name"));
        }
        if (request.getParameter("price") != null && !request.getParameter("price").isEmpty()) {
            product.setPrice(Integer.parseInt(request.getParameter("price")));
        }
        if (request.getParameter("quantity") != null && !request.getParameter("quantity").isEmpty()) {
            product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        }
        if (request.getParameter("discount") != null && !request.getParameter("discount").isEmpty()) {
            product.setDiscountPrice(Integer.parseInt(request.getParameter("discount")));
        } else {
            product.setDiscountPrice(0);
        }
        if (request.getParameter("category") != null && !request.getParameter("category").isEmpty()) {
            product.setCategoryId(request.getParameter("category"));
        }
        if (!image.isEmpty()) {
            try {
                if (image.getBytes().length != 0) {
                    product.setImage(image.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        product.setCreationDate(LocalDateTime.now());
        product.setAdminId(user.getId());
        product.setRate(1);

        product = productController.post(product).getBody();

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.addHeader("location", "/by_product/" + product.getId());
        return product(product.getId(), request, response);
    }

    @RequestMapping("/products")
    public String products(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                           HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            map.put("user", user);
        }

        List<ProductModel> products = productController.listAsPage(page - 1, 9).getBody();

        double sizeInt = Objects.requireNonNull(productController.list().getBody()).size();
        double size = Math.ceil(sizeInt / 9.0);

        if (page <= 0 || page > size) {
            page = 1;
        }

        map.put("products", products);
        map.put("categories", categories);
        map.put("page", page);
        map.put("size", size);
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
        CategoryModel categoryModel = categoryController.get(productModel.getCategoryId()).getBody();
        AdminModel adminModel = adminController.get(productModel.getAdminId()).getBody();
        //TODO Products with rate 4 or above
        List<ProductModel> recommendedProducts = productController.listByRateAsPage(4, 0, 4, product_id).getBody();

        map.put("product", productModel);
        map.put("category", categoryModel);
        map.put("admin", adminModel);
        map.put("recommendedProducts", recommendedProducts);
        response.setStatus(200);
        return render(map, "product.ftl");
    }

    @RequestMapping({"/categories", "/categories/{catId}"})
    public String categories(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                             @PathVariable(required = false, name = "catId") String catId,
                             HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            map.put("user", user);
        }

        if (catId == null) {
            assert categories != null;
            catId = categories.get(0).getId();
        }
        List<ProductModel> products = productController.listByCategoryAsPage(catId, page - 1, 9).getBody();
        CategoryModel categoryModel = categoryController.get(catId).getBody();

        double sizeInt = Objects.requireNonNull(productController.listByCategory(catId).getBody()).size();
        double size = Math.ceil(sizeInt / 9.0);

        if (page <= 0 || page > size) {
            page = 1;
        }

        map.put("products", products);
        map.put("categories", categories);
        map.put("category", categoryModel);
        map.put("page", page);
        map.put("size", size);
        response.setStatus(200);
        return render(map, "categories.ftl");
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return render(new HashMap(), "login.ftl");
    }

    @RequestMapping("/404")
    @ExceptionHandler(Throwable.class)
    public String blank(Map map) {
        return render(map, "blank.ftl");
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
