package com.fitnotif.dto;

import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase Madre de todos los tipos de Petici�n.
 * 
 * @author Fitbank
 * @version 2.0
 */
public abstract class GeneralRequest extends HashMap<String, Object> implements Serializable, Cloneable {

    private static final long serialVersionUID = 3L;

    /**
     * Tipo de mensaje
     */
    protected String processType;

    /**
     * Tipo de mensaje
     */
    protected String type;

    /**
     * Codigo de Usuario
     */
    protected String user;

    /**
     * Codigo de Usuario interno.
     */
    protected Integer innerusercode;

    /**
     * Password del usuario.
     */
    protected String password;

    /**
     * Codigo de Terminal.
     */
    protected String terminal;

    /**
     * Idioma preferido del usuario.
     */
    protected String language;

    /**
     * Rol del Usuario
     */
    protected Integer role;

    /**
     * Identificador de la sesión o token.
     */
    protected String sessionid;

    /**
     * Nivel de Seguridad del Usuario
     */
    protected Integer securitylevel;

    /**
     * IP del terminal
     */
    protected String ipaddress;

    /**
     * MAC address del terminal
     */
    protected String macaddress;

    /**
     * Subsistema seleccionado por el usuario
     */
    protected String subsystem;

    /**
     * Transacci�n seleccionado por el Usuario
     */
    protected String transaction;

    /**
     * Versión de la transacción selecionada
     */
    protected String version;

    /**
     * Numero de mensaje con el cual se procesa una transacción. user + threadNumber + timestamp de la base de datos.
     */
    protected String messageId;

    /**
     * Numero de hilo, utilizado en procesos batch procesamiento multihilo, este formara parte del numero de mensaje de
     * la transacción.
     */
    private String threadNumber = "1";

    /**
     * Numero de comprobante contable.
     */
    private String accountinNumber;

    /** Canal */
    protected String channel;

    /**
     * Compañía origen.
     */
    protected Integer company;

    /**
     * Sucursal origen.
     */
    protected Integer originBranch;

    /**
     * Oficina origen.
     */
    protected Integer originOffice;

    /**
     * Fecha conatable de la transacción.
     */
    protected Date accountingDate;

    /**
     * Codigo de usuario que autoriza la transacción.
     */
    protected String authorizeruser;

    /**
     * Password del usuario que autoriza la transacción.
     */
    protected String authorizerpassword;

    /**
     * Area
     */
    protected String area;

    /**
     * Indica si la transacción se ejecuta en modo reverso.
     */
    protected String reverse = "0";

    /**
     * Numero de mensaje a reversar.
     */
    protected String messageidreverse;

    /**
     * Password del usuario.
     */
    protected String newpassword;

    /**
     * Password del usuario.
     */
    protected String realtransaction;

    /**
     * Indica si la transacci�n se ejecuta en modo batch.
     */
    protected boolean batch = false;

    /**
     * Indica que la transacci�n es generada de manera Externa
     */
    protected boolean externalTransaction = false;

    /**
     * Nombre del archivo que origina la transacción
     */
    protected String file;
    
    /**
     * General response.
     */
    protected GeneralResponse response = null;

    private void appendValidate(Node pNode, String pTag, Object pValue) throws Exception {
        if (pValue != null) {
            XMLHelper.appendChild(pNode, pTag, pValue);
        }
    }

    public void copy(GeneralRequest pParam) throws Exception {
        this.copyFields(GeneralRequest.class.getDeclaredFields(), pParam);
    }

    private void copyFields(Field[] fs, GeneralRequest pParam) throws Exception {
        for (Field f : fs) {
            try {
                f.set(pParam, f.get(this));
            } catch (Exception e) {
                continue;
            }
        }
    }

