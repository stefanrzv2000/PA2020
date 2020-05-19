package sample.config;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class Property {
    private String name;
    private Object value;
    private String valueS;
    private Class clazz;

    public Property(String name, Object value) {
        this.name = (name);
        this.value = (value);
        valueS = (value.toString());
        clazz = value.getClass();
    }

    public Property() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = (value);
        valueS = (value.toString());
        clazz = value.getClass();
    }

    public String getValueS() {
        return valueS;
    }

    public void setValueS(String s){
        valueS = s;
        if(String.class.isAssignableFrom(clazz)){
            value = s;
        }
        if(Number.class.isAssignableFrom(clazz)){
            value = Double.parseDouble(s);
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isEditable(){
        return
                Number.class.isAssignableFrom(clazz) ||
                double.class.isAssignableFrom(clazz) ||
                int.class.isAssignableFrom(clazz) ||
                clazz.equals(String.class);
    }
}
