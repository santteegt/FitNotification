--Transacción 003000

delete from tcomandosmantenimiento where csubsistema='00' and ctransaccion='3000';
delete from tcompaniatransacciones where csubsistema='00' and ctransaccion='3000';
delete from tsubsistematransaccionesid where csubsistema='00' and ctransaccion='3000';
delete from tcomandosid where comando='com.fitbank.general.maintenance.FillUsersInformationForNotification';

insert into TCOMANDOSID (COMANDO, TIPOCOMANDO) values ('com.fitbank.general.maintenance.FillUsersInformationForNotification', 'MAN');
insert into TSUBSISTEMATRANSACCIONESID (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, ORDENMENU, PRESENTARENMENU, INTERNA, FORMULARIOBASE, AFECTAINMOVILIZACION, CGRUPOTRANSACCION, PRESENTARENREVERSO, CNIVELMENU, LISTAVALOR, USACACHE, SUMACOMPONENTES, PROVISION, COMPLETARRUBROS, LOTEGENERICO, VERIFICANIVELSEGURIDAD, CUSUARIO_INGRESO, FINGRESO, ACUMULACONTADOR, PAGINA, ORIGENDESTINO, CONTROLAEFECTIVO, REQUIEREAUTORIZACION, EJECUTAENAUTORIZACION) values ('00', '3000', '01', 0, '1', '0', '1', '0', null, null, null, '0', '0', '0', '0', '0', null, null, null, null, null, null, null, null, '0', '0');
insert into TCOMPANIATRANSACCIONES (CPERSONA_COMPANIA, CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, VERSIONCONTROL) values (2, '00', '3000', '01', 0);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('00', '3000', '01', 1, 'I', 'com.fitbank.general.maintenance.FillUsersInformationForNotification', 1, '0', 'MAN', '1', null, null, 0, null);

--Transacción 010117

delete from tcomandosmantenimiento where csubsistema='01' and ctransaccion='0117';

insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('01', '0117', '01', 1, 'I', 'com.fitbank.general.maintenance.ClearTablesMaintenance', 1, '0', 'MAN', '1', null, null, 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('01', '0117', '01', 2, 'I', 'com.fitbank.general.maintenance.FillUsersInformationForNotification', 2, '0', 'MAN', '1', null, null, 0, null);
insert into TCOMANDOSMANTENIMIENTO (CSUBSISTEMA, CTRANSACCION, VERSIONTRANSACCION, SCOMANDO, EVENTO, COMANDO, ORDEN, USACACHE, TIPOCOMANDO, ACTIVADO, CANALESEXCLUIDOS, PARAMETRO, VERSIONCONTROL, EJECUTADOPOR) values ('01', '0117', '01', 3, 'I', 'com.fitbank.processor.maintenance.MaintenanceProcessor', 3, '0', 'MAN', '1', null, null, 0, null);
