<#import "parts/common.ftl" as c>


<@c.page>

    <#if user??>
        <#if lang>
            <h5>Hello, ${user.username}!</h5>
            <div>These are the most liked posts</div>
        <#else>
            <h5>Привет, ${user.username}!</h5>
            <div>Это самые залайканые посты</div>
        </#if>
    <#else>
        <h5>Hello, guest!</h5>
        <div>These are the most liked posts</div>
    </#if>

</@c.page>
