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
       Этап     / Дествия                       /           Файлы
       Вскрытие / Сформировать протокол вскртия /           Протокол
       Снижение
        цены    / Пригласить участников на
                    процедуру снижения цены     /           Приглашения
       Принятия
        решения / Сформировать протокол принятия
                    решения                                 Протокол принятия решения
                  Сообщить участникам о
                    результатах                 /           Результат

    </div>
    <div class="tab-pane fade" id="nav-editTender" role="tabpanel" aria-labelledby="nav-editTender-tab">
        <#include "parts/addtender.ftl" />
    </div>
</div>


</@ht.page>
