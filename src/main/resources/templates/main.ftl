<#import "parts/common.ftl" as c>


<@c.page>
    <script src="https://kit.fontawesome.com/17c4207260.js" crossorigin="anonymous"></script>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="row d-flex flex-row">
                <#if lang>
                    <input type="text" name="filter" class="form-control col-4" value="" placeholder="Search ">
                    <select name="choice" size="1" class="rounded col-3 form-control ml-1">
                        <option value="1">Deep search</option>
                        <option value="2">Post name</option>
                        <option value="3">Comments</option>
                        <option value="4">Topic</option>
                        <option value="5">Hashtag</option>
                        <option value="6">Text</option>
                    </select>
                    <select name="sortChoice" size="1" class="col-3 rounded form-control ml-1">
                        <option value="1">Date(first earlier)</option>
                        <option value="2">Date(first latest)</option>
                    </select>
                    <button type="submit" class="btn btn-primary mt-2 col-2">Search</button>
                <#else>
                    <input type="text" name="filter" class="form-control col-4" value="" placeholder="Поиск ">
                    <select name="choice" size="1" class="rounded col-3 form-control ml-1">
                        <option value="1">Полный поиск</option>
                        <option value="2">Название постов</option>
                        <option value="3">Комментарии</option>
                        <option value="4">Темы</option>
                        <option value="5">Хэш-теги</option>
                        <option value="6">Текст</option>
                    </select>
                    <select name="sortChoice" size="1" class="col-3 rounded form-control ml-1">
                        <option value="1">Дата(сначала ранние)</option>
                        <option value="2">Дата(сначала поздние)</option>
                    </select>
                    <button type="submit" class="btn btn-primary mt-2 col-2">Поиск</button>
                </#if>


            </form>
        </div>
    </div>


    <div style="height: 400px; width: 900px">
        <#if lang>
            <#list messages as message>
                <div class="card my-3">
                    <h1 class="title"> ${message.name} </h1>
                    <a>${message.averageRate}
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor"
                             class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                        </svg>
                    </a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                        </#if>
                    </div>
                    <#if message.filename??>
                        <#if theme>
                            <img src="${message.filename}" class="card-img-top">
                        <#else>
                            <img style="filter: invert()" src="${message.filename}" class="card-img-top">
                        </#if>
                    </#if>
                    <div>
                        <span class="mainText" style="margin-top: 10px">${message.text}</span>
                    </div>
                    <div class="d-flex justify-content-between flex-row align-items-end">
                        <a class="btn btn-primary ml-2 mt-4 mb-2 " href="/post/${message.id}"> Read full</a>
                        <#if isAdmin>
                            <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                        </#if>
                        <#if user??>
                            <#if message.meLiked == 0>
                                <a class="mb-2 mx-4" href="/user/like/${message.id}">
                                    ${message.likesCount}<img style="width: 15px;height: 15px"
                                                              src="https://img.icons8.com/ios/50/000000/like--v1.png"/>
                                </a>

                            <#else>
                                <a class="mb-2 mx-4">
                                    ${message.likesCount}<img style="width: 15px;height: 15px"
                                                              src="https://cdn-icons-png.flaticon.com/512/32/32557.png"/>
                                </a>
                            </#if>
                        <#else>
                            <a class="mb-2 mx-4" href="/user/unlike/${message.id}">
                                ${message.likesCount}<img style="width: 15px;height: 15px"
                                                          src="https://cdn-icons-png.flaticon.com/512/32/32557.png"/>
                            </a>
                        </#if>

                    </div>
                    <div class="card-footer text-muted container">
                        <div class="d-flex justify-content-between flex-row align-items-center">
                            <div>
                                <a class="col align-self-center"
                                   href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Author: ${message.authorName}</a>
                                rate: ${message.getAuthor().getUserRate()}
                            </div>
                            <div>
                                <#if user??>
                                    <form class="d-flex flex-row justify-content-between align-items-center "
                                          method="post" action="/rate/${message.id}">
                                        <div class="form-group mx-2 ">
                                            <select name="rate" size="1" class="rounded">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15"
                                                 fill="currentColor"
                                                 class="bi bi-star" viewBox="0 0 16 16">
                                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                            </svg>
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

                        </#if>

                    </div>
                </div>
            <#else>
                No posts
            </#list>
        <#else>
            <#list messages as message>
                <div class="card my-3">
                    <h1 class="title"> ${message.name} </h1>
                    <a>${message.averageRate}
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor"
                             class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                        </svg>
                    </a>
                    <div>
                        <a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                        </#if>
                    </div>
                    <#if message.filename??>
                        <#if theme>
                            <img src="${message.filename}" class="card-img-top">
                        <#else>
                            <img style="filter: invert()" src="${message.filename}" class="card-img-top">
                        </#if>
                    </#if>
                    <div>
                        <span class="mainText" style="margin-top: 10px">${message.text}</span>
                    </div>
                    <div class="d-flex justify-content-between flex-row align-items-end">
                        <a class="btn btn-primary ml-2 mt-4 mb-2 " href="/post/${message.id}">Читать полностью</a>
                        <#if isAdmin>
                            <a class="btn btn-primary ml-2 mt-4 mb-2"
                               href="/user/profile/update/${message.id}">Изменить</a>
                        </#if>
                        <#if user??>
                            <#if message.meLiked == 0>
                                <a class="mb-2 mx-4" href="/user/like/${message.id}">
                                    ${message.likesCount}<img style="width: 15px;height: 15px"
                                                              src="https://img.icons8.com/ios/50/000000/like--v1.png"/>
                                </a>
                            <#else>
                                <a class="mb-2 mx-4" href="/user/unlike/${message.id}">
                                    ${message.likesCount}<img style="width: 15px;height: 15px"
                                                              src="https://cdn-icons-png.flaticon.com/512/32/32557.png"/>
                                </a>
                            </#if>
                        <#else>
                            <a class="mb-2 mx-4">
                                ${message.likesCount}<img style="width: 15px;height: 15px"
                                                          src="https://cdn-icons-png.flaticon.com/512/32/32557.png"/>
                            </a>
                        </#if>
                    </div>
                    <div class="card-footer text-muted container">
                        <div class="d-flex justify-content-between flex-row align-items-center">
                            <div>
                                <a class="col align-self-center"
                                   href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Автор: ${message.authorName}</a>
                                рейтинг: ${message.getAuthor().getUserRate()}
                            </div>
                            <div>
                                <#if user??>
                                    <form class="d-flex flex-row justify-content-between align-items-center "
                                          method="post" action="/rate/${message.id}">
                                        <div class="form-group mx-2 ">
                                            <select name="rate" size="1" class="rounded">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15"
                                                 fill="currentColor"
                                                 class="bi bi-star" viewBox="0 0 16 16">
                                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                            </svg>
                                        </div>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary">Подтвердить</button>
                                        </div>
                                    </form>
                                </#if>
                            </div>
                        </div>

                        <#if user??>
                            <div class="form-group mt-3">
                                <form method="post" action="/post/add/comment/${message.id}"
                                      enctype="multipart/form-data">
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

                        </#if>

                    </div>
                </div>
            <#else>
                Нет постов
            </#list>
        </#if>
    </div>
</@c.page>
