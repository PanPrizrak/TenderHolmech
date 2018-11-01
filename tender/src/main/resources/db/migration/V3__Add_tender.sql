insert into worker (idw , namew , patronymic , position , surname , memberofcommission)
        values (1, 'Имя','Отчество','Фамилия','Дирктор',true);
insert into orders (idO, numberO, dateO)
    values (1, '0', '1999-12-31');

insert into tender (idT, dateT,  namet, numberT, payment_factor, price_factor, stage, order_id)
    values (1, '1999-12-31',  'Тендер', '9999-999999','20','80','Вскрытие',1);