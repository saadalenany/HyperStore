<!-- row -->
<div class="row">
    <!-- section title -->
    <div class="col-md-12">
        <div class="section-title">
            <h2 class="title">Discounted Products</h2>
        </div>
    </div>
    <!-- section title -->

    <#list discountedProducts as product>
        <!-- Product Single -->
        <div class="col-md-4 col-sm-6 col-xs-6">
            <div class="product product-single">
                <div class="product-thumb">
                    <div class="product-label">
                        <span>Discount</span>
                        <span class="sale">-${product.getDiscountPrice()}%</span>
                    </div>
                    <button class="main-btn quick-view"><i class="fa fa-search-plus"></i> <a href="/by_product/${product.getId()}">Quick view</a></button>
                    <img src="${product.getBase64Image()}" alt="${product.getName()} Image" width="300" height="300">
                </div>
                <div class="product-body">
                    <#assign x = product.getPrice() - product.getPrice() * product.getDiscountPrice() / 100>
                    <h3 class="product-price">$${x} <del class="product-old-price">$${product.getPrice()} </del></h3>
                    <div class="product-rating">
                        <#list 1..product.getRate() as i>
                            <i class="fa fa-star"></i>
                        </#list>
                        <#if product.getRate() != 5>
                            <#list 1..5-product.getRate() as i>
                                <i class="fa fa-star-o empty"></i>
                            </#list>
                        </#if>
                    </div>
                    <h2 class="product-name"><a href="/by_product/${product.getId()}">${product.getName()}</a></h2>
                    <div class="product-btns">
                        <button onclick="addToCart('${product.getId()}',1)" class="primary-btn add-to-cart"><i class="fa fa-shopping-cart"></i> Add to Cart</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Product Single -->
    </#list>

</div>
<!-- /row -->
