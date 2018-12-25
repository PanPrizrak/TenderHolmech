<#import "parts/common.ftl" as ht>

<@ht.page>

<form method="post" >
    <div class="form-group mt-3">
<!--order.number-->
        <div class="form-group">
            <div class="row">
                <span class="input-group-text col-sm-2">№ Приказа</span>
                <input type="text" class="form-control ${(numberOError??)?string('is-invalid', '')} col-sm-2"
                       value="${order.numberO?if_exists}" name="numberO"
                       placeholder="Введите номер приказа"/>
            </div>
            <#if numberOError??>
            <div class="invalid-feedback">
                ${numberOError}
            </div>
            </#if>
    </div>

<!--order.date-->
    <div class="form-group">
        <div class="row">
            <span class="input-group-text col-sm-2">Дата приказа</span>
            <input type="date" value="${order.dateO?iso_local?date}"
                   class="form-control col-sm-2" id="date" name="dateO" required/>
        </div>
    </div>
<!--order.ido-->
    <input type="hidden" value="${order.idO}" name="order.idO"/>
    <a href="/memberofcommission/${numberT}">Редактировать тендерную комиссию</a>
    <div class="form-group">
        <button type="submit" name="save" class="btn btn-primary">Сохранить</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>
    <input type="hidden" value="${order.idO}" name="order.idO"/>
    </div>
</form>

</@ht.page>