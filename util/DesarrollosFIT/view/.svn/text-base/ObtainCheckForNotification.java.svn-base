package com.fitbank.view.query;

import com.fitbank.common.ApplicationDates;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.processor.query.QueryCommand;
import org.hibernate.ScrollableResults;

public class ObtainCheckForNotification extends QueryCommand{
    
    private static final String HQL_CHECKINFO = "select cc.pk.ccuenta, cc.pk.numerocheque, "
            + "cc.valorcheque, cc.cmoneda_original,(select ec.descripcion "
            + "from com.fitbank.hb.persistence.acco.view.Tcheckstatus ec "
            + "where ec.pk.cestatuscheque=cc.cestatuscheque and ec.pk.fhasta=cc.pk.fhasta and "
            + "ec.pk.cidioma='ES'),cc.ftransaccion,cc.identificacion_beneficiario||' - '||"
            + "cc.nombrebeneficiario,cc.observaciones,(select tc.descripcion "
            + "from com.fitbank.hb.persistence.acco.view.Tcheckbooktype tc "
            + "where tc.pk.ctipochequera=cc2.ctipochequera and tc.pk.fhasta=cc.pk.fhasta "
            + "and tc.pk.cidioma='ES'),cc2.primercheque||' - '||"
            + "cc2.ultimocheque "
            + "from com.fitbank.hb.persistence.acco.view.Tcheck cc, "
            + "com.fitbank.hb.persistence.acco.view.Taccountcheckbook cc2 "
            + "where cc.pk.fhasta=:expireDate and "
            + "cc.pk.cpersona_compania=:company and cc.pk.ccuenta=:account and "
            + "cc.pk.numerocheque=:check and cc.pk.ccuenta=cc2.pk.ccuenta and "
            + "cc.pk.fhasta=cc2.pk.fhasta and cc.pk.cpersona_compania=cc2.pk.cpersona_compania "
            + "and cc.pk.schequera=cc2.pk.schequera";

    @Override
    public Detail execute(Detail pDetail) throws Exception {
        String account = pDetail.findFieldByName("CCUENTA").getStringValue();
        Integer check = pDetail.findFieldByName("DOCUMENTO").getIntegerValue();
        Integer pCompany = pDetail.getCompany();
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_CHECKINFO);
        utilHB.setInteger("company", pCompany);
        utilHB.setString("account", account);
        utilHB.setInteger("check", check);
        utilHB.setTimestamp("expireDate", ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        ScrollableResults sr= utilHB.getScroll();
        Table table = new Table("CHEQUE", "CHEQUE");
        while(sr.next()){
            Record record = new Record();
            record.findFieldByNameCreate("NUMERO CHEQUE").setValue(sr.getInteger(1));
            record.findFieldByNameCreate("VALOR").setValue(sr.getBigDecimal(2));
            record.findFieldByNameCreate("MONEDA").setValue(sr.getString(3));
            record.findFieldByNameCreate("ESTADO").setValue(sr.getString(4));
            record.findFieldByNameCreate("FECHA TRAN.").setValue(sr.getDate(5));
            record.findFieldByNameCreate("BENEFICIARIO").setValue(sr.getString(6));
            record.findFieldByNameCreate("OBSERVACIONES").setValue(sr.getString(7));
            record.findFieldByNameCreate("TIPO CHEQUERA").setValue(sr.getString(8));
            record.findFieldByNameCreate("CHEQUES").setValue(sr.getString(9));
            table.addRecord(record);
        }
        pDetail.addTable(table);
        return pDetail;
    }
}