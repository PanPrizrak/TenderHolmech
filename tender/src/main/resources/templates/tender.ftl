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
        <form modelAttribute="tenderForm" id="myForm" method="post">
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
                    <td>
                        <button type="button" class="btn btn-outline-primary"
                                onclick="document.getElementById('valueActions').value='Generate autopsy protocol';document.getElementById('myForm').submit()">
                            Сформировать протокол вскртия
                        </button>
                    </td>
                    <td>Протокол</td>
                </tr>
                <tr>
                    <td>Снижение цены</td>
                    <!--Applicant-->
                        <td>
                        <input type="text" name="numbersMessage" placeholder="номер первого сообщения"/>
                        <input type="text" name="dateOfDecline" placeholder="дата снижения"/>
                        <button type="button" class="btn btn-outline-primary"
                                onclick="document.getElementById('valueActions').value='Invite members to the price reduction procedure'; document.getElementById('myForm').submit()">
                            Пригласить участников на процедуру снижения цены
                        </button>
                        </td>

                    <td>
                        <#if tenderForm.tenderList[0].filename??> <a href="/xlsx/${tenderForm.tenderList[0].numberT}/price reductionK/${tenderForm.tenderList[0].numberT}_reductionK.zip" download="">${tenderForm.tenderList[0].numberT}_reductionK.zip</a>
                        <#else>
                        Приглашение
                    </#if>
                    </td>
                </tr>
                <tr>
                    <td rowspan="2">Принятия решения</td>
                    <td>
                        <button type="button" class="btn btn-outline-primary"
                                onclick="document.getElementById('valueActions').value='Form a decision protocol'; document.getElementById('myForm').submit()">
                            Сформировать протокол принятия решения
                        </button>
                    </td>
                    <td>Протокол</td>
                </tr>
                <tr>
                    <td>
                        <button type="button" class="btn btn-outline-primary"
                                onclick="document.getElementById('valueActions').value='Notify participants about the results'; document.getElementById('myForm').submit()">
                            Сообщить участникам о результатах
                        </button>
                    </td>
                    <td>Результат</td>
                </tr>
                </tbody>
            </table>
            <input type="hidden" name="action" id="valueActions" value=""/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
    <div class="tab-pane fade" id="nav-editTender" role="tabpanel" aria-labelledby="nav-editTender-tab">
        <#include "parts/addtender.ftl" />
    </div>
</div>


</@ht.page>
