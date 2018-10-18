<#import "parts/common.ftl" as ht>

<@ht.page>
<form modelAttribute="tenderForm" method="post" enctype="multipart/form-data">

<div>Журнал тендеров</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col">Дата</th>
        <th scope="col">Краткое название</th>
        <th scope="col">Этап</th>
        <th scope="col">Номер и дата приказа</th>
        <th scope="col">Документы</th>

    </tr>
    </thead>

    <#list tenderForm.tenderList as tender>

    <tbody>
    <#if tender?index gt 0>
    <tr>
        <th scope="row">
        <#if tender.documents>
          <a href="/sad/${tender.numberT}">№ ${tender.numberT}</a><!-- sad - subject and documents-->
        <#else>
          № ${tender.numberT}
        </#if>
        </th>
        <td>${tender.dateT?date}</td>
        <td> <a href="/tender/${tender.numberT}">${tender.nameT}</a></td>
        <td>${tender.stage}</td>
        <td>№ ${tender.order.numberO} от ${tender.order.dateO?date}</td>
        <td><#if tender.filename??> <a href="/xlsx/${tender.filename}" download="">${tender.filename?keep_after(".")}</a><#else>
          Нет документов </#if>
        </td>
    </tr>
    </#if>
    </tbody>


</#list>
</table>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Добавить тендер
</a>
<div class="collapse <#if tender??>show</#if>" id="collapseExample">
<#include "parts/addtender.ftl" />
</div>
</form>
</@ht.page>
