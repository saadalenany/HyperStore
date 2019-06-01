<#include "header.ftl">

<!-- Page Container -->
<div class="w3-content w3-margin-top" style="max-width:1400px;">

  <!-- The Grid -->
  <div class="w3-row-padding">

    <!-- Left Column -->
      <div class="w3-white w3-text-grey w3-card-4">
        <div class="w3-third">
          <div class="flip-card">
            <div class="flip-card-inner">
              <#if visited??>
                      <div class="flip-card-front">
                        <img src="${visited.getBase64Image()}" alt="${visited.getName()}" style="width:400px;height:400px;">
                      </div>
                      <div class="flip-card-back">
                        <h1>${visited.getName()}</h1>
                        <button type="button" class="btn btn-default">Change Image</button>
                      </div>
                    </div>
                  </div>
                  <br>
                <div class="w3-container">
                  <#if visited.getAddress()??>
                      <p><i class="fa fa-home fa-fw w3-margin-right w3-large w3-text-teal"></i>${visited.getAddress()}</p>
                  </#if>
                  <p><i class="fa fa-envelope fa-fw w3-margin-right w3-large w3-text-teal"></i>${visited.getEmail()}</p>
                  <p><i class="fa fa-phone fa-fw w3-margin-right w3-large w3-text-teal"></i>${visited.getPhone()}</p>
              <#else>
                      <div class="flip-card-front">
                        <img src="${user.getBase64Image()}" alt="${user.getName()}" style="width:400px;height:400px;">
                      </div>
                      <div class="flip-card-back">
                        <h1>${user.getName()}</h1>
                        <button type="button" class="btn btn-default">Change Image</button>
                      </div>
                    </div>
                  </div>
                  <br>
                <div class="w3-container">
                  <#if user.getAddress()??>
                      <p><i class="fa fa-home fa-fw w3-margin-right w3-large w3-text-teal"></i>${user.getAddress()}</p>
                  </#if>
                  <p><i class="fa fa-envelope fa-fw w3-margin-right w3-large w3-text-teal"></i>${user.getEmail()}</p>
                  <p><i class="fa fa-phone fa-fw w3-margin-right w3-large w3-text-teal"></i>${user.getPhone()}</p>
              </#if>

          <br>
        </div>
      </div>
    <!-- End Left Column -->
    </div>

    <!-- Right Column -->
    <div class="w3-twothird">

		<!-- container -->
		<div class="container">
            <!-- row -->
            <div class="row">
        <#assign x=0>
        <#if visited??>
            <#list visited.getProducts() as product>
                <#if x%3=0>
                    </div>
                    <!-- /row -->
                    <!-- row -->
                    <div class="row">
                </#if>
                    <!-- Product Single -->
                    <div class="col-md-3 col-sm-6 col-xs-6">
                        <div class="product product-single">
                            <div class="product-thumb">
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
                <#assign x++>
            </#list>
        <#else>
              <#list user.getProducts() as product>
                <#if x%3=0>
                    </div>
                    <!-- /row -->
                    <!-- row -->
                    <div class="row">
                </#if>
                    <!-- Product Single -->
                    <div class="col-md-3 col-sm-6 col-xs-6">
                        <div class="product product-single">
                            <div class="product-thumb">
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
                <#assign x++>
            </#list>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
        </#if>

    <!-- End Right Column -->
    </div>

  <!-- End Grid -->
  </div>

  <!-- End Page Container -->
</div>

<#include "footer.ftl">
