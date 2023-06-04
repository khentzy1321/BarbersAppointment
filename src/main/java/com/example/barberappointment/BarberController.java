package com.example.barberappointment;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class BarberController {
    @FXML
    private TableView<Barber> tableView;
    @FXML
    private TableColumn<Barber, String> nameColumn;
    @FXML
    private TableColumn<Barber, String> addressColumn;
    @FXML
    private TableColumn<Barber, LocalTime> timeColumn;
    @FXML
    private TableColumn<Barber, LocalDate> dateColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<Integer> minuteComboBox;
    @FXML
    private ComboBox<String> ampmComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Barber> barbers;

    public BarberController() {
        barbers = FXCollections.observableArrayList();
    }

    @FXML
    protected void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());



        tableView.setItems(barbers);

        hourComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        minuteComboBox.getItems().addAll(0, 15, 30, 45);
        ampmComboBox.getItems().addAll("AM", "PM");

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Barber selectedBarber = tableView.getSelectionModel().getSelectedItem();
                if (selectedBarber != null) {
                    nameField.setText(selectedBarber.getName());
                    addressField.setText(selectedBarber.getAddress());

                    LocalTime time = selectedBarber.getTime();
                    hourComboBox.setValue(time.getHour());
                    minuteComboBox.setValue(time.getMinute());
                    ampmComboBox.setValue(time.getHour() >= 12 ? "PM" : "AM");

                    LocalDate date = selectedBarber.getDate();
                    datePicker.setValue(date);

                }
            }
        });
    }

    @FXML
    protected void onAddButtonClick() {

        if (fieldsAreEmpty()) {
            showAlert("Error", "Empty Fields", "Please fill in all the fields.");
            return;
        }

        String name = nameField.getText();
        String address = addressField.getText();
        int hour = hourComboBox.getValue();
        int minute = minuteComboBox.getValue();
        String ampm = ampmComboBox.getValue();
        LocalTime time = LocalTime.of(hour, minute);

        if (ampm.equals("PM") && hour != 12) {
            time = time.plusHours(12);
        }
        LocalDate date = datePicker.getValue();

        Barber barber = new Barber(name, address, time, date);
        barbers.add(barber);
        clearFields();
    }

    @FXML
    protected void onUpdateButtonClick() {

        if (fieldsAreEmpty()) {
            showAlert("Error", "Select Fields", "Select an item to update");
            return;
        }

        Barber selectedBarber = tableView.getSelectionModel().getSelectedItem();
        if (selectedBarber != null) {
            selectedBarber.setName(nameField.getText());
            selectedBarber.setAddress(addressField.getText());
            int hour = hourComboBox.getValue();
            int minute = minuteComboBox.getValue();
            String ampm = ampmComboBox.getValue();
            LocalTime time = LocalTime.of(hour, minute);
            if (ampm.equals("PM") && hour != 12) {
                time = time.plusHours(12);
            }
            selectedBarber.setTime(time);
            selectedBarber.setDate(datePicker.getValue());
            tableView.refresh();
            clearFields();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        if (fieldsAreEmpty()) {
            showAlert("Error", "Select Fields", "Select an item to delete.");
            return;
        }

        Barber selectedBarber = tableView.getSelectionModel().getSelectedItem();
        if (selectedBarber != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete the selected item?");

            confirmationAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    barbers.remove(selectedBarber);
                    clearFields();
                }
            });
        } else {
            showAlert("Error", "No Item Selected", "No item selected to delete.");
        }
    }
    private void clearFields() {
        nameField.clear();
        addressField.clear();
        hourComboBox.setValue(null);
        minuteComboBox.setValue(null);
        ampmComboBox.setValue(null);
        datePicker.setValue(null);
    }
    private boolean fieldsAreEmpty() {
        return nameField.getText().isEmpty()
                || addressField.getText().isEmpty()
                || hourComboBox.getValue() == null
                || minuteComboBox.getValue() == null
                || ampmComboBox.getValue() == null
                || datePicker.getValue() == null;
    }


    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}