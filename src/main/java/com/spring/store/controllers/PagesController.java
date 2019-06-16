package com.spring.store.controllers;

import com.spring.store.config.WebConfig;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.models.CategoryModel;
import com.spring.store.dao.models.ProductModel;
import com.spring.store.dao.models.RatesModel;
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

    @Autowired
    private RatesController ratesController;

    @RequestMapping({"/home", "/"})
    public String home(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
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

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            AdminModel user = (AdminModel) request.getSession().getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
            assert user != null;
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

    @GetMapping("/profile/{username}/settings")
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

    @PutMapping("/profile/save")
    public void saveAdmin(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image") MultipartFile image) {

        AdminModel user;
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            user = (AdminModel) request.getSession().getAttribute("user");
            user = adminController.get(user.getId()).getBody();
        } else {
            response.setHeader("Location", "/404");
            response.setStatus(302);
            return;
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

        response.setHeader("Location", "/profile/" + user.getName());
        response.setStatus(302);
    }

    @RequestMapping("/delete_user/{admin_id}")
    public void deleteAdmin(@PathVariable String admin_id, HttpServletRequest request, HttpServletResponse response) {
        adminController.delete(admin_id);

        response.setHeader("Location", "/login");
        response.setStatus(302);
    }

    @GetMapping("/create_product")
    public String createProduct(@RequestParam(required = false, name = "product") String product_id, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            AdminModel user = (AdminModel) request.getSession().getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
            if (product_id != null) {
                ProductModel productModel = productController.get(product_id).getBody();
                map.put("product", productModel);
            }
            return render(map, "create_product.ftl");
        } else {
            return blank(new HashMap());
        }
    }

    @PostMapping("/product/save")
    public void saveProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image") MultipartFile image) {

        AdminModel user;
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            user = (AdminModel) request.getSession().getAttribute("user");
            user = adminController.get(user.getId()).getBody();
        } else {
            response.setHeader("Location", "/404");
            response.setStatus(302);
            return;
        }

        ProductModel product = new ProductModel();
        if (request.getParameter("product") != null && !request.getParameter("product").isEmpty()) {
            product = productController.get(request.getParameter("product")).getBody();
        }
        if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
            product.setName(request.getParameter("name"));
        }
        if (request.getParameter("price") != null && !request.getParameter("price").isEmpty()) {
            product.setPrice(Integer.parseInt(request.getParameter("price").replace(",", "")));
        }
        if (request.getParameter("quantity") != null && !request.getParameter("quantity").isEmpty()) {
            product.setQuantity(Integer.parseInt(request.getParameter("quantity").replace(",", "")));
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

        if (request.getParameter("product") != null && !request.getParameter("product").isEmpty()) {
            product = productController.put(product).getBody();
        } else {
            product = productController.post(product).getBody();
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.setHeader("Location", "/by_product/" + product.getId());
        response.setStatus(302);
    }

    @GetMapping("/products")
    public String products(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                           HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
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

    @GetMapping("/by_product/{product_id}")
    public String product(@PathVariable String product_id, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        map.put("categories", categories);
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
            List<RatesModel> ratesModels = ratesController.getByProductAndAdmin(product_id, user.getId()).getBody();
            if (!ratesModels.isEmpty()) {
                map.put("rate", ratesModels.get(0));
            }
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

    @RequestMapping("/delete_product/{product_id}")
    public void deleteProduct(@PathVariable String product_id, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            session.setAttribute("user", user);
            productController.delete(product_id);
            response.setHeader("Location", "/profile/" + user.getName());
            response.setStatus(302);
        } else {
            response.setHeader("Location", "/404");
            response.setStatus(302);
        }
    }

    @GetMapping({"/categories", "/categories/{catId}"})
    public String categories(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                             @PathVariable(required = false, name = "catId") String catId,
                             HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
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

    @RequestMapping("/by_rate")
    public void updateRate(@RequestParam("admin_id") String admin_id,
                           @RequestParam("product_id") String product_id,
                           @RequestParam("rate") String rate) {

        Integer star = Integer.parseInt(rate);
        List<RatesModel> byProductAndAdmin = ratesController.getByProductAndAdmin(product_id, admin_id).getBody();

        if (!byProductAndAdmin.isEmpty()) {
            RatesModel ratesModel = byProductAndAdmin.get(0);
            ratesModel.setStar(star);
            ratesController.put(ratesModel);
        } else {
            RatesModel ratesModel = new RatesModel();
            ratesModel.setProductId(product_id);
            ratesModel.setAdminId(admin_id);
            ratesModel.setStar(star);
            ratesController.post(ratesModel);
        }

        List<RatesModel> byProduct = ratesController.getByProduct(product_id).getBody();
        int totalRates = 0;
        assert byProduct != null;
        for (RatesModel ratesModel : byProduct) {
            totalRates += ratesModel.getStar();
        }
        int productRate = Math.round(totalRates / byProduct.size());
        ProductModel productModel = productController.get(product_id).getBody();
        assert productModel != null;
        if (totalRates != 0) {
            productModel.setRate(productRate);
            productController.post(productModel);
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                         @RequestParam(required = false, name = "category", defaultValue = "1") String category,
                         @RequestParam(required = false, name = "name", defaultValue = "") String name,
                         HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        List<CategoryModel> categories = categoryController.list().getBody();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
        }

        List<ProductModel> products;
        double sizeInt;
        if (category.equals("0")) {
            products = productController.listByNameAsPage(name, page - 1, 9).getBody();
            sizeInt = Objects.requireNonNull(productController.listByName(name).getBody()).size();
        } else {
            products = productController.listByCategoryAndNameAsPage(category, name, page - 1, 9).getBody();
            sizeInt = Objects.requireNonNull(productController.listByCategoryAndName(category, name).getBody()).size();
        }

        double size = Math.ceil(sizeInt / 9.0);

        if (page <= 0 || page > size) {
            page = 1;
        }

        map.put("products", products);
        map.put("categories", categories);
        map.put("page", page);
        map.put("categoryId", category);
        map.put("name", name);
        map.put("size", size);
        response.setStatus(200);
        return render(map, "search.ftl");
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return render(new HashMap(), "login.ftl");
    }

    @GetMapping("/404")
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
