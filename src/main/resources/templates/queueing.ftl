<#include "header.ftl">

	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li class="active">Queueing</li>
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

            <#if errorMessage??>
                <br>
                <div style="width: 75%;margin: 0 auto;" class="alert alert-danger alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error!</strong>  ${errorMessage}
                </div>
            </#if>

            <#if wq??>
                <br>
                <div style="width: 75%;margin: 0 auto;" class="alert alert-info alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>WQ!</strong>  ${wq}
                </div>
            </#if>

            <#if ws??>
                <br>
                <div style="width: 75%;margin: 0 auto;" class="alert alert-info alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>WS!</strong>  ${ws}
                </div>
            </#if>

            <#if ls??>
                <br>
                <div style="width: 75%;margin: 0 auto;" class="alert alert-info alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>LS!</strong>  ${ls}
                </div>
            </#if>

            <#if lq??>
                <br>
                <div style="width: 75%;margin: 0 auto;" class="alert alert-info alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>LQ!</strong>  ${lq}
                </div>
            </#if>

			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

<#include "footer.ftl">