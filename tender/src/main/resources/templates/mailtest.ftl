<#import "parts/common.ftl" as ht>

<@ht.page>
<h5>Проверка отправления почты</h5>
<form method="post">
    <div>
        <input type="text" id="sendTo" value="${sendTo}" name="sendTo" placeholder="Введите номер"/>
    </div>
    <div>
        <button type="submit" class="btn btn-primary">Отправить</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</@ht.page>