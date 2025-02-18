package com.example.cumparaturimodel_test;

import Domain.Produs;
import Repo.Repository;
import Repo.SQLRepositoryProdus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
        Repository<Produs> dbRepoProdus = new SQLRepositoryProdus();
        Service service = new Service(dbRepoProdus);

        // Creăm un TabPane pentru a gestiona ambele taburi
        TabPane tabPane = new TabPane();

        Tab produsTab = new Tab("Produse");
        produsTab.setClosable(false);
        VBox produsVBox = new VBox();
        produsVBox.setSpacing(10);

        // Creăm tabelul pentru Mașini
        TableView<Produs> ProdusTable = new TableView<>();
        TableColumn<Produs, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<Produs, String> marcaColumn = new TableColumn<>("Marca");
        TableColumn<Produs, String> numeColumn = new TableColumn<>("Nume");
        TableColumn<Produs, Integer> pretColumn = new TableColumn<>("Pret");
        TableColumn<Produs, String> cantitateColumn = new TableColumn<>("Cantitate");

        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        marcaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarca()));
        numeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNume()));
        pretColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPret()));
        cantitateColumn.setCellValueFactory(data -> {
            int cantitate = data.getValue().getCantitate();
            return new SimpleStringProperty(cantitate == 0 ? "n/a" : String.valueOf(cantitate));
        });

        ProdusTable.getColumns().addAll(idColumn, marcaColumn, numeColumn, pretColumn, cantitateColumn);
        ObservableList<Produs> produsList = FXCollections.observableArrayList(service.finAllProdus());
        ProdusTable.setItems(produsList);

        // Formular pentru adăugarea, ștergerea și actualizarea mașinilor
        GridPane produsForm = new GridPane();
        produsForm.setVgap(3.5);
        produsForm.setHgap(3.5);

        Label idLabel = new Label("ID");
        Label marcaLabel = new Label("Marca");
        Label numeLabel = new Label("Nume");
        Label pretLabel = new Label("Pret");
        Label cantitateLabel = new Label("Cantitate");
        TextField idTextField = new TextField();
        TextField marcaTextField = new TextField();
        TextField numeTextField = new TextField();
        TextField pretTextField = new TextField();
        TextField cantitateTextField = new TextField();

        produsForm.add(marcaLabel, 0, 1);
        produsForm.add(marcaTextField, 1, 1);
        produsForm.add(numeLabel, 0, 2);
        produsForm.add(numeTextField, 1, 2);
        produsForm.add(pretLabel, 0, 3);
        produsForm.add(pretTextField, 1, 3);
        produsForm.add(cantitateLabel, 0, 4);
        produsForm.add(cantitateTextField, 1, 4);

        // Butoane pentru mașini
        HBox produsButtons = new HBox(10);
        Button addProdusButton = new Button("Add");
        Button deleteProdusButton = new Button("Delete");
        Button filterProdusButton = new Button("Filter");

        TextField filterTextField = new TextField();
        filterTextField.setPromptText("Introduceți text pentru filtrare");

        produsButtons.getChildren().addAll(addProdusButton, deleteProdusButton, filterProdusButton, filterTextField);
        produsButtons.setAlignment(Pos.CENTER);

        produsVBox.getChildren().addAll(ProdusTable, produsForm, produsButtons);
        produsTab.setContent(produsVBox);

        ProdusTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Produs selectedprodus = ProdusTable.getSelectionModel().getSelectedItem();
                if (selectedprodus != null) {
                    marcaTextField.setText(selectedprodus.getMarca());
                    numeTextField.setText(selectedprodus.getNume());
                    pretTextField.setText(String.valueOf(selectedprodus.getPret()));
                    cantitateTextField.setText(String.valueOf(selectedprodus.getCantitate()));
                } else {
                    System.out.println("You're clicking on the list, but no product is selected.");
                }

            }
        });

        // Adăugăm funcționalitate butoanelor pentru Mașini
        addProdusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = service.findNextId();
                    String marca = marcaTextField.getText();
                    String nume =numeTextField.getText();
                    int pret = Integer.parseInt(pretTextField.getText());
                    int cantitate = Integer.parseInt(cantitateTextField.getText());

                    // Creăm obiectul masina cu java.sql.Date
                    Produs produs = new Produs(id,marca, nume, pret, cantitate);
                    service.addProdus(produs);
                    produsList.setAll(service.finAllProdus());
                    ProdusTable.refresh();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });

        deleteProdusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Produs selectedprodus = ProdusTable.getSelectionModel().getSelectedItem();

                    // Verifică dacă o mașină a fost selectată
                    if (selectedprodus != null) {
                        int idToDelete = selectedprodus.getId();

                        // Apelăm serviciul pentru a șterge mașina
                        service.deleteProdus(idToDelete);

                        // Actualizăm lista de mașini din tabel
                        produsList.setAll(service.finAllProdus());
                        ProdusTable.refresh();
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

        filterProdusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    String filterText = filterTextField.getText().toLowerCase(); // Convertim la litere mici pentru comparații case-insensitive
                    if (filterText.isEmpty()) {
                        // Dacă câmpul de filtrare este gol, afișăm toate produsele
                        produsList.setAll(service.finAllProdus());
                    } else {
                        // Filtrăm produsele care conțin textul în marcă sau nume
                        ObservableList<Produs> filteredList = FXCollections.observableArrayList(
                                service.finAllProdus().stream()
                                        .filter(produs -> produs.getMarca().toLowerCase().contains(filterText) ||
                                                produs.getNume().toLowerCase().contains(filterText))
                                        .toList()
                        );

                        if (filteredList.isEmpty()) {
                            // Dacă lista filtrată este goală, afișăm un mesaj de eroare
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Filtrare eșuată");
                            alert.setHeaderText("Niciun produs găsit");
                            alert.setContentText("Nu există produse care să corespundă textului introdus.");
                            alert.show();
                        }

                        // Actualizăm lista cu produsele filtrate (chiar dacă e goală, se actualizează)
                        produsList.setAll(filteredList);
                    }
                    ProdusTable.refresh();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A apărut o eroare: " + e.getMessage());
                    alert.show();
                }
            }
        });



        // Adăugăm tab-urile în TabPane
        tabPane.getTabs().addAll(produsTab);

        // Setăm scena principală
        Scene scene = new Scene(tabPane, 1000, 600);
        stage.setTitle("Gestionare Produse");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
