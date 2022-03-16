<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    isEng = user.isEng(user.choice)
    isLight = user.isLight(user.theme)
    >
<#else>
    <#assign
    name = "unknown"
    isLight = true
    isEng = true
    isAdmin = false
    >
</#if>
