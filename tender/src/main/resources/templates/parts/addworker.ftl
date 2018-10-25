<form method="post">
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseAddWorker"
       role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Добавить работника
    </a>
    <div class="collapse " id="collapseAddWorker">
        <div class="form-group">
            <input type="text" class="form-control col-sm-4"
                   name="position"
                   placeholder="Должность"/>
        </div>
        <div class="form-group">
            <input type="text" class="form-control col-sm-4"
                   name="surname"
                   placeholder="Фаимлия"/>
        </div>
        <div class="form-group">
            <input type="text" class="form-control col-sm-4"
                   name="nameW"
                   placeholder="Имя"/>
        </div>
        <div class="form-group">
            <input type="text" class="form-control col-sm-4"
                   name="patronymic"
                   placeholder="Отчество"/>
        </div>

        <div>
            <label><input type="checkbox"
                          name="memberofcommission">Может быть членом тендерной комиссии</label>
        </div>
    </div>
</form>