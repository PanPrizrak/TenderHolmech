<#import "parts/common.ftl" as ht>

<@ht.page>

<div>Предложения</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">№ лота</th>
        <th scope="col">Название предприятия</th>
        <th scope="col">Отсрочка</th>
        <th scope="col">Ед. изм.</th>
        <th scope="col">Количество</th>
        <th scope="col">Цена. за ед. с НДС</th>
        <th scope="col">Код ОКРБ</th>
        <th scope="col">Условия оплаты</th>
    </tr>
    </thead>

   <!-- <#list applicants as applicant>

    <tbody>
    <tr>
        <th scope="row">${applicant.id}</th>
        <td>${applicant.nameA}</td>
        <td>${applicant.address?if_exists}</td>
        <td>${address.pan?if_exists}</td>
    </tr>
    </tbody>

</#list>-->
</table>

</@ht.page>