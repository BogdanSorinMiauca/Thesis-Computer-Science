package tesi.tesifx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class modificaController implements Initializable{
    @FXML
    private Button lunediButton;
    @FXML
    private Button martediButton;
    @FXML
    private Button mercolediButton;
    @FXML
    private Button giovediButton;
    @FXML
    private Button venerdiButton;
    @FXML
    private Button sabatoButton;
    @FXML
    private Button domenicaButton;
    @FXML
    private Button colazioneButton2;
    @FXML
    private Button cenaButton2;
    @FXML
    private Button backButton3;
    @FXML
    private Button pranzoButton2;
    @FXML
    private Button spuntinoButton2;
    @FXML
    private ListView<Label> listaModifica;
    private String nome;
    @FXML
    private Label nameLabel;
    private Logic l=Logic.getInstance();
    public void addNome(String n)
    {
        this.nome=n;
        nameLabel.setText(n);
        nameLabel.setAlignment(Pos.CENTER_LEFT);
        nameLabel.setStyle("-fx-font-weight: bold;");
        nameLabel.setStyle("-");
        nameLabel.setFont(new Font(15));
    }
    public void backPage3()
    {
        Parent fxmlLoader= null;
        try {
            fxmlLoader = FXMLLoader.load(getClass().getResource("primaryScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= (Stage) backButton3.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader,800,800));
    }
    @FXML
    private void showLunedi()
    {
        listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Lunedi"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+"     "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }
    @FXML
    private void showMartedi()
    {   listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Martedi"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+"     "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }
    @FXML
    private void showMercoledi()
    {   listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Mercoledi"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+"     "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }
    @FXML
    private void showGiovedi()
    {   listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Giovedi"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+"     "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }
    @FXML
    private void showVenerdi()
    {   listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Venerdi"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+"     "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }
    @FXML
    private void showSabato()
    {   listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Sabato"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+"     "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }

    @FXML
    private void showDomenica()
    {   listaModifica.getItems().clear();
        Dieta dieteGiorno=new Dieta();
        List<Dieta> diete=new ArrayList<Dieta>();
        diete=l.getDiete();
        String[] nomi=nome.split(" ");
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(nomi[0]) && d.getPersona().getCognome().equals(nomi[1]) && d.getGiorno().equals("Domenica"))
            {
                dieteGiorno=d;
            }
        }

        for(Alimento a:dieteGiorno.getTuttiAlimenti())
        {
            Label label=new Label(a.getNome()+" "+a.getGrammi());
            label.setStyle("-fx-background-color:LIGHTGREY");
            listaModifica.getItems().add(label);

        }
    }
    @FXML
    private void showColazione()
    {

    }
    @FXML
    private void showPranzo()
    {

    }
    @FXML
    private void showCena()
    {

    }
    @FXML
    private void showSpuntino()
    {

    }
    public void minimizeLunedi()
    {
        Font font=new Font(10);
        lunediButton.setFont(font);

    }
    public void maximizeLunedi()
    {
        Font font=new Font(15);
        lunediButton.setFont(font);
    }
    public void minimizeMartedi()
    {
        Font font=new Font(10);
        martediButton.setFont(font);

    }
    public void maximizeMartedi()
    {
        Font font=new Font(15);
        martediButton.setFont(font);
    }
    public void minimizeMercoledi()
    {
        Font font=new Font(10);
        mercolediButton.setFont(font);

    }
    public void maximizeMercoledi()
    {
        Font font=new Font(15);
        mercolediButton.setFont(font);
    }
    public void minimizeGiovedi()
    {
        Font font=new Font(10);
        giovediButton.setFont(font);

    }
    public void maximizeGiovedi()
    {
        Font font=new Font(15);
        giovediButton.setFont(font);
    }
    public void minimizeVenerdi()
    {
        Font font=new Font(10);
        venerdiButton.setFont(font);

    }
    public void maximizeVenerdi()
    {
        Font font=new Font(15);
        venerdiButton.setFont(font);
    }
    public void minimizeSabato()
    {
        Font font=new Font(10);
        sabatoButton.setFont(font);

    }
    public void maximizeSabato()
    {
        Font font=new Font(15);
        sabatoButton.setFont(font);
    }
    public void minimizeDomenica()
    {
        Font font=new Font(10);
        domenicaButton.setFont(font);

    }
    public void maximizeDomenica()
    {
        Font font=new Font(15);
        domenicaButton.setFont(font);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\back_arrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        backButton3.setGraphic(imageView);
    }
}

