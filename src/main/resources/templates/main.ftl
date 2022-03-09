<#import "parts/common.ftl" as c>


<@c.page>

    <script src="https://kit.fontawesome.com/17c4207260.js" crossorigin="anonymous"></script>

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
                <div>
                    <p class="topic">${message.tag} </p>
                    <#if message.hashtag??>
                        <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                    </#if>
                </div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div >
                    <span class="mainText" style="margin-top: 10px">${message.text}</span>
                </div>
                <div> <a class="btn btn-primary ml-2 mt-4 mb-2"   href = "/post/${message.id}"> Read full</a></div>
                <div class="card-footer text-muted container">
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
        <#else>
            No message
        </#list>
    </div>
</@c.page>
