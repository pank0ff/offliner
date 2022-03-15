<#import "parts/common.ftl" as c>

<@c.page>
    <#if lang>
        List of users

        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td><a href="/user/profile/${user.getId()}/${user.username}">${user.username}</a></td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        Список пользователей

        <table>
            <thead>
            <tr>
                <th>Имя</th>
                <th>Роль</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td><a href="/user/profile/${user.getId()}/${user.username}">${user.username}</a></td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</@c.page>
