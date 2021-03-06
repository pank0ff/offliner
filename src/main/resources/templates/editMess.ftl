<#import "parts/common.ftl" as c>

<@c.page>
    <#if lang>
        <form method="post" action="/user/profile/update/${message.id}" enctype="multipart/form-data">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Name:</label>
                <div class="col-sm-6">
                    <input required type="text" name="name" class="form-control" placeholder="Name"
                           value="${message.name!''}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Topic:</label>
                <div class="col-sm-6">
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
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Hashtag:</label>
            <div class="col-sm-6">
                <input required type="text" name="hashtag" class="form-control" placeholder="hashtag(#..)" value="${message.hashtag!''}" />
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" >Text:</label>
            <label style="padding-left: 15px;
             width:  80%;height: 100%;">
                        <textarea required minlength="5"  type="text" class="form-control" name="text" style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="Enter your post" >${message.text}</textarea>
            </label>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Save</button>
        </form>
        <form method="post" action="/user/profile/update/${message.id}/delete">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if theme>
                <button class="btn btn-danger mt-5" type="submit">Delete</button>
            <#else>
                <button style="filter: invert()" class="btn btn-danger mt-5" type="submit">Delete</button>
            </#if>
        </form>
        <#if theme>
            <div>
                <h5><b style="color: red">Check topic field</b></h5>
            </div>

        <#else>
            <div style="filter:invert()">
                <h5><b style="color: red">Check topic field</b></h5>
            </div>
        </#if>

    <#else>
        <form method="post" action="/user/profile/update/${message.id}" enctype="multipart/form-data">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">??????:</label>
                <div class="col-sm-6">
                    <input required type="text" name="name" class="form-control" placeholder="??????"
                           value="${message.name!''}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">????????:</label>
                <div class="col-sm-6">
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
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">??????-??????:</label>
                <div class="col-sm-6">
                    <input required type="text" name="hashtag" class="form-control" placeholder="??????-??????(#..)"
                           value="${message.hashtag!''}"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">??????????:</label>
                <label style="padding-left: 15px;
             width:  80%;height: 100%;">
                        <textarea required minlength="5" type="text" class="form-control" name="text" style="width:  100%;
                            height: 100%;
                            padding: 5px 10px 5px 10px;
                            border:1px solid #999;
                            font-size:16px;
                            font-family: Tacoma,serif"
                                  placeholder="?????????????? ???????? ????????">${message.text}</textarea>
                </label>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">???????????????? ????????</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">??????????????????</button>
        </form>
        <form method="post" action="/user/profile/update/${message.id}/delete">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if theme>
                <button class="btn btn-danger mt-5" type="submit">??????????????</button>
            <#else>
                <button class="btn btn-danger mt-5" style="filter: invert()" type="submit">??????????????</button>
            </#if>
        </form>
        <#if theme>
            <div>
                <h5><b style="color: red">?????????????????? ???????? ????????</b></h5>
            </div>

        <#else>
            <div style="filter:invert()">
                <h5><b style="color: red">?????????????????? ???????? ????????</b></h5>
            </div>
        </#if>
    </#if>
</@c.page>