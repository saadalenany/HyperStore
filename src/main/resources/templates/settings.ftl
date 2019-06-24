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
                            <script src="/js/location.js"></script>
                            <div class="group">
                                <#if user.getAddress()??>
                                    <label for="country">Current Address: ${user.getAddress()}</label>
                                    <input hidden="true" value="${user.getAddress()}" name="address" />
                                </#if>
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
                            <br/>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <a type="button" class="btn btn-danger" href="/delete_user/${user.getId()}">Delete Account</a>
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
