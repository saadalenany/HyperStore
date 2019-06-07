<#include "header.ftl">

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <!--  Form Details -->
            <div class="product product-details clearfix">
                <div class="col-md-12">
                    <form action="/profile/save" method="post" enctype="multipart/form-data">
                        <div class="product-main-view" style="display: block; margin-left: auto; margin-right: auto;">
                            <div class="flip-card" style="display: block; margin: 0 auto;">
                                <div class="flip-card-inner">
                                <div class="flip-card-front">
                                    <img src="${user.getBase64Image()}" alt="${user.getName()}" style="width:400px;height:400px;">
                                </div>
                                <div class="flip-card-back">
                                    <h1>${user.getName()}</h1>
                                    <input type="file" name="image" class="custom-file-input" id="customFile" class="btn btn-default">
                                </div>
                            </div>
                        </div>
                        <br>
                        <p><code>*</code> to indicate mandatory fields</p><br/>
                        <div class="product-body">
                            <div class="form-group">
                                <label for="phone">Phone:*</label>
                                <input type="phone" class="form-control" id="phone" value="${user.getPhone()}" name="phone" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email address:*</label>
                                <input type="email" class="form-control" id="email" value="${user.getEmail()}" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="pwd">Password:*</label>
                                <input type="password" class="form-control" id="pwd" name="pass" value="${user.getPassword()}" required>
                            </div>
                            <div class="form-group">
                                <label for="country">Address:</label>
                                <#if user.getAddress()??>
                                    <input type="text" placeholder="Address" class="form-control" id="country" value="${user.getAddress()}" name="address">
                                <#else>
                                    <input type="text" placeholder="Address" class="form-control" id="country" name="address">
                                </#if>
                            </div>
                            <br/>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- /row -->
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<#include "footer.ftl">
