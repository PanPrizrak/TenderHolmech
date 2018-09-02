<#import "parts/common.ftl" as ht>


<@ht.page>

<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/add" class="form-inline">
            <input type="text" name="filterName" class="form-control" value="${filterName?ifExists}" placeholder="Поиск по имени">
            <button type="submit" class="btn btn-primary ml-2">Поиск</button>
        </form>
    </div>
</div>

<div>
    ${error}
</div>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Добавить работника
</a>
<div class="collapse <#if worker??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form action="add" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}"
                       value="<#if worker??>${worker.name}</#if>" name="name" placeholder="Введите имя работника"/>
                <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}"
                       value="<#if worker??>${worker.email}</#if>" name="email" placeholder="Введите Email"/>
                <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFile" name="file">
                    <label class="custom-file-label col-sm-4" for="customFile">Choose file</label>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
</div>





<div>Пользователи</div>

<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Имя</th>
        <th scope="col">Емайл</th>
        <th scope="col">Пользователь</th>
        <th scope="col">Рис</th>
        <th scope="col">Действие</th>
    </tr>
    </thead>

<#list workers as worker>




    <tbody>
    <tr>
        <th scope="row">${worker.id}</th>
        <td>${worker.name}</td>
        <td>${worker.email}</td>
        <td>${worker.usrName}</td>
        <td><#if worker.filename??> <img class="img-fluid max-width: 50%" src = "/img/${worker.filename}" /><#else>
            No message </#if> </td>
        <td><form action="delete" method="post" >
            <input type="hidden" name="idworker" value=${worker.id} />
            <button type="submit">Удалить</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form></td>
    </tr>
    </tbody>



</#list>
</table>

</@ht.page>