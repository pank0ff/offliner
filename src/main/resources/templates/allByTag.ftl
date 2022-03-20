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
                    <a>${message.averageRate}
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor"
                             class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                        </svg>
                    </a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a>#${message.hashtag}</a>
                        </#if></div>
                    <#if message.filename??>
                        <img src="${message.filename}" class="card-img-top">
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
                    <a>${message.averageRate}
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor"
                             class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                        </svg>
                    </a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a>#${message.hashtag}</a>
                        </#if></div>
                    <#if message.filename??>
                        <img src="${message.filename}" class="card-img-top">
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
