<#import "parts/common.ftl" as ht>
<@ht.page>

<nav>
    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <a class="nav-item nav-link active" id="nav-tender-tab" data-toggle="tab" href="#nav-tender" role="tab"
           aria-controls="nav-tender" aria-selected="true">Работа с тендером</a>
        <a class="nav-item nav-link" id="nav-editTender-tab" data-toggle="tab" href="#nav-editTender" role="tab"
           aria-controls="nav-editTender" aria-selected="false">Редактировать тендер</a>
    </div>
</nav>

<div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-tender" role="tabpanel" aria-labelledby="nav-tender-tab">
        <table class="table table-bordered table-hover table-sm table-primary">
            <thead>
            <tr>
                <th>Этап</th>
                <th>Дествия</th>
                <th>Файлы</th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>Вскрытие</td>
                <td>Сформировать протокол вскртия</td>
                <td>Протокол</td>
            </tr>
            <tr>
                <td>Снижение цены</td>
                <td>Пригласить участников на процедуру снижения цены</td>
                <td>Приглашения</td>
            </tr>
            <tr>
                <td rowspan="2">Принятия решения</td>
                <td>Сформировать протокол принятия решения</td>
                <td>Протокол</td>
            </tr>
            <tr>
                <td>Сообщить участникам о результатах</td>
                <td>Результат</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="tab-pane fade" id="nav-editTender" role="tabpanel" aria-labelledby="nav-editTender-tab">
        <#include "parts/addtender.ftl" />
    </div>
</div>


</@ht.page>
