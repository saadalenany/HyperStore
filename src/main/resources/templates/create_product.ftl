<#include "header.ftl">

<div class="container">
    <br/><h2>Product Form</h2><br/>
    <p><code>*</code> indicates mandatory fields</p><br/>
    <form action="/product/save" method="post" enctype="multipart/form-data" class="was-validated">

        <#if product??>
            <input type="hidden" value="${product.getId()}" name="product"/>
        </#if>

        <div class="form-group">
            <label for="name">Name:*</label>
            <#if product?? && product.getName()??>
                <input type="text" class="form-control" id="name" placeholder="Enter name" name="name" value="${product.getName()}" required>
            <#else>
                <input type="text" class="form-control" id="name" placeholder="Enter name" name="name" required>
            </#if>
        </div>

        <div class="form-group">
            <label for="price">Price:*</label>
            <#if product?? && product.getPrice()??>
                <input type="text" class="form-control" id="price" placeholder="Enter price in $$" name="price" min="0" value="${product.getPrice()}" required>
            <#else>
                <input type="number" class="form-control" id="price" placeholder="Enter price in $$" name="price" min="0" required>
            </#if>
        </div>

        <div class="form-group">
            <label for="quantity">Quantity:*</label>
            <#if product?? && product.getQuantity()??>
                <input type="text" class="form-control" id="price" placeholder="Enter quantity" name="quantity" min="1" value="${product.getQuantity()}" required>
            <#else>
                <input type="number" class="form-control" id="price" placeholder="Enter quantity" name="quantity" min="1" required>
            </#if>
        </div>

        <div class="form-group">
            <label for="category">Category:*</label>
            <!-- category nav -->
            <select class="form-control" id="category" name="category">
                <#if categories??>
                    <#if product?? && product.getCategoryId()??>
                        <#list categories as cat>
                            <#if cat.getId() == product.getCategoryId()>
                                <option value="${cat.getId()}">${cat.getName()}</option>
                            </#if>
                        </#list>
                        <#list categories as cat>
                            <#if cat.getId() != product.getCategoryId()>
                                <option value="${cat.getId()}">${cat.getName()}</option>
                            </#if>
                        </#list>
                    <#else>
                        <#list categories as cat>
                            <option value="${cat.getId()}">${cat.getName()}</option>
                        </#list>
                    </#if>
                </#if>
            </select>
        </div>

        <div class="form-group">
            <label for="image" class="label">Image:*</label>
            <#if product?? && product.getImage()??>
                <div class="flip-card">
                    <div class="flip-card-inner">
                        <div class="flip-card-front">
                            <img src="${product.getBase64Image()}" alt="${product.getName()}" style="width:400px;height:400px;">
                        </div>
                        <div class="flip-card-back">
                            <h1>${product.getName()}</h1>
                            <input type="file" class="custom-file-input" id="customFile" name="image">
                        </div>
                    </div>
                </div>
            <#else>
                <input id="image" name="image" type="file" required>
            </#if>
        </div>

        <div class="form-group">
            <label for="discount">Discount Price:</label>
            <#if product?? && product.getDiscountPrice()??>
                <input type="number" class="form-control" id="discount" placeholder="Enter discount between 0 to 100" name="discount" min="0" max="100" value="${product.getDiscountPrice()}">
            <#else>
                <input type="number" class="form-control" id="discount" placeholder="Enter discount between 0 to 100" name="discount" min="0" max="100">
            </#if>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<#include "footer.ftl">
