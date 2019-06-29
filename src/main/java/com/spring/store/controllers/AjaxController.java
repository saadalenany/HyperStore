package com.spring.store.controllers;

import com.google.gson.Gson;
import com.spring.store.dao.models.ProductModel;
import com.spring.store.dao.models.RatesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class AjaxController {

    @Autowired
    private ProductController productController;

    @Autowired
    private RatesController ratesController;

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

    @RequestMapping("/refresh_cart")
    public String refreshCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String macAddress = getClientMacAddress();

        List<ProductModel> products;
        if (session.getAttribute(macAddress + " products") != null) {
            products = (List<ProductModel>) session.getAttribute(macAddress + " products");
        } else {
            products = new ArrayList<>();
            session.setAttribute(macAddress + " products", products);
        }

        Gson gson = new Gson();
        return gson.toJson(products);
    }

    @RequestMapping("/add_to_cart")
    public String addToCart(@RequestParam(name = "id") String id, @RequestParam(name = "qty") Integer qty, HttpServletRequest request, HttpServletResponse response) {

        ProductModel product = productController.get(id).getBody();
        assert product != null;
        product.setReserved(qty);

        String macAddress = getClientMacAddress();

        HttpSession session = request.getSession();
        List<ProductModel> products;
        if (session.getAttribute(macAddress + " products") != null) {
            products = (List<ProductModel>) session.getAttribute(macAddress + " products");
        } else {
            products = new ArrayList<>();
            session.setAttribute(macAddress + " products", products);
        }
        AtomicBoolean exists = new AtomicBoolean(false);
        products.forEach(pro -> {
            if (pro.getId().equals(id)) {
                pro.setReserved(qty);
                exists.set(true);
            }
        });
        if (!exists.get()) {
            products.add(product);
        }

        product.setBase64Image(product.getBase64Image());
        Gson gson = new Gson();
        return gson.toJson(product);
    }

    @RequestMapping("/delete_from_cart")
    public void deleteFromCart(@RequestParam(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        String macAddress = getClientMacAddress();

        HttpSession session = request.getSession();
        List<ProductModel> products;
        if (session.getAttribute(macAddress + " products") != null) {
            products = (List<ProductModel>) session.getAttribute(macAddress + " products");
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(id)) {
                    products.remove(i);
                }
            }
        }
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

}
