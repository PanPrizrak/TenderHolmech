<#include "security.ftl">
<#import "login.ftl" as l>
<nav class="navbar navbar-expand-xl navbar-dark bg-primary" xmlns="http://www.w3.org/1999/html">
    <a class="navbar-brand" href="/">WebFB</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="/add">Add</a>
            </li>
            <#if isAdmin>
            <li class="nav-item ">
                <a class="nav-link" href="/user">UserList</a>
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
        <button class="btn btn-primary" type="submit"><a href="/login">Sing in</a></button>
    </#if>
    </div>
</nav>