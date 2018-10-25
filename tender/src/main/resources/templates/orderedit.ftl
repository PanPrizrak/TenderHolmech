<#import "parts/common.ftl" as ht>


<@ht.page>
<form method="post"><!--modelAttribute="enumForm"-->

    <!--order.number-->
    <div class="form-group">
        <input type="text" class="form-control ${(numberOError??)?string('is-invalid', '')} col-sm-4"
               value="${tenderForm.tenderList[0].order.numberO?if_exists}" name="tenderList[0].order.numberO" placeholder="Введите номер приказа"/>
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
                <input type="date" value="${tenderForm.tenderList[0].order.dateO?iso_local?date}" class="form-control col-sm-2" id="date" name="tenderList[0].order.dateO" required/>
            </div>
        </div>
    </div>

    <div>
        <#include "parts/addworker.ftl" />
    </div>

    <div class="form-group">
        <label>Председатель тендерной комисии</label>
        <select name="surname">
    </div>

    <select name="rolesName">
    <#list roles as wrokerrole>
        <option value="${wrokerrole}">${wrokerrole}</option>
    </#list>
    </select>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Добавить</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

</@ht.page>