<#import "parts/common.ftl" as c>

<@c.page>
   Post #${message.id}

    <div class="card" style = "height: 400px; width: 900px">
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
                    ${message.authorName}
                </div>
            </div>
    </div>
</@c.page>
