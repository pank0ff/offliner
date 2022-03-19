<#import "parts/common.ftl" as c>

<@c.page>
    <#if lang>
        <div class="container mt-5 mb-5">
            <div class="row no-gutters">
                <div class="col-md-4 col-lg-4">
                    <#if user.avatarFilename??>
                        <#if theme>
                            <img src="${user.avatarFilename}">
                        <#else>
                            <img style="filter: invert()" src="${user.avatarFilename}">
                        </#if>
                    <#else>
                        <#if theme>
                            <img src="https://hornews.com/upload/images/blank-avatar.jpg">
                        <#else>
                            <img style="filter:invert()" src="https://hornews.com/upload/images/blank-avatar.jpg">
                        </#if>
                    </#if>

                </div>
                <#if theme>
                <div class="col-md-8 col-lg-8">
                    <#else>
                    <div style="filter: invert()" class="col-md-8 col-lg-8">
                        </#if>
                        <div class="d-flex flex-column">
                            <div class="d-flex flex-row justify-content-between align-items-center p-5 bg-black text-white">
                                <h3 class="display-5">${user.username}</h3>
                                <#if user.getLinkFacebook()??>
                                    <a href="${user.getLinkFacebook()}">
                                        <i class="fa fa-facebook"></i>
                                    </a>
                                </#if>
                                <#if user.getLinkGoogle()??>
                                    <a href="${user.getLinkGoogle()}">
                        <i class="fa fa-google" ></i>
                            </a>
                        </#if>
                        <#if user.getLinkYoutube()??>
                            <a type="hidden" href="${user.getLinkYoutube()}">
                        <i class="fa fa-youtube-play" ></i>
                            </a>
                        </#if>
                        <#if user.getLinkDribble()??>
                            <a  href="${user.getLinkDribble()}">
                        <i class="fa fa-dribbble"></i>
                            </a>
                        </#if>
                        <#if user.getLinkLinkedIn()??>
                            <a href="${user.getLinkLinkedIn()}">
                        <i class="fa fa-linkedin" ></i>
                            </a>
                        </#if>
                    </div>

                    <div class="p-3 bg-blue text-white">
                        <#if    aboutMyself??>
                            <h6> ${aboutMyself}</h6>
                        </#if>
                    </div>
                    <div class="d-flex flex-row text-white">
                        <div class="p-3 bg-primary text-center skill-block">
                            <h5>Posts:</h5>
                             <h6>${countOfPosts}</h6>
                        </div>
                        <div class="p-3 bg-success text-center skill-block">
                            <h5>Subscribers:</h5>
                            <h6>132</h6>
                        </div>
                        <div class="p-3 bg-warning text-center skill-block">
                            <h5>Subscriptions:</h5>
                            <h6>54</h6>

                        </div>
                        <div class="p-3 bg-danger text-center skill-block">
                           <h5>Date of registration:</h5>
                            <h6>${user.dateOfRegistration}</h6>
                        </div>
                    </div>
                </div>
            </div>
                <a class="btn btn-primary  mt-1" href="/user/profile/${user.username}/settings">Settings</a>
            </div>
        </div>

        <div class="form-row ">
            <div class="form-group col-md-6">
                <form method="get" action="/user/profile/${user.id}" class="row d-flex flex-row">
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
                </form>
            </div>
        </div>

        <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
           aria-controls="collapseExample">
            Add post
        </a>
        <div class="collapse" id="collapseExample">
            <div class="form-group mt-3">
                <form method="post" action="/user/profile/add/${user.username}" enctype="multipart/form-data">
                <div class="form-group">
                    <input required minlength="5" type="text" class="form-control" name="name" placeholder="Enter name of your post"/>
                </div>
                <div class="form-group">
                    <select name="tag" size="1"  class="rounded">
                        <option value="Nothing">Choose topic</option>
                        <option value="Books">Books</option>
                        <option value="Games">Games</option>
                        <option value="Music">Music</option>
                        <option value="Films">Films</option>
                        <option value="Sport">Sport</option>
                        <option value="IT">IT</option>
                    </select>
                </div>
                <div class="form-group">
                    <input required type="text" pattern="^[a-zA-Z]+$" class="form-control" name="hashtag" placeholder="Enter hashtag of your post(only latin letters)"/>
                </div>
                <div class="form-group" style="width:  100%;height: 100%;">
                    <label style="width:  100%;height: 100%;">
                        <textarea required minlength="5" type="text" class="form-control" name="text" style="width:  100%;
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
                <a>Average rate: ${message.averageRate}</a>
                <div><a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                    <#if message.hashtag??>
                        <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                    </#if></div>
                <#if message.filename??>
                    <img src="${message.filename}" class="card-img-top">
                </#if>
                <div >
                    <span class="mainText" style="margin-top: 10px">${message.text}</span>
                </div>
                <div> <a class="btn btn-primary ml-2 mt-4 mb-2"   href = "/post/${message.id}"> Read full</a>
                    <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                </div>
                <div class="card-footer text-muted container">
                    <div class="d-flex justify-content-between flex-row align-items-center">
                        <div>
                            <a class="col align-self-center" href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Author: ${message.authorName}</a>
                        </div>
                        <div>
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
                        </div>
                    </div>

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
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
    <#else>
        <div class="container mt-5 mb-5">
            <div class="row no-gutters">
                <div class="col-md-4 col-lg-4">

                    <#if user.avatarFilename??>
                        <#if theme>
                            <img src="${user.avatarFilename}">
                        <#else>
                            <img style="filter: invert()" src="${user.avatarFilename}">
                        </#if>
                    <#else>
                        <#if theme>
                            <img src="https://hornews.com/upload/images/blank-avatar.jpg">
                        <#else>
                            <img style="filter:invert()" src="https://hornews.com/upload/images/blank-avatar.jpg">
                        </#if>
                    </#if>
                </div>
                <#if theme>
                <div class="col-md-8 col-lg-8">
                    <#else>
                    <div style="filter: invert()" class="col-md-8 col-lg-8">
                        </#if>
                        <div class="d-flex flex-column">
                            <div class="d-flex flex-row justify-content-between align-items-center p-5 bg-black text-white">
                                <h3 class="display-5">${user.username}</h3>
                                <#if user.getLinkFacebook()??>
                                    <a href="${user.getLinkFacebook()}">
                                        <i class="fa fa-facebook"></i>
                                    </a>
                                </#if>
                                <#if user.getLinkGoogle()??>
                                    <a href="${user.getLinkGoogle()}">
                                        <i class="fa fa-google"></i>
                                    </a>
                                </#if>
                                <#if user.getLinkYoutube()??>
                                    <a type="hidden" href="${user.getLinkYoutube()}">
                                        <i class="fa fa-youtube-play"></i>
                                    </a>
                                </#if>
                                <#if user.getLinkDribble()??>
                                    <a href="${user.getLinkDribble()}">
                                        <i class="fa fa-dribbble"></i>
                                    </a>
                                </#if>
                                <#if user.getLinkLinkedIn()??>
                                    <a href="${user.getLinkLinkedIn()}">
                                        <i class="fa fa-linkedin"></i>
                                    </a>
                                </#if>
                            </div>

                            <div class="p-3 bg-blue text-white">
                                <#if    aboutMyself??>
                                    <h6> ${aboutMyself}</h6>
                                </#if>
                            </div>
                            <div class="d-flex flex-row text-white">
                                <div class="p-3 bg-primary text-center skill-block">
                                    <h5>Постов:</h5>
                                    <h6>${countOfPosts}</h6>
                                </div>
                                <div class="p-3 bg-success text-center skill-block">
                                    <h5>Подписчики:</h5>
                                    <h6>132</h6>
                                </div>
                                <div class="p-3 bg-warning text-center skill-block">
                                    <h5>Подписки:</h5>
                                    <h6>54</h6>

                                </div>
                                <div class="p-3 bg-danger text-center skill-block">
                                    <h5>Дата регистрации:</h5>
                                    <h6>${user.dateOfRegistration}</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                <a class="btn btn-primary  mt-1" href="/user/profile/${user.username}/settings">Настройки</a>
            </div>
        </div>

        <div class="form-row ">
            <div class="form-group col-md-6">
                <form method="get" action="/user/profile/${user.id}" class="row d-flex flex-row">
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
                </form>
            </div>
        </div>

        <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
           aria-controls="collapseExample">
            Добавить пост
        </a>
        <div class="collapse" id="collapseExample">
            <div class="form-group mt-3">
                <form method="post" action="/user/profile/add/${user.username}" enctype="multipart/form-data">
                    <div class="form-group">
                        <input required minlength="5" type="text" class="form-control" name="name"
                               placeholder="Введите название вашего поста"/>
                    </div>
                    <div class="form-group">
                        <select name="tag" size="1" class="rounded">
                            <option value="Nothing">Выберите тему</option>
                            <option value="Books">Книги</option>
                            <option value="Games">Игры</option>
                            <option value="Music">Музыка</option>
                            <option value="Films">Фильмы</option>
                            <option value="Sport">Спорт</option>
                            <option value="IT">ИТ</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input required type="text" pattern="^[a-zA-Z]+$" class="form-control" name="hashtag"
                               placeholder="Введите хэш-тег(только латинские буквы)"/>
                    </div>
                    <div class="form-group" style="width:  100%;height: 100%;">
                        <label style="width:  100%;height: 100%;">
                        <textarea required minlength="5" type="text" class="form-control" name="text" style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="Введите ваш пост"></textarea>
                        </label>
                    </div>
                    <div class="form-group">
                        <div class="custom-file">
                            <input type="file" name="file" id="customFile">
                            <label class="custom-file-label" for="customFile">Выберете фото</label>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </div>
                </form>
            </div>
        </div>
        <div style="height: 400px; width: 900px">
            <#list messages as message>
                <div class="card my-3">
                    <h1 class="title"> ${message.name}</h1>
                    <a>Средний рейтинг: ${message.averageRate}</a>
                    <div><a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                        </#if></div>
                    <#if message.filename??>
                        <img src="${message.filename}" class="card-img-top">
                    </#if>
                    <div>
                        <span class="mainText" style="margin-top: 10px">${message.text}</span>
                    </div>
                    <div><a class="btn btn-primary ml-2 mt-4 mb-2" href="/post/${message.id}"> Читать полностью</a>
                        <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Изменить</a>
                    </div>
                    <div class="card-footer text-muted container">
                        <div class="d-flex justify-content-between flex-row align-items-center">
                            <div>
                                <a class="col align-self-center"
                                   href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Автор: ${message.authorName}</a>
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
                </div>
            <#else>
                Нет постов
            </#list>
        </div>
    </#if>
</@c.page>