    private void copyFieldsToDetail(Field[] fs, GeneralRequest pParam) throws Exception {
        for (Field f : fs) {
            try {
                f.set(pParam, f.get(this));
            } catch (Exception e) {
                continue;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void copySuperClasses(Object o) {

        try {
            Class subclass = o.getClass();
            Class superclass = subclass.getSuperclass();
            while (superclass != null) {
                String className = superclass.getName();
                subclass = superclass;
                if (className.compareTo("com.fitbank.dto.GeneralRequest") == 0) {
                    Field[] campos = subclass.getDeclaredFields();
                    this.copy((GeneralRequest) o);
                    for (Field campo : campos) {
                        try {
                            campo.set(this, campo.get(o));
                        } catch (IllegalAccessException e) {
                            continue;
                        }
                    }
                }
                superclass = subclass.getSuperclass();
            }

        } catch (Exception ex) {
            
        }

    }

    public void copyToDetail(GeneralRequest pParam) {
        try {
            this.copyFieldsToDetail(pParam.getClass().getDeclaredFields(), pParam);
            this.copySuperClasses(pParam);
            this.copyFieldsToDetail(pParam.getClass().getFields(), pParam);
        } catch (Exception ex) {
            
        }

    }

    /**
     * Llena un general request dado un nodo de un Documento XML.
     * 
     * @param pNode
     */
    protected void fillRequest(Node pNode) {
        this.user = XMLHelper.getStringValueByTag(pNode, "USR");
        this.user = (this.user != null) ? this.user.toUpperCase() : this.user;
        this.password = XMLHelper.getStringValueByTag(pNode, "PWD");
        this.newpassword = XMLHelper.getStringValueByTag(pNode, "NPW");
        this.language = XMLHelper.getStringValueByTag(pNode, "IDM");
        this.terminal = XMLHelper.getStringValueByTag(pNode, "TER");
        this.processType = XMLHelper.getStringValueByTag(pNode, "TPP");
        this.type = XMLHelper.getStringValueByTag(pNode, "TIP");
        this.sessionid = XMLHelper.getStringValueByTag(pNode, "SID");
        this.role = XMLHelper.getIntegerValueByTag(pNode, "ROL");
        this.securitylevel = XMLHelper.getIntegerValueByTag(pNode, "NVS");
        this.ipaddress = XMLHelper.getStringValueByTag(pNode, "IPA");
        this.macaddress = XMLHelper.getStringValueByTag(pNode, "MAC");
        this.subsystem = XMLHelper.getStringValueByTag(pNode, "SUB");
        this.transaction = XMLHelper.getStringValueByTag(pNode, "TRN");
        this.version = XMLHelper.getStringValueByTag(pNode, "VER");
        this.company = XMLHelper.getIntegerValueByTag(pNode, "CIO");
        this.originBranch = XMLHelper.getIntegerValueByTag(pNode, "SUC");
        this.originOffice = XMLHelper.getIntegerValueByTag(pNode, "OFC");
        this.messageId = XMLHelper.getStringValueByTag(pNode, "MSG");
        this.messageidreverse = XMLHelper.getStringValueByTag(pNode, "NMR");
        this.reverse = XMLHelper.getStringValueByTag(pNode, "REV");
        this.channel = XMLHelper.getStringValueByTag(pNode, "CAN");
        this.accountingDate = XMLHelper.getDateValueByTag(pNode, "FCN");
        this.realtransaction = XMLHelper.getStringValueByTag(pNode, "RTN");
        this.area = XMLHelper.getStringValueByTag(pNode, "ARE");
        // Authorizer
        Element e = (Element) pNode;
        if (e != null) {
            NodeList nl = e.getElementsByTagName("AUT");
            if (nl.getLength() > 0) {
                Node n = nl.item(0);
                this.authorizeruser = XMLHelper.getStringValueByTag(n, "USR");
                this.authorizerpassword = XMLHelper.getStringValueByTag(n, "PWD");
            }
        }
    }

    /**
     * Entrega el Valor de accountingdate
     * 
     * @return accountingdate
     */
    public Date getAccountingDate() {
        return this.accountingDate;
    }

    @Deprecated
    public Date getAccountingdate() {
        return this.getAccountingDate();
    }

    /**
     * Entrega el valor de accountinNumber
     * 
     * @return accountinNumber
     */
    public String getAccountinNumber() {
        return this.accountinNumber;
    }

    public String getArea() {
        return this.area;
    }

    /**
     * Entrega el valor de authorizerpassword.
     * 
     * @return authorizerpassword.
     */
    public String getAuthorizerpassword() {
        return this.authorizerpassword;
    }

    /**
     * Entrega el valor de authorizeruser.
     * 
     * @return authorizeruser.
     */
    public String getAuthorizeruser() {
        return this.authorizeruser;
    }

    /**
     * Entrega el Valor de channel.
     * 
     * @return channel.
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * Entrega el Valor de company.
     * 
     * @return company.
     */
    public Integer getCompany() {
        return this.company;
    }

    public String getFile() {
        return this.file;
    }

    /**
     * Transforma el request General a un nodo XML.
     * 
     * @return nodo.
     * @throws Exception
     */
    public Node getHeaderNode() throws Exception {
        Node node = XMLHelper.createNode("GRQ");
        XMLHelper.appendChild(node, "USR", this.user);
        XMLHelper.appendChild(node, "IDM", this.language);
        XMLHelper.appendChild(node, "TER", this.terminal);
        XMLHelper.appendChild(node, "SID", this.sessionid);
        XMLHelper.appendChild(node, "ROL", this.role);
        XMLHelper.appendChild(node, "NVS", this.securitylevel);
        if ((this.type != null) && (this.type.compareTo("SIG") == 0)) {
            XMLHelper.appendChild(node, "PWD", this.password);
            XMLHelper.appendChild(node, "NPW", this.newpassword);
        }
        if (this.ipaddress != null) {
            XMLHelper.appendChild(node, "IPA", this.ipaddress);
        } else {
            XMLHelper.appendChild(node, "MAC", this.macaddress);
        }
        XMLHelper.appendChild(node, "TIP", this.type);
        XMLHelper.appendChild(node, "SUB", this.subsystem);
        XMLHelper.appendChild(node, "TRN", this.transaction);
        XMLHelper.appendChild(node, "VER", this.version);
        XMLHelper.appendChild(node, "ARE", this.area);
        this.appendValidate(node, "TPP", this.processType);
        this.appendValidate(node, "CIO", this.company);
        this.appendValidate(node, "SUC", this.originBranch);
        this.appendValidate(node, "OFC", this.originOffice);
        this.appendValidate(node, "MSG", this.messageId);
        this.appendValidate(node, "NMR", this.messageidreverse);
        this.appendValidate(node, "REV", this.reverse);
        this.appendValidate(node, "CAN", this.channel);
        this.appendValidate(node, "FCN", this.accountingDate);
        this.appendValidate(node, "RTN", this.realtransaction);
        return node;
    }

    /**
     * Entrega el valor de innerusercode
     * 
     * @return String
     */
    public Integer getInnerusercode() {
        return this.innerusercode;
    }

    /**
     * Entrega el Valor de ipaddress.
     * 
     * @return ipaddress.
     */
    public String getIpaddress() {
        return this.ipaddress;
    }

    /**
     * Entrega el Valor de language.
     * 
     * @return language.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Entrega el valor de macaddress.
     * 
     * @return macaddress.
     */
    public String getMacaddress() {
        return this.macaddress;
    }

    /**
     * Entrega el valor de messageid.
     * 
     * @return messageid.
     */
    public String getMessageId() {
        return this.messageId;
    }

    @Deprecated
    public String getMessageid() {
        return this.getMessageId();
    }

    /**
     * Entrega el numeor de mensaje a reversar.
     * 
     * @return messageidreverse
     */
    public String getMessageidreverse() {
        return this.messageidreverse;
    }

    public String getNewpassword() {
        return this.newpassword;
    }

    /**
     * Entrega el valor de originbranch.
     * 
     * @return originbranch.
     */
    public Integer getOriginBranch() {
        return this.originBranch;
    }

    @Deprecated
    public Integer getOriginbranch() {
        return this.getOriginBranch();
    }

    /**
     * Entrega el valor de originoffice.
     * 
     * @return originoffice.
     */
    public Integer getOriginOffice() {
        return this.originOffice;
    }

    @Deprecated
    public Integer getOriginoffice() {
        return this.getOriginOffice();
    }

    /**
     * Entrega el valor de password.
     * 
     * @return password.
     */
    public String getPassword() {
        return this.password;
    }

    public String getRealtransaction() {
        return this.realtransaction;
    }

    /**
     * Entrega el valor de response.
     * 
     * @return response.
     */
    public GeneralResponse getResponse() {
        return this.response;
    }    
    
    /**
     * Indica si la transacción se ejecuta en modo normal o reverso.
     * 
     * @return
     */
    public String getReverse() {
        return ((this.reverse == null) || (this.reverse.compareTo("") == 0)) ? "0" : this.reverse;
    }

    /**
     * Entrega el Valor de role.
     * 
     * @return role.
     */
    public Integer getRole() {
        return this.role;
    }

    /**
     * Entrega el Valor de securitylevel.
     * 
     * @return securitylevel.
     */
    public Integer getSecuritylevel() {
        return this.securitylevel;
    }

    /**
     * Entrega el Valor de sessionid.
     * 
     * @return sessionid.
     */
    public String getSessionid() {
        return this.sessionid;
    }

    /**
     * Entrega el Valor de subsystem.
     * 
     * @return subsystem.
     */
    public String getSubsystem() {
        return this.subsystem;
    }

    /**
     * Entrega el Valor de terminal.
     * 
     * @return terminal.
     */
    public String getTerminal() {
        return this.terminal;
    }

    /**
     * Entrega el valor de threadNumber
     * 
     * @return threadNumber
     */
    public String getThreadNumber() {
        return this.threadNumber;
    }

    /**
     * Entrega el Valor de transaction.
     * 
     * @return transaction.
     */
    public String getTransaction() {
        return this.transaction;
    }

    public String getProcessType() {
        return this.processType;
    }

    /**
     * Entrega el valor de type.
     * 
     * @return type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Entrega el Valor de user.
     * 
     * @return user.
     */
    public String getUser() {
        this.user = (this.user != null) ? this.user.toUpperCase() : this.user;
        return this.user;
    }

    /**
     * Entrega el Valor de version.
     * 
     * @return version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Entrega el valor de isBatch
     * 
     * @return boolean
     */
    public boolean isBatch() {
        return this.batch;
    }

    public boolean isExternalTransaction() {
        return this.externalTransaction;
    }

    /**
     * Indica si la transacción necesita iniciar una transacción de Hibernate.
     * 
     * @return
     */
    public abstract boolean isTransactionRequired();

    /**
     * Fija el valor de accountingdate
     * 
     * @param accountingDate
     */
    public void setAccountingDate(Date accountingDate) {
        this.accountingDate = accountingDate;
    }

    @Deprecated
    public void setAccountingdate(Date accountingDate) {
        this.setAccountingDate(accountingDate);
    }

    /**
     * Fija el valor de accountinNumber
     * 
     * @param accountinNumber
     */
    public void setAccountinNumber(String accountinNumber) {
        this.accountinNumber = accountinNumber;
    }

    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Fija el valor de authorizerpassword.
     * 
     * @param authorizerpassword .
     */
    public void setAuthorizerpassword(String authorizerpassword) {
        this.authorizerpassword = authorizerpassword;
    }

    /**
     * Fija el valor de authorizeruser.
     * 
     * @param authorizeruser .
     */
    public void setAuthorizeruser(String authorizeruser) {
        this.authorizeruser = authorizeruser;
    }

    /**
     * Fija el valor de isBatch
     * 
     * @param isBatch
     */
    public void setBatch(boolean isBatch) {
        this.batch = isBatch;
    }

    /**
     * Fija el valor de channel
     * 
     * @param channel Nuevo valor de channel.
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Fija el valor de company
     * 
     * @param company Nuevo valor de company.
     */
    public void setCompany(Integer company) {
        this.company = company;
    }

    public void setExternalTransaction(boolean externalTransaction) {
        this.externalTransaction = externalTransaction;
    }

    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Fija el valor de innerusercode
     * 
     * @param innerusercode
     */
    public void setInnerusercode(Integer innerusercode) {
        this.innerusercode = innerusercode;
    }

    /**
     * Fija el valor de ipaddress
     * 
     * @param ipaddress Nuevo valor de ipaddress.
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    /**
     * Fija el valor de language
     * 
     * @param language Nuevo valor de language.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Fija el valor de macaddress.
     * 
     * @param macaddress .
     */
    public void setMacAddress(String macaddress) {
        this.macaddress = macaddress;
    }

    /**
     * @param macaddress
     *
     * @deprecated usar setMacAddress
     */
    @Deprecated
    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    /**
     * Fija el valor de messageId.
     * 
     * @param messageId el id del mensaje
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @param messageId
     *
     * @deprecated usar setMessageId
     */
    @Deprecated
    public void setMessageid(String messageId) {
        this.setMessageId(messageId);
    }

    /**
     * Fija el número de mensaje a reversar.
     *
     * @param pMessageidreverse
     */
    public void setMessageIdReverse(String pMessageidreverse) {
        this.messageidreverse = pMessageidreverse;
    }

    @Deprecated
    public void setMessageidreverse(String pMessageidreverse) {
        this.messageidreverse = pMessageidreverse;
    }

    public void setNewPassword(String newpassword) {
        this.newpassword = newpassword;
    }

    @Deprecated
    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    /**
     * Fija el valor de originbranch.
     * 
     * @param originBranch .
     */
    public void setOriginBranch(Integer originBranch) {
        this.originBranch = originBranch;
    }

    @Deprecated
    public void setOriginbranch(Integer originBranch) {
        this.setOriginBranch(originBranch);
    }

    /**
     * Fija el valor de originoffice.
     * 
     * @param originOffice .
     */
    public void setOriginOffice(Integer originOffice) {
        this.originOffice = originOffice;
    }

    @Deprecated
    public void setOriginoffice(Integer originOffice) {
        this.setOriginOffice(originOffice);
    }

    /**
     * Fija el valor de password.
     * 
     * @param password .
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealtransaction(String newrealtransaction) {
        this.realtransaction = newrealtransaction;
    }


    /**
     * Fija el valor de response.
     * 
     * @param response .
     */
    public void setResponse(GeneralResponse response) {
        this.response = response;
    }  
    
    /**
     * Fija la transacción en modo normal (0), reverso(1).
     * 
     * @param pReverse
     */
    public void setReverse(String pReverse) {
        if ((pReverse == null) || (pReverse.compareTo("") == 0)) {
            this.reverse = "0";
        } else {
            this.reverse = pReverse;
        }
    }

    /**
     * Fija el valor de role
     * 
     * @param role Nuevo valor de role.
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * Fija el valor de securitylevel
     * 
     * @param securitylevel Nuevo valor de securitylevel.
     */
    public void setSecuritylevel(Integer securitylevel) {
        this.securitylevel = securitylevel;
    }

    /**
     * Fija el valor de sessionid
     * 
     * @param sessionid Nuevo valor de sessionid.
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * Fija el valor de subsystem
     * 
     * @param subsystem Nuevo valor de subsystem.
     */
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    /**
     * Fija el valor de terminal
     * 
     * @param terminal Nuevo valor de terminal.
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * Fija el valor de threadNumber
     * 
     * @param threadNumber
     */
    public void setThreadNumber(String threadNumber) {
        this.threadNumber = threadNumber;
    }

    /**
     * Fija el valor de transaction
     * 
     * @param transaction Nuevo valor de transaction.
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public void setProcessType(String pProcessType) {
        this.processType = pProcessType;
    }

    /**
     * Fija el valor de type.
     * 
     * @param pType .
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     * Fija el valor de user
     * 
     * @param pUser Nuevo valor de user.
     */
    public void setUser(String pUser) {
        this.user = pUser;
        this.user = (this.user != null) ? this.user.toUpperCase() : this.user;
    }

    /**
     * Fija el valor de version
     * 
     * @param version Nuevo valor de version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Implementaci�n toString
     */
    @Override
    public String toString() {
        Field[] fs = this.getClass().getDeclaredFields();
        String data = "";
        for (Field f : fs) {
            try {
                String name = f.getName();
                if ((name.compareTo("hashValue") == 0) || (name.compareTo("serialVersionUID") == 0)) {
                    continue;
                }
                data += name + "=" + f.get(this) + ";";
            } catch (Exception e) {
                continue;
            }
        }
        if (data.compareTo("") == 0) {
            data = super.toString();
        }
        return data;
    }
}
