package com.fitbank.person.query;

import com.fitbank.common.ApplicationDates;
import com.fitbank.common.hb.UtilHB;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Field;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.processor.query.QueryCommand;
import org.hibernate.ScrollableResults;

public class ObtainOfficerForNotification extends QueryCommand{

    private static final String HQL_OFFICERINFORMATION = "select u.pk.cusuario, p.nombrelegal, o.nombre  "
                + "from com.fitbank.hb.persistence.safe.Tuser u, "
                + "com.fitbank.hb.persistence.person.Tperson p, "
                + "com.fitbank.hb.persistence.safe.Tusercompany cu, "
                + "com.fitbank.hb.persistence.loc.Toffice o "
                + "where u.pk.cusuario in "
                + "(select distinct(a.cusuario_oficialcuenta) "
                + "from com.fitbank.hb.persistence.acco.Taccount a "
                + "where a.pk.ccuenta in (select distinct(cp.pk.ccuenta) "
                + "from com.fitbank.hb.persistence.acco.person.Tpersonaccount cp "
                + "where cp.pk.cpersona_compania=:company "
                + "and cp.pk.fhasta=:expireDate and cp.pk.cpersona "
                + "in (select p.pk.cpersona "
                + "from com.fitbank.hb.persistence.person.Tperson p "
                + "where p.identificacion=:id "
                + "and p.pk.fhasta=:expireDate))) "
                + "and u.pk.fhasta=:expireDate and p.pk.cpersona=u.cpersona "
                + "and p.pk.fhasta=u.pk.fhasta and cu.pk.cpersona_compania=:company "
                + "and cu.pk.cusuario=u.pk.cusuario and cu.pk.fhasta=u.pk.fhasta "
                + "and o.pk.coficina=cu.coficina "
                + "and o.pk.cpersona_compania=cu.pk.cpersona_compania "
                + "and o.pk.fhasta=u.pk.fhasta";
    
    private Table table;

    public Detail execute(Detail pDetail) throws Exception {
        this.table = new Table("OFICIALES", "OFICIALES");
        pDetail.addTable(table);
        this.completeDetail(pDetail);
        return pDetail;
    }


    private void completeDetail(Detail pDetail) throws Exception {
        ScrollableResults officers = getOfficers(pDetail);
        Record rec=null;
        while(officers.next()){
            rec=new Record();
            rec.addField(new Field("OFICIAL", officers.getString(0)));
            rec.addField(new Field("NOMBRE", officers.getString(1)));
            rec.addField(new Field("OFICINA", officers.getString(2)));
            this.table.addRecord(rec);
        }
    }

    private ScrollableResults getOfficers(Detail pDetail){
        String id=pDetail.findFieldByName("IDENTIFICACION").getStringValue();
        UtilHB utilHB = new UtilHB();
        utilHB.setSentence(HQL_OFFICERINFORMATION);
        utilHB.setString("id", id);
        utilHB.setInteger("company", pDetail.getCompany());
        utilHB.setTimestamp("expireDate", ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return utilHB.getScroll();
    }

}
