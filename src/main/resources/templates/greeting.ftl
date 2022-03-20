<#import "parts/common.ftl" as c>


<@c.page>

    <#if user??>
        <#if lang>
            <h5>Hello, ${user.username}!</h5>
            <div>This is a forum that will finally help you choose what to read, what to play and much, much more.</div>
        <#else>
            <h5>Привет, ${user.username}!</h5>
            <div>Это форум, который тебе поможет наконец-то выбрать что почитать,во что поиграть и многое-многое
                другое
            </div>
        </#if>
    <#else>
        <h5>Hello, guest!</h5>
        <div>This is a forum that will finally help you choose what to read, what to play and much, much more.</div>
    </#if>

</@c.page>
