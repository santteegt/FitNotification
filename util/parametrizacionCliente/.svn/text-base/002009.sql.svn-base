
delete from TCOMANDOSMANTENIMIENTO where CSUBSISTEMA='00' and CTRANSACCION='2009';
delete from TCOMANDOSID where COMANDO='com.fitbank.bpm.maintenance.InstanceFlowNameGetter';
delete from TCOMANDOSID where COMANDO='com.fitbank.bpm.maintenance.SendDetailToFitNotification';

insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.bpm.maintenance.InstanceFlowNameGetter', 'MAN');
insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.bpm.maintenance.SendDetailToFitNotification', 'MAN');

insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('00', '2009', '01', 1, 'I', 'com.fitbank.bpm.maintenance.InstanceFlowNameGetter', 1, '0', 'MAN', '1', null, null, 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('00', '2009', '01', 2, 'I', 'com.fitbank.bpm.maintenance.SendMessageToFlow', 2, '0', 'MAN', '1', null, null, 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('00', '2009', '01', 3, 'I', 'com.fitbank.bpm.maintenance.SendDetailToFitNotification', 3, '0', 'MAN', '1', null, null, 0, null);
