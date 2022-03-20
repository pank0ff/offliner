<#include "security.ftl">
<#import "login.ftl" as l>
<#if isLight>
<#else>
    <div style="background-color:white ">
</#if>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">OFFliner</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if user??>
                <#if isEng>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/main">Posts</a>
                    </li>
                    <#if isAdmin>
                        <li class="nav-item">
                            <a class="nav-link" href="/user">User list</a>
                        </li>
                    </#if>
                    <#if user??>
                        <li class="nav-item">
                            <a class="nav-link" href="/user/profile">Profile</a>
                        </li>
                    </#if>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Главная</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/main">Посты</a>
                    </li>
                    <#if isAdmin>
                        <li class="nav-item">
                            <a class="nav-link" href="/user">Список пользователей</a>
                        </li>
                    </#if>
                    <#if user??>
                        <li class="nav-item">
                            <a class="nav-link" href="/user/profile">Профиль</a>
                        </li>
                    </#if>
                </#if>
            <#else>
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/main">Posts</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">User list</a>
                    </li>
                </#if>
                <#if user??>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/profile">Profile</a>
                    </li>
                </#if>
            </#if>
        </ul>

        <div class="navbar-text mr-3">${name}</div>
        <@l.logout />
    </div>
</nav>
<#if isLight>
<#else>
    </div>
</#if>