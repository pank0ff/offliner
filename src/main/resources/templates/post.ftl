<#import "parts/common.ftl" as c>

<@c.page>
<#if lang>
    Post #${message.id}
<#else>
    Пост #${message.id}
</#if>
    <div style = "height: 400px; width: 900px">
            <div class="card my-3">
                <#if lang>
                    <h1>${message.name}</h1>
                    <a>Average rate: ${message.averageRate}</a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a href="/post/hashtag/${message.hashtag}">${message.hashtag}</a>
                        </#if>
                    </div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div >
                        <span>${message.text}</span>
                    </div>
                    <div class="d-flex align-items-end flex-column">
                        <a class="mb-2 mx-4 p-2" href="">
                            <#if true>
                                <i class = "fa-regular fa-heart"></i>
                            <#else>
                                <i class="fa-solid fa-heart"></i
                            </#if>
                        </a>
                    </div>

                    <div class="card-footer text-muted container">
                        <div class="d-flex justify-content-between flex-row align-items-center">
                            <div>
                                <a class="col align-self-center"
                                   href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Author: ${message.authorName}</a>
                                <#if isAdmin>
                                    <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                                </#if>
                            </div>
                            <div>
                                <form class="d-flex flex-row justify-content-between align-items-center " method="post"
                                      action="/rate/${message.id}/${user.username}">
                                    <div class="form-group mx-2 ">
                                        <select name="rate" size="1" class="rounded">
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
                            </div>
                        </div>
                        <div class="form-group mt-3">
                            <form method="post" action="/post/add/comment/${message.id}" enctype="multipart/form-data">
                                <div class="form-group" style="width:  100%;height: 100%;">
                                    <label style="width:  100%;height: 100%;">
                        <textarea required minlength="5" maxlength="255" type="text" class="form-control" name="text" style="width:  100%;
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
                    </div>
                    <h4>Comments:</h4>
                    <#list comments as comment>
                        <div class="card my-2">
                            <div>
                                <span>${comment.text}</span>
                            </div>
                            <div class="card-footer text-muted">
                                <a class="col align-self-center"
                                   href="/user/profile/${comment.author.id}/${comment.author.username}">${comment.author.username}</a>
                            </div>
                        </div>
                    <#else>
                        No comments
                    </#list>
                <#else>
                    <h1>${message.name}</h1>
                    <a>Средний рейтинг: ${message.averageRate}</a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a href="/post/hashtag/${message.hashtag}">${message.hashtag}</a>
                        </#if>
                    </div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div>
                        <span>${message.text}</span>
                    </div>
                    <div class="d-flex align-items-end flex-column">
                        <a class="mb-2 mx-4 p-2" href="">
                            <#if true>
                                <i class="fa-regular fa-heart"></i>
                            <#else>
                                <i class="fa-solid fa-heart"></i
                            </#if>
                        </a>
                    </div>

                    <div class="card-footer text-muted container">
                        <div class="d-flex justify-content-between flex-row align-items-center">
                            <div>
                                <a class="col align-self-center"
                                   href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Автор: ${message.authorName}</a>
                                <#if isAdmin>
                                    <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Изменить</a>
                                </#if>
                            </div>
                            <div>
                                <form class="d-flex flex-row justify-content-between align-items-center " method="post"
                                      action="/rate/${message.id}/${user.username}">
                                    <div class="form-group mx-2 ">
                                        <select name="rate" size="1" class="rounded">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
                                    </div>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">Подтвердить</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="form-group mt-3">
                            <form method="post" action="/post/add/comment/${message.id}" enctype="multipart/form-data">
                                <div class="form-group" style="width:  100%;height: 100%;">
                                    <label style="width:  100%;height: 100%;">
                        <textarea required minlength="5" maxlength="255" type="text" class="form-control" name="text"
                                  style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="Введите ваш комментарий"></textarea>
                                    </label>
                                </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary">Добавить</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <h4>Комментарии:</h4>
                    <#list comments as comment>
                        <div class="card my-2">
                            <div>
                                <span>${comment.text}</span>
                            </div>
                            <div class="card-footer text-muted">
                                <a class="col align-self-center"
                                   href="/user/profile/${comment.author.id}/${comment.author.username}">${comment.author.username}</a>
                            </div>
                        </div>
                    <#else>
                        Нет комментариев
                    </#list>
                </#if>
            </div>
    </div>
</@c.page>
