<#import "parts/common.ftl" as ht>


<@ht.page>
<form method="post"><!--modelAttribute="enumForm"-->
    <select name="rolesName">
    <#list roles as wrokerrole>
        <option value="${wrokerrole}">${wrokerrole}</option>
    </#list>
    </select>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Добавить</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

</@ht.page>