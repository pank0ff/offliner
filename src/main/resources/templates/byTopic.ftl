<#import "parts/common.ftl" as c>

<@c.page>
    <h3>Posts with topic ${topic}</h3>

    <div  style = "height: 400px; width: 900px">
        <#list messages as message>
            <div class="card my-3" >
                <h1 class="title" > ${message.name}</h1>
                <div><p class="topic">${message.tag}</p>  <#if message.hashtag??>
                        <a href="/post/hashtag/${message.hashtag}">${message.hashtag}</a>
                    </#if></div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div >
                    <span class="mainText" style="margin-top: 10px">${message.text}</span>
                </div>
                <div> <a class="btn btn-primary ml-2 mt-4 mb-2"   href = "/post/${message.id}"> Read full</a>
                    <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                </div>
                <div class="card-footer text-muted">
                    <a class="col align-self-center"> ${message.authorName}</a>
                    <a class="col align-self-end" href="">
                        <#if true>
                            <i class = "fa-regular fa-heart"></i>
                        <#else>
                            <i class = "fa-solid fa-heart"></i
                        </#if>
                    </a>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>