package tesi.tesifx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class addSceneController implements Initializable {
    Logic l=Logic.getInstance();
    @FXML
    private Button avantiButton;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private ComboBox comboObiettivo;
    @FXML
    private TextField altezza;
    @FXML
    private TextField peso;
    @FXML
    private TextField eta;
    @FXML
    private Button maschio;
    @FXML
    private Button infoDieta;
    @FXML
    private Button femmina;
    @FXML
    private Button backButton;
    private Persona p;
    @FXML
    private ComboBox comboAllenamento;
    private String genere;
    public void genereFemmina()
    {
        genere="Femmina";
        femmina.setStyle("-fx-background-color:DARKGREY");
        maschio.setStyle("-fx-background-color:WHITE");
        maschio.setStyle("-fx-background-radius:15");
        System.out.println(genere);
    }
    public void showInfoDieta()
    {    System.out.println("brooo"+p);
        FXMLLoader   fxmlLoader = new FXMLLoader(getClass().getResource("infoScene.fxml"));
        try {
            Parent root=(Parent) fxmlLoader.load();
            System.out.println("fanculo"+p);
            infoSceneController controller = fxmlLoader.getController();
            System.out.println(infoSceneController.class);

            controller.addPersona(p);
            Stage stage= new Stage();
            stage.setScene(new Scene(root,700,500));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void genereMaschio()
    {
        genere="Maschio";
        maschio.setStyle("-fx-background-color:DARKGREY");
        femmina.setStyle("-fx-background-color:WHITE");
        femmina.setStyle("-fx-background-radius:15");
        System.out.println(genere);
    }
    @FXML
    public void backPage()
    {
        Parent fxmlLoader= null;
        try {
            fxmlLoader = FXMLLoader.load(getClass().getResource("primaryScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader,800,800));
    }
    public void changeAddScene(ActionEvent event)
    {   int id=0;
        if(p!=null) {
        id = p.getID();
        System.out.println("Persona id:" + id);
        }
        Date d=new Date();
        Persona p=new Persona(nome.getText(),cognome.getText(),Integer.parseInt(altezza.getText()),Integer.parseInt(peso.getText()
        ),Integer.parseInt(eta.getText()),genere,String.valueOf(comboAllenamento.getSelectionModel().getSelectedItem()),String.valueOf(comboObiettivo.getSelectionModel().getSelectedItem()));
        p.setData(String.valueOf(d));
        System.out.println("Persona"+p);
        if(l.essistePersona(p.getNome(),p.getCognome())) {
            System.out.println("Essiste persona");
            p.setID(id);
            if(comboAllenamento.getSelectionModel().getSelectedItem()==null) {
                p.setAllenamento(comboAllenamento.getPromptText());
            }
            if(comboObiettivo.getSelectionModel().getSelectedItem()==null) {
                p.setObbiettivo(comboObiettivo.getPromptText());
            }
            System.out.println("Persona essistente"+p.getAllenamento());
            System.out.println("Allenamento:"+comboAllenamento.getPromptText());
            System.out.println("Selection model"+comboAllenamento.getSelectionModel().getSelectedItem());
            l.aggiornaPersona(p);

            /*String url = "jdbc:sqlite:database.db";
            Connection conn = null;


            try {
                conn = DriverManager.getConnection(url);

                Statement stmt = conn.createStatement();
            String sq3 = "DELETE FROM Dieta WHERE IDPersona ='"+id+"'";
            PreparedStatement pstmt = conn.prepareStatement(sq3);
            pstmt.executeUpdate();
            l.eliminaDieta(p.getNome(),p.getCognome());


        } catch (SQLException e) {
        e.printStackTrace();
    }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        }
        else {
            System.out.println("Non essiste persona");
            l.inserisciPersona(p);
        }
        System.out.println(d+" "+p.getPeso()+" "+l.dammiCalorieTotali(p));
        FXMLLoader   fxmlLoader = new FXMLLoader(getClass().getResource("addToDiet.fxml"));
        try {
            Parent root=(Parent) fxmlLoader.load();

            addToDietController controller = fxmlLoader.getController();

            controller.addPersona(p);
            System.out.println(p);
            Stage stage= (Stage) avantiButton.getScene().getWindow();
            stage.setScene(new Scene(root,800,800));

        } catch (IOException e) {
            e.printStackTrace();
        }



        //aggiungere il controllo dei testi


        //System.out.println((int) l.dammiCalorieTotali());

    }


    public void pulisciNome() {

        nome.setText("");
    }
    public void pulisciCognome() {
        cognome.setText("");}
    public void pulisciAltezza() {
        altezza.setText("");}
    public void pulisciPeso() {
        peso.setText("");}
    public void pulisciEta() {
        eta.setText("");}

public void inizializzaPersona(Persona p)
{   this.p=p;
    System.out.println(p);
    infoDieta.setVisible(true);
    nome.setText(p.getNome());
    cognome.setText(p.getCognome());
    altezza.setText(String.valueOf(p.getAltezza()));
    peso.setText(String.valueOf(p.getPeso()));
    eta.setText(String.valueOf(p.getEta()));
    if(p.getGenere().equals("Maschio"))
    {
        genere="Maschio";
        maschio.setStyle("-fx-background-color:DARKGREY");
        femmina.setStyle("-fx-background-color:WHITE");
        femmina.setStyle("-fx-background-radius:15");
    }
    else
    {
        genere="Femmina";
        femmina.setStyle("-fx-background-color:DARKGREY");
        maschio.setStyle("-fx-background-color:WHITE");
        maschio.setStyle("-fx-background-radius:15");
    }

    comboObiettivo.setPromptText(p.getObbiettivo());
    comboAllenamento.setPromptText(p.getAllenamento());

}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\back_arrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        backButton.setGraphic(imageView);

        ObservableList<String> options = FXCollections.observableArrayList("Nessuno","Leggero","Moderato","Intenso");
        comboAllenamento.setItems(options);
        ObservableList<String> options2 = FXCollections.observableArrayList("Dimagrimento","Mantenimento peso","Aumento massa");
        comboObiettivo.setItems(options2);

    }
}
