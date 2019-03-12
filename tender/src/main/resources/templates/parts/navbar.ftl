<#include "security.ftl">
<#import "login.ftl" as l>
<nav class="navbar navbar-expand-xl navbar-dark bg-primary" xmlns="http://www.w3.org/1999/html">
    <a class="navbar-brand" href="/">Холмеч Тендер</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
                <a class="nav-link" href="/">Главная</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="/journal">Журнал</a>
            </li>
           <!-- <li class="nav-item ">
                <a class="nav-link" href="/subject">Субъект</a>
            </li>-->
            <#if isAdmin>
            <li class="nav-item ">
                <a class="nav-link" href="/user">UserList</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="/mailtest">MailTest</a>
            </li>
        </#if>

        <#if user??>
        <li class="nav-item">
            <a class="nav-link" href="/user/profile">Profile</a>
        </li>
        </#if>

    </ul>
    <div class="navbar-text mr-3">${name}</div>
    <#if user??>
        <@l.logout />
    <#else>
        <a href="/login" class="btn btn-primary " role="button" aria-pressed="true">Sing in</a>
    </#if>
    </div>
</nav>