package com.fitbank.view.query;

import com.fitbank.balance.helper.BalanceData;
import com.fitbank.balance.helper.TransactionBalance;
import com.fitbank.common.ApplicationDates;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.common.helper.Constant;
import com.fitbank.common.helper.Dates;
import com.fitbank.common.helper.FormatDates;
import com.fitbank.common.helper.SqlHelper;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.fin.helper.AccountHelper;
import com.fitbank.hb.persistence.acco.Taccount;
import com.fitbank.hb.persistence.acco.view.Taveragebalanceaccount;
import com.fitbank.hb.persistence.acco.view.Tcutaveragebalanceaccount;
import com.fitbank.hb.persistence.prod.view.Tviewproduct;
import com.fitbank.processor.query.QueryCommand;
import com.fitbank.view.acco.AccountBalances;
import com.fitbank.view.check.CheckStatusTypes;
import com.fitbank.view.common.ViewHelper;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Clase que llena la tabla de saldos.
 * @author malgia
 */
public class ObtainBalancesForNotification extends QueryCommand {

    /**
     * Saldos de la cuenta
     */
    private AccountBalances accountBalances = null;
    
    /**
     * Numero de cuenta
     */
    private String account = null;
    
    /**
     * Record para la tabla de SALDOS
     */
    private Record record = null;

    /**
     * Nombre de la tabla
     */
    private final static String SALDOS = "SALDOS";
    
    private Taccount getAccount(Detail pDetail) throws Exception {
        this.account = pDetail.findFieldByName("CCUENTA").getStringValue();
        Integer pCompany = pDetail.getCompany();
        AccountHelper accountHelper = new AccountHelper();
        return accountHelper.getAccount(pCompany, this.account);
    }

    public Detail execute(Detail pDetail) throws Exception {
        Taccount taccount = this.getAccount(pDetail);
        if (taccount == null) {
            return null;
        }
        BigDecimal[] promedios = null;
        Tviewproduct tvp = ViewHelper.getInstance().getTviewproduct(taccount.getPk().getCpersona_compania(),
                taccount.getCsubsistema(), taccount.getCgrupoproducto(), taccount.getCproducto());
        if (tvp.getLibreta() != null && tvp.getLibreta().compareTo("1") == 0) {
            promedios = this.processSavingAccount(pDetail, taccount);
        }
        if (tvp.getCheques() != null && tvp.getCheques().compareTo("1") == 0) {
            promedios = this.processCheckAccount(pDetail, taccount);
        }
        this.fillDetail(pDetail, promedios);      
        
        Dates auxdate = new Dates(ApplicationDates.getInstance().getDataBaseDate());
        auxdate.addYearBased(-365);
        Date ayearagodate = auxdate.getDate();

        Table table=pDetail.findTableByAlias(SALDOS);
        this.record=table.getRecords().iterator().next();
        
        this.record.findFieldByNameCreate("CHEQUES PROTESTADOS").setValue(
                this.getChecksCount(ayearagodate, CheckStatusTypes.PROTESTED.getStatus()).toString());
        this.record.findFieldByNameCreate("CHEQUES REVOCADOS").setValue(
                this.getChecksCount(ayearagodate, CheckStatusTypes.REVOKED.getStatus()).toString());
        this.record.findFieldByNameCreate("CHEQUES DEVUELTOS").setValue(
                this.getChecksCount(ayearagodate, CheckStatusTypes.RETURN.getStatus()).toString());
        this.getReceiveAccount();
        
        BigDecimal localreturncount = new BigDecimal(""+this.getLocalCheckReturnedCount());
        BigDecimal remitreturncount = new BigDecimal(""+this.getRemitCheckReturnedCount());
        BigDecimal total = localreturncount.add(remitreturncount);
        this.record.findFieldByNameCreate("CHEQUES DEP. DEVUELTOS").setValue(total.toString());        
        
        TransactionBalance.setBalanceData(new BalanceData());
        accountBalances = new AccountBalances(taccount, ApplicationDates.getDefaultExpiryDate());
        if (accountBalances == null) {
            return pDetail;
        } else {
            this.fillDetail(pDetail);
            return pDetail;
        }        
    }

