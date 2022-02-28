<#import "parts/common.ftl" as c>

<@c.page>

    <div class="container mt-5 mb-5">
        <div class="row no-gutters">
            <div class="col-md-4 col-lg-4"><img src="https://i.imgur.com/aCwpF7V.jpg"></div>
            <div class="col-md-8 col-lg-8">
                <div class="d-flex flex-column">
                    <div class="d-flex flex-row justify-content-between align-items-center p-5 bg-black text-white">
                        <h3 class="display-5">${user.username}</h3><i class="fa fa-facebook">facebook</i><i class="fa fa-google">google</i><i class="fa fa-youtube-play">youtube</i><i class="fa fa-dribbble">dribble</i><i class="fa fa-linkedin">linkedin</i>
                    </div>

                    <div class="p-3 bg-blue text-white">
                        <#if user.aboutMyself??>
                        <h6> ${user.aboutMyself}</h6>
                        </#if>
                    </div>

                    <div class="d-flex flex-row text-white">
                        <div class="p-3 bg-primary text-center skill-block">
                            <h5>Posts:</h5>
                             <h6>12</h6>
                        </div>
                        <div class="p-3 bg-success text-center skill-block">
                            <h5>Comments:</h5>
                            <h6>132</h6>
                        </div>
                        <div class="p-3 bg-warning text-center skill-block">
                            <h5>Likes:</h5>
                            <h6>54</h6>

                        </div>
                        <div class="p-3 bg-danger text-center skill-block">
                           <h5>Date of registration:</h5>
                            <h6>22.02.2022</h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

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