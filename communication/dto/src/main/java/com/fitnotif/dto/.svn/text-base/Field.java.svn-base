package com.fitnotif.dto;

import com.fitnotif.parser.XMLHelper;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase que contiene datos de un campo sea de base de datos o de control.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class Field implements DetailField, Serializable, Cloneable {

    private static final long serialVersionUID = 2L;

    private Object parent;

    /**
     * Alias del campo.
     */
    private String alias;

    /**
     * Nombre del campo.
     */
    private String name;

    /**
     * Tipo de dato del campo.
     */
    private String datatype = "java.lang.String";

    /**
     * Formato de presentacion.
     */
    private String format;

    /**
     * Valor de un campo.
     */
    private Object value;

    /**
     * Anterior valor del campo
     */
    private Object oldValue;

    /**
     * Tipo de campo
     */
    private String type = "NORMAL";

    /**
     * Indica si el campo es un primary key de una tabla.
     */
    private String pk;

    private String hint;

    private String label;

    private int length = 0;

    private int size = 0;

    private int x = 0;

    private int y = 0;

    private String lov = null;

    private String aditional;

    private String change;

    private boolean required = false;

    private boolean hidden = false;

    private boolean showLabel = false;

    private boolean absolute = false;

    private boolean readOnly = false;

    private String initValue;

    private String bind;

    private String include;

    private boolean combo;

    private String comboData;

    private String comboId;

    private String comboLabel;

    private int order = -1;

    private String language = "es";

    private String functionName;

    /**
     * Crea una nueva instancia de Field dado un nodo de un Documento XML.
     * 
     * @param pNode
     *            Nodo de referencia.
     */
    @SuppressWarnings("unchecked")
    public Field(Node pNode) {
        this.alias = XMLHelper.getStringValueByAttribute(pNode, "alias");
        this.name = XMLHelper.getStringValueByAttribute(pNode, "name");
        this.datatype = XMLHelper.getStringValueByAttribute(pNode, "tda");
        this.format = XMLHelper.getStringValueByAttribute(pNode, "fmt");
        if (this.datatype.compareTo("") == 0) {
            this.datatype = "java.lang.String";
        }
        Class c = null;
        try {
            c = Class.forName(this.datatype);
        } catch (ClassNotFoundException e) {
            c = String.class;
        }
        Class[] cl = new Class[] { String.class };
        Constructor cons = null;
        try {
            cons = c.getConstructor(cl);
        } catch (Exception e) {
            cons = null;
        }
        if ((cons != null) && (c.getName().compareTo("java.lang.String") != 0)) {
            Object val = XMLHelper.getStringValueByTag(pNode, "VAL");
            this.value = null;
            this.oldValue = null;
            if (val != null) {
                Object obj[] = new Object[] { val };
                try {
                    this.value = cons.newInstance(obj);
                } catch (Exception ex) {
                    throw new Error(ex);
                }
            }
            Object oldval = XMLHelper.getStringValueByTag(pNode, "OLDVAL");
            if (val != null) {
                Object obj[] = new Object[] { oldval };
                try {
                    this.oldValue = cons.newInstance(obj);
                } catch (Exception ex) {
                    throw new Error(ex);
                }
            }
        } else {
            if (c.getName().compareTo("java.lang.String") == 0) {
                this.value = XMLHelper.getStringValueByTag(pNode, "VAL");
            }
            if (c.getName().compareTo("java.sql.Date") == 0) {
                this.value = XMLHelper.getDateValueByTag(pNode, "VAL");
            }
            if (c.getName().compareTo("java.sql.Timestamp") == 0) {
                this.value = XMLHelper.getTimestampValueByTag(pNode, "VAL");
            }
            if (c.getName().compareTo("java.lang.String") == 0) {
                this.oldValue = XMLHelper.getStringValueByTag(pNode, "OLDVAL");
            }
            if (c.getName().compareTo("java.sql.Date") == 0) {
                this.oldValue = XMLHelper.getDateValueByTag(pNode, "OLDVAL");
            }
            if (c.getName().compareTo("java.sql.Timestamp") == 0) {
                this.oldValue = XMLHelper.getTimestampValueByTag(pNode, "OLDVAL");
            }
        }
        this.pk = XMLHelper.getStringValueByAttribute(pNode, "pk");
        if (this.pk == null) {
            this.pk = "0";
        }
        this.hint = XMLHelper.getStringValueByAttribute(pNode, "hint");
        this.type = XMLHelper.getStringValueByAttribute(pNode, "tipo");
        if (this.type == null) {
            this.type = "NORMAL";
        }
        this.functionName = XMLHelper.getStringValueByAttribute(pNode, "functionName");
    }

    /**
     * Crea una nueva instancia de Field.
     * 
     * @param pName
     */
    public Field(String pName) {
        this.name = pName;
    }

    public Field(String name, Object value) {
        this(name);
        this.setValue(value);
    }

    public Field(String alias, String pName, Object value) {
        this.alias = alias;
        this.name = pName;
        this.setValue(value);
    }

    /**
     * Clona una campo.
     */
    public Field cloneMe() throws CloneNotSupportedException {
        Field field = (Field) super.clone();
        return field;
    }

    @SuppressWarnings("unchecked")
    private boolean compare() {
        try {
            Object value1 = this.oldValue;
            if ((this.value == null) && (value1 == null)) {
                return true;
            }
            if (this.value.getClass().getName().compareTo(value1.getClass().getName()) != 0) {
                String currentValue = (String) this.value;
                String oldValue = (String) value1;
                if (currentValue.compareTo(oldValue) == 0) {
                    return true;
                } else {
                    return false;
                }
            }
            if ((this.value != null) && !(this.value instanceof Comparable)) {
                return false;
            }
            if ((value1 != null) && !(value1 instanceof Comparable)) {
                return false;
            }
            if ((this.value == null) && (value1 != null)) {
                return false;
            }
            if ((value1 == null) && (this.value != null)) {
                return false;
            }
            if (((Comparable) this.value).compareTo(value1) != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private List<String> divideString(String pVal, String pToken) {
        StringTokenizer st = new StringTokenizer(pVal, pToken);
        List<String> data = new ArrayList<String>();
        while (st.hasMoreElements()) {
            data.add((String) st.nextElement());
        }
        return data;
    }

    public String getAditional() {
        return this.aditional;
    }

    public String getAmountValue() throws Exception {
        try {
            if (this.value == null) {
                return null;
            }
            if (this.value.toString().trim().compareTo("") == 0) {
                return "";
            }

            DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("en"));// this.getLanguage()));
            DecimalFormat df1 = new DecimalFormat("###,###,###,###,##0.00", dfs);
            return df1.format(this.getDoubleValue());
        } catch (Exception e) {
            e.printStackTrace();
            return "" + this.value;
        }
    }

    public BigDecimal getBigDecimalValue() throws Exception {
        return (BigDecimal) this.getValue();
    }

    public String getBind() {
        return this.bind;
    }

    public boolean getBooleanValue() {
        String val = this.getStringValue();
        if (val == null) {
            return false;
        }
        return val.compareTo("1") == 0;
    }

    public String getChange() {
        return this.change;
    }

    public List<String> getComboCodes() {
        return this.divideString(this.comboId, ",");
    }

    public String getComboData() {
        if (this.comboData.compareToIgnoreCase("CTL") == 0) {
            // System.out.println(">>>>>>>>>>>>"+this.parent+" "+this.name);
            if (this.getParent() instanceof Record) {
                Record rec = (Record) this.getParent();
                Table t = (Table) rec.getParent();
                return "CTL_" + t.getAlias() + "|" + this.name;
            } else {
                return "CTL_" + this.name;
            }
        }
        return this.comboData;
    }

    public String getComboId() {
        if (this.comboData.compareToIgnoreCase("CTL") == 0) {
            return "ID";
        }
        return this.comboId;
    }

    public String getComboLabel() {
        if (this.comboData.compareToIgnoreCase("CTL") == 0) {
            return "LABEL";
        }
        return this.comboLabel;
    }

    public List<String> getComboLabels() {
        return this.divideString(this.comboLabel, ",");
    }

    /**
     * Entrega el valor de datatype.
     * 
     * @return datatype.
     */
    public String getDatatype() {
        return this.datatype;
    }

    public String getDateValue(){
        if (this.value == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format((Timestamp) this.value);
        } catch (Exception e) {
            return null;
        }
    }

    public double getDoubleValue() {
        if (this.value instanceof BigDecimal) {
            return ((BigDecimal) this.value).doubleValue();
        }
        return (Double) this.value;
    }

    /**
     * Entrega el valor de format.
     * 
     * @return format.
     */
    public String getFormat() {
        return this.format;
    }

    public String getHint() {
        return this.hint;
    }

    public String getInclude() {
        if (this.parent instanceof Record) {
            return this.include;
        }
        if ((this.include != null) && this.hidden) {
            return this.include + ((this.include.indexOf('?') > 0) ? "&" : "?") + "hidden=1";
        }
        if (this.include != null) {
            return this.include + ((this.include.indexOf('?') > 0) ? "&" : "?") + "f_name=f" + this.name;
        }

        return this.include;
    }

    public String getInitValue() {
        if (this.datatype == null) {
            return this.initValue;
        }
        try {
            if ((this.datatype.compareTo("dijit.form.NumberTextBox") == 0)
                    || (this.datatype.compareTo("dijit.form.CurrencyTextBox") == 0)
                    || (this.datatype.compareTo("fit.widget.NumberTextBox") == 0)) {
                DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("en"));// this.getLanguage()));
                DecimalFormat df1 = new DecimalFormat("###,###,###,###,##0.00", dfs);
                return df1.format(new BigDecimal(this.initValue));
            }
            if (this.datatype.compareTo("dijit.form.DateTextBox") == 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                return sdf.format(this.initValue);
            }
            if (this.datatype.compareTo("dijit.form.DateTimeTextBox") == 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                return sdf.format(this.initValue);
            }
        } catch (Exception e) {
        }
        return this.initValue;
    }

    public Integer getIntegerValue() {
        return (Integer) this.getValue();
    }

    public Date getRealDateValue() throws Exception {
        return (Date) this.getValue();
    }

    public Timestamp getRealTimestampValue() throws Exception {
        return (Timestamp) this.getValue();
    }

    public String getLabel() {
        return this.label;
    }

    public String getLanguage() {
        return (this.language == null) ? this.language : "es";
    }

    public int getLength() {
        return this.length;
    }

    public Long getLongValue() {
        return (Long) this.getValue();
    }

    public String getLov() {
        return this.lov;
    }

    public String getAlias() {
        return this.alias;
    }

    /**
     * Entrega el valor de name.
     * 
     * @return name.
     */
    public String getName() {
        return this.name;
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public String getType() {
        return this.type;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public int getOrder() {
        return this.order;
    }

    public Object getParent() {
        return this.parent;
    }

    /**
     * Entrega el valor de pk.
     * 
     * @return pk.
     */
    public String getPk() {
        return this.pk;
    }

    public String getRealName() {
        String aux = this.name;
        int index = aux.indexOf('+');
        if (index > 0) {
            aux = aux.substring(0, index) + "." + aux.substring(index + 1);
        }
        index = aux.indexOf(':');
        if (index > 0) {
            aux = aux.substring(0, index) + "." + aux.substring(index + 1);
        }
        return aux.toUpperCase();
    }

    public Object getRealOldValue() {
        if ((this.oldValue instanceof String) && (this.oldValue != null)) {
            if (((String) this.oldValue).trim().compareTo("") == 0) {
                return null;
            }
        }
        return this.oldValue;
    }

    public Object getRealValue() {
        return this.value;
    }

    public int getSize() {
        return this.size;
    }

    public String getStringValue() {
        return (String) this.getValue();
    }

    public int getTextAreaRows() {
        try {
            return (this.length * 7 / this.size) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public String getTimestampValue() {
        if (this.value == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return sdf.format(this.value);
        } catch (Exception e) {
            return null;
        }
    }

    public String getTimeValue() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(this.value);
    }

    /*
     * public void setAmountValue(String pValue) throws Exception{ } public void
     * setDateValue(String pValue) throws Exception{ }
     */
    /**
     * Entrega el valor de value.
     * 
     * @return value.
     */
    public Object getValue() {
        return this.getRealValue();
    }

    public Object getValueParsingHTMLCharacters() {
        if (this.value != null) {
            String svalue = (String) this.value;
            if ((svalue.indexOf('&') != -1) && (svalue.indexOf(';') != -1)) {
                svalue = svalue.replaceAll("&amp;#", "|");
                svalue = svalue.replaceAll("&#", "|");
                svalue = svalue.replaceAll(";", "|");
                StringTokenizer st = new StringTokenizer(svalue, "|");
                String var = "";
                String res = "";
                while (st.hasMoreElements()) {
                    var = st.nextToken();
                    if (var.length() == 3) {
                        try {
                            int i = Integer.valueOf(var).intValue();
                            char c = (char) i;
                            res += c;
                        } catch (Exception e) {
                            res += var;
                        }
                    } else {
                        res += var;
                    }
                }
                return res;
            } else {
                return this.value;
            }
        }
        return null;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean hasData() {
        return !((this.getRealValue() == null) && (this.getRealOldValue() == null));
    }

    public boolean isAbsolute() {
        return this.absolute;
    }

    public boolean isChanged() {
        return !this.compare();
    }

    public boolean isCombo() {
        return this.combo;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean isLovNedeed() {
        return (this.lov == null);
    }

    /**
     * Indica si el campo es de Clave primaria o no.
     * 
     * @return
     */
    public boolean isPrimaryKey() {
        return ((this.pk != null) && (this.pk.compareTo("1") == 0));
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isShowCriteria() {
        return ((this.include == null) || (this.include.compareTo("1") != 0));

    }

    public boolean isShowLabel() {
        return this.showLabel;
    }

    public boolean isTable() {
        try {
            String data = this.getInclude();
            if (data.indexOf("table.jsp") > -1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    public void setAditional(String aditional) {
        this.aditional = aditional;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public void setCombo(boolean combo) {
        this.combo = combo;
    }

    public void setComboData(String comboData) {
        this.comboData = comboData;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public void setComboLabel(String comboLabel) {
        this.comboLabel = comboLabel;
    }

    /**
     * Fija el valor de datatype.
     * 
     * @param datatype
     *            .
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public void setDoubleValue(double doubleValue) throws Exception {
        this.value = doubleValue;
    }

    /**
     * Fija el valor de format.
     * 
     * @param format
     *            .
     */
    public void setFormat(String format) {
        this.format = format;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLov(String lov) {
        this.lov = lov;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Fija el valor de name.
     * 
     * @param name
     *            .
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setOldValue(Object oldValue) {
        /*
         * if(oldValue instanceof String){
         * oldValue=((String)oldValue).toUpperCase(); }
         */
        this.oldValue = oldValue;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setParent(Object parent) {
        try {
            if (parent instanceof Detail) {
                this.language = ((Detail) parent).getLanguage().toLowerCase();
            }
            if (parent instanceof Record) {
                Table tab = (Table) ((Record) parent).getParent();
                Detail det = (Detail) tab.getParent();
                this.language = det.getLanguage().toLowerCase();
            }
        } catch (Exception e) {
            this.language = "es";
        }
        this.parent = parent;
    }

    /**
     * Fija el valor de pk.
     * 
     * @param pPk
     *            .
     */
    public void setPk(String pPk) {
        this.pk = pPk;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setRealValue(Object pValue) {
        this.setValue(pValue);
        this.setOldValue(pValue);
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Fija el valor de value.
     * 
     * @param value
     *            .
     */
    public void setValue(Object pvalue) {
        if (pvalue instanceof String) {
            String svalue = (String) pvalue;
            if (svalue.length() == 0) {
                this.value = null;
                return;
            }
            if ((this.datatype != null) && (this.datatype.equals("fit.widget.NumberTextBox"))) {
                String sValue = (String) pvalue;
                if (sValue.indexOf(',') > 0) {
                    sValue = sValue.replace(".", "");
                    sValue = sValue.replace(',', '.');
                }
                this.value = sValue;
                return;
            }
        }
        this.value = pvalue;

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Transforma un campo en un nodo XML.
     * 
     * @return
     * @throws Exception
     */
    public Node toNode() throws Exception {
        Node node = XMLHelper.createNode("CAM");
        if (this.alias != null) {
            XMLHelper.addAttribute(node, "alias", this.alias);
        }
        XMLHelper.addAttribute(node, "name", this.name);
        /*
         * if(this.datatype != null){
         * XMLHelper.addAttribute(node,"tda",this.datatype); } if(this.format !=
         * null){ XMLHelper.addAttribute(node,"fmt",this.format); }
         * if(this.label != null){
         * XMLHelper.addAttribute(node,"label",this.label); } if(this.hint !=
         * null){ XMLHelper.addAttribute(node,"hint",this.hint); }
         */
        if (this.pk != null) {
            XMLHelper.addAttribute(node, "pk", this.pk);
        } else {
            XMLHelper.addAttribute(node, "pk", "0");
        }
        if (StringUtils.isNotBlank(this.hint)) {
            XMLHelper.addAttribute(node, "hint", this.hint);
        }
        if ((this.type != null) && (this.type != "NORMAL")) {
            XMLHelper.addAttribute(node, "tipo", this.type);
        }
        if (StringUtils.isNotBlank(this.functionName)) {
            XMLHelper.addAttribute(node, "functionName", this.functionName);
        }
        if (this.value != null) {
            XMLHelper.appendChild(node, "VAL", this.value);
        }
        if (this.oldValue != null) {
            XMLHelper.appendChild(node, "OLDVAL", this.oldValue);
        }
        return node;
    }

    @Override
    public String toString() {
        if (this.value instanceof Field) {
            return "Field(" + this.getName() + "): FIELD";
        }
        return "Field(" + this.getName() + "):" + this.value;
    }

    public boolean isTransportField() {
        ResourceBundle ignoredFields = ResourceBundle.getBundle("ignoredfields");
        List<Object> keyList = EnumerationUtils.toList(ignoredFields.getKeys());

        return (this.getName().startsWith("_") || (keyList.contains(this.getName())
                && Boolean.valueOf(ignoredFields.getString(this.getName()))));
    }
}
