package ui;

import controller.AddAgendaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Entry;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddAgendaUI implements Runnable {
     private AddAgendaController controller;
    private Entry entry;
    private Date startingDate;

    @FXML
    private Button btnConfirm;

    @FXML
    private ChoiceBox<Entry> choiceEntry;

    @FXML
    private DatePicker dateDate;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEntry;
    public AddAgendaUI() {
        controller= new AddAgendaController();
    }

    public void run() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAgendaUI.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
//           e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "'authentication' UI not open");
            a.showAndWait();
        }

    }
    @FXML
    void initialize(){
        List<Entry> toDoList = controller.getToDoList();
        ObservableList<Entry> toDoList1 = FXCollections.observableArrayList();
        toDoList1.addAll(toDoList);
        this.choiceEntry.setItems(toDoList1);
    }

    @FXML
    void handleConfirm(ActionEvent event) {
        boolean flag=true;
        try {
            this.entry = choiceEntry.getSelectionModel().getSelectedItem();
        } catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Please select an entry");
            alert.showAndWait();
        }
        if (entry==null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Plese select an entry");
            a.showAndWait();
            flag=false;
        }
        if (this.dateDate.getValue()==null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a date");
            a.showAndWait();
            flag=false;
        }
        else{
            LocalDate ld = dateDate.getValue();
            Calendar c =  Calendar.getInstance();
            c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
            Date date = c.getTime();
            Date today=new Date();
            if (today.compareTo(date) > 0) {
                Alert a=new Alert(Alert.AlertType.ERROR, "Please select a future date");
                a.showAndWait();
                flag=false;
                this.handleShowEntriesprivate();
            }
            startingDate=date;
        }
        if (flag) {
            submitData();
            this.choiceEntry.getSelectionModel().clearSelection();
            ObservableList<Entry> toDoList1 = FXCollections.observableArrayList();
            this.choiceEntry.setItems(toDoList1);
            this.handleShowEntriesprivate();
        }

    }

    private void handleShowEntriesprivate() {
        List<Entry> toDoList = controller.getToDoList();
        ObservableList<Entry> toDoList1 = FXCollections.observableArrayList();
        toDoList1.addAll(toDoList);
        this.choiceEntry.setItems(toDoList1);
    }

    private void submitData() {
        try {
            boolean result = controller.addAgenda(entry,startingDate);
            if (result == true) {
                System.out.println("\nEntry successfully registered in to-do-list!");
                Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Entry succesfully registered in Agenda");
                a.showAndWait();
            } else {
                System.out.println("\nEntry registration failed!");
                Alert a=new Alert(Alert.AlertType.ERROR,"Entry registration failed");
                a.showAndWait();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }

    }
}
