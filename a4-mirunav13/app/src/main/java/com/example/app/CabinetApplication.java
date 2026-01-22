package com.example.app;

import Domain.Pacient;
import Domain.Programare;
import javafx.application.Application;
import javafx.stage.Stage;
import Repository.Repository;
import Service.PacientService;
import Service.ProgramareService;
import Service.RapoarteService;
import UI.MainGUI;

public class CabinetApplication extends Application {

    private static PacientService pacientService;
    private static ProgramareService programareService;
    private static RapoarteService rapoarteService;

    public static void setServices(PacientService ps, ProgramareService prs, RapoarteService rs) {
        pacientService = ps;
        programareService = prs;
        rapoarteService = rs;
    }

    @Override
    public void start(Stage primaryStage) {
        MainGUI gui = new MainGUI();
        gui.setServices(pacientService, programareService, rapoarteService);
        gui.start(primaryStage);
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}