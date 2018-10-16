<#import "parts/common.ftl" as ht>

<@ht.page>
<form modelAttribute="tenderForm" method="post">

<div>Журнал тендеров</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col">Дата</th>
        <th scope="col">Краткое название</th>
        <th scope="col">Этап</th>
        <th scope="col">Номер и дата приказа</th>
        <th scope="col">Действие</th>
        <th scope="col">Файл</th>

    </tr>
    </thead>

    <#list tenderForm.tenderList as tender>

    <tbody>
    <tr>
        <th scope="row">
        <#if tender.documents>
          <a href="/tender/${tender.numberT}">№ ${tender.numberT}</a>
        <#else>
          № ${tender.numberT}
        </#if>
        </th>
        <td>${tender.dateT?date}</td>
        <td>${tender.nameT}</td>
        <td>${tender.stage}</td>
        <td>№ ${tender.order.numberO} от ${tender.order.dateO?date}</td>
        <td><form action="/journal" method="post" >
            <input type="hidden" name="idtender" value=${tender.idT} />
            <button type="submit">Выполнить импорт</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form></td>
        <td><#if tender.filename??> <a href="/xlsx/${tender.filename}" download="">${tender.filename?keep_after(".")}</a><#else>
          <a href="/editTender/${tender.numberT}">Добавить файл</a> </#if>
        </td>
    </tr>
    </tbody>

</#list>
</table>


<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Добавить тендер
</a>
<div class="collapse <#if tender??>show</#if>" id="collapseExample">
<#include "parts/addtender.ftl" />
</div>

</@ht.page>
