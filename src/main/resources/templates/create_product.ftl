<#include "header.ftl">

<div class="container">
    <br/><h2>Product Form</h2><br/>
    <p><code>*</code> to indicate mandatory fields</p><br/>
    <form action="/product/save" method="post" enctype="multipart/form-data" class="was-validated">

        <div class="form-group">
            <label for="name">Name:*</label>
            <input type="text" class="form-control" id="name" placeholder="Enter name" name="name" required>
        </div>

        <div class="form-group">
            <label for="price">Price:*</label>
            <input type="number" class="form-control" id="price" placeholder="Enter price in $$" name="price" required>
        </div>

        <div class="form-group">
            <label for="quantity">Quantity:*</label>
            <input type="number" class="form-control" id="price" placeholder="Enter quantity" name="quantity" required>
        </div>

        <div class="form-group">
            <label for="category">Category:*</label>
            <!-- category nav -->
            <select class="form-control" id="category" name="category">
                <#if categories??>
                    <#list categories as cat>
                        <option value="${cat.getId()}">${cat.getName()}</option>
                    </#list>
                </#if>
            </select>
        </div>

        <div class="form-group">
            <label for="image" class="label">Image:*</label>
            <input id="image" name="image" type="file" required>
        </div>

        <div class="form-group">
            <label for="discount">Discount Price:</label>
            <input type="number" class="form-control" id="discount" placeholder="Enter discount between 0 to 100" name="discount">
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<#include "footer.ftl">
