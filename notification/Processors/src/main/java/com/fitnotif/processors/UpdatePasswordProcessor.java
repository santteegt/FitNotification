package com.fitnotif.processors;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.common.NotificationException;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Request;
import com.fitnotif.persistence.tablas.TUsuarioInformacionAdicional;
import com.fitnotif.persistence.tablas.TUsuarioPassword;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.LoginHelper;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.tables.helper.SessionHelper;
import com.fitnotif.tables.helper.UserHelper;

/**
 * Clase que actualiza la contraseña del usuario cuando el usuario lo solicita.
 * @author malgia
 * @version 1.0
 */
public class UpdatePasswordProcessor implements Processor{
    
    private TUsuarioPassword userPassword;
    
    /**
     * Proceso normal
     * Caduca la contraseña anterior y crea una nueva
     * @param request
     * @return 
     */
    
    @Override
    public Notification process(Request request) {
        Notification not = (Notification) request;
        this.updatePassword(not);
        this.updateUserInfo(not);        
        return not;
    }
    
    /**
     * Método que valida la contraseña
     * @param username
     * @param password
     * @return
     */
    private Boolean validatePassword(String username, String password){
        Boolean valid=false;
        this.userPassword=LoginHelper.getUserPassword(username);
        if(this.userPassword.getPassword().compareTo(password)==0){
            valid=true;
        }
        return valid;
    }
   
    /**
     * Método que actualiza la informacion adicional del Usuario.
     * @param not 
     */
    private void updateUserInfo(Notification not){
        TUsuarioInformacionAdicional uia = UserHelper.getUserInfo(not.getUser());        
        String newName=not.getControlFieldValueByName("NAME");
        String newEmail=not.getControlFieldValueByName("EMAIL");
        String newKeyword=not.getControlFieldValueByName("KEYWORD");
        if(uia!=null){
            TUsuarioInformacionAdicional nuia = (TUsuarioInformacionAdicional) uia.cloneMe();
            String currentName=uia.getNombre()!=null?uia.getNombre():"";
            String currentEmail=uia.getMail()!=null?uia.getMail():"";
            String currentKeyword=uia.getKeyword()!=null?uia.getKeyword():"";
            Boolean edited = false;
            if(newName!=null && currentName.toUpperCase().compareTo(newName.toUpperCase())!=0){
                edited=true;
                uia.setNombre(newName.toUpperCase());
            }
            if(newEmail!=null && currentEmail.toLowerCase().compareTo(newEmail.toLowerCase())!=0){
                edited=true;
                uia.setMail(newEmail.toLowerCase());
            }
            if(newKeyword!=null && currentKeyword.compareTo(newKeyword)!=0){
                edited=true;
                uia.setKeyword(newKeyword);
            }
            if(edited){
                nuia.setFhasta(DatesHelper.getCurrentTimeStamp());
                HibernateUtil.saveOrUpdate(nuia);
                uia.setFdesde(DatesHelper.getCurrentTimeStamp());
                HibernateUtil.save(uia);
            }
        }else{
            uia = new TUsuarioInformacionAdicional();
            uia.setFdesde(DatesHelper.getCurrentTimeStamp());
            uia.setFhasta(DatesHelper.getExpireTimeStamp());
            uia.setFkcusuario(not.getUser());
            uia.setMail(newEmail!=null?newEmail.toLowerCase():"");
            uia.setNombre(newName!=null?newName.toUpperCase():"");
            uia.setKeyword(newKeyword!=null?newKeyword:"");
            HibernateUtil.save(uia);
        }
    }
    
    /**
     * Método que actualiza la contraseña.
     * @param not 
     */
    private void updatePassword(Notification not){
        String username=not.getUser();
        String oldPassword=not.getPassword();
        String newPassword=not.getNewPassword();
        if(oldPassword!=null && newPassword!=null && oldPassword.compareTo(newPassword)==0){
            throw new NotificationException("PWD001", "LA CONTRASEÑA NUEVA NO PUEDE SER IGUAL A LA ANTIGUA."); 
        }
        if(!this.validatePassword(username, oldPassword)){
            throw new NotificationException("PWD002", "LA COMBINACIÓN DE USUARIO Y CONTRASEÑA ES INCORRECTA PARA {0}", username);
        } 
        if(newPassword!=null){  
            LoginHelper.expirePassword(this.userPassword);
            this.userPassword.setPassword(newPassword);
            this.userPassword.setFdesde(DatesHelper.getCurrentTimeStamp());  
            Integer expireDays=ParameterHelper.getIntegerValue("password-expiredays");
            this.userPassword.setFcaducidad(DatesHelper.getAddedDate(expireDays));
            HibernateUtil.saveOrUpdate(this.userPassword);
            SessionHelper.expireUserActiveSessions(username);
        }
    }
    
    /**
     * Método que pone en el Notification información adicional del usuario.
     * @param not
     * @return 
     */
    public static Notification fillUserInfo(Notification not){
        TUsuarioInformacionAdicional uia = UserHelper.getUserInfo(not.getUser());
        if(uia!=null){
            not.addControlField("NAME", uia.getNombre()!=null?uia.getNombre():"");
            not.addControlField("EMAIL", uia.getMail()!=null?uia.getMail():"");
        }else{
            not.addControlField("NAME", "");
            not.addControlField("EMAIL", "");
        }
        return not;
    }
}
