package sample.config;

import sample.Component;

import javafx.scene.Node;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NodeConfig {
    private Set<Property> properties = new HashSet<>();
    private Class clazz;

    public NodeConfig(){}

    public NodeConfig(Node node){
        Component component = new Component(node);
        clazz = component.getClazz();
        try {
            var bean = Introspector.getBeanInfo(clazz);
            for(var pd: bean.getPropertyDescriptors()){
                if(pd.getPropertyType()==null){continue;}
                if(String.class.isAssignableFrom(pd.getPropertyType()) ||
                        double.class.isAssignableFrom(pd.getPropertyType()) ||
                        int.class.isAssignableFrom(pd.getPropertyType())
                ){
                    Method method = pd.getReadMethod();
                    System.out.println(pd.getName());
                    System.out.flush();
                    Object result = method.invoke(component.getNode());
                    if(result!=null) {
                        properties.add(new Property(pd.getName(), result));
                    }
                }
            }

        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public NodeConfig(Component component){
        clazz = component.getClazz();
        try {
            var bean = Introspector.getBeanInfo(clazz);
            for(var pd: bean.getPropertyDescriptors()){
                if(String.class.isAssignableFrom(pd.getPropertyType()) || Number.class.isAssignableFrom(pd.getPropertyType())){
                    Method method = pd.getReadMethod();
                    System.err.println(pd.getName());
                    Object result = method.invoke(component.getNode());
                    properties.add(new Property(pd.getName(),result));
                }
            }

        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void addProperty(Property property){
        properties.add(property);
    }

    public Component create(){
        Component component = new Component(clazz);
        for(var property: properties){
            try{
                component.apply(property);
            }catch (Exception ignored){}
        }
        return component;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
