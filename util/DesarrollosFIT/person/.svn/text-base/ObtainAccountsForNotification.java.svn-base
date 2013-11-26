package com.fitbank.person.query;

import com.fitbank.common.ApplicationDates;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Field;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.processor.query.QueryCommand;
import org.hibernate.ScrollableResults;

public class ObtainAccountsForNotification extends QueryCommand{

    private static final String HQL_ACCOUNTS = "select c.pk.ccuenta, c.csubsistema, p.descripcion "
            + "from com.fitbank.hb.persistence.acco.Taccount c, "
            + "com.fitbank.hb.persistence.prod.Tproduct p "
            + "where c.pk.fhasta=:expireDate and c.pk.cpersona_compania=:company "
            + "and p.pk.cidioma=:language and p.pk.cpersona_compania=c.pk.cpersona_compania "
            + "and p.pk.csubsistema=c.csubsistema and p.pk.cgrupoproducto=c.cgrupoproducto "
            + "and p.pk.cproducto=c.cproducto and p.pk.fhasta=c.pk.fhasta "
            + "and c.pk.ccuenta in ((select distinct(cp.pk.ccuenta) "
            + "from com.fitbank.hb.persistence.acco.person.Tpersonaccount cp "
            + "where cp.pk.cpersona_compania=:company and cp.pk.fhasta=:expireDate "
            + "and cp.pk.cpersona in "
            + "(select t.pk.cpersona "
            + "from com.fitbank.hb.persistence.person.Tperson t "
            + "where t.identificacion=:id "
            + "and t.pk.fhasta=:expireDate)))";
    
    private Table table;

    public Detail execute(Detail pDetail) throws Exception {
        this.table = new Table("CUENTAS", "CUENTAS");
        pDetail.addTable(table);
        this.completeDetail(pDetail);
        return pDetail;
    }


    private void completeDetail(Detail pDetail) throws Exception {
        ScrollableResults accounts = getAccounts(pDetail);
        Record rec=null;
        while(accounts.next()){
            rec=new Record();
            rec.addField(new Field("CUENTA", accounts.getString(0)));
            rec.addField(new Field("SUBSISTEMA", accounts.getString(1)));
            rec.addField(new Field("PRODUCTO", accounts.getString(2)));
            this.table.addRecord(rec);
        }
    }

    private ScrollableResults getAccounts(Detail pDetail){
        String id=pDetail.findFieldByName("IDENTIFICACION").getStringValue();
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_ACCOUNTS);
        utilHB.setString("id", id);
        utilHB.setInteger("company", pDetail.getCompany());
        utilHB.setString("language", pDetail.getLanguage());
        utilHB.setTimestamp("expireDate", ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return utilHB.getScroll();
    }

}