    private void fillDetail(Detail pDetail) throws Exception {
        Record record = pDetail.findTableByAlias(SALDOS).getRecords().iterator().next();
        this.fillAccountant(record);
        this.fillWithhold(record);
        this.fillLocked(record);
        this.fillPawned(record);
        this.fillAvailable(record);
        this.fillEffective(record);
        this.fillRetention(record);
        this.fillContrated(record);
        this.fillOccasional(record);
        this.fillCheckauthorized(record);
        this.fillIndirect(record);
        this.fillSaving(record);
        this.fillEfectiveWithHold(record);
        this.fillPayroll(record);
    }

    private void fillAccountant(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO CONTABLE").setValue(accountBalances.getAccountant().toString());
    }

    private void fillWithhold(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO RETENCIONES").setValue(accountBalances.getWithhold());
    }

    private void fillLocked(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO BLOQUEADO").setValue(
                (accountBalances.getLocked() == null ? Constant.BD_ZERO : accountBalances.getLocked()).toString());
    }

    private void fillPawned(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO PIGNORADO").setValue(
                (accountBalances.getPawned() == null ? Constant.BD_ZERO : accountBalances.getPawned()).toString());
    }

    private void fillAvailable(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO DISPONIBLE").setValue(accountBalances.getAvailable().toString());
    }

    private void fillEffective(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO EFECTIVO")
                .setValue(
                        (accountBalances.getEffective() == null ? Constant.BD_ZERO : accountBalances.getEffective())
                                .toString());
    }
    private void fillRetention(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO RETENCION JUDICIAL")
                .setValue(
                        (accountBalances.getRetentions() == null ? Constant.BD_ZERO : accountBalances.getRetentions())
                                .toString());
    }

    private void fillContrated(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("AUT. SOB. CONTRATADO").setValue(
                (accountBalances.getContratedauthorizedoverdraft() == null ? Constant.BD_ZERO : accountBalances
                        .getContratedauthorizedoverdraft()).toString());
        pRecord.findFieldByNameCreate("SOB. CONTRATADO").setValue(
                (accountBalances.getContratedoverdraft() == null ? Constant.BD_ZERO : accountBalances
                        .getContratedoverdraft()).toString());

    }

    private void fillOccasional(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("AUT. SOB. OCASIONAL").setValue(
                (accountBalances.getOccasionalauthorizedoverdraft() == null ? Constant.BD_ZERO : accountBalances
                        .getOccasionalauthorizedoverdraft()).toString());
        pRecord.findFieldByNameCreate("SOB. OCASIONAL").setValue(
                (accountBalances.getOccasionaloverdraft() == null ? Constant.BD_ZERO : accountBalances
                        .getOccasionaloverdraft()).toString());
    }

    private void fillCheckauthorized(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("AUT. SOB. CHEQUES").setValue(
                (accountBalances.getCheckauthorizedoverdraft() == null ? Constant.BD_ZERO : accountBalances
                        .getCheckauthorizedoverdraft()).toString());
        pRecord.findFieldByNameCreate("SOB. CHEQUES").setValue(
                (accountBalances.getCheckoverdraft() == null ? Constant.BD_ZERO : accountBalances.getCheckoverdraft())
                        .toString());
    }

    private void fillIndirect(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SOB. INDIRECTO").setValue(
                (accountBalances.getIndirectoverdraft() == null ? Constant.BD_ZERO : accountBalances
                        .getIndirectoverdraft()).toString());
    }

    private void fillSaving(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO DEUDOR")
                .setValue(
                        (accountBalances.getSavingoverdraft() == null ? Constant.BD_ZERO : accountBalances
                                .getSavingoverdraft()).toString());
    }

    private void fillEfectiveWithHold(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO EFECTIVO FUTURO").setValue(
                (accountBalances.getEfectiveWithHold() == null ? Constant.BD_ZERO : accountBalances
                        .getEfectiveWithHold()).toString());
    }

    private void fillPayroll(Record pRecord) throws Exception {
        pRecord.findFieldByNameCreate("SALDO LIBRETA").setValue((accountBalances.getPayroll()).toString());
    }
    
    /**
     * Procesa los promedios de cuentas cuyo producto indica que tiene cheques (cuenta corrientes)
     * 
     * @param pDetail Mensaje
     * @param pAccount Cuenta
     * @return Promedios corte procesados
     * @throws Exception
     */
    private BigDecimal[] processCheckAccount(Detail pDetail, Taccount pAccount) throws Exception {
        Dates fecha = new Dates(SqlHelper.getInstance().getAccountingdate(pDetail.getCompany(), 0).getFcontable());
        Date date_to = fecha.getDate();
        fecha.addField(Calendar.MONTH, -6);
        Date date_from = fecha.getDate();
        BigDecimal[] result = new BigDecimal[4];
        String HQL_AVERAGE = " from com.fitbank.hb.persistence.acco.view.Tcutaveragebalanceaccount "
                + " where pk.ccuenta=:account " + " and pk.cpersona_compania=:company "
                + " and fcalculohasta > :initdate " + " and fcalculodesde <= :finaldate "
                + " order by fcalculodesde desc";
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_AVERAGE);
        utilHB.setString("account", pAccount.getPk().getCcuenta());
        utilHB.setInteger("company", pAccount.getPk().getCpersona_compania());
        utilHB.setDate("initdate", date_from);
        utilHB.setDate("finaldate", date_to);
        utilHB.setReadonly(true);
        List<Tcutaveragebalanceaccount> l_average = utilHB.getList(false);
        if (l_average != null) {
            result = this.getCheckAccountResults(l_average);
        }
        return result;
    }

    /**
     * Calcula saldos promedio de cuentas con cheques para los casos: actual, mensual, trimestral y semestral
     * 
     * @param l_average Lista de promedios
     * @return Promedios procesados
     */
    private BigDecimal[] getCheckAccountResults(List<Tcutaveragebalanceaccount> l_average) {
        BigDecimal tres = new BigDecimal(3);
        BigDecimal seis = new BigDecimal(6);
        BigDecimal[] result = new BigDecimal[4];
        result[0] = Constant.BD_ZERO;
        result[1] = Constant.BD_ZERO;
        result[2] = Constant.BD_ZERO;
        result[3] = Constant.BD_ZERO;
        BigDecimal promedio = Constant.BD_ZERO;
        int i = 0;
        for (Tcutaveragebalanceaccount o : l_average) {
            promedio = o.getPromedioefectivo();
            if (i == 0) {
                result[0] = promedio;
            } else if (i == 1) {
                result[1] = promedio;
                result[2] = result[2].add(promedio);
                result[3] = result[3].add(promedio);
            } else if (i <= 3) {
                result[2] = result[2].add(promedio);
                result[3] = result[3].add(promedio);
            } else if (i > 3) {
                result[3] = result[3].add(promedio);
            }
            i++;
        }
        if (result[2].compareTo(Constant.BD_ZERO) > 0) {
            result[2] = result[2].divide(tres, 2, BigDecimal.ROUND_HALF_UP);
        }
        if (result[3].compareTo(Constant.BD_ZERO) > 0) {
            result[3] = result[3].divide(seis, 2, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    /**
     * Procesa los promedios de cuentas cuyo producto indica que tiene libreta (cuentas de ahorro)
     * 
     * @param pDetail Mensaje
     * @param pAccount Cuenta
     * @return Promedios corte procesados
     * @throws Exception
     */
    private BigDecimal[] processSavingAccount(Detail pDetail, Taccount pAccount) throws Exception {
        Dates fecha = new Dates(pDetail.getAccountingdate());
        Date date_to = fecha.getDate();
        fecha.addField(Calendar.MONTH, -7);
        Date date_from = fecha.getDate();
        String yearmonthfrom = FormatDates.formatFPartition(date_from);
        String yearmonthto = FormatDates.formatFPartition(date_to);
        BigDecimal[] result = new BigDecimal[4];
        String HQL_AVERAGE = " from com.fitbank.hb.persistence.acco.view.Taveragebalanceaccount "
                + " where pk.ccuenta=:account " + " and pk.cpersona_compania=:company "
                + " and pk.fpromedio >= :yearmonthinit " + " and pk.fpromedio <= :yearmonthfinal "
                + " order by pk.fpromedio desc ";
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_AVERAGE);
        utilHB.setString("account", pAccount.getPk().getCcuenta());
        utilHB.setInteger("company", pAccount.getPk().getCpersona_compania());
        utilHB.setString("yearmonthinit", yearmonthfrom);
        utilHB.setString("yearmonthfinal", yearmonthto);
        utilHB.setReadonly(true);
        List<Taveragebalanceaccount> l_average = utilHB.getList(false);
        if (l_average != null) {
            result = this.getSavingAccountResults(l_average);
        }
        return result;
    }

    /**
     * Calcula saldos promedio de cuentas con libreta para los casos: actual, mensual, trimestral y semestral
     * 
     * @param l_average Lista de promedios
     * @return Promedios procesados
     */
    private BigDecimal[] getSavingAccountResults(List<Taveragebalanceaccount> l_average) {
        BigDecimal tres = new BigDecimal(3);
        BigDecimal seis = new BigDecimal(6);
        BigDecimal[] result = new BigDecimal[4];
        result[0] = Constant.BD_ZERO;
        result[1] = Constant.BD_ZERO;
        result[2] = Constant.BD_ZERO;
        result[3] = Constant.BD_ZERO;
        BigDecimal promedio = Constant.BD_ZERO;
        int i = 0;
        for (Taveragebalanceaccount o : l_average) {
            promedio = o.getPromedioefectivomensual();
            if (i == 0) {
                result[0] = promedio;
            } else if (i == 1) {
                result[1] = promedio;
                result[2] = result[2].add(promedio);
                result[3] = result[3].add(promedio);
            } else if (i <= 3) {
                result[2] = result[2].add(promedio);
                result[3] = result[3].add(promedio);
            } else if (i > 3) {
                result[3] = result[3].add(promedio);
            }
            i++;
        }
        if (result[2].compareTo(Constant.BD_ZERO) > 0) {
            result[2] = result[2].divide(tres, 2, BigDecimal.ROUND_HALF_UP);
        }
        if (result[3].compareTo(Constant.BD_ZERO) > 0) {
            result[3] = result[3].divide(seis, 2, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    /**
     * Llena el mensaje con el resultado de la consulta
     * 
     * @param pDetail Mensaje
     * @param promedios Promedios procesados
     */
    private void fillDetail(Detail pDetail, BigDecimal[] promedios) {
        Table balancetable = new Table(SALDOS, SALDOS);
        Record record=new Record(1);
        if (record != null) {
            record.findFieldByNameCreate("PROM. ACTUAL").setValue(promedios[0].toString());
            record.findFieldByNameCreate("PROM. MENSUAL").setValue(promedios[1].toString());
            record.findFieldByNameCreate("PROM. TRIMESTRAL").setValue(promedios[2].toString());
            record.findFieldByNameCreate("PROM. SEMESTRAL").setValue(promedios[3].toString());
        }
        balancetable.addRecord(record);
        pDetail.addTable(balancetable);
    }

    /**
     * Metodo que permite calcular el saldo promedio de una cuenta,informacion necesaria para la emision del estado de
     * cuenta
     * 
     * @param pDetail
     * @param taccount
     * @return
     * @throws Exception
     */
    public BigDecimal[] obtainAverageAccount(Detail pDetail, Taccount taccount) throws Exception {
        BigDecimal[] promedios = null;
        Tviewproduct tvp = ViewHelper.getInstance().getTviewproduct(taccount.getPk().getCpersona_compania(),
                taccount.getCsubsistema(), taccount.getCgrupoproducto(), taccount.getCproducto());
        if (tvp.getLibreta() != null && tvp.getLibreta().compareTo("1") == 0) {
            promedios = this.processSavingAccount(pDetail, taccount);
        }
        if (tvp.getCheques() != null && tvp.getCheques().compareTo("1") == 0) {
            promedios = this.processCheckAccount(pDetail, taccount);
        }
        return promedios;

    }    

    private static final String EXPIREDATE = "expiredate";

    private static final String HQL_CHECK_BY_STATUS = "select count(*) from com.fitbank.hb.persistence.acco.view.Tcheck t "
            + "WHERE t.pk.ccuenta = :ctacheck "
            + "AND t.cestatuscheque = :checkstatus "
            + "AND t.fprimerestatus between :ayearagodate and :nowdate " + "and fhasta=:expiredate";

    /**
     * Cuenta el numero de cheques con el estatus consultado considerando la
     * fecha inicial = fecha de hoy menos 365 días
     * 
     * @param ayearagodate
     *            Fecha de inicio de la consulta
     * @param pCheckStatus
     *            Estatus del cheque
     * @return Contador
     * @throws Exception
     */
    private Object getChecksCount(Date ayearagodate, String pCheckStatus) throws Exception {
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_CHECK_BY_STATUS);
        utilHB.setString("ctacheck", this.account);
        utilHB.setString("checkstatus", pCheckStatus);
        utilHB.setDate("ayearagodate", ayearagodate);
        utilHB.setDate("nowdate", ApplicationDates.getInstance().getDataBaseDate());
        utilHB.setDate("expiredate", ApplicationDates.getDefaultExpiryDate());
        utilHB.setReadonly(true);
        return utilHB.getObject();
    }

    private static final String HQL_TOTALCOBRAR = "select sum(t.montopendiente) from com.fitbank.hb.persistence.acco.receive.Treceiveaccount t "
            + "WHERE t.pk.ccuenta= :cta " + "and fhasta=:expiredate ";

    /**
     * Consulta la suma total de las cuentas por cobrar de la cuenta
     * 
     * @throws Exception
     */
    private void getReceiveAccount() throws Exception {
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_TOTALCOBRAR);
        utilHB.setString("cta", this.account);
        utilHB.setDate(EXPIREDATE, ApplicationDates.getDefaultExpiryDate());
        utilHB.setReadonly(true);
        Object result = utilHB.getObject();
        if (result != null) {
            this.record.findFieldByNameCreate("CTAS. POR COBRAR").setValue(result.toString());
        } else {
            this.record.findFieldByNameCreate("CTAS. POR COBRAR").setValue("0");
        }
    }

    private static final String HQL_CHEQHESLOCDEV = "select count(*) from com.fitbank.hb.persistence.acco.view.Tlocalcheckaccount t "
            + "WHERE t.ccuenta = :ctalocal " + "AND t.devuelto = '1' ";

    /**
     * Cuenta el numero de cheques locales devueltos
     * 
     * @return Contador
     * @throws Exception
     */
    private Object getLocalCheckReturnedCount() throws Exception {
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_CHEQHESLOCDEV);
        utilHB.setString("ctalocal", this.account);
        utilHB.setReadonly(true);
        return utilHB.getObject();
    }

    private static final String HQL_CHEQUESREMDEV = "select count(*) from com.fitbank.hb.persistence.acco.view.Tremitcheckaccount t "
            + "WHERE t.ccuenta = :ctaremit " + "AND t.devuelto = '1' ";

    /**
     * Cuenta el numero de cheques de remesa devueltos
     * 
     * @return Contador
     * @throws Exception
     */
    private Object getRemitCheckReturnedCount() throws Exception {
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_CHEQUESREMDEV);
        utilHB.setString("ctaremit", this.account);
        utilHB.setReadonly(true);
        return utilHB.getObject();
    }    
    
}
