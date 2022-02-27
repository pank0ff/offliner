<#import "parts/common.ftl" as c>

<@c.page>
    <style>
        span.mainText {
            -ms-text-overflow: ellipsis;
            -o-text-overflow: ellipsis;
            text-overflow: ellipsis;
            overflow: hidden;
            -ms-line-clamp: 10;
            -webkit-line-clamp: 10;
            line-clamp: 10;
            display: -webkit-box;
            word-wrap: break-word;
            -webkit-box-orient: vertical;
            box-orient: vertical;
        }
        h1.title {
            -ms-text-overflow: ellipsis;
            -o-text-overflow: ellipsis;
            text-overflow: ellipsis;
            overflow: hidden;
            -ms-line-clamp: 1;
            -webkit-line-clamp: 1;
            line-clamp: 1;
            display: -webkit-box;
            word-wrap: break-word;
            -webkit-box-orient: vertical;
            box-orient: vertical;
        }

        p.topic {
            -ms-text-overflow: ellipsis;
            -o-text-overflow: ellipsis;
            text-overflow: ellipsis;
            overflow: hidden;
            -ms-line-clamp: 1;
            -webkit-line-clamp: 1;
            line-clamp: 1;
            display: -webkit-box;
            word-wrap: break-word;
            -webkit-box-orient: vertical;
            box-orient: vertical;
        }

    </style>
    <h5>${user.username}</h5>
   <a href="user/${user.id}/settings">Settings</a>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/user/profile/${user.id}" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Search by topic">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new Message
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" action="/user/profile/add/${user.id}" enctype="multipart/form-data">
                <div class="form-group">
                    <input required type="text" class="form-control" name="name" placeholder="Enter name of your post"/>
                </div>
                <div class="form-group">
                    <input required type="text" class="form-control" name="tag" placeholder="Enter topic of your post"/>
                </div>
                <div class="form-group" style="width:  100%;height: 100%;">
                    <label style="width:  100%;height: 100%;">
                        <textarea required  type="text" class="form-control" name="text" style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="Enter your post"></textarea>
                    </label>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>
    <div  style = "height: 400px; width: 900px">
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
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>