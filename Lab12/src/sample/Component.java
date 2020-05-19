package sample;

import javafx.scene.Node;
import sample.config.Property;
import sample.exceptions.InvalidClassException;
import sample.exceptions.InvalidMethodException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Component {
    private Node node;
    private Class clazz;

    public Component(Class clazz) {

        if(!Node.class.isAssignableFrom(clazz)){
            throw new InvalidClassException(clazz.getName());
        }

        this.clazz = clazz;

        try {
            node = (Node) this.clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public Component(String classname){
        try {
            clazz = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            System.out.println("error 1");
            throw new InvalidClassException(classname);
        }

        try {
            node = (Node) this.clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassCastException e) {
            System.out.println("error 2");
            throw new InvalidClassException(classname);
        }

    }

    public Component(Node node) {
        this.node = node;
        clazz = node.getClass();
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setX(double x){
        try {
            clazz.getMethod("setLayoutX",double.class).invoke(node,x);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("setX Invoke went wrong");
            e.printStackTrace();
        }
    }

    public void setY(double y){
        try {
            clazz.getMethod("setLayoutY",double.class).invoke(node,y);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("setY Invoke went wrong");
            e.printStackTrace();
        }
    }

    public void setText(String text){
        Method method;
        try {
             method = clazz.getMethod("setText", String.class);
        } catch (NoSuchMethodException e) {
            throw new InvalidMethodException("setText",clazz.getName());
        }

        try {
            method.invoke(node,text);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("setText Invoke went wrong");
            e.printStackTrace();
        }
    }

    public void show(){
        try {
            clazz.getMethod("setVisible", boolean.class).invoke(node,true);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("show invoke went wrong");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Component{" +
                "node=" + node +
                ", clazz=" + clazz +
                '}';
    }

    public Class getClazz() {
        return clazz;
    }

    public void apply(Property property){

        try {
            var clazz = this.getClazz();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            for(PropertyDescriptor pd: beanInfo.getPropertyDescriptors()) {
                if(pd.getName().equals(property.getName())){
                    Method method = pd.getWriteMethod();
                    if(method == null){
                        throw new InvalidMethodException(pd.getName() + " setter",clazz.getName());
                    }
                    method.invoke(this.getNode(),property.getValue());
                }
            }

            //Map<String,Object> properties = BeanUtils.describe(current.getNode());
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("dafuq");
            e.printStackTrace();
        }
    }
}
