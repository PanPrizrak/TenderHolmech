truncate table worker;
truncate table orders;
truncate table tender;

insert into worker (idw , namew , patronymic , position , surname , memberofcommission)
        values (1, 'Imya','Otchestvo','Familiya','Rabotnik',true);
insert into orders (idO, numberO, dateO)
    values (1, '0', '1999-12-31');

insert into tender (idT, dateT,  namet, numberT, payment_factor, price_factor, stage, order_id)
    values (1, '1999-12-31',  'Tender', '9999-999999','20','80','Vskritie',1);

insert into tender (idT, dateT,  namet, numberT, payment_factor, price_factor, stage, order_id)
    values (2, '2000-12-31',  'Tender2', '8888-888888','20','80','Vskritie',1);