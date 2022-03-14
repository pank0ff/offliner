<#import "parts/common.ftl" as c>


<@c.page>

    <script src="https://kit.fontawesome.com/17c4207260.js" crossorigin="anonymous"></script>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <select name="choice" size="1"  class="rounded form-control " >
                    <option value="1">Deep search</option>
                    <option value="2">Post name</option>
                    <option value="3">Comments</option>
                    <option value="4">Topic</option>
                    <option value="5">Hashtag</option>
                    <option value="6">Text</option>
                </select>
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Search by topic">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>


    <div style = "height: 400px; width: 900px">
        <#list messages as message>
            <div class="card my-3" >
                <h1 class="title" > ${message.name} </h1>
                <a>Average rate: ${message.averageRate} stars</a>
                <div>
                    <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
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
                <div class="d-flex justify-content-between flex-row align-items-end"> <a class="btn btn-primary ml-2 mt-4 mb-2 "   href = "/post/${message.id}"> Read full</a>
                    <a class="mb-2 mx-4" href="">
                        <#if true>
                            <i class = "fa-regular fa-heart"></i>
                        <#else>
                            <i class = "fa-solid fa-heart"></i
                        </#if>
                    </a>
                </div>
                <div class="card-footer text-muted container">
                    <div class="d-flex justify-content-between flex-row align-items-center">
                        <div>
                            <a class="col align-self-center" href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Author: ${message.authorName}</a>
                        </div>
                        <div>
                        <#if user??>
                            <form class="d-flex flex-row justify-content-between align-items-center " method="post" action="/rate/${message.id}/${user.username}">
                                <div class="form-group mx-2 ">
                                    <select name="rate" size="1"  class="rounded">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary">Confirm</button>
                                </div>
                            </form>
                        </#if>
                        </div>
                    </div>

                    <#if user??>
                            <div class="form-group mt-3">
                                <form method="post" action="/post/add/comment/${message.id}" enctype="multipart/form-data">
                                    <div class="form-group" style="width:  100%;height: 100%;">
                                        <label style="width:  100%;height: 100%;">
                        <textarea required minlength="5" maxlength="255"  type="text" class="form-control" name="text" style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="Enter your comment"></textarea>
                                        </label>
                                    </div>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">Add</button>
                                    </div>
                                </form>
                            </div>

                    </#if>

                    </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>
