<#import "parts/common.ftl" as ht>


<@ht.page>
<div class="form-group mt-3">
    <form method="post" >
        <!--order.number-->
        <input type="hidden" value="${order.idO}" name="order.idO"/>
        <div class="form-group">
            <input type="text" class="form-control ${(numberOError??)?string('is-invalid', '')} col-sm-4"
                   value="${order.numberO?if_exists}" name="numberO"
                   placeholder="Введите номер приказа"/>
            <#if numberOError??>
            <div class="invalid-feedback">
                ${numberOError}
            </div>
        </#if>
</div>
<!--order.date-->
<div class="form-group">
    <div class="container">
        <div class="row">
            <input type="date" value="${order.dateO?iso_local?date}"
                   class="form-control col-sm-2" id="date" name="dateO" required/>
        </div>
    </div>
</div>



<div class="form-group">
    <label>Председатель тендерной комисии</label>
    <select name="thechairman">
        <#list workers as worker>
            <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
        </#list>
    </select>
</div>

<div class="form-group">
    <label>Заместитель председателя тендерной комисии</label>
    <select name="vicechairman">
        <#list workers as worker>
        <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
    </#list>
    </select>
</div>

<div class="form-group">
    <label>Секретарь тендерной комисии</label>
    <select name="secretary">
        <#list workers as worker>
        <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
    </#list>
    </select>
</div>

<div class="form-group">
    <label>Члены тендерной комисии</label>
    <select multiple name="commissionmember">
        <#list workers as worker>
        <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
    </#list>
    </select>
</div>

<div class="form-group">
    <button type="submit" name="save" class="btn btn-primary">Сохранить</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</div>

</form>
</div>
<a class="btn btn-primary" data-toggle="collapse" href="#collapseAddWorker"
   role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Добавить работника
</a>
<div class="collapse " id="collapseAddWorker">
    <form method="post">
        <#include "parts/addworker.ftl" />
    </form>
</div>


</@ht.page>