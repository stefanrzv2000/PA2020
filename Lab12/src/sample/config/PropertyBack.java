package sample.config;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class PropertyBack {
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty value = new SimpleObjectProperty();
    private StringProperty valueS = new SimpleStringProperty();
    private Class clazz;

    public PropertyBack(String name, Object value) {
        this.name.setValue(name);
        this.value.setValue(value);
        valueS.setValue(value.toString());
        clazz = value.getClass();
    }

    public PropertyBack() {
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value.setValue(value);
        valueS.setValue(value.toString());
        clazz = value.getClass();
    }

    public StringProperty getValueS() {
        return valueS;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isEditable(){
        return Number.class.isAssignableFrom(clazz) || clazz.equals(String.class);
    }
}
