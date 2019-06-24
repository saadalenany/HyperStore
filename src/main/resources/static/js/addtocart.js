function addToCart(id, qty) {

    var quantity = 0;
    if (parseInt(qty) == 0) {
        if (Number.isNaN(document.getElementById("qty").value)) {
            quantity = 1;
        } else {
            quantity = parseInt(document.getElementById("qty").value);
        }
    } else {
        quantity = 1;
    }
    console.log("id = "+id+" , quantity = "+quantity);
    $.ajax({
        type: 'GET',
        url: '/add_to_cart',
        data: "id="+id+"&qty="+quantity,
        success: function(data) {
            console.log('Add to cart succeeded.');
            refreshCart();
        },
        failure: function(data) {
            console.log('Add to cart failed.');
        }
    });

}

function refreshCart() {

    $.ajax({
        type: 'GET',
        url: '/refresh_cart',
        success: function(data) {
            console.log('Refresh succeeded.');
            document.getElementById("shopping-cart-list").innerHTML = "";
            var products = JSON.parse(data);
            console.log(Object.keys(products));
            if (products.length > 0) {
                for(let i = 0; i < products.length; i++){
                    var product_widget = document.createElement("DIV");
                    product_widget.className = "product product-widget";

                    var product_thumb = document.createElement("DIV");
                    product_thumb.className = "product-thumb";

                    var product_img = document.createElement("IMG");
                    product_img.src = products[i].base64Image;

                    product_thumb.appendChild(product_img);

                    var product_body = document.createElement("DIV");
                    product_body.className = "product-body";

                    var product_price = document.createElement("H3");
                    product_price.className = "product-price";
                    product_price.innerHTML = "$" + products[i].price;

                    var product_qty = document.createElement("SPAN");
                    product_qty.className = "qty";
                    product_qty.innerHTML = "  x" + products[i].quantity;

                    product_price.appendChild(product_qty);

                    var product_name = document.createElement("H2");
                    product_name.className = "product-name";

                    console.log(products[i].id);
                    var product_href = document.createElement("A");
                    product_href.href = "/by_product/" + products[i].id;
                    product_href.innerHTML = products[i].name;

                    product_name.appendChild(product_href);

                    product_body.appendChild(product_price);
                    product_body.appendChild(product_name);

                    var product_button = document.createElement("BUTTON");
                    product_button.className = "cancel-btn";

                    var product_i = document.createElement("I");
                    product_i.className = "fa fa-trash";

                    product_button.appendChild(product_i);

                    product_widget.appendChild(product_thumb);
                    product_widget.appendChild(product_body);
                    product_widget.appendChild(product_button);

                    document.getElementById("total_qty").innerHTML = products.length;
                    document.getElementById("shopping-cart-list").appendChild(product_widget);
                }
            }
        },
        failure: function(data) {
            console.log('Refresh failed.');
        }
    });

}
