<#include "header.ftl">

	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="/home">Home</a></li>
				<li class="active">Sold Products</li>
			</ul>
		</div>
	</div>
	<!-- /BREADCRUMB -->

    <!-- section -->
    <div class="section">
        <!-- container -->
        <div class="container">

            <!-- store top filter -->
            <div class="store-filter clearfix">
                <div class="pull-right">
                    <ul class="store-pages">
                        <#if page == 1>
                            <li class="prev disabled"> ← Previous</li>
                        <#else>
                            <li class="prev"><a href="/sold/${user.getId()}?page=${page-1}">← Previous</a></li>
                        </#if>
                        <li class="active"><a href="#">${page}</a></li>
                        <#if page gte size>
                            <li class="next disabled">Next → </li>
                        <#else>
                            <li class="next"><a href="/sold/${user.getId()}?page=${page+1}">Next → </a></li>
                        </#if>
                    </ul>
                </div>
            </div>
            <!-- /store top filter -->
            <br>

            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th></th>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Address</th>
                            <th>Zip Code</th>
                            <th>Phone</th>
                            <th>MAC Address</th>
                            <th>Checked out</th>
                            <th>Quantity</th>
                            <th>Shipping</th>
                            <th>Payment</th>
                            <th>Price</th>
                            <th>Paid</th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list payments as payment>
                        <tr>
                            <td class="thumb"><img src="${payment.getProductModel().getBase64Image()}" width="60px" height="60px" alt="${payment.getProductModel().getName()}"></td>
                            <td class="details">
                                <a href="/by_product/${payment.getProductModel().getId()}">${payment.getProductModel().getName()}</a>
                                <ul>
                                    <div class="product-rating">
                                        <#list 1..payment.getProductModel().getRate() as i>
                                            <i class="fa fa-star"></i>
                                        </#list>
                                        <#if payment.getProductModel().getRate() != 5>
                                            <#list 1..5-payment.getProductModel().getRate() as i>
                                                <i class="fa fa-star-o empty"></i>
                                            </#list>
                                        </#if>
                                    </div>
                                </ul>
                            </td>
                            <td>${payment.getFirstName()}</td>
                            <td>${payment.getLastName()}</td>
                            <td>${payment.getAddress()}</td>
                            <td>${payment.getZipCode()}</td>
                            <td>${payment.getPhone()}</td>
                            <td>${payment.getBuyer()}</td>
                            <td>${payment.getBuyDate()}</td>
                            <td>${payment.getQuantity()}</td>
                            <td>${payment.getShipping()}</td>
                            <td>${payment.getPayment()}</td>
                            <td>${payment.getPrice()}</td>
                            <#if payment.getPaid() == 1>
                                <td>Paid</td>
                            <#else>
                                <td>Not Paid</td>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <br>

            <!-- store top filter -->
            <div class="store-filter clearfix">
                <div class="pull-right">
                    <ul class="store-pages">
                        <#if page == 1>
                            <li class="prev disabled"> ← Previous</li>
                        <#else>
                            <li class="prev"><a href="/sold/${user.getId()}?page=${page-1}">← Previous</a></li>
                        </#if>
                        <li class="active"><a href="#">${page}</a></li>
                        <#if page gte size>
                            <li class="next disabled">Next → </li>
                        <#else>
                            <li class="next"><a href="/sold/${user.getId()}?page=${page+1}">Next → </a></li>
                        </#if>
                    </ul>
                </div>
            </div>
            <!-- /store top filter -->
            <br>

        </div>
        <!-- /container -->
    </div>
    <!-- /section -->

<#include "footer.ftl">
