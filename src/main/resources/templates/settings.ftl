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
                                    <div class="custom-file mb-3">
                                        <input type="file" class="custom-file-input" id="customFile" name="image">
                                        <label class="custom-file-label" for="customFile">Choose Image</label>
                                    </div>
                                    <script>
                                        // Add the following code if you want the name of the file appear on select
                                        $(".custom-file-input").on("change", function() {
                                            var fileName = $(this).val().split("\\").pop();
                                            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
                                        });
                                    </script>
                                </div>
                            </div>
                        </div>
                        <br>
                        <p><code>*</code> indicates mandatory fields</p><br/>
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
