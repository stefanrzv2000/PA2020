package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.config.DesignConfig;
import sample.config.Property;
import sample.exceptions.InvalidClassException;
import sample.exceptions.InvalidMethodException;

import java.beans.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.*;

public class Controller implements Initializable {

    private static Controller controller;

    @FXML
    ComboBox<String> elemBox;
    @FXML
    TextField nameText, elemText;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Button newButton, deleteButton, saveButton, loadButton;
    @FXML
    TableView<Property> table;
    @FXML
    TableColumn<Property, String> col1;
    @FXML
    TableColumn<Property, String> col2;
    @FXML
    Pane design, container;

    Component current;

    public static Controller getController() {
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elemBox.getItems().addAll("Button", "Label");
        controller = this;

        table.setEditable(true);

        //col1.setCellValueFactory(data -> data.getValue().getName());
        //col2.setCellValueFactory(data -> data.getValue().getValueS());

        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("valueS"));

        PseudoClass editableCssClass = PseudoClass.getPseudoClass("editable");

        Callback<TableColumn<Property, String>, TableCell<Property, String>> ddefaultTextFieldCellFactory
                = TextFieldTableCell.forTableColumn();


        col2.setCellFactory(col -> {
            TableCell<Property, String> cell = ddefaultTextFieldCellFactory.call(col);

            cell.itemProperty().addListener((observableValue, s, t1) -> {
                TableRow row = cell.getTableRow();
                if (row == null) {
                    cell.setEditable(false);
                } else {
                    Property item = cell.getTableRow().getItem();
                    //System.out.println(item);
                    if (item == null) {
                        cell.setEditable(false);
                    } else {
                        //System.out.println("MORTII MA-TI " + cell);
                        cell.setEditable(item.isEditable());
                    }
                }
                //System.out.println(cell.isEditable());
                //System.out.println(editableCssClass);
                cell.pseudoClassStateChanged(editableCssClass, cell.isEditable());
                //System.out.println(cell);
            });

            return cell;
        });

        col2.setOnEditCommit(event -> {
            Property property = event.getTableView().getItems().get(
                    event.getTablePosition().getRow()
            );
            property.setValueS(event.getNewValue());
            apply(property);
        });

        showTableDataTest();
    }

    public void onNewClicked(){
        String classname = "javafx.scene.control." + elemText.getText();

        try {
            current = new Component(classname);
        } catch (InvalidClassException e) {
            showAlert(ERROR,e.toString(),"Invalid Class");
            return;
        }

        if(!nameText.getText().isBlank()){
            try {
                current.setText(nameText.getText());
            }catch (InvalidMethodException e){
                showAlert(WARNING,e.toString() + "\nThe text you have entered will have no effect.","Warning");
            }
        }

        design.getChildren().add(current.getNode());
        current.getNode().setOnMouseClicked(onClickSelect);
        System.out.println("created: " + current.getNode());
        showTableData();
    }

    public void showAlert(Alert.AlertType type, String message, String title){
        //System.out.println("clicked");
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
        //System.out.println("passed");
    }

    public void onDesignClicked(MouseEvent event){
        if(current == null){return;}

        double x = event.getX();
        double y = event.getY();

        current.setX(x);
        current.setY(y);
        current.show();

    }

    public Component getCurrent() {
        return current;
    }

    public void setCurrent(Component current) {
        this.current = current;
    }

    public void onDeleteClicked(){
        if(current == null){
            showAlert(WARNING,"There is no Component selected.","Warning");
            return;
        }

        design.getChildren().remove(current.getNode());
    }

    public void onLoadClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./resources/designs"));

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("design files (.des)", "*.des");
        fileChooser.getExtensionFilters().add(extFilter);


        Stage primaryStage = (Stage) loadButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(primaryStage);

        if(file == null){
            showAlert(WARNING,"Nothing to load: No file selected!","Warning");
            return;
        }

        try(XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))){
            System.out.println("old design: " + design.getChildren());
            DesignConfig designConfig = (DesignConfig) decoder.readObject();
            design.getChildren().setAll(designConfig.create().getChildren());
            for(var child: design.getChildren()){
                child.setOnMouseClicked(onClickSelect);
            }
            System.out.println("new design: " + design.getChildren());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            showAlert(ERROR,"The design in the file could not be loaded!","Invalid format");
        }


    }

    public void onSaveClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./resources/designs"));

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("design files (.des)", "*.des");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage primaryStage = (Stage) saveButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(primaryStage);

        if(file == null){
            showAlert(WARNING,"No file selected, the design won't be saved.","Warning");
            return;
        }

        try(XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            encoder.writeObject(new DesignConfig(design));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showTableDataTest(){
        ObservableList<Property> data = FXCollections.observableArrayList(
            new Property("name","Tom"),
            new Property("age",12),
            new Property("color",Color.WHITE)
        );

        table.setItems(data);
    }

    public void showTableData(){
        ObservableList<Property> data = FXCollections.observableArrayList();

        try {
            var clazz = current.getClazz();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            for(PropertyDescriptor pd: beanInfo.getPropertyDescriptors()){
                System.out.println(pd.getName() + " " + pd.getPropertyType());
                System.out.flush();
                try {
                    Method read = pd.getReadMethod();
                    if(read == null){
                        System.err.println("\tThis property is null");
                        System.err.flush();
                        break;
                    }
                    Object result = read.invoke(current.getNode());
                    System.out.println("\t" + result);
                    System.out.flush();

                    if(result!=null){
                        if(Node.class.isAssignableFrom(result.getClass())){
                            data.add(new Property(pd.getName(),result.toString()));
                        }else {
                            data.add(new Property(pd.getName(), result));
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println("\tSomething went wrong");
                    System.err.flush();
                }

            }

            //Map<String,Object> properties = BeanUtils.describe(current.getNode());
        } catch (IntrospectionException e) {
            System.out.println("dafuq");
            e.printStackTrace();
        }

        table.setItems(data);
    }

    private void apply(Property property){
        if(current != null) {
            try{
            current.apply(property);
            }catch (InvalidMethodException e){
                showAlert(WARNING,e.toString(),"This field is not editable");
            }

        }
    }

    EventHandler<MouseEvent> onClickSelect = (event) -> {
        Controller.getController().setCurrent(new Component((Node) event.getSource()));
        System.out.println("current = " + current);
        showTableData();
    };

}
