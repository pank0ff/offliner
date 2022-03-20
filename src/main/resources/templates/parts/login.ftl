<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="User name" />
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password" />
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="some@some.com"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Language of site:</label>
                <div class="col-sm-6">
                    <select name="choice" size="1" class="rounded form-control ">
                        <option value="ENG">ENGLISH</option>
                        <option value="RU">РУССКИЙ</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Theme of site:</label>
                <div class="col-sm-6">
                    <select name="theme" size="1" class="rounded form-control ">
                        <option value="LIGHT">LIGHT</option>
                        <option value="DARK">DARK</option>
                    </select>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><#if user??>Sign Out <#else>Log In</#if></button>
    </form>
</#macro>
