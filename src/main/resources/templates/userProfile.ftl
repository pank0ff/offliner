<#import "parts/common.ftl" as c>

<@c.page>

    <div class="container mt-5 mb-5">
        <div class="row no-gutters">
            <div class="col-md-4 col-lg-4">
                <#if user.avatarFilename??>
                    <img src="/img/${user.avatarFilename}">
                </#if>
            </div>
            <div class="col-md-8 col-lg-8">
                <div class="d-flex flex-column">
                    <div class="d-flex flex-row justify-content-between align-items-center p-5 bg-black text-white">
                        <h3 class="display-5">${user.getUsername()}</h3>
                        <#if user.getLinkFacebook()??>
                            <a href="${user.getLinkFacebook()}">
                                <i class="fa fa-facebook" ></i>
                            </a>
                        </#if>
                        <#if user.getLinkGoogle()??>
                            <a href="${user.getLinkGoogle()}">
                                <i class="fa fa-google" ></i>
                            </a>
                        </#if>
                        <#if user.getLinkYoutube()??>
                            <a href="${user.getLinkYoutube()}">
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
                        <#if user.aboutMyself??>
                            <h6> ${user.aboutMyself}</h6>
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
            <#if admin>
                <a class="btn btn-primary  mt-1"  href="/user/profile/${user.username}/settings">Settings</a>
            </#if>
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/user/profile/${user.username}/else" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Search by topic">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
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
                <div> <a class="btn btn-primary ml-2 mt-4 mb-2"   href = "/post/${message.id}"> Read full</a>
                    <#if admin>
                    <a class="btn btn-primary ml-2 mt-4 mb-2" href="/user/profile/update/${message.id}">Edit</a>
                    </#if>
                </div>
                <div class="card-footer text-muted">
                   <a class="col align-self-center"> ${message.authorName} </a>
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