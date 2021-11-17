package tesi.tesifx;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;




public class PrimarySceneController  implements Initializable{

    @FXML
    private Button aggiungiButton;
    @FXML
    private TextField searchbar;
    @FXML
    private ListView<Label> listView;
    @FXML
    private Button modificaButton;
    @FXML
    private Button rimuoviButton;
    private int index;









    private Logic l=Logic.getInstance();

    public void closeTableView()
    {
        listView.setVisible(false);
        modificaButton.setVisible(false);
        rimuoviButton.setVisible(false);
    }
    public void showMenuItem(MouseEvent event)
    {
        int index2=listView.getSelectionModel().getSelectedIndex();
        listView.getItems().clear();
        List<Persona> p=new ArrayList<Persona>();
        List<Label> labels=new ArrayList<Label>();
        p=l.getPersone();
        for(Persona p2:p)
        {


                Label label=new Label(p2.getNome()+" "+p2.getCognome());
                label.setStyle("-fx-font-size: 18;");
                label.setStyle("-fx-background-color:LIGHTGREY;");
                label.setMinWidth(288.00);
                try {
                    label.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\User_Icon_32.png"))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                listView.getItems().add(label);


        }



        listView.getItems().get(index2).setStyle("-fx-background-color:DARKGREY");


        if(event.getY()<40) {
            rimuoviButton.setLayoutY(5);
            modificaButton.setLayoutY(5);
        }
        if(event.getY()<80 && event.getY()>=40) {
            rimuoviButton.setLayoutY(45);
            modificaButton.setLayoutY(45);
        }
        if(event.getY()<120 && event.getY()>=80) {
            rimuoviButton.setLayoutY(82);
            modificaButton.setLayoutY(82);
        }
        if(event.getY()<160 && event.getY()>=120) {
            rimuoviButton.setLayoutY(120);
            modificaButton.setLayoutY(120);
        }
        if(event.getY()<200 && event.getY()>=160) {
            rimuoviButton.setLayoutY(160);
            modificaButton.setLayoutY(160);
        }
        rimuoviButton.setVisible(true);
        modificaButton.setVisible(true);




index=index2;


    }
    public void deletePersona()
    {
        String nome=listView.getItems().get(index).getText();

        String [] nomi=nome.split(" ");
        l.rimuoviPersona(nomi[0],nomi[1]);
        dammiTabella();
    }
public void modificaPersona()
{


    FXMLLoader   fxmlLoader = new FXMLLoader(getClass().getResource("addScene.fxml"));
    try {
        Parent root = (Parent) fxmlLoader.load();
        String nome = listView.getItems().get(index).getText();
        addSceneController controller = fxmlLoader.getController();

        System.out.println(nome);
        List<Persona> persone = l.getPersone();

        String nomi[]=nome.split(" ");
        for (Persona p : persone)
        {
            if(p.getNome().equals(nomi[0]) && p.getCognome().equals(nomi[1]))
            {
                System.out.println("Persona id:"+p.getID());
                controller.inizializzaPersona(p);

            }
        }

        Stage stage= (Stage) aggiungiButton.getScene().getWindow();
        stage.setScene(new Scene(root,800,800));

    } catch (IOException e) {
        e.printStackTrace();
    }



}
    public void dammiTabella()
    {
        listView.getItems().clear();
        List<Persona> p=new ArrayList<Persona>();
        List<Label> labels=new ArrayList<Label>();
        p=l.getPersone();





        for(Persona p2:p)
        {
            if(p2.getNome().contains(searchbar.getText()) || p2.getCognome().contains(searchbar.getText()))
            {

                Label label=new Label(p2.getNome()+" "+p2.getCognome());
                label.setStyle("-fx-font-size: 18;");
                label.setStyle("-fx-background-color:LIGHTGREY;");
                label.setMinWidth(288.00);
                try {
                    label.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\User_Icon_32.png"))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                listView.getItems().add(label);

            }
        }

        listView.setVisible(true);


    }


    public void changePrimaryScene(ActionEvent event)
    {
        Parent fxmlLoader= null;
        try {
            fxmlLoader = FXMLLoader.load(getClass().getResource("addScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= (Stage) aggiungiButton.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader,800,800));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchbar.clear();
    }
}