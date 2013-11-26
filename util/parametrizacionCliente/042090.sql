--Subir primero las reglas 43,101 y el formulario 042090

delete from TCOMANDOSMANTENIMIENTO where CSUBSISTEMA='04' and CTRANSACCION='2090';
delete from TCOMANDOSID where comando='com.fitbank.view.maintenance.SolicitSpecialCheckbookAustro';
delete from TCOMANDOSID where comando='com.fitbank.view.maintenance.austro.CreateControlFieldsM';
delete from TCOMANDOSID where comando='com.fitbank.view.query.ObtainBalancesForNotification';

insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.view.maintenance.SolicitSpecialCheckbookAustro', 'MAN');
insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.view.maintenance.austro.CreateControlFieldsM', 'MAN');
insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.view.query.ObtainBalancesForNotification', 'CON');

insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 1, 'I', 'com.fitbank.view.maintenance.austro.checkbook.RestrictionSolicitCheckbook', 1, '0', 'MAN', '1', null, null, 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 2, 'I', 'com.fitbank.view.maintenance.SolicitSpecialCheckbookAustro', 2, '0', 'MAN', '1', null, 'CHQ', 0, 'B');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 3, 'I', 'com.fitbank.view.maintenance.GenerateCheckbookTariff', 3, '0', 'MAN', '1', null, null, 0, 'B');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 4, 'I', 'com.fitbank.view.maintenance.austro.CreateControlFieldsM', 4, '0', 'MAN', '1', null, 'CCUENTA,ttctadoc._record0.CCUENTA.value', 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 6, 'I', 'com.fitbank.view.maintenance.austro.CreateControlFieldsConstantM', 6, '0', 'MAN', '1', null, '_NOTIFICATION_,CAMBIO OFICIAL CUENTA', 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 8, 'I', 'com.fitbank.view.maintenance.austro.AddControlFieldsNotification', 8, '0', 'MAN', '1', null, null, 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 9, 'I', 'com.fitbank.maintenance.MaintenanceImages', 9, '0', 'MAN', '1', null, null, 0, 'B');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 10, 'I', 'com.fitbank.bpm.command.BPMInitFlowFinish', 10, '0', 'MAN', '1', null, 'visCheckbook', 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 11, 'I', 'com.fitbank.processor.maintenance.MaintenanceProcessor', 11, '0', 'MAN', '1', null, null, 0, 'B');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 12, 'I', 'com.fitbank.view.query.ObtainBalancesForNotification', 12, '0', 'CON', '1', null, null, 0, 'N');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('04', '2090', '01', 13, 'I', 'com.fitbank.processor.query.QueryProcessor', 13, '0', 'CON', '1', null, null, 0, null);

update TREGLASBPMID set SUBROGADO='0' where CREGLABPM='101';
update TREGLASBPMID set SUBROGADO='0' where CREGLABPM='43';

update TUSUARIOS set CESTATUSUSUARIO='ACT' where FHASTA=FNCFHASTA;

delete from TMODELOSCHEQUERATIPOS where CTIPOCHEQUERA='ESP';
insert into TMODELOSCHEQUERATIPOS (CMODELOCHEQUERA, CTIPOCHEQUERA, FHASTA, FDESDE, NUMEROCHEQUES, TALON, TIPOMODELO, VERSIONCONTROL) values ('1', 'ESP', TIMESTAMP '2999-12-31 00:00:00', TIMESTAMP '2010-04-29 14:38:44', 100, '0', null, 2);
insert into TMODELOSCHEQUERATIPOS (CMODELOCHEQUERA, CTIPOCHEQUERA, FHASTA, FDESDE, NUMEROCHEQUES, TALON, TIPOMODELO, VERSIONCONTROL) values ('2', 'ESP', TIMESTAMP '2999-12-31 00:00:00', TIMESTAMP '2010-04-29 14:38:44', 50, '0', null, 2);
insert into TMODELOSCHEQUERATIPOS (CMODELOCHEQUERA, CTIPOCHEQUERA, FHASTA, FDESDE, NUMEROCHEQUES, TALON, TIPOMODELO, VERSIONCONTROL) values ('3', 'ESP', TIMESTAMP '2999-12-31 00:00:00', TIMESTAMP '2010-04-29 14:38:44', 100, '0', null, 2);
insert into TMODELOSCHEQUERATIPOS (CMODELOCHEQUERA, CTIPOCHEQUERA, FHASTA, FDESDE, NUMEROCHEQUES, TALON, TIPOMODELO, VERSIONCONTROL) values ('4', 'ESP', TIMESTAMP '2999-12-31 00:00:00', TIMESTAMP '2010-04-29 14:38:44', 50, '0', null, 2);

insert into TROLESID (CROL, TIEMPOSESION, CUSUARIO_INGRESO, FINGRESO) values (98, null, null, null);
insert into TROLES (CIDIOMA, CROL, FHASTA, FDESDE, DESCRIPCION, VERSIONCONTROL) values ('ES', 98, TIMESTAMP '2999-12-31 00:00:00', TIMESTAMP '2011-08-19 17:33:36', 'ACTIVACION CHEQUERAS', 0);

--Agregar en TCOMPANIAUSUARIOSROLES UN USUARIO QUE TENGA CROL 98