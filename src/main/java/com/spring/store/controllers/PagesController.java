package com.spring.store.controllers;

import com.spring.store.config.WebConfig;
import com.spring.store.dao.models.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    @Autowired
    private PaymentController paymentController;

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

    @RequestMapping("/profile/{username}")
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

    @RequestMapping("/profile/save")
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

        if ((request.getParameter("region") != null && !request.getParameter("region").isEmpty()) &
                (request.getParameter("place") != null && !request.getParameter("place").isEmpty())) {
            user.setAddress(request.getParameter("region") + ", " + request.getParameter("place"));
        } else {
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

    @RequestMapping("/create_product")
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

    @RequestMapping("/product/save")
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

    @RequestMapping("/products")
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

    @RequestMapping("/by_product/{product_id}")
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

    @RequestMapping({"/categories", "/categories/{catId}"})
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

    @RequestMapping("/search")
    public String search(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                         @RequestParam(required = false, name = "category", defaultValue = "1") String category,
                         @RequestParam(required = false, name = "name", defaultValue = "") String name,
                         HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
        }
        if (session.getAttribute("categories") != null) {
            map.put("categories", session.getAttribute("categories"));
        } else {
            List<CategoryModel> categories = categoryController.list().getBody();
            map.put("categories", categories);
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
        map.put("page", page);
        map.put("categoryId", category);
        map.put("name", name);
        map.put("size", size);
        response.setStatus(200);
        return render(map, "search.ftl");
    }

    @RequestMapping("/checkout")
    public String checkout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
        }
        if (session.getAttribute("categories") != null) {
            map.put("categories", session.getAttribute("categories"));
        } else {
            List<CategoryModel> categories = categoryController.list().getBody();
            map.put("categories", categories);
        }

        String macAddress = getClientMacAddress();

        List<ProductModel> products;
        if (session.getAttribute(macAddress + " products") != null) {
            products = (List<ProductModel>) session.getAttribute(macAddress + " products");
            int total = 0;
            for (ProductModel p : products) {
                int discounted = p.getPrice() - ((p.getPrice() * p.getDiscountPrice()) / 100);
                total += (discounted * p.getReserved());
            }
            map.put("products", products);
            map.put("total", total);
        }

        return render(map, "checkout.ftl");
    }

    @RequestMapping("/save_payment")
    public void savePayment(HttpServletRequest request, HttpServletResponse response) {
        String firstName = null, lastName = null, email = null, phone = null, zipCode = null, address = null, shipping = null, payment = null;
        LocalDateTime buyDate = LocalDateTime.now();
        String result = "failure";
        if (request.getParameter("first_name") != null && !request.getParameter("first_name").isEmpty()) {
            firstName = request.getParameter("first_name");
        }
        if (request.getParameter("last_name") != null && !request.getParameter("last_name").isEmpty()) {
            lastName = request.getParameter("last_name");
        }
        if (request.getParameter("email") != null && !request.getParameter("email").isEmpty()) {
            email = request.getParameter("email");
        }
        if (request.getParameter("phone") != null && !request.getParameter("phone").isEmpty()) {
            phone = request.getParameter("phone");
        }
        if (request.getParameter("zip_code") != null && !request.getParameter("zip_code").isEmpty()) {
            zipCode = request.getParameter("zip_code");
        }
        if ((request.getParameter("region") != null && !request.getParameter("region").isEmpty()) &
                (request.getParameter("place") != null && !request.getParameter("place").isEmpty())) {
            address = request.getParameter("region") + ", " + request.getParameter("place");
        }
        if (request.getParameter("shipping") != null && !request.getParameter("shipping").isEmpty()) {
            shipping = request.getParameter("shipping");
        }
        if (request.getParameter("payment") != null && !request.getParameter("payment").isEmpty()) {
            payment = request.getParameter("payment");
        }

        String macAddress = getClientMacAddress();
        List<ProductModel> products = (List<ProductModel>) request.getSession().getAttribute(macAddress + " products");
        for (ProductModel productModel : products) {
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setFirstName(firstName);
            paymentModel.setLastName(lastName);
            paymentModel.setEmail(email);
            paymentModel.setPhone(phone);
            paymentModel.setZipCode(zipCode);
            paymentModel.setAddress(address);
            paymentModel.setShipping(shipping);
            paymentModel.setPayment(payment);

            paymentModel.setBuyer(macAddress);
            paymentModel.setBuyDate(buyDate);
            paymentModel.setProductId(productModel.getId());
            paymentModel.setQuantity(productModel.getReserved());

            int discounted = productModel.getPrice() - ((productModel.getPrice() * productModel.getDiscountPrice()) / 100);
            paymentModel.setPrice((discounted * productModel.getReserved()));
            paymentModel.setPaid(0);

            ResponseEntity<PaymentModel> posted = paymentController.post(paymentModel);
            if (posted.getBody() != null) {
                result = "success";
            }
        }

        response.setHeader("Location", "/checkout_result/" + result);
        response.setStatus(302);
    }

    @RequestMapping("/checkout_result/{result}")
    public String checkoutSuccess(@PathVariable(name = "result") String result,
                                  HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
        }
        if (session.getAttribute("categories") != null) {
            map.put("categories", session.getAttribute("categories"));
        } else {
            List<CategoryModel> categories = categoryController.list().getBody();
            map.put("categories", categories);
        }
        map.put("result", result);
        return render(map, "checkout_result.ftl");
    }

    @RequestMapping("/sold/{adminId}")
    public String soldProducts(@PathVariable(name = "adminId") String adminId,
                               @RequestParam(required = false, name = "page", defaultValue = "1") int page,
                               @RequestParam(required = false, name = "paymentId") String paymentId,
                               HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap();
        if (session.getAttribute("categories") != null) {
            map.put("categories", session.getAttribute("categories"));
        } else {
            List<CategoryModel> categories = categoryController.list().getBody();
            map.put("categories", categories);
        }
        if (session.getAttribute("user") != null) {
            AdminModel user = (AdminModel) session.getAttribute("user");
            user = adminController.get(user.getId()).getBody();
            map.put("user", user);
        } else {
            return blank(map);
        }

        if (paymentId != null && !paymentId.isEmpty()) {
            boolean result = false;
            PaymentModel paymentModel = paymentController.get(paymentId).getBody();
            paymentModel.setPaidDate(LocalDateTime.now());
            paymentModel.setPaid(1);
            PaymentModel body = paymentController.post(paymentModel).getBody();
            if (body != null) {
                result = true;
            }
            map.put("result", result);
        }

        List<PaymentModel> payments = paymentController.getByAdminAsPage(adminId, page - 1, 10).getBody();

        assert payments != null;
        payments.forEach(pay -> {
            pay.setProductModel(productController.get(pay.getProductId()).getBody());
        });

        double sizeInt = Objects.requireNonNull(paymentController.getByAdmin(adminId).getBody()).size();
        double size = Math.ceil(sizeInt / 10.0);

        if (page <= 0 || page > size) {
            page = 1;
        }

        map.put("payments", payments);
        map.put("page", page);
        map.put("size", size);
        response.setStatus(200);
        return render(map, "sold_products.ftl");
    }

    @RequestMapping("/queueing")
    public String queueing() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        //retrieve the given values
        double lambda = paymentController.getSubmittedByLastHour().getBody().size();
        double mu = paymentController.getApprovedByLastHour().getBody().size();
        double c = adminController.getSellersOfLastHour().getBody().size();

        //start equating
        double p = lambda / mu;
        double utilizationFactor = p / c;

        if (utilizationFactor > 1) {
            map.put("errorMessage", "Utilization Factor was greater than 1");
        }

        double p0 = 0;
        for (int i = 0; i < c; i++) {
            p0 += Math.pow(p, i) / factorial(i);
        }
        p0 += Math.pow(p, c) / (factorial(c) * (1 - utilizationFactor));
        p0 = Math.pow(p0, -1);

        double lq = (Math.pow(p, (c + 1)) / (factorial(c - 1) * Math.pow((c - p), 2))) * p0;
        double ls = lq + p;
        double wq = lq / lambda;
        double ws = wq + (1 / mu);

        if (lq < 0) {
            lq *= -1;
        }
        if (ls < 0) {
            ls *= -1;
        }
        if (wq < 0) {
            wq *= -1;
        }
        if (ws < 0) {
            ws *= -1;
        }

        String wqStr = "Waiting time at queue " + wq + " hours";
        String wsStr = "Waiting time at system " + ws + " hours";
        String lsStr = "Num of customers at system " + ls + " customers";
        String lqStr = "Num of customers at queue " + lq + " customers";

        map.put("wq", wqStr);
        map.put("ws", wsStr);
        map.put("ls", lsStr);
        map.put("lq", lqStr);
        return render(map, "queueing.ftl");
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

    private String render(Map map, String filename) {
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

    private String getClientMacAddress() {
        String macAddress = "";
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            macAddress = sb.toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    private double factorial(double n) {
        double fact = 1;
        for (double i = 2.0; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
}
