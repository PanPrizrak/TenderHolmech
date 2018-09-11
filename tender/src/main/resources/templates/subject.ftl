<#import "parts/common.ftl" as ht>

<@ht.page>

<div>Организации</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col">Название</th>
        <th scope="col">Адрес</th>
        <th scope="col">УНП</th>
    </tr>
    </thead>

    <#list applicants as applicant>

    <tbody>
    <tr>
        <th scope="row">${applicant.id}</th>
        <td>${applicant.nameA}</td>
        <td>${applicant.address?if_exists}</td>
        <td>${address.pan?if_exists}</td>
    </tr>
    </tbody>

</#list>
</table>

</@ht.page>