package com.fitbank.warranty.query;

import com.fitbank.common.ApplicationDates;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.processor.query.QueryCommand;
import org.hibernate.ScrollableResults;

public class ObtainWarrantyForNotification extends QueryCommand{

    private static final String HQL_WARRANTY = "select cg.pk.ccuenta, "
            + "cg.cmoneda, (select f.pk.cfrecuencia||' - '||f.descripcion "
            + "from com.fitbank.hb.persistence.gene.Tfrecuency f "
            + "where f.pk.cidioma='ES' and f.pk.fhasta=cg.pk.fhasta and "
            + "f.pk.cfrecuencia=cg.cfrecuencia_inspeccion),"
            + "(select p.identificacion||' - '||p.nombrelegal "
            + "from com.fitbank.hb.persistence.person.Tperson p "
            + "where p.pk.cpersona=cg.cpersona_avaluador and p.pk.fhasta=cg.pk.fhasta),"
            + "(select tg.pk.ctipogarantia||' - '||tg.descripcion "
            + "from com.fitbank.hb.persistence.person.Tguaranteetype tg "
            + "where tg.pk.cidioma='ES' and tg.pk.fhasta=cg.pk.fhasta and "
            + "tg.pk.ctipogarantia=cg.ctipogarantia), "
            + "(select tb.pk.ctipobien||' - '||"
            + "tb.descripcion "
            + "from com.fitbank.hb.persistence.person.natural.Tbenefictype tb "
            + "where tb.pk.cidioma='ES' and tb.pk.fhasta=cg.pk.fhasta "
            + "and tb.pk.ctipobien=cg.ctipobien), cg.detalle, cg.valorcomercial "
            + "from com.fitbank.hb.persistence.acco.loan.Tguaranteeaccount cg "
            + "where cg.pk.fhasta=:expireDate and cg.pk.cpersona_compania=:company "
            + "and cg.pk.numerorenovacion='1' and cg.pk.ccuenta=:account";
    
    private Table table;

    @Override
    public Detail execute(Detail pDetail) throws Exception {
        this.table = new Table("GARANTIA", "GARANTIA");
        pDetail.addTable(table);
        this.completeDetail(pDetail);
        return pDetail;
    }


    private void completeDetail(Detail pDetail) throws Exception {
        ScrollableResults accounts = getAccounts(pDetail);
        Record rec=new Record();
        while(accounts.next()){
            rec.findFieldByNameCreate("GARANTIA").setValue(accounts.getString(0));
            rec.findFieldByNameCreate("MONEDA").setValue(accounts.getString(1));
            rec.findFieldByNameCreate("FRECUENCIA INSPECCION").setValue(accounts.getString(2));
            rec.findFieldByNameCreate("AVALUADOR").setValue(accounts.getString(3));
            rec.findFieldByNameCreate("TIPO GARANTIA").setValue(accounts.getString(4));
            rec.findFieldByNameCreate("TIPO BIEN").setValue(accounts.getString(5));
            rec.findFieldByNameCreate("DETALLE").setValue(accounts.getString(6));
            rec.findFieldByNameCreate("VALOR").setValue(accounts.getBigDecimal(7));
        }
        this.table.addRecord(rec);
    }

    private ScrollableResults getAccounts(Detail pDetail){
        String account=pDetail.findFieldByName("CCUENTA").getStringValue();
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_WARRANTY);
        utilHB.setString("account", account);
        utilHB.setInteger("company", pDetail.getCompany());
        utilHB.setTimestamp("expireDate", ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return utilHB.getScroll();
    }

}
