package com.fitbank.processor.query;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ScrollableResults;

import com.fitbank.common.TransportBean;
import com.fitbank.common.exception.FitbankException;
import com.fitbank.common.helper.FormatDates;
import com.fitbank.common.logger.FitbankLogger;
import com.fitbank.dto.GeneralRequest;
import com.fitbank.dto.management.Criterion;
import com.fitbank.dto.management.Dependence;
import com.fitbank.dto.management.Detail;
import com.fitbank.dto.management.Field;
import com.fitbank.dto.management.Record;
import com.fitbank.dto.management.Table;
import com.fitbank.hb.persistence.trans.Tcommandmanagement;
import com.fitbank.processor.Processor;
import com.fitbank.processor.helper.FitbankClassLoader;
import com.fitbank.processor.helper.ProcessorHelper;
import com.fitbank.query.SQLBuilderFinal;
import com.fitbank.query.TableFiller;

/**
 * Clase que se encarga de obtener comandos para consultas especiales de una transacción, si no encuentra definido un
 * comando ejecuta la consulta genérica.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class QueryProcessor extends QueryCommand implements Processor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Lista de comandos a ejecutar para consultas especiales.
     */
    private List<Tcommandmanagement> lTcommandmanagement;

    private GeneralRequest generalRequest;

    /**
     * Detalle a procesar.
     */
    private Detail detail;

    private boolean bpm = false;

    /**
     * Adiciona criterios resultantes de queres anteriores de una tabla.
     * 
     * @param pTable Tabla a adicionar o comletar criterio con valor.
     */
    private boolean addCriteria(Table pTable) throws Exception {
        List<Dependence> dependences = pTable.getDependencies();
        boolean added = false;
        for (Dependence obj : dependences) {
            String alias = obj.getFrom().substring(0, obj.getFrom().indexOf("."));
            String fieldname = obj.getFrom().substring(obj.getFrom().indexOf(".") + 1);
            // TODO completar exception alias no existe
            Criterion criteria = this.lookForCriteria(pTable, alias, fieldname);
            if (criteria == null) {
                return false;
            }
            pTable.addCriterion(criteria);
            added = true;
        }
        return added;
    }

    @Override
    public Detail execute(Detail pDetail) throws Exception {
        return this.normalProcess(pDetail);
    }

    /**
     * Ejecuta comandos de inicio de una transacción.
     */
    private void executeBeginCommands() throws Exception {
        if (this.lTcommandmanagement != null) {
            for (Tcommandmanagement command : this.lTcommandmanagement) {
                if (command.getEvento().compareTo("I") == 0) {
                    this.executeCommand(command);
                }
            }
        }
    }

    private void verifyBPM(Detail pDetail) throws Exception {
        Field f = pDetail.findFieldByName("__BPM_CALL_");
        if ((f != null) && (f.getValue() != null)) {
            this.bpm = true;
        }
    }

    /**
     * Ejecuta un comando para la transacción.
     * 
     * @param pCommand
     * @throws Exception
     */
    private void executeCommand(Tcommandmanagement pCommand) throws Exception {
        String executed = pCommand.getEjecutadopor();
        if (executed == null) {
            executed = "F";
        }
        FitbankLogger.getLogger().debug("Comando de consulta " + pCommand.getComando() + " ejecutado por " + 
                executed + (this.bpm ? " BPM ON" : " BPM OFF"));
        if (this.bpm && (executed.compareTo("F") == 0)) {
            return;
        }
        if (!this.bpm && (executed.compareTo("B") == 0)) {
            return;
        }
	boolean notification = executed.compareTo("N") == 0;
        if(notification && ((Detail)this.generalRequest).findFieldByName("__NOTIFICATION_M") == null ){
            return;
        }
        boolean fitbankClassLoader = executed.compareTo("C") == 0;
        QueryCommand command = null;
        try {
            if (fitbankClassLoader) {
                command = (QueryCommand) FitbankClassLoader.forName(pCommand.getComando(), pCommand.getTipocomando()).newInstance();
            } else {
                command = (QueryCommand) Class.forName(pCommand.getComando()).newInstance();
            }
            command.setParameter(pCommand.getParametro());
            long init = System.currentTimeMillis();
            this.generalRequest = command.execute((Detail) this.generalRequest);
            FitbankLogger.getLogger().debug(
                "Tiempo tomado en el comando "
                        + pCommand.getComando()
                        + " "
                        + this.generalRequest.getMessageid()
                        + " "
                        + FormatDates.getInstance().getTimeCountFormat()
                                .format(new Date(System.currentTimeMillis() - init)));
            FitbankLogger.getLogger().debug(
                "Salida al comando " + pCommand.getComando() + " " + ((Detail) this.generalRequest).toErrorXml());
        } catch (ClassNotFoundException e) {
            throw new FitbankException("FIN010",
                    "COMANDO {0} A EJECUTAR NO EXISTE SUBSISTEMA {1} TRANSACCION {2} VERSION {3}", e,
                    pCommand.getComando(), this.generalRequest.getSubsystem(), this.generalRequest.getTransaction(),
                    this.generalRequest.getVersion());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Ejecuta comandos de finalizacion de la transacción.
     */
    private void executeEndCommands() throws Exception {
        if (this.lTcommandmanagement != null) {
            for (Tcommandmanagement command : this.lTcommandmanagement) {
                if (command.getEvento().compareTo("F") == 0) {
                    this.executeCommand(command);
                }
            }
        }
    }

    /**
     * Ejecuta comandos de procesamiento de una transacción.
     */
    private void executeProcessCommands() throws Exception {
        if (this.lTcommandmanagement != null) {
            for (Tcommandmanagement command : this.lTcommandmanagement) {
                if (command.getEvento().compareTo("P") == 0) {
                    this.executeCommand(command);
                }
            }
        }
    }

    /**
     * Obtiene el listado de Beanes.
     * 
     * @param pBeans
     * @return
     */
    public List<TransportBean> getTransportBean(List<Object> pBeans) {
        List<TransportBean> beans = new ArrayList<TransportBean>();
        if (pBeans != null) {
            for (Object obj : pBeans) {
                if (obj instanceof TransportBean) {
                    beans.add((TransportBean) obj);
                }
                if (obj instanceof Object[]) {
                    Object[] obj1 = (Object[]) obj;
                    if (obj1.length > 0) {
                        beans.add((TransportBean) obj1[0]);
                    }
                }
            }
        }
        return beans;
    }

    /**
     * Busca un criterio en la tabla. Si existe retorna ese criterio que se cambiara de valor, si no existe crea un
     * nuevo criterio con valor null.
     * 
     * @param pTable Datos de una tabla.
     * @param pName Nombre del criterio.
     * @return
     */
    private Criterion lookForCriteria(Table pTable, String pAlias, String pName) throws Exception {
        Object value = this.lookForValue(pAlias, pName);
        if (value == null) {
            return null;
        }
        List<Criterion> criterias = pTable.getCriteria();
        Criterion criteria = null;
        for (Criterion obj : criterias) {
            if (obj.getName().compareTo(pName) == 0) {
                criteria = obj;
                criteria.setValue(value);
            }
        }
        if (criteria == null) {
            criteria = new Criterion(pName, value);
            criteria.setCondition(" = ");
        }
        return criteria;
    }

    /**
     * Busca el valor del criterio de una tabla que se consulto previamnete.
     * 
     * @param pAlias Alias de la tabla a buscar.
     * @param pName Nombre del campo a obtener el valor.
     * @return
     */
    private Object lookForValue(String pAlias, String pName) throws Exception {
        Object value = null;
        Table table = this.detail.findTableByAlias(pAlias);
        if (table == null) {
            return null;
        }
        List<Field> fields = null;
        Integer index = table.getCurrentRecord();
        if (index == null) {
            return null;
        }
        Record record = table.findRecordByNumber(index);
        if (record == null) {
            return null;
        }
        fields = record.getFields();
        for (Field obj : fields) {
            if (obj.getName().compareTo(pName) == 0) {
                value = obj.getValue();
                break;
            }
        }
        return value;
    }

    private void manageCriteria(Table pTable, String address, Criterion criterion) throws Exception {
        try {
            Object criteria = this.detail.getValueByAddress(address + ".value");
            if (criteria == null) {
                pTable.setReadonly(true);
            }
            criterion.setValue(this.detail.getValueByAddress(address + ".value"));
        } catch (Exception e) {
            FitbankLogger.getLogger().warn("No se pudo encontrar el valor de " + address + ".value", e);
        }
    }

    /**
     * Procesa la consulta.
     * 
     * @param pDetail Petición de consulta
     * @return the detail
     * @throws Exception the exception
     */
    public Detail normalProcess(Detail pDetail) throws Exception {
        if ("Join".equals(pDetail.getProcessType())) {
            // process join query
            return new JoinQueryCommand().execute(pDetail);
        }
        this.detail = pDetail;
        for (Table table : pDetail.getTables()) {
            if (table.isReadonly()) {
                continue;
            }
            if (table.isSpecial()) {
                continue;
            }
            if (table.getRecordCount() > 0) {
                Record r = table.getRecords().iterator().next();
                if (r.getFields().size() < 1) {
                    continue;
                }
            }
            if ((table.getDependencies().size() > 0) && !this.addCriteria(table)) {
                // Add criteria
                table.clearRecords();
                continue;
            }
            this.processTable(table);
        }
        return this.detail;
    }

    /**
     * Verifica si el valor de un criterio debe ser tomado como resultado de una consulta de una tabla previa.
     * 
     * @param pTable Tabla a procesar
     * @throws Exception Error
     */
    private Map<String, String> prepareDependencyCriteria(Table pTable) throws Exception {
        Map<String, String> ref = new HashMap<String, String>();
        List<Criterion> cri = pTable.getCriteria();
        for (Criterion criterion : cri) {
            Object val = criterion.getValue();
            if ((val != null) && (val instanceof String)) {
                String sVal = (String) val;
                int begin = sVal.indexOf("_$");
                int end = sVal.indexOf("$_");
                if ((begin > -1) && (end > -1) && (end > begin)) {
                    String address = sVal.substring(begin + 2, end);
                    ref.put(criterion.getName(), sVal);
                    this.manageCriteria(pTable, address, criterion);
                    if (pTable.isReadonly()) {
                        break;
                    }
                }
            }
        }
        return ref;
    }

    /**
     * Obtiene el comando de consulta especial, si no existe ejecuta la consulta generica.
     */
    public GeneralRequest process(GeneralRequest pGeneralRequest) throws Exception {
        this.generalRequest = pGeneralRequest;
        this.verifyBPM((Detail) pGeneralRequest);
        this.lTcommandmanagement = ProcessorHelper.getInstance().getTcommandmanagement(pGeneralRequest);
        if ((this.lTcommandmanagement == null) || (this.lTcommandmanagement.size() == 0)) {
            this.normalProcess((Detail) pGeneralRequest);
        } else {
            // process comands query
            this.processSpecialCommands();
        }
        return this.generalRequest;
    }

    private void processSpecialCommands() throws Exception {
        this.executeBeginCommands();
        this.executeProcessCommands();
        this.executeEndCommands();
    }

    /**
     * Procesa la consulta referente a una Tabla
     * 
     * @param pTable Tabla
     * @throws Exception
     */
    public void processTable(Table pTable) throws Exception {
        SQLBuilderFinal hql = new SQLBuilderFinal(pTable);
        Map<String, String> ref = this.prepareDependencyCriteria(pTable);
        if (pTable.isReadonly()) {
            return;
        }
        ScrollableResults beans = hql.execute();
        for (String criName : ref.keySet()) {
            List<Criterion> cri = pTable.getCriteria();
            for (Criterion criterion : cri) {
                if (criterion.getName().compareTo(criName) == 0) {
                    criterion.setValue(ref.get(criName));
                }
            }
        }
        try {
            new TableFiller(pTable, beans, hql);
        } finally {
            beans.close();
        }
    }

}
