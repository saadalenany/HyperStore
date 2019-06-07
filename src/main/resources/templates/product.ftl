<#include "header.ftl">

	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="/home">Home</a></li>
				<li><a href="/categories/${category.getId()}?page=1">${category.getName()}</a></li>
				<li class="active">${product.getName()}</li>
			</ul>
		</div>
	</div>
	<!-- /BREADCRUMB -->

	<!-- section -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!--  Product Details -->
				<div class="product product-details clearfix">
					<div class="col-md-6">
						<div id="product-main-view">
							<div class="product-view" style="display: block; margin-left: auto; margin-right: auto;">
								<img src="${product.getBase64Image()}" alt="${product.getName()}" style="width: 30%; height: 30%; display: block; margin: 0 auto;">
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="product-body">
							<div class="product-label">
							<#if product.getDiscountPrice()??>
                                <#if product.getDiscountPrice() gt 0>
                                    <span>Discount</span>
                                    <span class="sale">-${product.getDiscountPrice()}%</span>
                                <#elseif product.getRate() gte 4>
                                    <span>Hot</span>
                                <#elseif 10 gte product.getQuantity()>
                                    <span>Limited</span>
                                </#if>
                            </#if>
							</div>
                            <h2 class="product-name"><strong>${product.getName()}</strong></h2>
							<#if product.getDiscountPrice() gt 0>
                                <#assign x = product.getPrice() - product.getPrice() * product.getDiscountPrice() / 100>
                                <h3 class="product-price">$${x} <del class="product-old-price">$${product.getPrice()} </del></h3>
                            <#else>
                                <h3 class="product-price">$${product.getPrice()} </h3>
                            </#if>
							<div>
							    <#if product.getRate()??>
                                    <#list 1..product.getRate() as i>
                                        <i class="fa fa-star"></i>
                                    </#list>
                                    <#if product.getRate() != 5>
                                        <#list 1..5-product.getRate() as i>
                                            <i class="fa fa-star-o empty"></i>
                                        </#list>
                                    </#if>
                                </#if>
							</div>
                            <br/>
							<p><strong>Availability:</strong> In Stock</p>
							<p><strong>Seller:</strong> <a href="/profile/${admin.getId()}">${admin.getName()}</a></p>

							<br/><hr/><br/>

							<div class="product-btns">
								<div class="qty-input">
									<span class="text-uppercase">QTY: </span>
									<input class="input" type="number">
								</div>
								<button class="primary-btn add-to-cart"><i class="fa fa-shopping-cart"></i> Add to Cart</button>
								<div class="pull-right">
									<button class="main-btn icon-btn"><i class="fa fa-heart"></i></button>
									<button class="main-btn icon-btn"><i class="fa fa-exchange"></i></button>
									<button class="main-btn icon-btn"><i class="fa fa-share-alt"></i></button>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- /Product Details -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

    <!-- section -->
    <div class="section">
        <!-- container -->
        <div class="container">

            <!-- row -->
            <div class="row">
                <!-- section title -->
                <div class="col-md-12">
                    <div class="section-title">
                        <h2 class="title">Picked for you</h2>
                    </div>
                </div>
                <!-- section title -->

                <#list recommendedProducts as product>
                    <!-- Product Single -->
                    <div class="col-md-3 col-sm-6 col-xs-6">
                        <div class="product product-single">
                            <div class="product-thumb">
                                <div class="product-label">
                                    <span>New</span>
                                    <#if product.getDiscountPrice() gt 0>
                                        <span class="sale">-${product.getDiscountPrice()}%</span>
                                    <#elseif product.getRate() gte 4>
                                        <span class="sale">Hot</span>
                                    <#elseif 10 gte product.getQuantity()>
                                        <span class="sale">Limited</span>
                                    </#if>
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

        </div>
        <!-- /container -->
    </div>
    <!-- /section -->

<#include "footer.ftl">
