<#import "parts/common.ftl" as ht>

<@ht.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/journal" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Поиск по названию" />
            <button type="submit" class="btn btn-primary ml-2">Поиск</button>
        </form>
    </div>
</div>


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
        <th scope="row">${tender.numberT}</th>
        <td>${tender.dateT?date}</td>
        <td>${tender.nameT}</td>
        <td>${tender.stage}</td>
        <td>№ ${tender.order.numberO} от ${tender.order.dateO?date}</td>
        <td><form action="journal/delete" method="post" >
            <input type="hidden" name="idtender" value=${tender.id} />
            <button type="submit">Удалить</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form></td>
    </tr>
    </tbody>

</#list>
</table>


<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Добавить тендер
</a>
<div class="collapse <#if tender??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form action="journal" method="post" enctype="multipart/form-data">
<!--number-->
    <div class="form-group">
                <input type="text" class="form-control ${(numberTError??)?string('is-invalid', '')} col-sm-4"
                       value="<#if tender??>${tender.numberT}</#if>" name="numberT" placeholder="Введите номер"/>
                <#if numberTError??>
                    <div class="invalid-feedback">
                        ${numberTError}
                    </div>
                </#if>
    </div>
<!--name-->
    <div class="form-group">
        <input type="text" class="form-control ${(nameTError??)?string('is-invalid', '')} col-sm-4"
               value="<#if tender??>${tender.nameT}</#if>" name="nameT" placeholder="Введите краткое название"/>
        <#if nameTError??>
        <div class="invalid-feedback">
            ${nameTError}
        </div>
    </#if>
    </div>
<!--date-->
    <div class="form-group">
        <input type="date" class="form-control col-sm-2" id="date" name="dateT" placeholder="Дата" required>
    </div>
<!--stage-->
<div class="form-group">
    <input type="text" class="form-control ${(stageError??)?string('is-invalid', '')} col-sm-4"
           value="<#if tender??>${tender.stage}</#if>" name="stage" placeholder="Введите этап"/>
    <#if stageError??>
    <div class="invalid-feedback">
        ${stageError}
    </div>
</#if>
</div>
<!--priceFactor-->
<div class="form-group">
    <input type="text" class="form-control ${(priceFactorError??)?string('is-invalid', '')} col-sm-4"
           value="<#if tender??>${tender.priceFactor}</#if>" name="priceFactor" placeholder="Введите фатор цены"/>
    <#if priceFactorError??>
    <div class="invalid-feedback">
        ${priceFactorError}
    </div>
</#if>
</div>
<!--paymentFactor-->
<div class="form-group">
    <input type="text" class="form-control ${(paymentFactorError??)?string('is-invalid', '')} col-sm-4"
           value="<#if tender??>${tender.paymentFactor}</#if>" name="paymentFactor" placeholder="Введите фактор условия оплаты"/>
    <#if paymentFactorError??>
    <div class="invalid-feedback">
        ${paymentFactorError}
    </div>
</#if>
</div>
<!--filename-->
    <div class="form-group">
    <div class="custom-file">
        <input type="file" class="custom-file-input" id="customFile" name="file">
        <label class="custom-file-label col-sm-4" for="customFile">Выберите файл</label>
    </div>
</div>
<!--order.number-->
<div class="form-group">
    <input type="text" class="form-control ${(numberOError??)?string('is-invalid', '')} col-sm-4"
           value="<#if order??>${order.numberO}</#if>" name="order.namberO" placeholder="Введите номер приказа"/>
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
            <input type="date" class="form-control col-sm-2" id="date" name="dateO" placeholder="Дата"  required/><!---->
        </div>
    </div>
</div>

<div class="form-group">
    <button type="submit" class="btn btn-primary">Добавить</button>
</div>
<input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</div>
</div>





</@ht.page>