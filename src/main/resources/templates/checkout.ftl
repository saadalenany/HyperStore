<#include "header.ftl">

	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="/home">Home</a></li>
				<li class="active">Checkout</li>
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
				<form id="checkout-form" class="clearfix">
					<div class="col-md-6">
						<div class="billing-details">
							<div class="section-title">
								<h3 class="title">Billing Details</h3>
							</div>
							<div class="form-group">
								<input class="input" type="text" name="first-name" placeholder="First Name">
							</div>
							<div class="form-group">
								<input class="input" type="text" name="last-name" placeholder="Last Name">
							</div>
							<div class="form-group">
								<input class="input" type="email" name="email" placeholder="Email">
							</div>
                            <script src="/js/location.js"></script>
                            <div class="group">
    							<div class="form-group">
                                    <select class="input" style="display: block; height: 40px;" onchange="set_country(this,country,city_state)" size="1" name="region">
                                        <option value="" disabled selected>SELECT REGION</option>
                                        <script type="text/javascript">
                                            setRegions(this);
                                        </script>
                                    </select>
                                </div>
    							<div class="form-group">
                                    <select class="input" style="display: block; height: 40px;" name="country" size="1" disabled="disabled" onchange="set_city_state(this,city_state)">
                                    </select>
                                </div>
    							<div class="form-group">
                                    <select class="input" style="display: block; height: 40px;" name="city_state" size="1" disabled="disabled" onchange="print_city_state(country,this)">
                                    </select>
                                </div>
                                <input type="hidden" name="region" id="txtregion"></input>
                                <input type="hidden" name="place" id="txtplacename"></input>
                            </div>
							<div class="form-group">
								<input class="input" type="text" name="zip-code" placeholder="ZIP Code">
							</div>
							<div class="form-group">
								<input class="input" type="tel" name="tel" placeholder="Telephone">
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="shiping-methods">
							<div class="section-title">
								<h4 class="title">Shipping Methods</h4>
							</div>
							<div class="input-checkbox">
								<input type="radio" name="shipping" id="shipping-1" value="free" checked>
								<label for="shipping-1">Free Shipping -  $0.00</label>
								<div class="caption">
									<p>You can go to the market by your self & receive the product from there.<p>
								</div>
							</div>
							<div class="input-checkbox">
								<input type="radio" name="shipping" id="shipping-2" value="standard">
								<label for="shipping-2">Standard - $4.00</label>
								<div class="caption">
									<p>A Delivery Man will deliver you the product directly at home.<p>
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-12">
						<div class="order-summary clearfix">
							<div class="section-title">
								<h3 class="title">Order Review</h3>
							</div>
							<table class="shopping-cart-table table">
							<#if products??>
								<thead>
									<tr>
										<th>Product</th>
										<th></th>
										<th class="text-center">Price</th>
										<th class="text-center">Quantity</th>
										<th class="text-center">Total</th>
										<th class="text-right"></th>
									</tr>
								</thead>
								<tbody id="checkout_table">
								<#assign index=0>
                                <#list products as product>
									<tr>
										<td class="thumb"><img src="${product.getBase64Image()}" alt="${product.getName()}"></td>
										<td class="details">
											<a href="/by_product/${product.getId()}">${product.getName()}</a>
											<ul>
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
											</ul>
										</td>
                                        <#assign x = product.getPrice() - product.getPrice() * product.getDiscountPrice() / 100>
										<td class="price text-center"><strong>$${x}</strong><br><del class="font-weak"><small>$${product.getPrice()}</small></del></td>
										<td class="qty text-center"><strong>$${product.getQuantity()}</strong></td>
										<#assign sub_tot = x * product.getQuantity()>
										<td class="total text-center"><strong class="primary-color">$${sub_tot}</strong></td>
										<td class="text-right">
										    <button class="main-btn icon-btn" onclick="deleteProductFromCheckout('${product.getId()}', ${index}, ${sub_tot}, ${total})"><i class="fa fa-close"></i></button>
                                        </td>
									</tr>
									<#assign index = index + 1>
                                </#list>
								</tbody>
								<tfoot>
									<tr>
										<th class="empty" colspan="3"></th>
										<th>TOTAL</th>
										<th colspan="2" class="total" id="total_price">$${total}</th>
									</tr>
								</tfoot>
                            <#else>
                                No Orders made.
                            </#if>
							</table>
							<div class="pull-right">
								<button class="primary-btn">Place Order</button>
							</div>
						</div>

					</div>
				</form>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

<#include "footer.ftl">
