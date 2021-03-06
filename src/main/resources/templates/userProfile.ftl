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
                                <h3 class="display-5">${user.getUsername()}</h3><h5>${user.getCountOfLikes()} <img
                                            style="width: 15px;height: 15px"
                                            src="https://img.icons8.com/ios/50/000000/like--v1.png"/></h5>
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
                                    <a href="${user.getLinkYoutube()}">
                                        <i class="fa fa-youtube-play" ></i>
                                    </a>
                                </#if>
                                <#if user.getLinkDribble()??>
                                    <a href="${user.getLinkDribble()}">
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
                                <#if user.aboutMyself??>
                                    <h6> ${user.aboutMyself}</h6>
                                </#if>
                            </div>

                            <div class="d-flex flex-row text-white">
                                <div class="p-3 bg-primary text-center skill-block">
                                    <h5>Posts:</h5>
                                    <h6>${user.getCountOfPosts()}</h6>
                                </div>
                                <div class="p-3 bg-success text-center skill-block">
                                    <h5>Subscribers:</h5>
                                    <h5><a href="/user/subscribers/${user.id}/list">${subscribersCount}</a></h5>
                                </div>
                                <div class="p-3 bg-warning text-center skill-block">
                                    <h5>Subscriptions:</h5>
                                    <h5><a href="/user/subscriptions/${user.id}/list">${subscriptionsCount}</a></h5>
                                </div>
                                <div class="p-3 bg-danger text-center skill-block">
                                    <h5>Date of registration:</h5>
                                    <h6>${user.dateOfRegistration}</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                    <#if admin>
                        <a class="btn btn-primary  mt-1" href="/user/profile/${user.username}/settings">Settings</a>
                    </#if>
                </div>
            </div>
        <#if isCurrentUser>
        <#else>
            <#if isSubscriber>
                <a class="btn btn-info mb-5" href="/user/unsubscribe/${user.id}">Unsubscribe</a>
            <#else>
                <a class="btn btn-info mb-5" href="/user/subscribe/${user.id}">Subscribe</a>
            </#if>
        </#if>
        <div class="form-row ">
            <div class="form-group col-md-6">
                <form method="get" action="/user/profile/${user.username}/else" class="row d-flex flex-row">
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
        <#if admin>
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                Add post
            </a>
            <div class="collapse" id="collapseExample">
                <div class="form-group mt-3">
                    <form method="post" action="/user/profile/add/${user.id}" enctype="multipart/form-data">
                        <div class="form-group">
                            <input required minlength="5" type="text" class="form-control" name="name"
                                   placeholder="Enter name of your post"/>
                        </div>
                        <div class="form-group">
                            <select name="tag" size="1" class="rounded">
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
                        <textarea required minlength="5"  type="text" class="form-control" name="text" style="width:  100%;
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
    </#if>

    <div  style = "height: 400px; width: 900px">
        <#list messages as message>
            <div class="card my-3">
                <h1 class="title"> ${message.name}</h1>
                <a>${message.averageRate}
                    <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor"
                         class="bi bi-star" viewBox="0 0 16 16">
                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                    </svg>
                </a>
                <div><a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                    <#if message.hashtag??>
                        <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                    </#if></div>
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
                <div class="d-flex align-items-end flex-column">
                    <#if message.meLiked == 0>
                        <a class="mb-2 mx-4" href="/user/like/${message.id}">
                            ${message.likesCount}<img style="width: 15px;height: 15px"
                                                      src="https://img.icons8.com/ios/50/000000/like--v1.png"/>
                        </a>
                    <#else>
                        <a class="mb-2 mx-4" href="/user/unlike/${message.id}">
                            ${message.likesCount}<img style="width: 15px;height: 15px"
                                                      src="https://cdn-icons-png.flaticon.com/512/32/32557.png"/> </a>
                    </#if>
                </div>
                <div><a class="btn btn-primary ml-2 mt-4 mb-2" href="/post/${message.id}"> Read full</a>
                    <#if admin>
                        <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                    </#if>
                </div>
                <div class="card-footer text-muted container">
                    <div class="d-flex justify-content-between flex-row align-items-center">
                        <div>
                            <a class="col align-self-center"
                               href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">Author: ${message.authorName}</a>
                            rate: ${user.getUserRate()}
                        </div>
                        <div>
                            <#if user??>
                                <form class="d-flex flex-row justify-content-between align-items-center " method="post"
                                      action="/rate/${message.id}">
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
            No posts
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
                                <h3 class="display-5">${user.getUsername()}</h3><h5>${user.getCountOfLikes()} <img
                                            style="width: 15px;height: 15px"
                                            src="https://img.icons8.com/ios/50/000000/like--v1.png"/></h5>
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
                                    <a href="${user.getLinkYoutube()}">
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
                                <#if user.aboutMyself??>
                                    <h6> ${user.aboutMyself}</h6>
                                </#if>
                            </div>

                            <div class="d-flex flex-row text-white">
                                <div class="p-3 bg-primary text-center skill-block">
                                    <h5>????????????:</h5>
                                    <h6>${user.getCountOfPosts()}</h6>
                                </div>
                                <div class="p-3 bg-success text-center skill-block">
                                    <h5>????????????????????:</h5>
                                    <h5><a href="/user/subscribers/${user.id}/list">${subscribersCount}</a></h5>
                                </div>
                                <div class="p-3 bg-warning text-center skill-block">
                                    <h5>????????????????:</h5>
                                    <h5><a href="/user/subscriptions/${user.id}/list">${subscriptionsCount}</a></h5>
                                </div>
                                <div class="p-3 bg-danger text-center skill-block">
                                    <h5>???????? ??????????????????????:</h5>
                                    <h6>${user.dateOfRegistration}</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                    <#if admin>
                        <a class="btn btn-primary  mt-1" href="/user/profile/${user.username}/settings">??????????????????</a>
                    </#if>
                </div>
            </div>
        <#if isCurrentUser>
        <#else>
            <#if isSubscriber>
                <a class="btn btn-info mb-5" href="/user/unsubscribe/${user.id}">????????????????????</a>
            <#else>
                <a class="btn btn-info mb-5" href="/user/subscribe/${user.id}">??????????????????????</a>
            </#if>
        </#if>
        <div class="form-row ">
            <div class="form-group col-md-6">
                <form method="get" action="/user/profile/${user.username}/else" class="row d-flex flex-row">
                    <input type="text" name="filter" class="form-control col-4" value="" placeholder="?????????? ">
                    <select name="choice" size="1" class="rounded col-3 form-control ml-1">
                        <option value="1">???????????? ??????????</option>
                        <option value="2">???????????????? ????????????</option>
                        <option value="3">??????????????????????</option>
                        <option value="4">????????</option>
                        <option value="5">??????-????????</option>
                        <option value="6">??????????</option>
                    </select>
                    <select name="sortChoice" size="1" class="col-3 rounded form-control ml-1">
                        <option value="1">????????(?????????????? ????????????)</option>
                        <option value="2">????????(?????????????? ??????????????)</option>
                    </select>
                    <button type="submit" class="btn btn-primary mt-2 col-2">??????????</button>
                </form>
            </div>
        </div>
        <#if admin>
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                ???????????????? ????????
            </a>
            <div class="collapse" id="collapseExample">
                <div class="form-group mt-3">
                    <form method="post" action="/user/profile/add/${user.id}" enctype="multipart/form-data">
                        <div class="form-group">
                            <input required minlength="5" type="text" class="form-control" name="name"
                                   placeholder="?????????????? ???????????????? ???????????? ??????????"/>
                        </div>
                        <div class="form-group">
                            <select name="tag" size="1" class="rounded">
                                <option value="Nothing">???????????????? ????????</option>
                                <option value="Books">??????????</option>
                                <option value="Games">????????</option>
                                <option value="Music">????????????</option>
                                <option value="Films">????????????</option>
                                <option value="Sport">??????????</option>
                                <option value="IT">????</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input required type="text" pattern="^[a-zA-Z]+$" class="form-control" name="hashtag"
                                   placeholder="?????????????? ??????-??????(???????????? ?????????????????? ??????????)"/>
                        </div>
                        <div class="form-group" style="width:  100%;height: 100%;">
                            <label style="width:  100%;height: 100%;">
                        <textarea required minlength="5" type="text" class="form-control" name="text" style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="?????????????? ????????"></textarea>
                            </label>
                        </div>
                        <div class="form-group">
                            <div class="custom-file">
                                <input type="file" name="file" id="customFile">
                                <label class="custom-file-label" for="customFile">???????????????? ????????</label>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">????????????????</button>
                        </div>
                    </form>
                </div>
            </div>
        </#if>

        <div style="height: 400px; width: 900px">
            <#list messages as message>
                <div class="card my-3">
                    <h1 class="title"> ${message.name}</h1>
                    <a>${message.averageRate}
                        <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor"
                             class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                        </svg>
                    </a>
                    <div><a class="topic" href="/post/topic/${message.tag}">${message.tag}</a>
                        <#if message.hashtag??>
                            <a href="/post/hashtag/${message.hashtag}">#${message.hashtag}</a>
                        </#if></div>
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
                    <div class="d-flex align-items-end flex-column">
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
                    </div>
                    <div><a class="btn btn-primary ml-2 mt-4 mb-2" href="/post/${message.id}"> ???????????? ??????????????????</a>
                        <#if admin>
                            <a class="btn btn-primary ml-2 mt-4 mb-2"
                               href="/user/profile/update/${message.id}">????????????????</a>
                        </#if>
                    </div>
                    <div class="card-footer text-muted container">
                        <div class="d-flex justify-content-between flex-row align-items-center">
                            <div>
                                <a class="col align-self-center"
                                   href="/user/profile/${message.getAuthor().id}/${message.getAuthor().username}">??????????: ${message.authorName}</a>
                                ??????????????: ${user.getUserRate()}
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
                                            <button type="submit" class="btn btn-primary">??????????????????????</button>
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
                                  placeholder="?????????????? ?????? ??????????????????????"></textarea>
                                        </label>
                                    </div>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">????????????????</button>
                                    </div>
                                </form>
                            </div>

                        </#if>

                    </div>
                </div>
            <#else>
                ?????? ????????????
            </#list>
        </div>
    </#if>
</@c.page>