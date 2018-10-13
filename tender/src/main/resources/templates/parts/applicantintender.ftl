<form modelAttribute="subjectAndDocumentsForm"  method="post">

    <#list subjectAndDocumentsForm.documentsList as documents>

    <a class="btn btn-primary btn-lg col-sm-5" data-toggle="collapse" href="#collapseExample${documents?index}" role="button" aria-expanded="false" aria-controls="collapseExample">
        ${documents.applicant.nameA}
    </a>
<!--Applicant-->
    <div class="collapse" id="collapseExample${documents?index}">
        <div class="card card-body">
            <form>
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
            </form>
<!--documents <input type="hidden" name="_documentsList[${documents?index}].registration" value="on" />-->
            <form class="was-validated">
                <div class="mb-3">
                    <input type="hidden" value="#{documents.idD}" name="documentsList[${documents?index}].idD" />
                    <input type="checkbox"
                           value='${documents.registration?c}'
                           name="documentsList[${documents?index}].registration"  />

                    <label >Сертификат регистрации</label>
                    <div class="invalid-feedback">Example invalid feedback text</div>
                </div>
                <div class="mb-3">
                    <input type="checkbox"
                           value='${documents.charter?c}'
                           name="documentsList[${documents?index}].charter" >
                    <label >Устав предприятия</label>
                    <div class="invalid-feedback">Example invalid feedback text</div>
                </div>
                <div class="custom-control custom-checkbox mb-3">
                    <input type="checkbox"
                           value='${documents.bankreference?c}'
                           name="documentsList[${documents?index}].bankreference" class="custom-control-input" id="documentsBankreference" required>
                    <label class="custom-control-label" for="documentsBankreference">Спарвка из банка</label>
                    <div class="invalid-feedback">Example invalid feedback text</div>
                </div>
                <div class="custom-control custom-checkbox mb-3">
                    <input type="checkbox"
                           value='${documents.dealer?c}'
                           name="documentsList[${documents?index}].dealer" class="custom-control-input" id="documentsDealer" required>
                    <label class="custom-control-label" for="documentsDealer">Дилерский сертификат</label>
                    <div class="invalid-feedback">Example invalid feedback text</div>
                </div>
                <div class="custom-control custom-checkbox mb-3">
                    <input type="checkbox"
                           value='${documents.product?c}'
                           name="documentsList[${documents?index}].product" class="custom-control-input" id="documentsProduct" required>
                    <label class="custom-control-label" for="documentsProduct">Сертификат товара</label>
                    <div class="invalid-feedback">Example invalid feedback text</div>
                </div>
                <div class="custom-control custom-checkbox mb-3">
                    <input type="checkbox"
                           value='${documents.feedback?c}'
                           name="documentsList[${documents?index}].feedback" class="custom-control-input" id="documentsFeaadback" required>
                    <label class="custom-control-label" for="documentsFeaadback">Отзыв</label>
                    <div class="invalid-feedback">Example invalid feedback text</div>
                </div>
            </form>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

    </div>
    <p></p>

</#list>

</form>
