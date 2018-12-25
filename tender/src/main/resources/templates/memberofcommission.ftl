<#import "parts/common.ftl" as ht>


<@ht.page>

<form method="post" >
    <div class="form-group mt-3">
<!--order.number-->
        <div class="form-group">
            <div class="row">
                <span class="input-group-text col-sm-2">Приказ №${order.numberO?if_exists} от ${order.dateO?iso_local?date}</span>
                <input type="hidden" value="${order.idO}" name="order.idO"/>
            </div>
    </div>

    <div class="form-group row" >
        <span class="input-group-text col-sm-3">Председатель тендерной комисии</span>
        <select name="thechairman" class="form-control col-sm-4">
        <#list workers as worker>
                <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
        </#list>
            </select>
    </div>

    <div class="form-group row">
        <span class="input-group-text col-sm-3">Зам. председателя  комисии</span>
        <select name="vicechairman" class="form-control col-sm-4">
        <#list workers as worker>
            <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
    </#list>
        </select>
    </div>

    <div class="form-group row">
        <span class="input-group-text col-sm-3">Секретарь тендерной комисии</span>
        <select name="secretary" class="form-control col-sm-4">
        <#list workers as worker>
            <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
        </#list>
        </select>
    </div>

    <div class="form-group row">
        <span class="input-group-text col-sm-3">Члены тендерной комисии</span>
        <select multiple name="commissionmember" class="form-control col-sm-4">
        <#list workers as worker>
            <option value="#{worker.idW}">${worker.surname+" "+worker.nameW+" "+worker.patronymic}</option>
        </#list>
        </select>
    </div>
        <a href="/addworker/${numberT}">Добавит работника</a>
    <div class="form-group">
        <button type="submit" name="save" class="btn btn-primary">Сохранить</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>

    </div>
</form>

</@ht.page>