<#import "parts/common.ftl" as ht>

<@ht.page>
<h5>Проверка отправления почты</h5>
<form method="post" id="testForm" enctype="multipart/form-data">
    <div>
        <input type="text" id="sendTo" value="${sendTo}" name="sendTo" placeholder="Введите номер"/>
    </div>
    <div>
        <button type="button" class="btn btn-outline-primary"
                onclick="document.getElementById('valueActions').value='1'; document.getElementById('testForm').submit()">
            Отправить</button>
    </div>
    <div>
        <button type="button" class="btn btn-outline-primary"
                onclick="document.getElementById('valueActions').value='2'; document.getElementById('testForm').submit()">
            Тест дампа БД</button>
    </div>
    <div>
        <button type="button" class="btn btn-outline-primary"
                onclick="document.getElementById('valueActions').value='3'; document.getElementById('testForm').submit()">
            Тест разархивирования</button>
    </div>
    <!--filename-->
    <div class="form-group">
        <div class="custom-file">
            <input type="file" class="custom-file-input" id="customFile" name="file"/>
            <label class="custom-file-label col-sm-4" for="customFile">Выберите файл</label>
        </div>
    </div>
    <div>
        <button type="button" class="btn btn-outline-primary"
                onclick="document.getElementById('valueActions').value='4'; document.getElementById('testForm').submit()">
            Тест востановления БД</button>
    </div>
    <input type="hidden" name="action" id="valueActions" value=""/>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</@ht.page>
