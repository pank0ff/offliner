<#import "parts/common.ftl" as c>

<@c.page>
    <#if lang>
        <h3>Posts with hashtag #${hashtag}</h3>
    <#else>
        <h3>Посты с хэш-тегом #${hashtag}</h3>
    </#if>
    <div  style = "height: 400px; width: 900px">
        <#if lang>
            <#list messages as message>
                <div class="card my-3">
                    <h1 class="title"> ${message.name}</h1>
                    <a>Average rate: ${message.averageRate}</a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a>#${message.hashtag}</a>
                        </#if></div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div>
                        <span class="mainText" style="margin-top: 10px">${message.text}</span>
                    </div>
                    <div><a class="btn btn-primary ml-2 mt-4 mb-2" href="/post/${message.id}"> Read full</a>
                        <#if isAdmin>
                            <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                        </#if>
                    </div>
                    <div class="card-footer text-muted">
                        <a class="col align-self-center"
                           href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">
                            Author: ${message.authorName}</a>
                        <a class="col align-self-end" href="">
                            <#if true>
                                <i class="fa-regular fa-heart"></i>
                            <#else>
                                <i class="fa-solid fa-heart"></i
                            </#if>
                        </a>
                    </div>
                </div>
            <#else>
                No posts
            </#list>
        <#else>
            <#list messages as message>
                <div class="card my-3">
                    <h1 class="title"> ${message.name}</h1>
                    <a>Средний рейтинг: ${message.averageRate}</a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a>#${message.hashtag}</a>
                        </#if></div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div>
                        <span class="mainText" style="margin-top: 10px">${message.text}</span>
                    </div>
                    <div>
                        <a class="btn btn-primary ml-2 mt-4 mb-2" href="/post/${message.id}">Читать полностью</a>
                        <#if isAdmin>
                            <a class="btn btn-primary ml-2 mt-4 mb-2"
                               href="/user/profile/update/${message.id}">Изменить</a>
                        </#if>

                    </div>
                    <div class="card-footer text-muted">
                        <a class="col align-self-center"
                           href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">
                            Автор: ${message.authorName}</a>
                        <a class="col align-self-end" href="">
                            <#if true>
                                <i class="fa-regular fa-heart"></i>
                            <#else>
                                <i class="fa-solid fa-heart"></i
                            </#if>
                        </a>
                    </div>
                </div>
            <#else>
                Нет постов
            </#list>
        </#if>
    </div>
</@c.page>