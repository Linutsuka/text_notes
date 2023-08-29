package com.example.noteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PrincipalController implements Initializable {

    //FILE SELECTED VARIABLE
     static String fileS="";
     protected boolean finish = true;

    @FXML
    public Button saveButton, selectButton,inputButton,deleteButton;
    boolean deleteActivated = false;
    public TextField inputText;
    public ComboBox selectP,selectG;
    /**/

    //TABLES AND DATA
    public TableView<Object> table;
    public TableColumn<Object, String> columnOne;
    public TableColumn<Object, String> columnTwo;
    public ArrayList<Object> objectsInput = new ArrayList<Object>();
    private ObservableList<Object> rows = FXCollections.observableArrayList();
    //SAVE INFORMATION FILES
    private static ArrayList<Object> fileInformation = new ArrayList<Object>();
    //COMBOBOX-MANIPULATION
    private ObservableList<String> comboNamesFiles = FXCollections.observableArrayList();
    //BOTONES

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectG.setValue("--select--");
        readMkdir();
    }
    public void onClickCreate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);//
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onClickDelete(){
        if(deleteActivated == false){
            deleteActivated = true;
            deleteButton.setText("ON");
        }
        else{
            deleteActivated = false;
            deleteButton.setText("OFF");

        }
    }
    public void onClickPut(){
        boolean correct = true;
        try{
            String description = inputText.getText().toString().trim();
            String percent = selectP.getValue().toString();
            fileInformation.add(new Object(description,percent));
            tableAdd(fileInformation);
        }
        catch(Exception e){
            alertError("ERROR","Maybe you put wrong value","ERROR GETTING VALUES");
        }
    }
    //LOS ARCHIVOS SE ENCUENTRAN EN ./src/main/files_text
    public void onClickSelect(){
        fileInformation.clear();
        finish = true;


        try{
            String comboSelect = selectG.getValue().toString();
            try{
                 fileS = comboSelect+".txt";
                //File file = new File("../"+textFile);
                File fileSelected = new File("./src/main/files_text"+fileS+".txt");
                fileSelected = new File("./src/main/files_text/"+fileS);
                String d = "done";

                if(fileSelected.exists()){
                  //TODO: MOSTRAR LOS ELEMENTOS EN LA TABLA, EXTRAER Y GUARDAR EN ARRAYLIST
                    try{
                        int n = 0;
                        Scanner readingFile = new Scanner(fileSelected);
                        while(finish){

                            String cadena = readingFile.nextLine();
                            if(!(cadena.equalsIgnoreCase("endFile"))){
                                String[] separador = cadena.split(";");
                                fileInformation.add(new Object(separador[0],separador[1]));
                                n++;

                            }
                            else{
                                finish = false;
                            }

                        }

                        readingFile.close();
                        tableAdd(fileInformation);

                    }
                    catch(Exception e){
                            alertError("ERROR","Error with showing information","INFORMATION ERROR");
                            alertError("error",e.toString(),e.toString());
                    }
                }
                else{
                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath().toString();
                    alertError("ERROR","Cannot search your file"+s ,"FILE NOT FOUND");
                }
            }
            catch (Exception e){

            }

        }
        catch (Exception e){
            //if combobox select is null
           alertError("ERROR","You can only select no-null files","Error Select");
        }


    }
    public void onClickSave() throws FileNotFoundException {
        PrintStream escriptor = new PrintStream("./src/main/files_text/"+fileS);
        for(int i=0; i < fileInformation.size() ;i++) {
            escriptor.println(fileInformation.get(i).getDescription()+";"+fileInformation.get(i).getPercent());

        }
        escriptor.println("endFile");
        escriptor.close();
    }
    //DELETE MODE
    public void deleteMode(MouseEvent event){

        Object selectedItem = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedItem);
        table.refresh();
        rows.remove(selectedItem);
        fileInformation.remove(selectedItem);
    }
    //ALERTA ERROR
    public void alertError(String title, String content, String header){
        Alert missatge = new Alert(Alert.AlertType.ERROR);
        missatge.setTitle(title);
        missatge.setContentText(content);
        missatge.setHeaderText(header);
        missatge.show();
    }
    //TABLE AÑADIR VALORES DESDE PUT
    public void tableAdd(ArrayList<Object> object){
        columnOne.setCellValueFactory(new PropertyValueFactory<Object,String>("description"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<Object,String>("percent"));
        rows.clear();
        rows.addAll(object);
        table.setItems(rows); table.getItems();  table.refresh();

    }
    /*COMBO BOX CONTAINER--------------------->*/
    //COGE LOS NOMBRES DE LOS ARCHIVOS CREADOS EN LA CARPETA Y LOS ENSEÑA EN EL COMBOBOX
    public void readMkdir(){
        File direcB = new File("./src/main/files_text");
        if(direcB.exists()) {
            File fichers []= direcB.listFiles();
            for(int i = 0; i < fichers.length; i++) {
                if(fichers[i].getName().endsWith(".txt")) { //SI LA SEVA TERMINACIO ES .TXT

                    comboNamesFiles.add(fichers[i].getName().substring(0,fichers[i].getName().length()-4));
                }
            }
        }
        selectG.setItems(comboNamesFiles);
    }
}
