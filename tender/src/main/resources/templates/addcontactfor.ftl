<#import "parts/common.ftl" as ht>

<@ht.page>
<form method="post">
    <div>
        <label>Email:</label><input type="email" name="emailFromHTML" placeholder="электронная почта"/>
        <label>Номер телефона</label><input type="tel" name="phoneFromHTML" placeholder="телефон"/>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Добавить</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>
</form>

</@ht.page>