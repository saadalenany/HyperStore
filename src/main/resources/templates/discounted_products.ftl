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
        <div class="col-md-3 col-sm-6 col-xs-6">
            <div class="product product-single">
                <div class="product-thumb">
                    <div class="product-label">
                        <span>Discount</span>
                        <span class="sale">-${product.getDiscountPrice()}%</span>
                    </div>
                    <button class="main-btn quick-view"><i class="fa fa-search-plus"></i> Quick view</button>
                    <img src="${product.getBase64Image()}" alt="${product.getName()} Image">
                </div>
                <div class="product-body">
                    <h3 class="product-price">${product.getPrice()} $</h3>
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
                    <h2 class="product-name"><a href="#">${product.getName()}</a></h2>
                    <div class="product-btns">
                        <button class="main-btn icon-btn"><i class="fa fa-heart"></i></button>
                        <button class="main-btn icon-btn"><i class="fa fa-exchange"></i></button>
                        <button class="primary-btn add-to-cart"><i class="fa fa-shopping-cart"></i> Add to Cart</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Product Single -->
    </#list>

</div>
<!-- /row -->