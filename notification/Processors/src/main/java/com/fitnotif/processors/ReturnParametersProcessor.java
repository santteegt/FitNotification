package com.fitnotif.processors;

import com.fitnotif.notification.Column;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Page;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.ConfPagesTypes;
import com.fitnotif.notification.emuntypes.UserTypes;
import com.fitnotif.persistence.tablas.TSesiones;
import com.fitnotif.persistence.tablas.TUsuarios;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.tables.helper.SessionHelper;
import com.fitnotif.tables.helper.UserHelper;
import org.hibernate.ScrollableResults;

/**
 * Clase que devuelve los parametros del sistema
 * @author malgia
 * @version 1.0
 */
public class ReturnParametersProcessor implements Processor{
     
    private Notification notification = null;
    
    /**
     * Proceso normal
     * Retorna los parametros del sistema
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        this.notification=(Notification) request;
        this.notification.deleteAllPages();
        Page uci=new Page(ConfPagesTypes.CONFIGURATTION.getPage());
        Register reg=new Register("1");
        reg.addColumn(new Column("web-ip",ParameterHelper.getStringValue("web-ip")));
        reg.addColumn(new Column("web-port",ParameterHelper.getIntegerValue("web-port").toString()));
        reg.addColumn(new Column("session-timeout",ParameterHelper.getIntegerValue("session-timeout").toString()));
        reg.addColumn(new Column("user-expiredays",ParameterHelper.getIntegerValue("user-expiredays").toString()));
        reg.addColumn(new Column("password-expiredays",ParameterHelper.getIntegerValue("password-expiredays").toString()));
        reg.addColumn(new Column("password",ParameterHelper.getStringValue("password")));
        reg.addColumn(new Column("smtp-host",ParameterHelper.getStringValue("smtp-host")));
        reg.addColumn(new Column("smtp-port",ParameterHelper.getStringValue("smtp-port")));
        reg.addColumn(new Column("smtp-user",ParameterHelper.getStringValue("smtp-user")));
        reg.addColumn(new Column("smtp-password",ParameterHelper.getStringValue("smtp-password")));
        reg.addColumn(new Column("token-timeout",ParameterHelper.getIntegerValue("token-timeout").toString()));
        reg.addColumn(new Column("maxdev",ParameterHelper.getIntegerValue("maxdev").toString()));
        uci.addRegister(reg);
        this.notification.addPage(uci);
        this.fillSessionsPage();
        return this.notification;
    }
    
    /**
     * Método que llena una página con sesiones activas.
     */
    private void fillSessionsPage(){
        Page sessions=new Page(ConfPagesTypes.SESSION.getPage());
        Register reg=null;
        Integer i=1;
        ScrollableResults sr=SessionHelper.getActiveSessions();
        while(sr.next()){
            TSesiones session=(TSesiones) sr.get()[0];
            TUsuarios user=UserHelper.getUser(session.getCusuario());
            if(user.getFkcrol()==UserTypes.ADMIN.getRole()){
                continue;
            }
            reg=new Register(i.toString());
            reg.addColumn(new Column("user", user.getCusuario()));
            reg.addColumn(new Column("date", session.getFinicio().toString()));
            i++;
            sessions.addRegister(reg);
        }
        this.notification.addPage(sessions);
    }
}
