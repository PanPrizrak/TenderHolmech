<form modelAttribute="subjectForm"  method="post">
<table class="table">
    <thead>
    <tr>
        <th scope="col">№ лота</th>
        <th scope="col">Название предприятия</th>
        <th scope="col">Отсрочка</th>
        <th scope="col">Название продукта</th>
        <th scope="col">Ед. изм.</th>
        <th scope="col">Количество</th>
        <th scope="col">Цена. за ед. с НДС</th>
        <th scope="col">Код ОКРБ</th>
        <th scope="col">Условия поставки</th>
    </tr>
    </thead>

    <#list subjectForm.subjectList as subject>

    <tbody>
    <tr>
        <td></td>
        <th scope="row">${subject.numberS}</th>
        <td>${subject.applicant.nameA}</td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.payment?if_exists}"
                   name="payment"
                   placeholder="Отсрочка"/>
        </td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.nameS?if_exists}"
                   name="subjectList[${subject?index}].nameS"
                   placeholder="Название"/>
        </td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.units?if_exists}"
                   name="subjectList[${subject?index}].units"
                   placeholder="ед. изм."/>
        </td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.amount?if_exists}"
                   name="subjectList[${subject?index}].amount"
                   placeholder="количество"/>
        </td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.price?if_exists}"
                   name="subjectList[${subject?index}].price"
                   placeholder="цена"/>
        </td>
           <td>
            <input type="text" class="form-control"
                   value="${subject.code?if_exists}"
                   name="subjectList[${subject?index}].code"
                   placeholder="код ОКРБ"/>
        </td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.delivery?if_exists}"
                   name="subjectList[${subject?index}].delivery"
                   placeholder="условия поставки"/>
        </td>
    </tr>
    </tbody>

</#list>
</table>

    <div class="form-group">
        <button type="submit" class="btn btn-primary">Добавить</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

