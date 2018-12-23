<form modelAttribute="subjectAndDocumentsForm"  method="post" enctype="multipart/form-data">
<table class="table table-bordered table-hover table-sm table-primary" id="grid">


    <#if subjectAndDocumentsForm.subjectList[0].tender.stage=="Снижение цены">
    <a href="/satrintender/${subjectAndDocumentsForm.subjectList[0].tender.numberT}">Снижение цены</a>
    </#if>

    <thead>
    <tr >
        <th scope="col" data-type="number">№ лота</th> <!--class="col-sm-0"-->
        <th scope="col" data-type="string">Название предприятия</th>
        <th scope="col" >Отсрочка</th>
        <th scope="col" class="col-sm-1">Название продукта</th>
        <th scope="col" >Ед. изм.</th>
        <th scope="col" >Количество</th>
        <th scope="col" >Цена. за ед. с НДС</th>
        <th scope="col" >Соответствие</th>
        <th scope="col" >Код ОКРБ</th>
        <th scope="col" >Условия поставки</th>

    </tr>
    </thead>



    <tbody>
    <#list subjectAndDocumentsForm.subjectList as subject>
    <tr >
        <input type="hidden" value="#{subject.idS}" name="subjectList[${subject?index}].idS"/>
        <th scope="row">${subject.numberS}</th>
        <input type="hidden" value="${subject.numberS}" name="subjectList[${subject?index}].numberS"/>
       <td>${subject.applicant.nameA}</td>
        <input type="hidden" value='${subject.applicant.nameA}' name="subjectList[${subject?index}].applicantNameA"/>
        <td>
            <input type="text" class="form-control"
                   value="${subject.payment?if_exists}"
                   name="subjectList[${subject?index}].payment"
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
            <#if subject.price??>
            <input type="text" class="form-control"
                   value="#{subject.price?if_exists}"
                   name="subjectList[${subject?index}].price"
                   placeholder="цена"/>
            <#else>
                <input type="text" class="form-control"
                       value="${subject.price?if_exists}"
                       name="subjectList[${subject?index}].price"
                       placeholder="цена"/>
            </#if>
        </td>
        <td>
            <#if subject.meet?if_exists>
                <#assign trueBuf = "${subject.meet?then('checked',' ')}"   >
            <#else>
                <#assign trueBuf = ' '>
            </#if>
            <input type="checkbox" name="subjectList[${subject?index}].meet"
            placeholder="Соответствие" ${trueBuf}/>
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
        <input type="hidden" value="${subject.tender.numberT}" name="subjectList[${subject?index}].tenderNumberT"/>
    </tr>
        </#list>
    </tbody>


</table>

    <script>
    // сортировка таблицы
    // использовать делегирование!
    // должно быть масштабируемо:
    // код работает без изменений при добавлении новых столбцов и строк

    var grid = document.getElementById('grid');

    grid.onclick = function(e) {
      if (e.target.tagName != 'TH') return;

      // Если TH -- сортируем
      sortGrid(e.target.cellIndex, e.target.getAttribute('data-type'));
    };

    function sortGrid(colNum, type) {
      var tbody = grid.getElementsByTagName('tbody')[0];

      // Составить массив из TR
      var rowsArray = [].slice.call(tbody.rows);

      // определить функцию сравнения, в зависимости от типа
      var compare;

      switch (type) {
        case 'number':
          compare = function(rowA, rowB) {
            return rowA.cells[colNum].innerHTML - rowB.cells[colNum].innerHTML;
          };
          break;
        case 'string':
          compare = function(rowA, rowB) {
            return rowA.cells[colNum].innerHTML > rowB.cells[colNum].innerHTML;
          };
          break;
      }

      // сортировать
      rowsArray.sort(compare);

      // Убрать tbody из большого DOM документа для лучшей производительности
      grid.removeChild(tbody);

      // добавить результат в нужном порядке в TBODY
      // они автоматически будут убраны со старых мест и вставлены в правильном порядке
      for (var i = 0; i < rowsArray.length; i++) {
        tbody.appendChild(rowsArray[i]);
      }

      grid.appendChild(tbody);

    }
  </script>


    <!--filename-->
    <div class="form-group">
        <div class="custom-file">
            <input type="file" class="custom-file-input" id="customFile" name="file">
            <label class="custom-file-label col-sm-4" for="customFile">Выберите файл</label>
        </div>
    </div>
    <label><input type="checkbox" name="refreshSubjectList" />Обновить данные о предмете закупки</label>

    <div class="form-group">
        <button type="submit" class="btn btn-primary">Добавить</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
