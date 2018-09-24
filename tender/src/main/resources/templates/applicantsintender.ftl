<#list subjects as subject>

<tbody>
<tr>

    <th scope="row">${subject.numberS}</th>
    <td>${subject.applicant.nameA}</td>
    <td>
        <input type="text" class="form-control"
               value="${subject.payment?if_exists}"
               name="subject.payment"
               placeholder="Введите Отсрочка"/>
    </td>
    <td>
        <input type="text" class="form-control"
               value="${subject.units?if_exists}"
               name="subject.units"
               placeholder="Введите ед. изм."/>
    </td>
    <td>
        <input type="text" class="form-control"
               value="${subject.amount?if_exists}"
               name="subject.amount"
               placeholder="Введите количество"/>
    </td>
    <td>
        <input type="text" class="form-control"
               value="${subject.price?if_exists}"
               name="subject.price"
               placeholder="Введите цену"/>
    </td>
    <td>
        <input type="text" class="form-control"
               value="${subject.code?if_exists}"
               name="subject.code"
               placeholder="Введите код ОКРБ"/>
    </td>
    <td>
        <input type="text" class="form-control"
               value="${subject.delivery?if_exists}"
               name="subject.delivery"
               placeholder="Введите условия поставки"/>
    </td>
</tr>
</tbody>

</#list>