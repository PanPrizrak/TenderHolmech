<div>Претенденты</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col">Дата</th>
        <th scope="col">Краткое название</th>
        <th scope="col">Этап</th>
        <th scope="col">Номер и дата приказа</th>
        <th scope="col">Действие</th>
        <th scope="col">Файл</th>

    </tr>
    </thead>

    <#list tenders as tender>

    <tbody>
    <tr>
        <th scope="row">№ ${tender.numberT}</th>
        <td>${tender.dateT?date}</td>
        <td>${tender.nameT}</td>
        <td>${tender.stage}</td>
        <td>№ ${tender.order.numberO} от ${tender.order.dateO?date}</td>
        <td><form action="/journal" method="post" >
            <input type="hidden" name="idtender" value=${tender.id} />
            <button type="submit">Выполнить импорт</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form></td>
        <td><#if tender.filename??> <a href="/xlsx/${tender.filename}" download="">${tender.filename?keep_after(".")}</a><#else>
            No message </#if>
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
        <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">№</span>
                </div>
                <input type="text" id="numberTender" class="form-control ${(numberTError??)?string('is-invalid', '')} col-sm-4"
                       value="${numberT?if_exists}" name="numberT" placeholder="Введите номер"/>
                <#if numberTError??>
                    <div class="invalid-feedback">
                        ${numberTError}
                    </div>
                </#if>
            <script>
            //Код jQuery, установливающий маску для ввода телефона элементу input
            //1. После загрузки страницы,  когда все элементы будут доступны выполнить...
            $(function(){
            //2. Получить элемент, к которому необходимо добавить маску
            $("#numberTender").mask("9999-999999");
            });
            </script>
        </div>
    </div>
<!--name-->
    <div class="form-group">
        <input type="text" class="form-control ${(nameTError??)?string('is-invalid', '')} col-sm-4"
               value="${nameT?if_exists}" name="nameT" placeholder="Введите краткое название"/>
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
           value="${stage?if_exists}" name="stage" placeholder="Введите этап"/>
    <#if stageError??>
    <div class="invalid-feedback">
        ${stageError}
    </div>
</#if>
</div>
<!--priceFactor-->
<div class="form-group">
    <input type="text" class="form-control ${(priceFactorError??)?string('is-invalid', '')} col-sm-4"
           value="${priceFactor?if_exists}" name="priceFactor" placeholder="Введите фатор цены"/>
    <#if priceFactorError??>
    <div class="invalid-feedback">
        ${priceFactorError}
    </div>
</#if>
</div>
<!--paymentFactor-->
<div class="form-group">
    <input type="text" class="form-control ${(paymentFactorError??)?string('is-invalid', '')} col-sm-4"
           value="${paymentFactor?if_exists}" name="paymentFactor" placeholder="Введите фактор условия оплаты"/>
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
           value="${numberO?if_exists}" name="numberO" placeholder="Введите номер приказа"/>
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