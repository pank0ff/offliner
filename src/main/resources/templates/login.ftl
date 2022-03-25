<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<@l.login "/login" false/>
    <div>
        <h4>
            <a href="/oauth2/authorization/google">Login with Google</a>
        </h4>
    </div>
</@c.page>
