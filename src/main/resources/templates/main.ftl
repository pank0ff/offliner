<#import "parts/common.ftl" as c>


<@c.page>


    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Search by topic">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>


    <div style = "height: 400px; width: 900px">
        <#list messages as message>
            <div class="card my-3" >
                <h1 class="title" > ${message.name}</h1>
                <div><p class="topic">${message.tag}</p></div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div >
                    <span class="mainText">${message.text}</span>
                </div>
                <div> <a class="nav-link" style="color: cornflowerblue"  href = "/post/${message.id}"> Read full</a></div>
                <div class="card-footer text-muted">
                    <a href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">${message.authorName}</a>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>
