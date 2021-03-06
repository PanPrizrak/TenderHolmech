<div class="form-group mt-3">
<form modelAttribute="tenderForm" method="post" enctype="multipart/form-data">

<!--number-->
    <div class="form-group">
        <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">№</span>
                </div>
                <input type="text" id="numberTender" class="form-control ${(numberTError??)?string('is-invalid', '')} col-sm-4"
                       value="${tenderForm.tenderList[0].numberT?if_exists}" name="tenderList[0].numberT" placeholder="Введите номер"/>
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
               value="${tenderForm.tenderList[0].nameT?if_exists}" name="tenderList[0].nameT" placeholder="Введите краткое название"/>
        <#if nameTError??>
        <div class="invalid-feedback">
            ${nameTError}
        </div>
        </#if>
    </div>
<!--date-->



    <div class="form-group">
        <input type="date" value="${tenderForm.tenderList[0].dateT?iso_local?date}" required="true" class="form-control col-sm-2" id="date" name="tenderList[0].dateT" />
    </div>
<!--stage-->
<div class="form-group">
    <input type="text" class="form-control ${(stageError??)?string('is-invalid', '')} col-sm-4"
           value="${tenderForm.tenderList[0].stage?if_exists}" name="tenderList[0].stage" placeholder="Введите этап"/>
    <#if stageError??>
    <div class="invalid-feedback">
        ${stageError}
    </div>
</#if>
</div>
<!--priceFactor-->
<div class="form-group">
    <input type="text" class="form-control ${(priceFactorError??)?string('is-invalid', '')} col-sm-4"
           value="${tenderForm.tenderList[0].priceFactor?if_exists}" name="tenderList[0].priceFactor" placeholder="Введите фатор цены"/>
    <#if priceFactorError??>
    <div class="invalid-feedback">
        ${priceFactorError}
    </div>
</#if>
</div>
<!--paymentFactor-->
<div class="form-group">
    <input type="text" class="form-control ${(paymentFactorError??)?string('is-invalid', '')} col-sm-4"
           value="${tenderForm.tenderList[0].paymentFactor?if_exists}" name="tenderList[0].paymentFactor" placeholder="Введите фактор условия оплаты"/>
    <#if paymentFactorError??>
    <div class="invalid-feedback">
        ${paymentFactorError}
    </div>
</#if>
</div>
<!--filename-->
    <div class="form-group">
    <div class="custom-file">
        <input type="file" class="custom-file-input" id="customFile" name="file"/>
        <label class="custom-file-label col-sm-4" for="customFile">Выберите файл</label>
    </div>
</div>

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
            <input type="date" value="${tenderForm.tenderList[0].order.dateO?iso_local?date}" required="true" class="form-control col-sm-2" id="date" name="tenderList[0].order.dateO" />
        </div>
    </div>
</div>

<div class="form-group">
    <button type="submit" class="btn btn-primary">Добавить</button>
</div>
<input type="hidden" name="_csrf" value="${_csrf.token}"/>

</form>
</div>
