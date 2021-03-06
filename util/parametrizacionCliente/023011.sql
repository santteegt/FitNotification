--SUBIR LA REGLA 16

delete from TCOMANDOSMANTENIMIENTO where CSUBSISTEMA='02' and CTRANSACCION='3011';
delete from TCOMANDOSID where COMANDO='com.fitbank.person.query.ObtainOfficerForNotification';
delete from TCOMANDOSID where COMANDO='com.fitbank.person.query.ObtainAccountsForNotification';

insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.person.query.ObtainOfficerForNotification', 'CON');
insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.person.query.ObtainAccountsForNotification', 'CON');

insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('02', '3011', '01', 1, 'I', 'com.fitbank.general.maintenance.GeneralChangeAccount', 2, '0', 'MAN', '1', null, null, 0, 'B');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('02', '3011', '01', 2, 'I', 'com.fitbank.bpm.command.BPMInitFlowFinish', 1, '0', 'MAN', '1', null, 'changeOfficerAuthorization', 0, 'F');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('02', '3011', '01', 3, 'I', 'com.fitbank.processor.maintenance.MaintenanceProcessor', 3, '0', 'MAN', '1', null, null, 0, 'B');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('02', '3011', '01', 4, 'I', 'com.fitbank.person.query.ObtainOfficerForNotification', 4, '0', 'CON', '1', null, null, 0, 'N');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('02', '3011', '01', 5, 'I', 'com.fitbank.person.query.ObtainAccountsForNotification', 5, '0', 'CON', '1', null, null, 0, 'N');
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('02', '3011', '01', 6, 'I', 'com.fitbank.processor.query.QueryProcessor', 6, '0', 'CON', '1', null, null, 0, null);


update TREGLASBPMID set SUBROGADO='0' where CREGLABPM='16';

update TUSUARIOS set CESTATUSUSUARIO='ACT' where FHASTA=FNCFHASTA;

--Agregar el select en la tabla TOFICIALESCUENTAID. 
--Especificar NUEVO OFICIAL, TIPO DE BANCA E IDENTIFICACION.

select * from TOFICIALESCUENTAID;

select 'ADMIN' CUSUARIO, C.CPERSONA_COMPANIA, C.CSUBSISTEMA, C.CGRUPOPRODUCTO, C.CPRODUCTO, C.CTIPOBANCA from TCUENTASPERSONA CP, TPERSONA P, TCUENTA C where CP.FHASTA=FNCFHASTA and CP.CPERSONA_COMPANIA=2 and CP.CPERSONA=P.CPERSONA 
and P.FHASTA=CP.FHASTA and P.IDENTIFICACION='00E0008600525'
and C.CCUENTA=CP.CCUENTA and C.CPERSONA_COMPANIA=CP.CPERSONA_COMPANIA and C.FHASTA=CP.FHASTA;