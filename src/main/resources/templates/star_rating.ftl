<#if user??>

    <#if rate??>
        <#if rate.getStar() == 1>
            <form class="rating">
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="1" checked/>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="2" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="3" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="4" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="5" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
            </form>
        <#elseif rate.getStar() == 2>
            <form class="rating">
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="1" />
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="2" checked/>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="3" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="4" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="5" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
            </form>
        <#elseif rate.getStar() == 3>
            <form class="rating">
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="1" />
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="2" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="3" checked/>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="4" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="5" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
            </form>
        <#elseif rate.getStar() == 4>
            <form class="rating">
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="1" />
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="2" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="3" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="4" checked/>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="5" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
            </form>
        <#else>
            <form class="rating">
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="1" />
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="2" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="3" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="4" />
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
                <label>
                    <input type="radio" name="stars" onclick="check(this.value)" value="5" checked/>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                    <span class="icon">★</span>
                </label>
            </form>
        </#if>
    <#else>
        <form class="rating">
            <label>
                <input type="radio" name="stars" onclick="check(this.value)" value="1" />
                <span class="icon">★</span>
            </label>
            <label>
                <input type="radio" name="stars" onclick="check(this.value)" value="2" />
                <span class="icon">★</span>
                <span class="icon">★</span>
            </label>
            <label>
                <input type="radio" name="stars" onclick="check(this.value)" value="3" />
                <span class="icon">★</span>
                <span class="icon">★</span>
                <span class="icon">★</span>
            </label>
            <label>
                <input type="radio" name="stars" onclick="check(this.value)" value="4" />
                <span class="icon">★</span>
                <span class="icon">★</span>
                <span class="icon">★</span>
                <span class="icon">★</span>
            </label>
            <label>
                <input type="radio" name="stars" onclick="check(this.value)" value="5" />
                <span class="icon">★</span>
                <span class="icon">★</span>
                <span class="icon">★</span>
                <span class="icon">★</span>
                <span class="icon">★</span>
            </label>
        </form>
    </#if>

    <script>
        function check(star) {
            console.log(star);
            $.ajax({
                type: 'DELETE',
                url: '/by_rate',
                data: "admin_id=${user.getId()}&product_id=${product.getId()}&rate=" + star,
                success: function(data) {
                    console.log('Load was performed.');
                },
                failure: function(data) {
                    console.log('Load was not performed.');
                }
            });
          }
    </script>
</#if>
