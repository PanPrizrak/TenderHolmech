<form modelAttribute="subjectAndDocumentsForm" method="post">

    <#list subjectAndDocumentsForm.documentsList as documents>
    <a class="btn btn-primary btn-lg col-sm-5" data-toggle="collapse" href="#collapseExample${documents?index}" role="button" aria-expanded="false" aria-controls="collapseExample">
        ${documents.applicant.nameA}
    </a>
<!--Applicant-->
    <div class="collapse" id="collapseExample${documents?index}">
        <div class="card card-body">

                <div class="form-group row">
                    <label for="applicantNameA" class="col-sm-2 col-form-label col-form-label-lg">Наименование предприятия</label>
                    <div class="col-sm-10">
                        <input type="hidden" value="#{documents.applicant.idA}" name="documentsList[${documents?index}].applicant.idA" />
                        <input type="text" value='${documents.applicant.nameA?if_exists}'
                               name="documentsList[${documents?index}].applicant.nameA"
                               class="form-control form-control-lg" id="applicantNameA"
                               placeholder="Наименование предприятия">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="applicantAddress" class="col-sm-2 col-form-label col-form-label-lg">Адрес предприятия</label>
                    <div class="col-sm-10">
                        <input type="text" value='${documents.applicant.address?if_exists}'
                               name="documentsList[${documents?index}].applicant.address"
                               class="form-control form-control-lg" id="applicantAddress"
                               placeholder="Адрес предприятия">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="applicantPAN" class="col-sm-2 col-form-label col-form-label-lg">УНП предприятия</label>
                    <div class="col-sm-10">
                        <input type="text" value='${documents.applicant.pan?if_exists}'
                               name="documentsList[${documents?index}].applicant.pan"
                               class="form-control form-control-lg" id="applicantPAN"
                               placeholder="УНП предприятия">
                    </div>
                </div>

<!--documents <input type="hidden" name="_documentsList[${documents?index}].registration" value="on" />-->

                <div class="form-group row">
                <h1>Претендент ${documents.applicant.nameA} предоставил следующие документы:</h1>
                    <input type="hidden" value="#{documents.idD}" name="documentsList[${documents?index}].idD" />
                    ${documents.registration?c}
                    <label >Сертификат регистрации:</label>
                    <#assign trueBuf = "${documents.registration?then('checked',' ')}"
                             falseBuf = "${documents.registration?then(' ','checked')}"
                    >
                    <input type="radio" name="documentsList[${documents?index}].registration"
                           value="true" ${trueBuf}>Да</input>
                    <input type="radio" name="documentsList[${documents?index}].registration"
                           value="false" ${falseBuf}>Нет</input>
                </div>
                <div class="form-group row">
                    <label >Устав предприятия:</label>
                    <#assign trueBuf = "${documents.charter?then('checked',' ')}"
                            falseBuf = "${documents.charter?then(' ','checked')}"
                    >
                    <input type="radio" name="documentsList[${documents?index}].charter"
                           value="true" ${trueBuf}>Да</input>
                    <input type="radio" name="documentsList[${documents?index}].charter"
                           value="false" ${falseBuf}>Нет</input>
                </div>
                <div class="form-group row">
                    ${documents.bankreference?c}
                    <label>Спарвка из банка:</label>
                    <#assign trueBuf = "${documents.bankreference?then('checked',' ')}"
                            falseBuf = "${documents.bankreference?then(' ','checked')}"
                    >
                    <input type="radio" name="documentsList[${documents?index}].bankreference"
                           value="true" ${trueBuf}>Да</input>
                    <input type="radio" name="documentsList[${documents?index}].bankreference"
                           value="false" ${falseBuf}>Нет</input>
                </div>
                <div class="form-group row">
                    <label>Дилерский сертификат:</label>
                    <#assign trueBuf = "${documents.dealer?then('checked',' ')}"
                            falseBuf = "${documents.dealer?then(' ','checked')}"
                    >
                    <input type="radio" name="documentsList[${documents?index}].dealer"
                           value="true" ${trueBuf}>Да</input>
                    <input type="radio" name="documentsList[${documents?index}].dealer"
                           value="false" ${falseBuf}>Нет</input>
                </div>
                <div class="form-group row">
                    <label>Сертификат товара:</label>
                    <#assign trueBuf = "${documents.product?then('checked',' ')}"
                            falseBuf = "${documents.product?then(' ','checked')}"
                    >
                    <input type="radio" name="documentsList[${documents?index}].product"
                           value="true" ${trueBuf}>Да</input>
                    <input type="radio" name="documentsList[${documents?index}].product"
                           value="false" ${falseBuf}>Нет</input>
                </div>
                <div class="form-group row">
                    <label>Отзыв:</label>
                    <#assign trueBuf = "${documents.feedback?then('checked',' ')}"
                            falseBuf = "${documents.feedback?then(' ','checked')}"
                    >
                    <input type="radio" name="documentsList[${documents?index}].feedback"
                           value="true" ${trueBuf}>Да</input>
                    <input type="radio" name="documentsList[${documents?index}].feedback"
                           value="false" ${falseBuf}>Нет</input>
                </div>

        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

    </div>
    <p></p>

    </#list>

<div class="form-group">
    <button type="submit" class="btn btn-primary">Добавить</button>
</div>
<input type="hidden" name="_csrf" value="${_csrf.token}"/>

</form>
