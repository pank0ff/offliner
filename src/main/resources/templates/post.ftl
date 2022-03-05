<#import "parts/common.ftl" as c>

<@c.page>
   Post #${message.id}

    <div style = "height: 400px; width: 900px">
            <div class="card my-3">
                <h1 >${message.name}</h1>
                <div><p >${message.tag}</p></div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div >
                    <span>${message.text}</span>
                </div>
                <div class="card-footer text-muted">
                    <a class="col align-self-center" href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">${message.authorName}</a>
                    <a class="col align-self-end" href="">
                        <#if true>
                            <i class = "fa-regular fa-heart"></i>
                        <#else>
                            <i class = "fa-solid fa-heart"></i
                        </#if>
                    </a>
                </div>
            </div>
    </div>
</@c.page>
