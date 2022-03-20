<#import "parts/common.ftl" as c>

<@c.page>
    <h3>${userChannel.username}</h3>
    <div>${type}</div>
    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">
                <a href="/user/profile/${user.id}/${user.username}">${user.username}</a>
            </li>
        <#else>
            Empty
        </#list>
    </ul>
</@c.page>