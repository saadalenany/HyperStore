<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

	<title>E-Shop</title>

	<!-- Google font -->
	<link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

	<!-- Bootstrap -->
	<link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css" />

	<!-- Slick -->
	<link type="text/css" rel="stylesheet" href="/css/slick.css" />
	<link type="text/css" rel="stylesheet" href="/css/slick-theme.css" />

	<!-- nouislider -->
	<link type="text/css" rel="stylesheet" href="/css/nouislider.min.css" />

	<!-- Font Awesome Icon -->
	<link rel="stylesheet" href="/css/font-awesome.min.css">

	<!-- Custom stylesheet -->
	<link type="text/css" rel="stylesheet" href="/css/style.css" />

	<!-- profile style -->
	<link type="text/css" rel="stylesheet" href="/css/profile-style.css" />

	<!-- Rating input -->
	<link type="text/css" rel="stylesheet" href="/css/rating-input.css" />

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

</head>

<body onload="refreshCart();">
	<!-- HEADER -->
	<header>
		<!-- header -->
		<div id="header">
			<div class="container">
				<div class="pull-left">
					<!-- Logo -->
					<div class="header-logo">
						<a class="logo" href="/home">
							<img src="/img/hyper_store.png" alt="E-Shop">
						</a>
					</div>
					<!-- /Logo -->

					<!-- Search -->
					<div class="header-search">
						<form action="/search?page=1">
							<input class="input search-input" type="text" name="name" placeholder="Enter your keyword">
							<select name="category" class="input search-categories">
								<option value="0">All Categories</option>
								<#if categories??>
                                    <#list categories as cat>
                                        <option value="${cat.getId()}">${cat.getName()}</option>
                                    </#list>
                                </#if>
							</select>
							<button class="search-btn"><i class="fa fa-search"></i></button>
						</form>
					</div>
					<!-- /Search -->
				</div>
				<div class="pull-right">
					<ul class="header-btns">
						<!-- Account -->
						<li class="header-account dropdown default-dropdown">
							<#if user??>
                                <a href="/profile/${user.getName()}">
                                    <div>
                                        <img src="${user.getBase64Image()}" width="40px" height="40px"/>
                                        <strong class="text-uppercase">${user.getName()}</strong>
                                    </div>
                                </a>
                            <#else>
    							<a href="/login">
                                    <div class="dropdown-toggle" role="button" data-toggle="dropdown" aria-expanded="true">
                                        <div class="header-btns-icon">
                                            <i class="fa fa-user-o"></i>
                                        </div>
                                        <strong class="text-uppercase">Join <i class="fa fa-caret-down"></i></strong>
                                    </div>
                                    <a href="/login" class="text-uppercase"> Login</a>
    							</a>
                            </#if>
						</li>
						<!-- /Account -->

						<!-- Cart -->
						<li class="header-cart dropdown default-dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
								<div class="header-btns-icon">
									<i class="fa fa-shopping-cart"></i>
									<span id="total_qty" name="total_qty" class="qty">0</span>
								</div>
								<strong class="text-uppercase">My Cart:</strong>
								<br>
							</a>
							<div class="custom-menu">
								<div id="shopping-cart">
									<div id="shopping-cart-list" class="shopping-cart-list">

									</div>
									<div class="shopping-cart-btns">
										<a href="/checkout"><button class="main-btn">Checkout</button></a>
									</div>
								</div>
							</div>
						</li>
						<!-- /Cart -->

						<li class="header-account dropdown default-dropdown">
							<#if user??>
                                <a href="/create_product">
                                    <div>
                                        <button type="button" class="btn btn-default btn-sm">Create Product</button>
                                    </div>
                                </a>
                            </#if>
						</li>

						<!-- Mobile nav toggle-->
						<li class="nav-toggle">
							<button class="nav-toggle-btn main-btn icon-btn"><i class="fa fa-bars"></i></button>
						</li>
						<!-- / Mobile nav toggle -->
					</ul>
				</div>
			</div>
			<!-- header -->
		</div>
		<!-- container -->
	</header>
	<!-- /HEADER -->

	<!-- NAVIGATION -->
	<div id="navigation">
		<!-- container -->
		<div class="container">
			<div id="responsive-nav">

				<!-- category nav -->
				<div class="category-nav show-on-click">
					<span class="category-header">Categories <i class="fa fa-list"></i></span>
					<ul class="category-list">
                        <#if categories??>
                            <#list categories as cat>
                                <li><a href="/categories/${cat.getId()}">${cat.getName()}</a></li>
                            </#list>
                        </#if>
					</ul>
				</div>
				<!-- /category nav -->

				<!-- menu nav -->
				<div class="menu-nav">
					<span class="menu-header">Menu <i class="fa fa-bars"></i></span>
					<ul class="menu-list">
						<li><a href="/home">Home</a></li>
						<li><a href="/categories">Categories</a></li>
						<li><a href="/products?page=1">Shop</a></li>
                    <#if user??>
						<li><a href="/sold/${user.getId()}?page=1">Sold products</a></li>
                    </#if>
					</ul>
				</div>
				<!-- menu nav -->
			</div>
		</div>
		<!-- /container -->
	</div>
	<!-- /NAVIGATION -->
