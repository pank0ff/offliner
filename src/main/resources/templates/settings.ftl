<#import "parts/common.ftl" as c>

<@c.page>

        <form method="post" action ="/user/profile/${username}/settings/" enctype="multipart/form-data">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-6">
                    <input type="password" name="password" class="form-control" placeholder="Password" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}" />
                </div>
            </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">About myself:</label>
                    <div class="col-sm-6">
                    <input maxlength="255" type="text" name="aboutMyself" class="form-control" placeholder="about yourself" value="${aboutMyself!''}" />
                    </div>
                </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Facebook</label>
                <div class="col-sm-6">
                    <input maxlength="255" type="url" name="linkFacebook" class="form-control" placeholder="facebook" value="${linkFacebook!''}" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Google:</label>
                <div class="col-sm-6">
                    <input maxlength="255" type="url" name="linkGoogle" class="form-control" placeholder="google" value="${linkGoogle!''}" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Youtube:</label>
                <div class="col-sm-6">
                    <input maxlength="255" type="url" name="linkYoutube" class="form-control" placeholder="youtube" value="${linkYoutube!''}" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Dribble:</label>
                <div class="col-sm-6">
                    <input maxlength="255" type="url" name="linkDribble" class="form-control" placeholder="dribble" value="${linkDribble!''}" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">LinkedIn:</label>
                <div class="col-sm-6">
                    <input maxlength="255" type="url" name="linkLinkedIn" class="form-control" placeholder="linkedIn" value="${linkLinkedIn!''}" />
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Your avatar:</label>
                <div class="col-sm-6">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button class="btn btn-primary" type="submit">Save</button>
        </form>

    <form method="post" action = "/user/profile/${username}/settings/delete">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-danger mt-5" type="submit" >Delete</button>
    </form>
    <div><h5><b style="color: red">To see that the changes have taken effect re-login.</b></h5></div>

</@c.page>