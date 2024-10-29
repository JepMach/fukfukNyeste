package com.example.demo;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class HelloApplication extends Application {
    int[] ects = new int[3];

    static Connection conn = null;
    static String url = "jdbc:sqlite:SQLit.sqlite";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bachelor program");

        //I disse comboboxes skal der stå navnene på de forskellige fag der kan vælges. her skal dataen fra SQL være.
        ComboBox<String> programComboBox = new ComboBox<>();
        programComboBox.getItems().addAll(getData("programNavn","BachelorProgrammer","programNavn='Bach1'"));
        programComboBox.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> subject1ComboBox = new ComboBox<>();
        subject1ComboBox.getItems().addAll(getData("modulNavn","Moduler","modulNavn='Mod1' or modulNavn='Mod2' or modulNavn='Basis'"));
        subject1ComboBox.setMaxWidth(Double.MAX_VALUE);




        ComboBox<String> subject2ComboBox = new ComboBox<>();
        subject2ComboBox.getItems().addAll(getData("kursusNavn","Kurser","modulNavn='rør'"));
        subject2ComboBox.setMaxWidth(Double.MAX_VALUE);

        subject1ComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            subject2ComboBox.getItems().clear();
            if (Objects.equals(newValue, "Mod1")) {
                subject2ComboBox.getItems().addAll(getData("kursusNavn","Kurser","modulNavn='Mod1'"));
        } else if(Objects.equals(newValue, "Mod2")) {
            subject2ComboBox.getItems().addAll(getData("kursusNavn","Kurser","modulNavn='Mod2'"));
        } else {
            subject2ComboBox.getItems().addAll(getData("kursusNavn","Kurser","modulNavn='Basis'"));
        }
        });

        ComboBox<String> electiveComboBox = new ComboBox<>();
        electiveComboBox.getItems().addAll(getData("projektNavn","Projekter","modulNavn='Mod2'"));
        electiveComboBox.setMaxWidth(Double.MAX_VALUE);

        subject1ComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            electiveComboBox.getItems().clear();
            if (Objects.equals(newValue, "Mod1")) {
                electiveComboBox.getItems().addAll(getData("projektNavn","Projekter","modulNavn='Mod1'"));
            } else if(Objects.equals(newValue, "Mod2")) {
                electiveComboBox.getItems().addAll(getData("projektNavn","Projekter","modulNavn='Mod2'"));
            } else {
                electiveComboBox.getItems().addAll(getData("projektNavn","Projekter","modulNavn='Basis'"));
            }
        });

        //lister til at se de fag der er blevet added.
        ListView<String> programListView = new ListView<>();

        ListView<String> modulListView = new ListView<>();

        ListView<String> kurserListView = new ListView<>();

        ListView<String> projekterListView = new ListView<>();

        // Labels der viser hvor mange ects der er valgt i en given collum
        Label programNumberLabel = new Label("ECTS:");;
        Label kurserNumberLabel = new Label("ECTS:");
        Label projekterNumberLabel = new Label("ECTS:");

        //labels der viser hvad der vælges i en given collum. ikke vigtigt for funktionalitet.
        Label programLabel = new Label("Program:");
        Label modulLabel = new Label("Modul:");
        Label kurserLabel = new Label("Kurser:");
        Label projekterLabel = new Label("Projekter:");

        //knapper til at add de forskellige fag til listerne.
        Button addProgramButton = new Button("Add");
        Button addModulButton = new Button("Add");
        Button addKursusButton = new Button("Add");
        Button addProjektButton = new Button("Add");

        //gør knapperne brede for at det passer sammen med resten af programmet. ikke vigtigt for funktionalitet.
        addProgramButton.setMaxWidth(Double.MAX_VALUE);
        addModulButton.setMaxWidth(Double.MAX_VALUE);
        addKursusButton.setMaxWidth(Double.MAX_VALUE);
        addProjektButton.setMaxWidth(Double.MAX_VALUE);

        //gør at knapperne virker. henter information fra combo boksene og sætter dem ind i listerne, samt ændre ects label. der er en for hver collum
        addProgramButton.setOnAction(e -> {
            String selectedProgram = programComboBox.getSelectionModel().getSelectedItem();
            if (selectedProgram != null) {
                programListView.getItems().add(selectedProgram);
            }
        });

        addModulButton.setOnAction(e -> {

            String selectedSubject1 = subject1ComboBox.getSelectionModel().getSelectedItem();
            if (selectedSubject1 != null) {
                if(!modulListView.getItems().contains(selectedSubject1)) {
                    modulListView.getItems().clear();
                    modulListView.getItems().add(selectedSubject1);
                }

            }
        });

        addKursusButton.setOnAction(e -> {
            String selectedSubject2 = subject2ComboBox.getSelectionModel().getSelectedItem();
            if (selectedSubject2 != null) {
                kurserListView.getItems().add(selectedSubject2);

                StringBuilder fuk = new StringBuilder();
                fuk.append(getData("ects","Kurser","kursusNavn='"+selectedSubject2+"'").toString());
                fuk.deleteCharAt(0);
                fuk.deleteCharAt(fuk.length()-1);
                ects[0]+=Integer.parseInt(fuk.toString());
                ects[1]+=Integer.parseInt(fuk.toString());
                kurserNumberLabel.setText("ECTS:"+ ects[1]);
                programNumberLabel.setText("ECTS:" + ects[0]);
            }
        });

        addProjektButton.setOnAction(e -> {
            String selectedElective = electiveComboBox.getSelectionModel().getSelectedItem();
            if (selectedElective != null) {
                projekterListView.getItems().add(selectedElective);

                StringBuilder fuk = new StringBuilder();
                fuk.append(getData("ects","Projekter","projektNavn='"+selectedElective+"'").toString());
                fuk.deleteCharAt(0);
                fuk.deleteCharAt(fuk.length()-1);
                ects[0]+=Integer.parseInt(fuk.toString());
                ects[2]+=Integer.parseInt(fuk.toString());

                projekterNumberLabel.setText("ECTS:"+ects[2]);
                programNumberLabel.setText("ECTS:" + ects[0]);

            }
        });


        // tilføjer de forskellige dele i collums. delene bliver tilføjer fra toppen.
        HBox topRow = new HBox(10,
                new VBox(5,programLabel, programComboBox, addProgramButton, programListView, programNumberLabel),
                new VBox(5,modulLabel, subject1ComboBox, addModulButton, modulListView),
                new VBox(5, kurserLabel, subject2ComboBox, addKursusButton, kurserListView, kurserNumberLabel),
                new VBox(5, projekterLabel, electiveComboBox, addProjektButton, projekterListView, projekterNumberLabel)
        );

        //tilføjer det til scenen og giver lidt ekstra spacing.
        VBox mainLayout = new VBox(10, topRow);
        mainLayout.setPadding(new Insets(10));


        Scene scene = new Scene(mainLayout, 800, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static List<String> getData(String input, String table, String spec) {
        List<String> dataListe = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url);
            String query = "select "+input+" from "+table+" where "+spec;
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    dataListe.add(rs.getString(input));
                }
                return dataListe;

            } catch (SQLException e) {
                throw new Error("Problem", e);
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }


    }

    public static String getECTSSum(String input) {

        try {
            conn = DriverManager.getConnection(url);
            String query = "SELECT SUM(ects) as 'ECTS' from (select SUM(Kurser.ects) ects from Kurser where "+input+" UNION ALL select SUM(Projekter.ects) ects from Projekter where "+input+")";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                return rs.getString("ECTS");

            } catch (SQLException e) {
                throw new Error("Problem", e);
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }


    }
}
