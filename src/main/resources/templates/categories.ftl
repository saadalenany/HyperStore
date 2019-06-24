<#include "header.ftl">

	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="/home">Home</a></li>
				<li><a href="/categories/${category.getId()}?page=1">Categories</a></li>
				<li class="active">${category.getName()}</li>
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

				<!-- ASIDE -->
				<div id="aside" class="col-md-2">
                    <ul class="nav flex-column" role="tablist">
                        <#if categories??>
                            <#assign x=0>
                            <#list categories as cat>
                                <#if x==0 >
                                    <li class="nav-item">
                                        <a class="nav-link active" href="/categories/${cat.getId()}?page=1">${cat.getName()}</a>
                                    </li>
                                <#else>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/categories/${cat.getId()}?page=1">${cat.getName()}</a>
                                    </li>
                                </#if>
                                <#assign x++>
                            </#list>
                        </#if>
                    </ul>
				</div>
				<!-- /ASIDE -->

				<!-- MAIN -->
				<div id="main" class="col-md-10">

					<!-- store top filter -->
					<div class="store-filter clearfix">
						<div class="pull-right">
							<ul class="store-pages">
                                <#if page == 1>
                                    <li class="prev disabled"> ← Previous</li>
                                <#else>
                                    <li class="prev"><a href="/categories/${category.getId()}?page=${page-1}">← Previous</a></li>
                                </#if>
                                <li class="active"><a href="#">${page}</a></li>
                                <#if page gte size>
                                    <li class="next disabled">Next → </li>
                                <#else>
                                    <li class="next"><a href="/categories/${category.getId()}?page=${page+1}">Next → </a></li>
                                </#if>
							</ul>
						</div>
					</div>
					<!-- /store top filter -->

					<!-- STORE -->
					<div id="store">
						<!-- row -->
						<div class="row">

                        <#list products as product>
                            <!-- Product Single -->
                            <div class="col-md-4 col-sm-6 col-xs-6">
                                <div class="product product-single">
                                    <div class="product-thumb">
                                        <div class="product-label">
                                            <span>New</span>
                                        </div>
                                        <button class="main-btn quick-view"><i class="fa fa-search-plus"></i> <a href="/by_product/${product.getId()}">Quick view</a></button>
                                        <img src="${product.getBase64Image()}" alt="${product.getName()} Image" width="300" height="300">
                                    </div>
                                    <div class="product-body">
                                        <h3 class="product-price">$${product.getPrice()}</h3>
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
                                            <button onclick="addToCart(${product},1)" class="primary-btn add-to-cart"><i class="fa fa-shopping-cart"></i> Add to Cart</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /Product Single -->
                        </#list>

						</div>
						<!-- /row -->
					</div>
					<!-- /STORE -->

					<!-- store bottom filter -->
					<div class="store-filter clearfix">
						<div class="pull-right">
							<ul class="store-pages">
                                <#if page == 1>
                                    <li class="prev disabled"> ← Previous</li>
                                <#else>
                                    <li class="prev"><a href="/categories/${category.getId()}?page=${page-1}">← Previous</a></li>
                                </#if>
                                <li class="active"><a href="#">${page}</a></li>
                                <#if page gte size>
                                    <li class="next disabled">Next → </li>
                                <#else>
                                    <li class="next"><a href="/categories/${category.getId()}?page=${page+1}">Next → </a></li>
                                </#if>
							</ul>
						</div>
					</div>
					<!-- /store bottom filter -->

				</div>
				<!-- /MAIN -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

<#include "footer.ftl">
