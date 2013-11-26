package com.fitnotif.processors;

import com.fitnotif.common.DatesHelper;
import com.fitnotif.notification.Notification;
import com.fitnotif.notification.Register;
import com.fitnotif.notification.Request;
import com.fitnotif.notification.emuntypes.UserTypes;
import com.fitnotif.persistence.tablas.TUsuarioInformacionAdicional;
import com.fitnotif.persistence.tablas.TUsuarioPassword;
import com.fitnotif.persistence.tablas.TUsuarios;
import com.fitnotif.persistence.util.HibernateUtil;
import com.fitnotif.tables.helper.ParameterHelper;
import com.fitnotif.tables.helper.UserHelper;
import java.util.Iterator;


/**
 * Clase que permite migrar los usuarios
 * @author malgia
 * @version 1.0
 */
public class FillUsersTableProcessor implements Processor{
    
    /**
     * Proceso normal
     * Agrega o actualiza la tabla de usuarios.
     * @param request
     * @return 
     */
    
    @Override
    public Request process(Request request) {
        Notification not= (Notification) request;
        Register user=null;
        TUsuarios tuser;
        String username="";
        String email="";
        String name="";
        Iterator<Register> users=not.getPageById("USUARIOS").getRegisters().iterator();
        while(users.hasNext()){
            user=users.next();
            username=user.getColumnValueByName("USUARIO");
            tuser=UserHelper.getUser(username);
            email=user.getColumnValueByName("EMAIL");
            name=user.getColumnValueByName("NOMBRE");
            if(tuser!=null){
                this.updateUser(tuser,email,name);
            }else{
                this.createNewUser(username, email, name);
            }
        }
        return not;
    }
       
    /**
     * Método que añade un nuevo usuario a la base de datos.
     * @param username
     * @param email
     * @param name 
     */
    private void createNewUser(String username, String email, String name){
        TUsuarios user=new TUsuarios();
        user.setActivo(Boolean.TRUE);
        user.setCusuario(username);
        user.setFdesde(DatesHelper.getCurrentTimeStamp());
        user.setFkcrol(UserTypes.OFICCER.getRole());
        user.setFkctipousuario(UserTypes.OFICCER.getType());
        user.setFkfhasta(DatesHelper.getExpireTimeStamp());
        user.setFvigenciadesde(DatesHelper.getCurrentTimeStamp());
        Integer expireDays=ParameterHelper.getIntegerValue("user-expiredays");
        user.setFvigenciahasta(DatesHelper.getAddedTimeStamp(expireDays));
        HibernateUtil.save(user);
        this.setUserInfo(user, email, name);
    }    
        
    /**
     * Método que actualza la información de un usuario.
     * @param user
     * @param email
     * @param name 
     */
    private void updateUser(TUsuarios user, String email, String name){
        user.setActivo(Boolean.TRUE);
        user.setFdesde(DatesHelper.getCurrentTimeStamp());
        user.setFvigenciadesde(DatesHelper.getCurrentTimeStamp());
        Integer expireDays=ParameterHelper.getIntegerValue("user-expiredays");
        user.setFvigenciahasta(DatesHelper.getAddedTimeStamp(expireDays));
        HibernateUtil.saveOrUpdate(user);
        this.setUserInfo(user, email, name);
    }      
    
    /**
     * Método que determina si el usuario ya posee informcación adicional.
     * @param user
     * @param email
     * @param name 
     */
    private void setUserInfo(TUsuarios user, String email, String name){
        TUsuarioInformacionAdicional userInfo=UserHelper.getUserInfo(user.getCusuario());
        if(userInfo==null){
            this.createUserInfo(user, email, name);
        }else{
            this.updateUserInfo(userInfo, email, name);
        }
        TUsuarioPassword userPwd=UserHelper.getUserPwd(user.getCusuario());
        if(userPwd==null){
            this.createPwd(user);
        }else{
            this.updatePwd(userPwd);
        }
    }
    
    /**
     * Método que graba la información adicional de un usuario.
     * @param user
     * @param email
     * @param name 
     */
    private void createUserInfo(TUsuarios user, String email, String name){
        TUsuarioInformacionAdicional userInfo=new TUsuarioInformacionAdicional();
        userInfo.setFdesde(DatesHelper.getCurrentTimeStamp());
        userInfo.setFhasta(DatesHelper.getExpireTimeStamp());
        userInfo.setFkcusuario(user.getCusuario());
        userInfo.setMail(email);
        userInfo.setNombre(name);
        HibernateUtil.save(userInfo);
    }      
    
    /**
     * Método que actualiza la información adicional de un usuario.
     * @param userInfo
     * @param email
     * @param name 
     */
    private void updateUserInfo(TUsuarioInformacionAdicional userInfo, String email, String name){
        TUsuarioInformacionAdicional expiredUserInfo= (TUsuarioInformacionAdicional) userInfo.cloneMe();
        expiredUserInfo.setFhasta(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.save(expiredUserInfo);
        userInfo.setFdesde(DatesHelper.getCurrentTimeStamp());
        userInfo.setMail(email);
        userInfo.setNombre(name);
        HibernateUtil.saveOrUpdate(userInfo);
    }
    
    /**
     * Método que graba la contraseña de un usuario.
     * @param user
     * @param email
     * @param name 
     */
    private void createPwd(TUsuarios user){
        TUsuarioPassword userPwd=new TUsuarioPassword();
        Integer expireDays=ParameterHelper.getIntegerValue("password-expiredays");
        userPwd.setFcaducidad(DatesHelper.getAddedDate(expireDays));
        userPwd.setFdesde(DatesHelper.getCurrentTimeStamp());
        userPwd.setFhasta(DatesHelper.getExpireTimeStamp());
        userPwd.setFkcusuario(user.getCusuario());
        String defaultPassword=ParameterHelper.getStringValue("password");
        userPwd.setPassword(defaultPassword);
        HibernateUtil.save(userPwd);
    }     
    
    /**
     * Método que resetea la contraseña de un usuario.
     * @param userInfo
     * @param email
     * @param name 
     */
    private void updatePwd(TUsuarioPassword userPwd){
        TUsuarioPassword expiredUserPwd= (TUsuarioPassword) userPwd.cloneMe();
        expiredUserPwd.setFhasta(DatesHelper.getCurrentTimeStamp());
        HibernateUtil.save(expiredUserPwd);
        userPwd.setFdesde(DatesHelper.getCurrentTimeStamp());
        Integer expireDays=ParameterHelper.getIntegerValue("password-expiredays");
        userPwd.setFcaducidad(DatesHelper.getAddedDate(expireDays));
        String defaultPassword=ParameterHelper.getStringValue("password");
        userPwd.setPassword(defaultPassword);
        HibernateUtil.saveOrUpdate(userPwd);
    }    
}
