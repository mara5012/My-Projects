package com.example.laborator4;

import Domain.Masina;
import Domain.Inchiriere;
import Repo.Repository;
import Repo.SQLRepositoryInchiriere;
import Repo.SQLRepositoryMasina;
import Service.Service;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Repository<Masina> dbRepoMasina = new SQLRepositoryMasina();
        Repository<Inchiriere> dbRepoInchiriere = new SQLRepositoryInchiriere();
        Service service = new Service(dbRepoMasina, dbRepoInchiriere);

        // Creăm un TabPane pentru a gestiona ambele taburi
        TabPane tabPane = new TabPane();

        // Tab pentru mașini
        Tab masinaTab = new Tab("Masini");
        masinaTab.setClosable(false);
        VBox masinaVBox = new VBox();
        masinaVBox.setSpacing(10);

        // Creăm tabelul pentru Mașini
        TableView<Masina> masinaTable = new TableView<>();
        TableColumn<Masina, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<Masina, String> marcaColumn = new TableColumn<>("Marca");
        TableColumn<Masina, String> modelColumn = new TableColumn<>("Model");

        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        marcaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarca()));
        modelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel()));

        masinaTable.getColumns().addAll(idColumn, marcaColumn, modelColumn);
        ObservableList<Masina> masinaList = FXCollections.observableArrayList(service.findAllMasina());
        masinaTable.setItems(masinaList);

        // Formular pentru adăugarea, ștergerea și actualizarea mașinilor
        GridPane masinaForm = new GridPane();
        masinaForm.setVgap(3.5);
        masinaForm.setHgap(3.5);

        Label idLabel = new Label("ID");
        Label marcaLabel = new Label("Marca");
        Label modelLabel = new Label("Model");
        TextField idTextField = new TextField();
        TextField marcaTextField = new TextField();
        TextField modelTextField = new TextField();

        masinaForm.add(idLabel, 0, 0);
        masinaForm.add(idTextField, 1, 0);
        masinaForm.add(marcaLabel, 0, 1);
        masinaForm.add(marcaTextField, 1, 1);
        masinaForm.add(modelLabel, 0, 2);
        masinaForm.add(modelTextField, 1, 2);

        // Butoane pentru mașini
        HBox masinaButtons = new HBox(10);
        Button addMasinaButton = new Button("Add");
        Button deleteMasinaButton = new Button("Delete");
        Button updateMasinaButton = new Button("Update");
        Button stream1Button = new Button("Stream1");
        Button stream2Button = new Button("Stream2");
        Button stream3Button = new Button("Stream3");

        masinaButtons.getChildren().addAll(addMasinaButton, deleteMasinaButton, updateMasinaButton,stream1Button,stream2Button,stream3Button);
        masinaButtons.setAlignment(Pos.CENTER);

        masinaVBox.getChildren().addAll(masinaTable, masinaForm, masinaButtons);
        masinaTab.setContent(masinaVBox);

        // Tab pentru Închirieri
        Tab inchiriereTab = new Tab("Inchirieri");
        inchiriereTab.setClosable(false);
        VBox inchiriereVBox = new VBox();
        inchiriereVBox.setSpacing(10);

        // Creăm tabelul pentru Închirieri
        TableView<Inchiriere> inchiriereTable = new TableView<>();
        TableColumn<Inchiriere, Integer> idInchiriereColumn = new TableColumn<>("ID");
        TableColumn<Inchiriere, String> masinaInchiriataColumn = new TableColumn<>("Masina");
        TableColumn<Inchiriere, Date> data_inceputColumn = new TableColumn<>("Data_inceput");
        TableColumn<Inchiriere, Date> data_sfarsitColumn = new TableColumn<>("Data_sfarsit");

        idInchiriereColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        masinaInchiriataColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMasina().getId() + " " + data.getValue().getMasina().getMarca() + " " + data.getValue().getMasina().getModel()));
        data_inceputColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getData_inceput()));
        data_sfarsitColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getData_sfarsit()));

        inchiriereTable.getColumns().addAll(idInchiriereColumn, masinaInchiriataColumn, data_inceputColumn, data_sfarsitColumn);
        ObservableList<Inchiriere> inchiriereList = FXCollections.observableArrayList(service.findAllInchiriere());
        inchiriereTable.setItems(inchiriereList);

        // Formular pentru adăugarea, ștergerea și actualizarea închirierilor
        GridPane inchiriereForm = new GridPane();
        inchiriereForm.setVgap(3.5);
        inchiriereForm.setHgap(3.5);

        Label inchiriereIdLabel = new Label("ID");
        Label masinaInchiriataLabel = new Label("Masina");
        Label data_inceputInchiriereLabel = new Label("Data inceput");
        Label data_sfarsitInchiriereLabel = new Label("Data sfarsit");
        TextField inchiriereIdTextField = new TextField();
        ComboBox<Masina> masinaInchiriataComboBox = new ComboBox<>();  // Folosim un ComboBox pentru selecția mașinii
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TextField data_inceputInchiriereTextField = new TextField();
        TextField data_sfarsitInchiriereTextField = new TextField();

        masinaInchiriataComboBox.setItems(FXCollections.observableArrayList(service.findAllMasina()));

        inchiriereForm.add(inchiriereIdLabel, 0, 0);
        inchiriereForm.add(inchiriereIdTextField, 1, 0);
        inchiriereForm.add(masinaInchiriataLabel, 0, 1);
        inchiriereForm.add(masinaInchiriataComboBox, 1, 1);
        inchiriereForm.add(data_inceputInchiriereLabel, 0, 2);
        inchiriereForm.add(data_inceputInchiriereTextField, 1, 2);
        inchiriereForm.add(data_sfarsitInchiriereLabel, 0, 3);
        inchiriereForm.add(data_sfarsitInchiriereTextField, 1, 3);

        // Butoane pentru Închirieri
        HBox inchiriereButtons = new HBox(10);
        Button addInchiriereButton = new Button("Add");
        Button deleteInchiriereButton = new Button("Delete");
        Button updateInchiriereButton = new Button("Update");

        inchiriereButtons.getChildren().addAll(addInchiriereButton, deleteInchiriereButton, updateInchiriereButton);
        inchiriereButtons.setAlignment(Pos.CENTER);

        inchiriereVBox.getChildren().addAll(inchiriereTable, inchiriereForm, inchiriereButtons);
        inchiriereTab.setContent(inchiriereVBox);

        masinaTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Masina selectedmasina = masinaTable.getSelectionModel().getSelectedItem();
                if (selectedmasina != null) {
                    idTextField.setText(String.valueOf(selectedmasina.getId()));
                    marcaTextField.setText(selectedmasina.getMarca());
                    modelTextField.setText(selectedmasina.getModel());
                } else {
                    System.out.println("You're clicking on the list, but no car is selected.");
                }

            }
        });

        inchiriereTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Inchiriere selectedinchiriere = inchiriereTable.getSelectionModel().getSelectedItem();
                if (selectedinchiriere != null) {
                    inchiriereIdTextField.setText(String.valueOf(selectedinchiriere.getId()));
                    masinaInchiriataComboBox.getSelectionModel().select(selectedinchiriere.getMasina());
                    data_inceputInchiriereTextField.setText(sdf.format(selectedinchiriere.getData_inceput()));
                    data_sfarsitInchiriereTextField.setText(sdf.format(selectedinchiriere.getData_sfarsit()));
                } else {
                    System.out.println("You're clicking on the list, but no rental is selected.");
                }

            }
        });

        stream1Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Map<String, Integer> nrInchirieriMasini = service.nrInchirieriMasini();
                for (Map.Entry<String, Integer> entry : nrInchirieriMasini.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        });

        stream2Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Map<String, Integer> nrInchirieriLuna = service.nrInchirieriLuna();
                for (Map.Entry<String, Integer> entry : nrInchirieriLuna.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        });

        stream3Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Map<String,Integer> nrZileInchiriere = service.nrZileInchiriate();
                for (Map.Entry<String, Integer> entry : nrZileInchiriere.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        });

        // Adăugăm funcționalitate butoanelor pentru Mașini
        addMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                    int id = Integer.parseInt(idTextField.getText());
                    String marca = marcaTextField.getText();
                    String model = modelTextField.getText();

                    // Creăm obiectul masina cu java.sql.Date
                    Masina masina = new Masina(id, marca, model);
                    service.addMasina(masina);
                    masinaList.setAll(service.findAllMasina());
                    masinaTable.refresh();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });

        deleteMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Masina selectedmasina = masinaTable.getSelectionModel().getSelectedItem();

                    // Verifică dacă o mașină a fost selectată
                    if (selectedmasina != null) {
                        int idToDelete = selectedmasina.getId();

                        // Apelăm serviciul pentru a șterge mașina
                        service.deleteMasina(idToDelete);

                        // Actualizăm lista de mașini din tabel
                        masinaList.setAll(service.findAllMasina());
                        masinaTable.refresh();
                    } else {
                        // Afișează un mesaj de avertizare dacă nu este selectată nicio mașină
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Atenție");
                        alert.setContentText("Vă rugăm să selectați o mașină pentru a o șterge.");
                        alert.show();
                    }
                } catch (Exception e) {
                    // Dacă apare o eroare, o afișăm într-un mesaj de alertă
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A apărut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });



        updateMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Masina selectedMasina = masinaTable.getSelectionModel().getSelectedItem();

                    // Verifică dacă o mașină este selectată
                    if (selectedMasina != null) {
                        int id = Integer.parseInt(idTextField.getText());
                        String marca = marcaTextField.getText();
                        String model = modelTextField.getText();

                        // Actualizează mașina cu valorile noi
                        Masina updatedMasina = new Masina(id, marca, model);
                        service.updateMasina(updatedMasina);

                        // Actualizează lista de mașini din tabel
                        masinaList.setAll(service.findAllMasina());
                        masinaTable.refresh();
                    } else {
                        // Dacă nu este selectată nicio mașină, afișează un mesaj de avertizare
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Atenție");
                        alert.setContentText("Vă rugăm să selectați o mașină pentru a o actualiza.");
                        alert.show();
                    }
                } catch (Exception e) {
                    // Dacă apare o eroare, o afișăm într-un mesaj de alertă
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A apărut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });

        // Funcționalitate pentru Închirieri
        addInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                    int id = Integer.parseInt(inchiriereIdTextField.getText());
                    Masina masina = masinaInchiriataComboBox.getSelectionModel().getSelectedItem();  // Selectăm mașina din ComboBox
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataInceput = sdf.parse(data_inceputInchiriereTextField.getText());
                    Date dataSfarsit = sdf.parse(data_sfarsitInchiriereTextField.getText());

                    // Convertim java.util.Date în java.sql.Date
                    java.sql.Date sqlDataInceput = new java.sql.Date(dataInceput.getTime());
                    java.sql.Date sqlDataSfarsit = new java.sql.Date(dataSfarsit.getTime());

                    // Creăm obiectul Inchiriere cu java.sql.Date
                    Inchiriere inchiriere = new Inchiriere(id, masina, sqlDataInceput, sqlDataSfarsit);
                    service.addInchiriere(inchiriere);
                    inchiriereList.setAll(service.findAllInchiriere());
                    inchiriereTable.refresh();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });

        deleteInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Inchiriere selectedInchiriere = inchiriereTable.getSelectionModel().getSelectedItem();
                    int idToDelete = selectedInchiriere.getId();
                    service.deleteInchiriere(idToDelete);
                    inchiriereList.setAll(service.findAllInchiriere());
                    inchiriereTable.refresh();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });

        updateInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Inchiriere selectedInchiriere = inchiriereTable.getSelectionModel().getSelectedItem();
                    if (selectedInchiriere != null) {
                        int id = Integer.parseInt(inchiriereIdTextField.getText());
                        Masina masina = masinaInchiriataComboBox.getSelectionModel().getSelectedItem();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dataInceput = sdf.parse(data_inceputInchiriereTextField.getText());
                        Date dataSfarsit = sdf.parse(data_sfarsitInchiriereTextField.getText());

                        // Convertim java.util.Date în java.sql.Date
                        java.sql.Date sqlDataInceput = new java.sql.Date(dataInceput.getTime());
                        java.sql.Date sqlDataSfarsit = new java.sql.Date(dataSfarsit.getTime());

                        // Creăm obiectul Inchiriere cu java.sql.Date
                        Inchiriere updatedInchiriere = new Inchiriere(id, masina, sqlDataInceput, sqlDataSfarsit);
                        service.updateInchiriere(updatedInchiriere);
                        inchiriereList.setAll(service.findAllInchiriere());
                        inchiriereTable.refresh();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });

        // Adăugăm tab-urile în TabPane
        tabPane.getTabs().addAll(masinaTab, inchiriereTab);

        // Setăm scena principală
        Scene scene = new Scene(tabPane, 1000, 600);
        stage.setTitle("Gestionare Masini si Inchirieri");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
