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

    <br/>
    <#if result == "success">
        <div class="alert alert-success" style="width: 50%;margin: 0 auto;">
            <strong>Success!</strong> Checkout sent successfully to the Queue.
        </div>
    <#else>
        <div class="alert alert-danger" style="width: 50%;margin: 0 auto;">
            <strong>Failure!</strong> Some error occurred during sending checkout data, Try placing Order later.
        </div>
    </#if>
    <br/>

<#include "footer.ftl">
