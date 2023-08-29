package com.example.noteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class Settings implements Initializable {

    public Button createButtonC, deleteButtonC;
    public TextField input;
    public ComboBox combo;
    private ObservableList<String> comboNamesFiles = FXCollections.observableArrayList();
    PrincipalController principalController = new PrincipalController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readMkdir();
    }

    public void onClickCreate(){
        try{
            String newFile = input.getText().toString().trim();
            File fileToCreate = new File("./src/main/files_text/"+newFile+".txt");
            if(fileToCreate.exists()){
                principalController.alertError("ERROR","This file currently exist","ERROR CREATING  NEW FILE");
            }
            else{
                fileToCreate.createNewFile();
                comboNamesFiles.add(newFile);
                try{
                    PrintStream escriptor = new PrintStream("./src/main/files_text/"+newFile+".txt");
                    escriptor.println("endFile");
                    escriptor.close();
                }
                catch (Exception e){

                }
            }
        }
        catch(Exception e){

        }

    }
    public void onClickDelete(){

            try{
                String selectedItem = combo.getValue().toString();
                try{
                    File fileSelected = new File("./src/main/files_text/"+selectedItem+".txt");
                    fileSelected.delete();
                    comboNamesFiles.clear();
                    readMkdir();
                }
                catch (Exception e){
                    principalController.alertError("ERROR","Error choosing the files","ERROR GETTING VALUES");
                }
            }
            catch(Exception e){

                principalController.alertError("ERROR","Maybe you put wrong value","ERROR GETTING VALUES");
            }
    }
    public void onClickReturn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("principal.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);//
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
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
        combo.setItems(comboNamesFiles);
    }
}
