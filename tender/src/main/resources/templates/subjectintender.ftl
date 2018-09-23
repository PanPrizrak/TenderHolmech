<table class="table">
    <thead>
    <tr>
        <th scope="col">№ лота</th>
        <th scope="col">Название предприятия</th>
        <th scope="col">Отсрочка</th>
        <th scope="col">Ед. изм.</th>
        <th scope="col">Количество</th>
        <th scope="col">Цена. за ед. с НДС</th>
        <th scope="col">Код ОКРБ</th>
        <th scope="col">Условия поставки</th>
    </tr>
    </thead>

    <#list subjects as subject>

    <tbody>
    <tr>

        <td><th scope="row">${subject.numberS}</th>
        <td>${subject.applicant.nameA}</td>
            <!--col-sm-4    ?if_exists   -->
           "${subject.payment}"

        </td>
        <td>
            <input type="text" class="form-control"
                   value="${subject.units}"
                   name="subject.units"
                   placeholder="Введите ед. изм."/>
        </td>
           <td>
            <input type="text" class="form-control"
                   value="${subject.amount}"
                   name="subject.amount"
                   placeholder="Введите количество"/>
        </td>
           <td>
            <input type="text" class="form-control"
                   value="${subject.price}"
                   name="subject.price"
                   placeholder="Введите цену"/>
        </td>
           <td>
            <input type="text" class="form-control"
                   value="${subject.code}"
                   name="subject.code"
                   placeholder="Введите код ОКРБ"/>
        </td>
           <td>
            <input type="text" class="form-control"
                   value="${subject.delivery}"
                   name="subject.delivery"
                   placeholder="Введите условия поставки"/>
        </td>
    </tr>
    </tbody>

</#list>
</table>
