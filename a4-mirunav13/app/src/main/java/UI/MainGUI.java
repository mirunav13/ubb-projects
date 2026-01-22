package UI;

import Domain.Pacient;
import Domain.Programare;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Service.PacientService;
import Service.ProgramareService;
import Service.RapoarteService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainGUI {
    private PacientService pacientService;
    private ProgramareService programareService;
    private RapoarteService rapoarteService;

    private TableView<Pacient> pacientTable;
    private TableView<Programare> programareTable;
    private TextArea raportArea;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MainGUI() {}

    public void setServices(PacientService ps, ProgramareService prs, RapoarteService rs) {
        this.pacientService = ps;
        this.programareService = prs;
        this.rapoarteService = rs;
    }

    public void start(Stage primaryStage) { //Stage = fereastra principala
        primaryStage.setTitle("Cabinet Stomatologic - Sistem de Gestiune");

        TabPane tabPane = new TabPane();

        Tab tabPacienti = new Tab("Pacienti", createPacientiTab());
        tabPacienti.setClosable(false);

        Tab tabProgramari = new Tab("Programari", createProgramariTab());
        tabProgramari.setClosable(false);

        Tab tabRapoarte = new Tab("Rapoarte", createRapoarteTab());
        tabRapoarte.setClosable(false);

        tabPane.getTabs().addAll(tabPacienti, tabProgramari, tabRapoarte);

        Scene scene = new Scene(tabPane, 1000, 650); //Scene = continutul ferestrei
        primaryStage.setScene(scene);
        primaryStage.show();

        refreshPacienti();
        refreshProgramari();
    }

    private VBox createPacientiTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        pacientTable = new TableView<>();
        TableColumn<Pacient, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getID()).asObject());

        TableColumn<Pacient, String> numeCol = new TableColumn<>("Nume");
        numeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNume()));

        TableColumn<Pacient, String> prenumeCol = new TableColumn<>("Prenume");
        prenumeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenume()));

        TableColumn<Pacient, Integer> varstaCol = new TableColumn<>("Varsta");
        varstaCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getVarsta()).asObject());

        pacientTable.getColumns().addAll(idCol, numeCol, prenumeCol, varstaCol);

        HBox buttons = new HBox(10);
        Button btnAdd = new Button("Adauga");
        Button btnUpdate = new Button("Modifica");
        Button btnDelete = new Button("Sterge");
        Button btnRefresh = new Button("Reincarca");

        btnAdd.setOnAction(e -> addPacient());
        btnUpdate.setOnAction(e -> updatePacient());
        btnDelete.setOnAction(e -> deletePacient());
        btnRefresh.setOnAction(e -> refreshPacienti());

        buttons.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnRefresh);

        vbox.getChildren().addAll(pacientTable, buttons);
        VBox.setVgrow(pacientTable, Priority.ALWAYS);

        return vbox;
    }

    private void refreshPacienti() {
        List<Pacient> list = pacientService.all();
        pacientTable.setItems(FXCollections.observableArrayList(list));
    }

    private void addPacient() {
        Dialog<Pacient> dialog = new Dialog<>();
        dialog.setTitle("Adauga Pacient");

        ButtonType addButtonType = new ButtonType("Adauga", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField idField = new TextField();
        TextField numeField = new TextField();
        TextField prenumeField = new TextField();
        TextField varstaField = new TextField();

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Prenume:"), 0, 2);
        grid.add(prenumeField, 1, 2);
        grid.add(new Label("Varsta:"), 0, 3);
        grid.add(varstaField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String nume = numeField.getText();
                    String prenume = prenumeField.getText();
                    int varsta = Integer.parseInt(varstaField.getText());
                    return new Pacient(id, nume, prenume, varsta);
                } catch (Exception e) {
                    showError("Date invalide!");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(pacient -> {
            try {
                pacientService.add(pacient);
                refreshPacienti();
                showInfo("Pacient adaugat cu succes!");
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
    }

    private void updatePacient() {
        Pacient selected = pacientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza un pacient!");
            return;
        }

        Dialog<Pacient> dialog = new Dialog<>();
        dialog.setTitle("Modifica Pacient");

        ButtonType updateButtonType = new ButtonType("Modifica", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField numeField = new TextField(selected.getNume());
        TextField prenumeField = new TextField(selected.getPrenume());
        TextField varstaField = new TextField(String.valueOf(selected.getVarsta()));

        grid.add(new Label("ID: " + selected.getID()), 0, 0, 2, 1);
        grid.add(new Label("Nume:"), 0, 1);
        grid.add(numeField, 1, 1);
        grid.add(new Label("Prenume:"), 0, 2);
        grid.add(prenumeField, 1, 2);
        grid.add(new Label("Varsta:"), 0, 3);
        grid.add(varstaField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                try {
                    String nume = numeField.getText();
                    String prenume = prenumeField.getText();
                    int varsta = Integer.parseInt(varstaField.getText());
                    return new Pacient(selected.getID(), nume, prenume, varsta);
                } catch (Exception e) {
                    showError("Date invalide!");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(pacient -> {
            try {
                pacientService.update(pacient);
                refreshPacienti();
                showInfo("Pacient actualizat cu succes!");
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
    }

    private void deletePacient() {
        Pacient selected = pacientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza un pacient!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText("Stergi pacientul?");
        alert.setContentText(selected.toString());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    pacientService.delete(selected.getID());
                    refreshPacienti();
                    showInfo("Pacient sters cu succes!");
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }
        });
    }

    // ========== TAB PROGRAMARI ==========

    private VBox createProgramariTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        programareTable = new TableView<>();
        TableColumn<Programare, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getID()).asObject());

        TableColumn<Programare, Integer> pacientCol = new TableColumn<>("ID Pacient");
        pacientCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getPacientId()).asObject());

        TableColumn<Programare, String> dataCol = new TableColumn<>("Data");
        dataCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(sdf.format(data.getValue().getData())));

        TableColumn<Programare, String> scopCol = new TableColumn<>("Scop");
        scopCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getScop()));

        programareTable.getColumns().addAll(idCol, pacientCol, dataCol, scopCol);

        HBox buttons = new HBox(10);
        Button btnAdd = new Button("Adauga");
        Button btnUpdate = new Button("Modifica");
        Button btnDelete = new Button("Sterge");
        Button btnRefresh = new Button("Reincarca");

        btnAdd.setOnAction(e -> addProgramare());
        btnUpdate.setOnAction(e -> updateProgramare());
        btnDelete.setOnAction(e -> deleteProgramare());
        btnRefresh.setOnAction(e -> refreshProgramari());

        buttons.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnRefresh);

        vbox.getChildren().addAll(programareTable, buttons);
        VBox.setVgrow(programareTable, Priority.ALWAYS);

        return vbox;
    }

    private void refreshProgramari() {
        List<Programare> list = programareService.all();
        programareTable.setItems(FXCollections.observableArrayList(list));
    }

    private void addProgramare() {
        Dialog<Programare> dialog = new Dialog<>();
        dialog.setTitle("Adauga Programare");

        ButtonType addButtonType = new ButtonType("Adauga", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField idField = new TextField();
        TextField pacientIdField = new TextField();
        DatePicker datePicker = new DatePicker();
        ComboBox<String> oraCombo = new ComboBox<>();
        for (int h = 8; h < 18; h++) {
            oraCombo.getItems().add(String.format("%02d:00", h));
            oraCombo.getItems().add(String.format("%02d:30", h));
        }
        oraCombo.setValue("09:00");
        TextField scopField = new TextField();

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("ID Pacient:"), 0, 1);
        grid.add(pacientIdField, 1, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Ora:"), 0, 3);
        grid.add(oraCombo, 1, 3);
        grid.add(new Label("Scop:"), 0, 4);
        grid.add(scopField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int pacientId = Integer.parseInt(pacientIdField.getText());
                    String scop = scopField.getText();

                    LocalDate localDate = datePicker.getValue();
                    String[] timeParts = oraCombo.getValue().split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);

                    Date data = Date.from(localDate.atTime(hour, minute)
                            .atZone(ZoneId.systemDefault()).toInstant());

                    return new Programare(id, pacientId, data, scop);
                } catch (Exception e) {
                    showError("Date invalide!");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(programare -> {
            try {
                programareService.add(
                        programare.getID(),
                        programare.getPacientId(),
                        programare.getData(),
                        programare.getScop()
                );
                refreshProgramari();
                showInfo("Programare adaugata cu succes!");
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
    }

    private void updateProgramare() {
        Programare selected = programareTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza o programare!");
            return;
        }

        Dialog<Programare> dialog = new Dialog<>();
        dialog.setTitle("Modifica Programare");

        ButtonType updateButtonType = new ButtonType("Modifica", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField pacientIdField = new TextField(String.valueOf(selected.getPacientId()));
        DatePicker datePicker = new DatePicker();
        LocalDate localDate = selected.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePicker.setValue(localDate);

        ComboBox<String> oraCombo = new ComboBox<>();
        for (int h = 8; h < 18; h++) {
            oraCombo.getItems().add(String.format("%02d:00", h));
            oraCombo.getItems().add(String.format("%02d:30", h));
        }
        oraCombo.setValue(sdf.format(selected.getData()).split(" ")[1]);

        TextField scopField = new TextField(selected.getScop());

        grid.add(new Label("ID: " + selected.getID()), 0, 0, 2, 1);
        grid.add(new Label("ID Pacient:"), 0, 1);
        grid.add(pacientIdField, 1, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Ora:"), 0, 3);
        grid.add(oraCombo, 1, 3);
        grid.add(new Label("Scop:"), 0, 4);
        grid.add(scopField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                try {
                    int pacientId = Integer.parseInt(pacientIdField.getText());
                    String scop = scopField.getText();

                    LocalDate ld = datePicker.getValue();
                    String[] timeParts = oraCombo.getValue().split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);

                    Date data = Date.from(ld.atTime(hour, minute)
                            .atZone(ZoneId.systemDefault()).toInstant());

                    return new Programare(selected.getID(), pacientId, data, scop);
                } catch (Exception e) {
                    showError("Date invalide!");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(programare -> {
            try {
                programareService.update(
                        programare.getID(),
                        programare.getPacientId(),
                        programare.getData(),
                        programare.getScop()
                );
                refreshProgramari();
                showInfo("Programare actualizata cu succes!");
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
    }

    private void deleteProgramare() {
        Programare selected = programareTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza o programare!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText("Stergi programarea?");
        alert.setContentText(selected.toString());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    programareService.delete(selected.getID());
                    refreshProgramari();
                    showInfo("Programare stearsa cu succes!");
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }
        });
    }


    private VBox createRapoarteTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        raportArea = new TextArea();
        raportArea.setEditable(false);
        raportArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        GridPane buttons = new GridPane();
        buttons.setHgap(10);
        buttons.setVgap(10);

        Button btn1 = new Button("Programari per Pacient");
        Button btn2 = new Button("Programari per Luna");
        Button btn3 = new Button("Zile de la ultima programare");
        Button btn4 = new Button("Luni cele mai aglomerate");

        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);

        btn1.setOnAction(e -> showRaport1());
        btn2.setOnAction(e -> showRaport2());
        btn3.setOnAction(e -> showRaport3());
        btn4.setOnAction(e -> showRaport4());

        buttons.add(btn1, 0, 0);
        buttons.add(btn2, 1, 0);
        buttons.add(btn3, 0, 1);
        buttons.add(btn4, 1, 1);

        vbox.getChildren().addAll(buttons, raportArea);
        VBox.setVgrow(raportArea, Priority.ALWAYS);

        return vbox;
    }

    private void showRaport1() {
        StringBuilder sb = new StringBuilder();
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        sb.append("  RAPORT: NUMAR PROGRAMARI PER PACIENT\n");
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        List<Map.Entry<Pacient, Long>> raport = rapoarteService.programariPerPacient();

        for (Map.Entry<Pacient, Long> entry : raport) {
            Pacient p = entry.getKey();
            sb.append(String.format("%-20s %-15s | %d programari\n",
                    p.getNume(), p.getPrenume(), entry.getValue()));
        }

        raportArea.setText(sb.toString());
    }

    private void showRaport2() {
        StringBuilder sb = new StringBuilder();
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        sb.append("  RAPORT: NUMAR PROGRAMARI PER LUNA\n");
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        List<Map.Entry<String, Long>> raport = rapoarteService.programariPerLuna();

        for (Map.Entry<String, Long> entry : raport) {
            sb.append(String.format("Luna: %-10s | %d programari\n",
                    entry.getKey(), entry.getValue()));
        }

        raportArea.setText(sb.toString());
    }

    private void showRaport3() {
        StringBuilder sb = new StringBuilder();
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        sb.append("  RAPORT: ZILE DE LA ULTIMA PROGRAMARE\n");
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<RapoarteService.PacientUltimaProgramare> raport = rapoarteService.zileDelaUltimaProgramare();

        for (RapoarteService.PacientUltimaProgramare entry : raport) {
            Pacient p = entry.pacient;
            sb.append(String.format("%-20s %-15s | Ultima: %s | %d zile\n",
                    p.getNume(),
                    p.getPrenume(),
                    dateFormat.format(entry.data),  // ← DATA ULTIMEI PROGRAMĂRI
                    entry.zile));
        }

        raportArea.setText(sb.toString());
    }

    private void showRaport4() {
        StringBuilder sb = new StringBuilder();
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        sb.append("  RAPORT: CELE MAI AGLOMERATE LUNI\n");
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        List<Map.Entry<String, Long>> raport = rapoarteService.celeMaiAglomerateLuni();

        int rank = 1;
        for (Map.Entry<String, Long> entry : raport) {
            sb.append(String.format("%d. Luna: %-10s | %d programari\n",
                    rank++, entry.getKey(), entry.getValue()));
        }

        raportArea.setText(sb.toString());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}