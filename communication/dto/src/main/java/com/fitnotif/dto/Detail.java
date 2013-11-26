package com.fitnotif.dto;

import com.fitnotif.parser.XMLHelper;
import com.fitnotif.parser.XMLParser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Datos de mantenimiento.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class Detail extends GeneralRequest {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    public static Detail valueOf(String pXML) throws Exception {
        synchronized (Detail.class) {
            XMLParser xml = new XMLParser(pXML) {};
            return new Detail(xml);
        }
    }

    /**
     * Lista de tablas que intervienen en consultas o mantenimientos de una transaccion por alias.
     */
    private final Map<String, Table> tablesByAlias = new LinkedHashMap<String, Table>();

    /**
     * Lista de tablas que intervienen en consultas o mantenimientos de una transaccion por nombre.
     */
    private final Map<String, Table> tablesByName = new LinkedHashMap<String, Table>();

    /**
     * Lista de tablas que intervienen en consultas o mantenimientos de una transaccion por nombre.
     */
    private final List<Table> tables = new ArrayList<Table>();

    /**
     * Lista de campos de control del forumlario por nombre.
     */
    private final Map<String, Field> fieldsByName = new LinkedHashMap<String, Field>();

    public Detail() {
    }

    /**
     * Crea un detalle dado un XMLPArser.
     * 
     * @param pXml
     */
    public Detail(XMLParser pXml) {
        // Create header
        Node node = pXml.getChildByName("GRQ");
        // Llena el generalrequest
        super.fillRequest(node);
        // Crea y llena objetos para el manejo del xml.
        this.completeDetail(pXml);
    }

    /**
     * Adiciona un campo al registro.
     */
    public void addField(Field pField) {
        pField.setParent(this);
        this.fieldsByName.put(pField.getName(), pField);
        this.put("f" + pField.getName(), pField);
    }

    /**
     * Adiciona un campo al registro.
     */
    public void addTable(Table pTable) {
        pTable.setParent(this);
        this.tablesByAlias.put(pTable.getAlias(), pTable);
        this.tablesByName.put(pTable.getName(), pTable);
        this.tables.clear();
        for (Table t : this.tablesByAlias.values()) {
            this.tables.add(t);
        }
        this.put("t" + pTable.getAlias(), pTable);
    }

    public void addTable(Table pTable, int pPosition) {
        pTable.setParent(this);
        this.tablesByAlias.put(pTable.getAlias(), pTable);
        this.tablesByName.put(pTable.getName(), pTable);
        this.tables.add(pPosition, pTable);
        this.put("t" + pTable.getAlias(), pTable);
    }

    public Detail cloneMe() throws Exception {
        return Detail.valueOf(this.toErrorXml());
    }

    /**
     * Crea objetos tabla, registros, campos, criterios, descripciones y campos de control
     * 
     * @param pXml Xml de entrada
     */
    private void completeDetail(XMLParser pXml) {
        // Call table
        Node node = pXml.getChildByName("DET");
        NodeList nltbl = pXml.getChildrenByName(node, "TBL");
        int size = 0;
        if (nltbl != null) {
            size = nltbl.getLength();
        }
        for (int i = 0; i < size; i++) {
            Node n = nltbl.item(i);
            Table table = new Table(n);
            this.addTable(table);
        }

        // Control fields
        node = pXml.getChildByName(node, "CTL");
        NodeList nlcam = pXml.getChildrenByName(node, "CAM");
        if (nlcam != null) {
            for (int i = 0; i < nlcam.getLength(); i++) {
                Node n = nlcam.item(i);
                Field field = new Field(n);
                this.addField(field);
            }
        }
        try {
            node = pXml.getChildByName("GRS");
            String msgu = pXml.getChildByName(node, "MSGU").getFirstChild().getNodeValue();
            String cod = node.getAttributes().getNamedItem("cod").getNodeValue();
            GeneralResponse gr = new GeneralResponse(cod, msgu);
            this.setResponse(gr);
        } catch (Exception e) {

        }
    }

    /**
     * Elimina las tablas que tienen registros vacios
     * 
     * @return
     */
    public void deleteUnusedTables() {
        List<String> alias = new ArrayList<String>();
        for (Table t : this.getTables()) {
            t.clearEmptyRecords();
            int records = t.getRecordCount();
            if (records == 0) {
                alias.add(t.getAlias());
            }
        }
        for (String name : alias) {
            this.removeTable(name);
        }
    }

    /**
     * Entrega un campo dado un ejemplo.
     * 
     * @param pTableFieldName tabla.campo a buscar
     * @return
     */
    public Field findFieldByExample(Field pField) {
        Field field = this.findFieldByName(pField.getName());
        if (field == null) {
            this.addField(pField);
            field = pField;
        }
        return field;
    }

    /**
     * Busca un campo de control por Nombre
     * 
     * @param pName Nombre del campo de Suelto
     * @return Field
     */
    public Field findFieldByName(String pName) {
        return this.fieldsByName.get(pName);
    }

    public Field findFieldByNameCreate(String pName) {
        Field f = this.fieldsByName.get(pName);
        if (f == null) {
            f = new Field(pName);
            this.addField(f);
        }
        return f;
    }

    /**
     * Busca una tabla dado el Alias
     * 
     * @param pAlias Alias de la tabla
     * @return Tabla
     */
    public Table findTableByAlias(String pAlias) {
        return this.tablesByAlias.get(pAlias);
    }

    /**
     * Busca una tabla dado el Alias o el Nombre. Si esta no existe sera creada.
     * 
     * @param pName Nombre de la tabla
     * @param pAlias Alias de la tabla
     * @return Tabla
     */
    public Table findTableByExample(Table tTable) {
        Table t = this.findTableByAlias(tTable.getAlias());
        if (t == null) {
            this.addTable(tTable);
            t = tTable;
        }
        return t;
    }

    /**
     * Busca una tabla dado el Nombre
     * 
     * @param pName Nombre de la tabla
     * @return Tabla
     */
    public Table findTableByName(String pName) {
        for (Table table : this.getTables()) {
            String name = table.getName();
            if ((name != null) && (name.compareTo(pName) == 0)) {
                return table;
            }
        }
        return null;
        // return this.tablesByName.get(pName);
    }

    private List<String> getAddress(String pKey) {
        List<String> data = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(pKey, ".");
        while (st.hasMoreElements()) {
            data.add((String) st.nextElement());
        }
        return data;
    }

    /**
     * Entrega el valor de fields.
     * 
     * @return fields.
     */
    public Iterable<Field> getFields() {
        return this.fieldsByName.values();
    }

    public Object getFieldValue(String pName, Object pDefault) {
        try {
            Object value = this.findFieldByName(pName).getValue();
            if ((value instanceof String) && (((String) value).compareTo("") == 0)) {
                value = null;
            }
            if (value == null) {
                return pDefault;
            }
            return value;
        } catch (Exception e) {
            return pDefault;
        }
    }

    /**
     * Entrega un noddo generar de Fitbank.
     * 
     * @return fitbanknode
     * @throws Exception
     */
    public Node getFitbankNode(boolean pError) throws Exception {
        Node fitbanknode = XMLHelper.createNode("FITBANK");
        try {
            XMLHelper.appendChild(fitbanknode, this.getHeaderNode());
        } catch (Exception e) {
            if (!pError) {
                throw e;
            }
        }
        try {
            XMLHelper.appendChild(fitbanknode, this.toNode());
        } catch (Exception e) {
            if (!pError) {
                throw e;
            }
        }
        return fitbanknode;
    }

    private Object getFormData(String sKey) {
        if (sKey.compareTo("_fields") == 0) {
            return this.getFields();
        }
        if (sKey.compareTo("_labeledFields") == 0) {
            return this.getLabeledFields();
        }
        if (sKey.compareTo("_structureTables") == 0) {
            return this.getStructureTables();
        }
        if (sKey.compareTo("_navigationOrder") == 0) {
            return this.getNavigationOrder();
        }
        return null;
    }

    public Iterable<Field> getLabeledFields() {
        List<Field> list = new ArrayList<Field>();
        Iterable<Field> f = this.getFields();
        Map<Integer, Field> m = new HashMap<Integer, Field>();
        for (Field field : f) {
            if ((field.getLabel() != null) && (field.getOrder() > -1)) {
                m.put(field.getOrder(), field);
            }
        }
        List<Integer> key = new ArrayList<Integer>();
        for (Integer k : m.keySet()) {
            key.add(k);
        }
        Collections.sort(key);
        for (Integer k : key) {
            list.add(m.get(k));
        }
        return list;
    }

    private Object getLast(List<String> pAddress) throws Exception {
        Object last = this;
        for (int i = 0; i < pAddress.size() - 1; i++) {
            try {
                last = this.getObject(last, pAddress.get(i));
            } catch (Exception e) {
                // System.out.println(pAddress + " " + pAddress.get(i) + " " +
                // last);
                throw e;
            }
        }
        return last;
    }

    public Iterable<Field> getNavigationOrder() {
        List<Field> list = new ArrayList<Field>();
        Iterable<Field> f = this.getLabeledFields();
        for (Field field : f) {
            if (field.isHidden() || field.isReadOnly()) {
                continue;
            }
            list.add(field);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private Object getObject(Object pParent, String pChild) throws Exception {
        Object value = null;
        boolean array = pChild.indexOf('[') > -1;
        int index = 0;
        if (array) {
            index = Integer.parseInt(pChild.substring(pChild.indexOf('[') + 1, pChild.indexOf(']') - 1));
            pChild = pChild.substring(0, pChild.indexOf('[') - 1);
        }
        if (pParent instanceof Map) {
            if (pParent instanceof Detail) {
                value = this.getValueFromDetail(pParent, pChild);
            } else if (pParent instanceof Table) {
                value = this.getValueFromTable(pParent, pChild);
            } else {
                if (pParent instanceof Record) {
                    Record rec = (Record) pParent;
                    value = rec.findFieldByNameCreate(pChild);
                } else {
                    Map m = (Map) pParent;
                    value = m.get(pChild);
                }
            }
        }
        if (array) {
            if (value instanceof List) {
                value = ((List) value).get(index);
            } else {
                throw new Exception("No es un arreglo: " + pChild);
            }
        }
        return value;
    }

    public List<Table> getStructureTables() {
        List<Table> tab = new ArrayList<Table>();
        for (Table table : this.tables) {
            if (table.getStructure() != null) {
                tab.add(table);
            }
        }
        return tab;
    }

    private Object getTableOrField(String sKey) {
        Object obj = null;
        char fType = sKey.toLowerCase().charAt(0);
        String name = sKey.substring(1);

        if (fType == 't') {
            obj = super.get('t' + name);
            if (obj == null) {
                obj = new Table(name, name);
                this.addTable((Table) obj);
            }
        }

        if (fType == 'f') {
            obj = this.findFieldByNameCreate(name);
        }
        return obj;
    }

    /**
     * Entrega el valor de tables.
     * 
     * @return tables.
     */
    public Iterable<Table> getTables() {
        return this.tables;
    }

    public int getTablesCount() {
        return this.tables.size();
    }


    private Object getValueFromDetail(Object pParent, String pChild) {
        Detail det = (Detail) pParent;
        Object value = det.get(pChild);
        if (value == null) {
            char car = pChild.charAt(0);
            pChild = pChild.substring(1);
            if (car == 'f') {
                det.findFieldByNameCreate(pChild);
            }
            value = det.get(pChild);
        }
        return value;
    }

    private Object getValueFromTable(Object pParent, String pChild) {
        Table table = (Table) pParent;
        Object value = table.get(pChild);
        if (value == null) {
            value = table.get(pChild.toLowerCase());
        }
        if ((value == null) && (pChild.toLowerCase().indexOf("_record") == 0)) {
            int rec = Integer.parseInt(pChild.toLowerCase().replaceAll("_record", "")) + 1;
            while (table.getRecordCount() < rec) {
                Record r0 = (Record) table.get("_record0");
                Record r = new Record();
                if (r0 != null) {
                    r = Clonador.clonar(r0);
                    List<Field> fs = r.getFields();
                    for (Field field : fs) {
                        field.setRealValue(null);
                    }
                }
                table.addRecord(r);
            }
            value = table.get(pChild);
        }
        return value;
    }

    private String getXMLData() {
        try {
            return this.toErrorXml();
        } catch (Exception e) {
            return "ERROR SERIALIZACION " + e.getMessage();
        }
    }

    public boolean hasFinancialRequest() {
        boolean has = false;
        for (Table table : this.getTables()) {
            has = table.isFinancial();
            if (has) {
                break;
            }

        }
        return has;
    }

    @Override
    public boolean isTransactionRequired() {
        return true;
    }

    public void removeField(String pName) {
        this.fieldsByName.remove(pName);
        this.remove("f" + pName);
    }

    public void removeFields() {
        List<String> names = new ArrayList<String>();
        Iterator<Field> fields = this.getFields().iterator();
        while (fields.hasNext()) {
            names.add(fields.next().getName());
        }
        for (String name : names) {
            this.removeField(name);
        }
    }

    public void removeTable(String alias) {
        this.tablesByName.remove(this.findTableByAlias(alias).getName());
        this.tablesByAlias.remove(alias);
        this.remove("t" + alias);
        this.tables.clear();
        for (Table t : this.tablesByAlias.values()) {
            this.tables.add(t);
        }
    }

    public void removeTables() {
        for (String key : this.tablesByAlias.keySet()) {
            this.remove("t" + key);
        }
        this.tablesByName.clear();
        this.tablesByAlias.clear();
        this.tables.clear();
    }

    public String toErrorXml() throws Exception {
        return XMLHelper.nodeToString(this.getFitbankNode(true));
    }

    /**
     * Transforma objetos tablas y campos a un nodo XML.
     */
    private Node toNode() throws Exception {
        Node node = XMLHelper.createNode("DET");
        for (Table obj : this.getTables()) {
            XMLHelper.appendChild(node, obj.toNode());
        }
        Node nodectl = XMLHelper.createNode("CTL");
        boolean hasFields = false;
        for (Field obj : this.getFields()) {
            hasFields = true;
            if ("_JSBehavior,_QueryAllowed,_SaveAllowed,".indexOf(obj.getName() + ",") < 0) {
                XMLHelper.appendChild(nodectl, obj.toNode());
            }
        }
        if (hasFields) {
            XMLHelper.appendChild(node, nodectl);
        }
        return node;
    }

    /**
     * Arma un xml de los objetos que conformar un detalle.
     * 
     * @return
     * @throws Exception
     */
    public String toXml() throws Exception {
        return XMLHelper.nodeToString(this.getFitbankNode(false));
    }

    public Map<String, Object> getTraceData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("H:user", this.user);
        map.put("H:password", this.password);
        map.put("H:newpassword", this.newpassword);
        map.put("H:language", this.language);
        map.put("H:terminal", this.terminal);
        map.put("H:processType", this.processType);
        map.put("H:type", this.type);
        map.put("H:sessionid", this.sessionid);
        map.put("H:role", this.role);
        map.put("H:securitylevel", this.securitylevel);
        map.put("H:ipaddress", this.ipaddress);
        map.put("H:macaddress", this.macaddress);
        map.put("H:subsystem", this.subsystem);
        map.put("H:transaction", this.transaction);
        map.put("H:version", this.version);
        map.put("H:company", this.company);
        map.put("H:originbranch", this.originBranch);
        map.put("H:originoffice", this.originOffice);
        map.put("H:messageid", this.messageId);
        map.put("H:messageidreverse", this.messageidreverse);
        map.put("H:reverse", this.reverse);
        map.put("H:channel", this.channel);
        map.put("H:accountingdate", this.accountingDate);
        map.put("H:realtransaction", this.realtransaction);
        map.put("H:area", this.area);
        for (Field f : this.getFields()) {
            map.put("F:" + f.getName(), f.getValue());
        }
        for (Table t : this.getTables()) {
            map.put("T:" + t.getName() + ":" + t.getAlias(), t.getRecordCount());
        }

        return map;
    }
}
