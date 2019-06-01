<!-- row -->
<div class="row">
    <!-- section-title -->
    <div class="col-md-12">
        <div class="section-title">
            <h2 class="title">Recommended Products</h2>
            <div class="pull-right">
                <div class="product-slick-dots-1 custom-dots"></div>
            </div>
        </div>
    </div>
    <!-- /section-title -->

    <!-- Product Slick -->
    <div class="col-md-9 col-sm-6 col-xs-6">
        <div class="row">
            <div id="product-slick-1" class="product-slick">

                <#list recommendedProducts as product>
                    <!-- Product Single -->
                    <div class="product product-single">
                        <div class="product-thumb">
                            <div class="product-label">
                                <span>Rated</span>
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
                            <h2 class="product-name"><a href="/by_product/${product.getId()}">${product.getName()}</a></h2>
                            <div class="product-btns">
                                <button class="main-btn icon-btn"><i class="fa fa-heart"></i></button>
                                <button class="main-btn icon-btn"><i class="fa fa-exchange"></i></button>
                                <button class="primary-btn add-to-cart"><i class="fa fa-shopping-cart"></i> Add to Cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /Product Single -->
                </#list>

            </div>
        </div>
    </div>
    <!-- /Product Slick -->
</div>
<!-- /row -->
