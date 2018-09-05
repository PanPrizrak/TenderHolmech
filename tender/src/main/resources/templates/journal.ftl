<#import "parts/common.ftl" as ht>

<@ht.page>
<div>Журнал тендеров</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col">Дата</th>
        <th scope="col">Краткое название</th>
        <th scope="col">Этап</th>
        <th scope="col">Номер и дата приказа</th>

    </tr>
    </thead>

    <#list tenders as tender>




    <tbody>
    <tr>
        <th scope="row">${tender.number}</th>
        <td>${tender.date}</td>
        <td>${tender.name}</td>
        <td>${tender.stage}</td>
        <td>${tender.order.date} + ${tender.order.number}</td>
        <td><#if worker.filename??> <img class="img-fluid max-width: 50%" src = "/img/${worker.filename}" /><#else>
            No message </#if> </td>
        <td><form action="delete" method="post" >
            <input type="hidden" name="idworker" value=${worker.id} />
            <button type="submit">Удалить</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form></td>
    </tr>
    </tbody>



</#list>
</table>

</@ht.page>