package tesi.tesifx;
import java.io.FileOutputStream;



import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.ObservableList;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class addToDietController implements Initializable {
    @FXML
    private Button infoButton;
    private int numeroAlimenti=0;
    private int proteine;
    private int carboidrati;
    private int grassi;
    @FXML
    private Button progressButton;
    String unita;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button colazioneButton;
    private int grammi;
    @FXML
    private Button fineButton;
    @FXML
    private Button stampaButton;
    @FXML
    private Button inviaButtonNota;
    @FXML
    private TextField textFieldNota;
    @FXML
    private TextArea textAreaNota;
    @FXML
    private Button pranzoButton;
    @FXML
    private Button cenaButton;
    @FXML
    private Button spuntinoButton;
    private Persona p;
    @FXML
    private VBox vboxItem;
    @FXML
    private TextField cercaAlimenti;
    @FXML
    private ListView<Label> listaAlimenti;
    @FXML
    private ListView<Label> quantitaView;
    @FXML
    private ListView<Label> listaAlimentiAggiunti;
    @FXML
    private Label alimentiLabel;
    @FXML
    private Button aggiungiButton2;
    @FXML
    private Button salvaButton;
    @FXML
    private Button buttonPasto;
    @FXML
    private Label labelPasto;
    @FXML
    private Button backButton2;
    @FXML
    private ChoiceBox choiseBox;
    @FXML
    private Button totaleButton;
    private String pasto;
    private Logic l=Logic.getInstance();

    public void openVbox()
    {
        if(vboxItem.isVisible()!=true) {


            vboxItem.setVisible(true);
            progressButton.setText("0/" + (int) l.dammiCalorieTotali(p));

        }
    }
    public void openInfo()
    {
        FXMLLoader   fxmlLoader = new FXMLLoader(getClass().getResource("infoScene.fxml"));
        try {
            Parent root=(Parent) fxmlLoader.load();
            //String nome=p.getNome()+" "+p.getCognome();
            //modificaController controller = fxmlLoader.getController();
            //System.out.println(nome);
            //controller.addNome(nome);
            Stage stage= new Stage();
            stage.setScene(new Scene(root,400,450));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addPersona(Persona pp)
    {
        this.p=pp;
        System.out.println(p);
        progressButton.setText("0/"+(int)l.dammiCalorieTotali(p));
    }

    public void backPage2()
    {
        Parent fxmlLoader= null;
        try {
            fxmlLoader = FXMLLoader.load(getClass().getResource("addScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= (Stage) backButton2.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader,800,800));
    }
    public void changeDay()
    {
        progressButton.setText("0/"+(int)l.dammiCalorieTotali(p));
        System.out.println((int)l.dammiCalorieTotali(p));

        progressBar.setProgress(0.0);
        listaAlimentiAggiunti.getItems().clear();
        textAreaNota.clear();




    }
    public void rimuoviAlimento()
    {   String giorno=choiseBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(giorno+" "+p.getID()+" "+pasto);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sei sicuro di volterlo rimuovere");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                String[] alimento=listaAlimentiAggiunti.getSelectionModel().getSelectedItem().getText().split(" ");
                StringBuilder builder=new StringBuilder();
                for(int i=0;i<alimento.length-2;i++)
                {
                    builder.append(alimento[i]+" ");
                }
                builder.deleteCharAt(builder.length()-1);
                l.rimuoviAlimento(builder.toString(),giorno,pasto,p.getID());
                System.out.println("Alimento rimosso"+builder);
            } else if (type == noButton) {System.out.println("Alimento non rimosso");
            }
        });
        //da aggiustare
        Label label=listaAlimentiAggiunti.getSelectionModel().getSelectedItem();
        listaAlimentiAggiunti.getItems().remove(label);
        if(pasto.equals("Colazione"))
        {
            setValueColazione();
        }
        if(pasto.equals("Pranzo"))
        {
            setValuePranzo();
        }
        if(pasto.equals("Cena"))
        {
            setValueCena();
        }
        if(pasto.equals("Spuntino"))
        {
            setValueSpuntino();
        }
        l.inizializza();
    }

    @FXML
    public void salvaDieta() {

        List<Label> list = (listaAlimentiAggiunti.getItems());
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        String personaNome = p.getNome();
        String personaCognome = p.getCognome();
        String giorno = String.valueOf(choiseBox.getSelectionModel().getSelectedItem());
if(!giorno.equals("Giorno")) {
    try {
        conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        String sql2 = "Select ID From Persona WHERE nome='" + personaNome + "' AND cognome='" + personaCognome + "'";
        ResultSet rs = stmt.executeQuery(sql2);


        String id = String.valueOf(rs.getInt("ID"));
        List<Alimento> alimenti = new ArrayList<Alimento>();
        String sql = "INSERT INTO Dieta (Alimento,Pasto,IDPersona,Giorno,Quantita,Nota) VALUES(?,?,?,?,?,?)";
        int i=0;
        for (int j=numeroAlimenti;j<list.size();j++) {


            PreparedStatement pstmt = null;
            try {

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, list.get(j).getText());
                pstmt.setString(2, pasto);
                pstmt.setInt(3, Integer.parseInt(id));
                pstmt.setString(4, giorno);
                pstmt.setString(5,quantitaView.getItems().get(i).getText());
                pstmt.setString(6,textAreaNota.getText());
                pstmt.executeUpdate();
                System.out.println("Dalla lista :"+list.get(j).getText());
                Alimento a = l.dammiAlimento(list.get(j).getText());
                System.out.println("Dagli alimenti :"+a);
                a.setGrammi(quantitaView.getItems().get(i).getText());
                alimenti.add(a);
                i++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn.close();

        List<Dieta> diete = new ArrayList<Dieta>();
        diete = l.getDiete();
        boolean essiste = false;
        for (Dieta d : diete) {
            if (d.getPersona().getNome().equals(personaNome) && d.getPersona().getCognome().equals(personaCognome) && d.getGiorno().equals(giorno)) {
                essiste = true;
                d.setNota(textAreaNota.getText());
                if (pasto == "Colazione") {
                    d.setAlimentiColazione(alimenti);
                }
                if (pasto == "Pranzo") {
                    d.setAlimentiPranzo(alimenti);
                }
                if (pasto == "Cena") {
                    d.setAlimentiCena(alimenti);
                }
                if (pasto == "Spuntino") {
                    d.setAlimentiSpuntino(alimenti);
                }

            }
        }
        if (essiste == false) {
            Dieta dieta = new Dieta();
            dieta.setPersona(p);
            dieta.setGiorno(giorno);
            dieta.setNota(textAreaNota.getText());
            if (pasto == "Colazione") {
                dieta.setAlimentiColazione(alimenti);
            }
            if (pasto == "Pranzo") {
                dieta.setAlimentiPranzo(alimenti);
            }
            if (pasto == "Cena") {
                dieta.setAlimentiCena(alimenti);
            }
            if (pasto == "Spuntino") {
                dieta.setAlimentiSpuntino(alimenti);
            }
            l.getDiete().add(dieta);

        }
        l.inizializza();
            /*    if (pasto == "Colazione") {
                    l.getDiete().get(l.getDiete().size() - 1).setAlimentiColazione(alimenti);
                }
                if (pasto == "Pranzo") {
                    l.getDiete().get(l.getDiete().size() - 1).setAlimentiPranzo(alimenti);
                }
                if (pasto == "Cena") {
                    l.getDiete().get(l.getDiete().size() - 1).setAlimentiCena(alimenti);
                }
                if (pasto == "Spuntino") {
                    l.getDiete().get(l.getDiete().size() - 1).setAlimentiSpuntino(alimenti);
                }*/
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
else
{
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Attenzione");
    alert.setHeaderText("Non hai scelto un giorno");
    alert.showAndWait();
}
           // l.getDiete().get(l.getDiete().size() - 1).setGiorno(giorno);
            listaAlimenti.getItems().clear();
            quantitaView.getItems().clear();
            List<Alimento> a = new ArrayList<Alimento>();
            a = l.getAlimenti();
            for (Alimento a2 : a) {
                if (a2.getNome().contains(cercaAlimenti.getText())) {
                    Label label = new Label(a2.getNome());// + " " + String.valueOf(a2.getCalorie()) + " " + String.valueOf(a2.getProteine()) + " " + String.valueOf(a2.getCarboidrati()) + " " + String.valueOf(a2.getColesterolo()) +
                    //   " " + String.valueOf(a2.getFibre()));
                    try {
                        label.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\food.png"))));
                        label.setStyle("-fx-font-size: 18;");
                        label.setStyle("-fx-background-color:LIGHTGREY;");
                        label.setMinWidth(280.00);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    listaAlimenti.getItems().add(label);
                }
                listaAlimenti.setVisible(true);
            }


            //inserto into dieta alimento,pasto,id;
            //sql2 = "UPDATE Dieta SET Alimento = 'SUCA', Pasto = 'UnCazzo' WHERE  IDPersona='"+id2+"'";



            /*for(Label label:list)
            {
                String sql="INSERT INTO Dieta (Alimento,Pasto) VALUES (?,?) WHERE IDPersona='"+id+"'";
                PreparedStatement pstmt = null;
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, label.getText());
                pstmt.setString(2, pasto);
                pstmt.executeUpdate();
            }*/
            listaAlimentiAggiunti.getItems().clear();


        }


    public void setValueColazione()
    {
        if(choiseBox.getSelectionModel().getSelectedItem().equals("Giorno"))
        {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Scegli un giorno");


            alert.showAndWait();
        }
        else {

            ridisegnaBottoni();
            colazioneButton.setStyle("-fx-background-color:darkgrey");
            String nome = p.getNome();
            String cognome = p.getCognome();
            listaAlimentiAggiunti.getItems().clear();
            pasto = "Colazione";
            List<Alimento> alimento = new ArrayList<Alimento>();

            List<Dieta> diete = new ArrayList<Dieta>();
            diete = l.getDiete();

            for (Dieta d : diete) {

                if (nome.equals(d.getPersona().getNome()) && cognome.equals(d.getPersona().getCognome()) && d.getGiorno().equals(String.valueOf(choiseBox.getSelectionModel().getSelectedItem()))) {
                    System.out.println("Nome :" + nome + " Cognome :" + cognome + " Giorno :" + d.getGiorno());
                    alimento = d.getAlimentiColazione();

                    setAlimentiGiorno(d);

                }
            }



            // alimento=l.getDiete().get(l.getDiete().size()-1).getAlimentiColazione();
            int ris=0;
            numeroAlimenti=0;
            for (Alimento a : alimento) {
                numeroAlimenti++;
                Label label = new Label(a.getNome()+" "+a.getGrammi());
                label.setStyle("-fx-background-color:LIGHTGREY");

                listaAlimentiAggiunti.getItems().add(label);

                int calorie= (int) a.getCalorie();
                String gramm=a.getGrammi();
                String[] g=gramm.split(" ");
                int gr=Integer.parseInt(g[0]);
                double percentuale=(double) gr/100;
                calorie=(int) ((int)a.getCalorie()*percentuale);
                ris=ris+calorie;




            }
            System.out.println("Ci sono :"+numeroAlimenti+" alimenti");
            buttonPasto.setText(String.valueOf(ris));

            labelPasto.setText("Colazione");

        }



    }
    public void setAlimentiGiorno(Dieta d)
    {
        List<Alimento>alimentiColazione=d.getAlimentiColazione();
        List<Alimento>alimentiPranzo=d.getAlimentiPranzo();
        List<Alimento>alimentiCena=d.getAlimentiCena();
        List<Alimento>alimentiSpuntino=d.getAlimentiSpuntino();
        int ris=0;

        for (Alimento a : alimentiColazione) {

            int calorie= (int) a.getCalorie();
            String gramm=a.getGrammi();
            String[] g=gramm.split(" ");
            int gr=Integer.parseInt(g[0]);
            double percentuale=(double) gr/100;
            calorie=(int) ((int)a.getCalorie()*percentuale);
            ris=ris+calorie;
        }
        for (Alimento a : alimentiPranzo) {
            int calorie= (int) a.getCalorie();
            String gramm=a.getGrammi();
            String[] g=gramm.split(" ");
            int gr=Integer.parseInt(g[0]);
            double percentuale=(double) gr/100;
            calorie=(int) ((int)a.getCalorie()*percentuale);
            ris=ris+calorie;
        }
        for (Alimento a : alimentiCena) {
            int calorie= (int) a.getCalorie();
            String gramm=a.getGrammi();
            String[] g=gramm.split(" ");
            int gr=Integer.parseInt(g[0]);
            double percentuale=(double) gr/100;
            calorie=(int) ((int)a.getCalorie()*percentuale);
            ris=ris+calorie;
        }
        for (Alimento a : alimentiSpuntino) {
            int calorie= (int) a.getCalorie();
            String gramm=a.getGrammi();
            String[] g=gramm.split(" ");
            int gr=Integer.parseInt(g[0]);
            double percentuale=(double) gr/100;
            calorie=(int) ((int)a.getCalorie()*percentuale);
            ris=ris+calorie;
        }
        double risultato=0.0000;
        risultato=(double)ris/l.dammiCalorieTotali(p);
        progressBar.setProgress(risultato);
        progressButton.setText(String.valueOf(ris)+"/"+(int)l.dammiCalorieTotali(p));
    }
    public void setValuePranzo()
    {
        if(choiseBox.getSelectionModel().getSelectedItem().equals("Giorno"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Scegli un giorno");


            alert.showAndWait();
        }
        else {
            ridisegnaBottoni();
            pranzoButton.setStyle("-fx-background-color:darkgrey");
            String nome = p.getNome();
            String cognome = p.getCognome();
            listaAlimentiAggiunti.getItems().clear();
            pasto = "Pranzo";
            List<Alimento> alimento = new ArrayList<Alimento>();
            List<Dieta> diete = new ArrayList<Dieta>();
            diete = l.getDiete();

            for (Dieta d : diete) {

                if (nome.equals(d.getPersona().getNome()) && cognome.equals(d.getPersona().getCognome()) && d.getGiorno().equals(String.valueOf(choiseBox.getSelectionModel().getSelectedItem()))) {
                    System.out.println("Nome :" + nome + " Cognome :" + cognome + " Giorno :" + d.getGiorno());
                    alimento = d.getAlimentiPranzo();
                    setAlimentiGiorno(d);

                }
            }
            int ris=0;
            // alimento=l.getDiete().get(l.getDiete().size()-1).getAlimentiColazione();
            numeroAlimenti=0;
            for (Alimento a : alimento) {
                numeroAlimenti++;
                Label label = new Label(a.getNome()+" "+a.getGrammi());
                label.setStyle("-fx-background-color:LIGHTGREY");

                listaAlimentiAggiunti.getItems().add(label);

                int calorie= (int) a.getCalorie();
                String gramm=a.getGrammi();
                String[] g=gramm.split(" ");
                int gr=Integer.parseInt(g[0]);
                double percentuale=(double) gr/100;
                calorie=(int) ((int)a.getCalorie()*percentuale);
                ris=ris+calorie;


            }
            System.out.println("Ci sono :"+numeroAlimenti+" alimenti");
            buttonPasto.setText(String.valueOf(ris));

            labelPasto.setText("Pranzo");
        }
    }
    public void setValueCena()
    {
        if(choiseBox.getSelectionModel().getSelectedItem().equals("Giorno"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Scegli un giorno");


            alert.showAndWait();
        }
        else {
            ridisegnaBottoni();
            cenaButton.setStyle("-fx-background-color:darkgrey");
            String nome = p.getNome();
            String cognome = p.getCognome();
            listaAlimentiAggiunti.getItems().clear();
            pasto = "Cena";
            List<Alimento> alimento = new ArrayList<Alimento>();
            List<Dieta> diete = new ArrayList<Dieta>();
            diete = l.getDiete();
            for (Dieta d : diete) {

                if (nome.equals(d.getPersona().getNome()) && cognome.equals(d.getPersona().getCognome()) && d.getGiorno().equals(String.valueOf(choiseBox.getSelectionModel().getSelectedItem()))) {
                    System.out.println("Nome :" + nome + " Cognome :" + cognome + " Giorno :" + d.getGiorno());
                    alimento = d.getAlimentiCena();
                    setAlimentiGiorno(d);

                }
            }
            int ris=0;
            numeroAlimenti=0;
            // alimento=l.getDiete().get(l.getDiete().size()-1).getAlimentiColazione();
            for (Alimento a : alimento) {
                numeroAlimenti++;
                Label label = new Label(a.getNome()+" "+a.getGrammi());
                label.setStyle("-fx-background-color:LIGHTGREY");

                listaAlimentiAggiunti.getItems().add(label);

                int calorie= (int) a.getCalorie();
                String gramm=a.getGrammi();
                String[] g=gramm.split(" ");
                int gr=Integer.parseInt(g[0]);
                double percentuale=(double) gr/100;
                calorie=(int) ((int)a.getCalorie()*percentuale);
                ris=ris+calorie;
            }
            System.out.println("Ci sono :"+numeroAlimenti+" alimenti");
            buttonPasto.setText(String.valueOf(ris));

            labelPasto.setText("Cena");
        }
    }
    public void ridisegnaBottoni()
    {
        colazioneButton.setStyle("\"-fx-base: initial;\"");
        pranzoButton.setStyle("\"-fx-base: initial;\"");
        cenaButton.setStyle("\"-fx-base: initial;\"");
        spuntinoButton.setStyle("\"-fx-base: initial;\"");
        totaleButton.setStyle("\"-fx-base: initial;\"");
        stampaButton.setVisible(false);
    }
    public void setValueSpuntino()
    {
        if(choiseBox.getSelectionModel().getSelectedItem().equals("Giorno"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Scegli un giorno");


            alert.showAndWait();
        }
        else {
            ridisegnaBottoni();
            spuntinoButton.setStyle("-fx-background-color:darkgrey");
            String nome = p.getNome();
            String cognome = p.getCognome();
            listaAlimentiAggiunti.getItems().clear();
            pasto = "Spuntino";
            List<Alimento> alimento = new ArrayList<Alimento>();
            List<Dieta> diete = new ArrayList<Dieta>();
            diete = l.getDiete();
            for (Dieta d : diete) {

                if (nome.equals(d.getPersona().getNome()) && cognome.equals(d.getPersona().getCognome()) && d.getGiorno().equals(String.valueOf(choiseBox.getSelectionModel().getSelectedItem()))) {
                    System.out.println("Nome :" + nome + " Cognome :" + cognome + " Giorno :" + d.getGiorno());
                    alimento = d.getAlimentiSpuntino();
                    setAlimentiGiorno(d);


                }
            }
            int ris=0;
            int calorie=0;
            numeroAlimenti=0;
            // alimento=l.getDiete().get(l.getDiete().size()-1).getAlimentiColazione();
            for (Alimento a : alimento) {
                numeroAlimenti++;

                Label label = new Label(a.getNome()+" "+a.getGrammi());
                label.setStyle("-fx-background-color:LIGHTGREY");

                listaAlimentiAggiunti.getItems().add(label);


                String gramm=a.getGrammi();
                String[] g=gramm.split(" ");
                int gr=Integer.parseInt(g[0]);
                double percentuale=(double) gr/100;
                calorie=(int) ((int)a.getCalorie()*percentuale);
                System.out.println("calorie:"+calorie);
                ris=ris+calorie;
                System.out.println("risultato:"+ris);
            }
            System.out.println("Ci sono :"+numeroAlimenti+" alimenti");
            System.out.println(ris);
            buttonPasto.setText(String.valueOf(ris));

            labelPasto.setText("Spuntino");
        }
    }
    public void stampaPDF()
    {


        try {
            Document document = new Document();
            String savePath="C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\java\\pdf\\"+p.getNome()+p.getCognome()+".pdf";
            try {
                PdfWriter.getInstance(document, new FileOutputStream(savePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            document.open();
            List<Label> labels=new ArrayList<Label>();
            labels=listaAlimentiAggiunti.getItems();

            document.addTitle(p.getNome()+" "+p.getCognome());
            for(Label l:labels) {
                String line = l.getText();
                document.add(new Paragraph(line));
            }
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        stampaButton.setVisible(false);

    }





    public void showTotale()
    {
        ridisegnaBottoni();
        stampaButton.setVisible(true);
        listaAlimentiAggiunti.getItems().clear();
        List<Dieta> diete = l.dieteDi(p.getNome(), p.getCognome());
        String giorno = String.valueOf(choiseBox.getSelectionModel().getSelectedItem());
        System.out.println(p.getNome());

        FileInputStream input = null;
        try {
            input = new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\User_Icon_32.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        Label nomeLabel = new Label(p.getNome() + " " + p.getCognome(), imageView);
        nomeLabel.setFont(new Font("Arial", 24));
        nomeLabel.setTextFill(Color.web("SEASHELL"));

        listaAlimentiAggiunti.getItems().add(nomeLabel);

            int risColazione=0;
            int risPranzo=0;
            int risCena=0;
            int risSpuntino=0;
            for (Dieta d : diete)
            {
                if (d.getGiorno().equals(giorno)) {

                    input = null;
                    try {
                        input = new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\table-calendar.png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    image = new Image(input);
                    imageView = new ImageView(image);

                    Label labelGiorno = new Label(giorno, imageView);
                    labelGiorno.setFont(new Font("Arial", 20));
                    labelGiorno.setTextFill(Color.web("ANTIQUEWHITE"));
                    listaAlimentiAggiunti.getItems().add(labelGiorno);
                    System.out.println("Giorno :" + giorno);
                    if (d.getAlimentiColazione() != null && d.getAlimentiColazione().size() > 0) {
                        int index=listaAlimentiAggiunti.getItems().size();
                        for (Alimento a : d.getAlimentiColazione()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaAlimentiAggiunti.getItems().add(labelPasto);
                            int calorie= (int) a.getCalorie();
                            String gramm=a.getGrammi();
                            String[] g=gramm.split(" ");
                            int gr=Integer.parseInt(g[0]);
                            double percentuale=(double) gr/100;
                            calorie=(int) ((int)a.getCalorie()*percentuale);
                            risColazione=risColazione+calorie;

                        }
                        Label calorie = new Label("Colazione "+String.valueOf(risColazione)+" calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaAlimentiAggiunti.getItems().add(index,calorie);



                    }
                    if (d.getAlimentiPranzo() != null && d.getAlimentiPranzo().size() > 0) {
                        int index=listaAlimentiAggiunti.getItems().size();
                        for (Alimento a : d.getAlimentiPranzo()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaAlimentiAggiunti.getItems().add(labelPasto);
                            System.out.println(a.getNome() + " " + a.getGrammi());
                            int calorie= (int) a.getCalorie();
                            String gramm=a.getGrammi();
                            String[] g=gramm.split(" ");
                            int gr=Integer.parseInt(g[0]);
                            double percentuale=(double) gr/100;
                            calorie=(int) ((int)a.getCalorie()*percentuale);
                            risPranzo=risPranzo+calorie;
                        }
                        Label calorie = new Label("Pranzo "+String.valueOf(risPranzo)+" calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaAlimentiAggiunti.getItems().add(index,calorie);

                    }
                    if (d.getAlimentiCena() != null && d.getAlimentiCena().size() > 0) {
                        int index=listaAlimentiAggiunti.getItems().size();
                        for (Alimento a : d.getAlimentiCena()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaAlimentiAggiunti.getItems().add(labelPasto);
                            System.out.println(a.getNome() + " " + a.getGrammi());
                            int calorie= (int) a.getCalorie();
                            String gramm=a.getGrammi();
                            String[] g=gramm.split(" ");
                            int gr=Integer.parseInt(g[0]);
                            double percentuale=(double) gr/100;
                            calorie=(int) ((int)a.getCalorie()*percentuale);
                            risCena=risCena+calorie;
                        }
                        Label calorie = new Label("Cena "+String.valueOf(risCena)+" calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaAlimentiAggiunti.getItems().add(index,calorie);

                    }
                    if (d.getAlimentiSpuntino() != null && d.getAlimentiSpuntino().size() > 0) {
                        int index=listaAlimentiAggiunti.getItems().size();
                        for (Alimento a : d.getAlimentiSpuntino()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaAlimentiAggiunti.getItems().add(labelPasto);
                            System.out.println(a.getNome() + " " + a.getGrammi());
                            int calorie= (int) a.getCalorie();
                            String gramm=a.getGrammi();
                            String[] g=gramm.split(" ");
                            int gr=Integer.parseInt(g[0]);
                            double percentuale=(double) gr/100;
                            calorie=(int) ((int)a.getCalorie()*percentuale);
                            risSpuntino=risSpuntino+calorie;
                        }
                        Label calorie = new Label("Spuntino "+String.valueOf(risSpuntino)+" calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaAlimentiAggiunti.getItems().add(index,calorie);
                        if(!d.getNota().equals("")){
                        Label notaTitle=new Label("Nota");
                        notaTitle.setFont(new Font("Arial", 15));
                        notaTitle.setTextFill(Color.web("ANTIQUEWHITE"));
                        listaAlimentiAggiunti.getItems().add(notaTitle);
                        Label nota=new Label(d.getNota());
                        nota.setFont(new Font("Arial", 10));
                        nota.setTextFill(Color.web("BLACK"));
                        listaAlimentiAggiunti.getItems().add(nota);}
                    }
                    buttonPasto.setText("");
                    setAlimentiGiorno(d);
                }
            }


    }

        @FXML
    private void showAggiungiButton()
    {
        aggiungiButton2.setVisible(true);
        cercaAlimenti.setText(listaAlimenti.getSelectionModel().getSelectedItem().getText());
        listaAlimenti.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:darkgrey");
        List<String> choices = new ArrayList<>();
        choices.add("grammo");
        choices.add("chilogrammo");
        choices.add("mililitro");
        choices.add("litro");
        choices.add("cucchiaio");

        TextInputDialog inputDialog=new TextInputDialog("");
        inputDialog.setHeaderText("Quantita da aggiungere");
        inputDialog.showAndWait();
        String text=inputDialog.getResult();
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Scegli", choices);


        dialog.setTitle("Unita di misura");
        dialog.setHeaderText("Scegli l'unita di misura");
        dialog.setContentText("");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(result.get().equals("litro") || result.get().equals("chilogrammo")) {
                grammi = Integer.parseInt(text);
                grammi=grammi*1000;
                unita= result.get();
            }
            if(result.get().equals("cucchiaio"))
            {
                grammi=Integer.parseInt(text)*12;
                unita= result.get();
            }
            if(result.get().equals("grammo") || result.get().equals("mililitro")) {
                grammi = Integer.parseInt(text);
                unita= result.get();
            }

        }

    }
    @FXML
    public void aggiungiAlimento()
    {
       Label label= (listaAlimenti.getSelectionModel().getSelectedItem());
       label.setStyle("-fx-background-color:lightgrey");
       Label quantitaLabel=new Label(grammi+" g");

        int totale= (int) l.dammiCalorieTotali(p);
        label.setMinWidth(300);
        label.setTextAlignment(TextAlignment.CENTER);
        quantitaLabel.setMinWidth(50);
        quantitaLabel.setStyle("-fx-background-color:LIGHTGREY;-fx-text-alignment: center;");

        quantitaLabel.setPrefHeight(label.getHeight());
        quantitaLabel.setTextAlignment(TextAlignment.CENTER);

        listaAlimentiAggiunti.getItems().add(label);
        quantitaView.getItems().add(quantitaLabel);
        salvaButton.setVisible(true);
        fineButton.setVisible(true);
        cercaAlimenti.clear();
        List<Alimento> alimenti=l.getAlimenti();
        for(Alimento a:alimenti)
        {
            if(label.getText().equals(a.getNome()))
            {
                int calorie= (int) a.getCalorie();
                double percentuale=(double) grammi/100;




                calorie=(int) ((int)a.getCalorie()*percentuale);

                proteine=proteine+(int)((int)calorie*((double)a.getProteine()/100));
                carboidrati=carboidrati+(int)((int)calorie*((double)a.getCarboidrati()/100));
                grassi=grassi+(int)((int)calorie*((double)a.getColesterolo()/100));
                System.out.println(calorie);
                System.out.println(a.getProteine()+" "+proteine);
                double risultato=0.0000;
                risultato=(double) calorie/totale;
                System.out.println("calorie"+calorie+" totale"+totale+" calorie/totale"+risultato);
                progressBar.setProgress(progressBar.getProgress()+risultato);
                double risultato2=totale*progressBar.getProgress();


                progressButton.setText((int)risultato2+"/"+totale);
                int ris=Integer.parseInt(buttonPasto.getText());
                System.out.println("buttonPasto :"+ris);
                ris=ris+(int)calorie;
                System.out.println("buttonPasto :"+ris);
                buttonPasto.setText(String.valueOf(ris));





            }
        }






    }
    @FXML
    private void showAlimenti()
    {

        listaAlimenti.getItems().clear();




        List<Alimento> a=new ArrayList<Alimento>();
        a=l.getAlimenti();
        for(Alimento a2:a)
        {
            if(a2.getNome().toLowerCase().contains(cercaAlimenti.getText()) || a2.getNome().contains(cercaAlimenti.getText())) {
                Label label = new Label(a2.getNome());// + " " + String.valueOf(a2.getCalorie()) + " " + String.valueOf(a2.getProteine()) + " " + String.valueOf(a2.getCarboidrati()) + " " + String.valueOf(a2.getColesterolo()) +
                     //   " " + String.valueOf(a2.getFibre()));
                try {
                    label.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\food.png"))));
                    label.setStyle("-fx-font-size: 18;");
                    label.setStyle("-fx-background-color:LIGHTGREY;");
                    label.setMinWidth(280.00);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                listaAlimenti.getItems().add(label);
            }
        }
        alimentiLabel.setVisible(true);
        listaAlimenti.setVisible(true);

    }
    public void tornaMenu()
    {
        Parent fxmlLoader= null;
        try {
            fxmlLoader = FXMLLoader.load(getClass().getResource("primaryScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= (Stage) fineButton.getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader,800,800));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\back_arrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        backButton2.setGraphic(imageView);

        ImageView imageView2 = null;
        try {
            imageView2 = new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\arrow16.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        ImageView imageView3 = null;
        try {
            imageView3 = new ImageView(new Image(new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\arrow16.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };

        buttonPasto.setGraphic(imageView2);
        progressButton.setGraphic(imageView3);
        textFieldNota.setStyle("-fx-control-inner-background:DIMGREY;");
        textAreaNota.setWrapText(true);
        choiseBox.setValue("Giorno");
        choiseBox.getStyleClass().add("center-aligned");
        choiseBox.getStyleClass().add("center-aligned");
        ObservableList<String> options = FXCollections.observableArrayList("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica","Riepilogo");
        choiseBox.setItems(options);
        choiseBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(String.valueOf(choiseBox.getItems().get(number2.intValue())).equals("Riepilogo"))
                {
                    vboxItem.setVisible(true);
                    System.out.println(choiseBox.getItems().get(number2.intValue()));
                    List<Dieta> diete = l.dieteDi(p.getNome(), p.getCognome());
                    String[] giorni = {"Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"};
                    System.out.println(p.getNome());

                    FileInputStream input = null;
                    try {
                        input = new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\User_Icon_32.png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image image = new Image(input);
                    ImageView imageView = new ImageView(image);
                    Label label = new Label(p.getNome()+" "+p.getCognome(),imageView);
                    label.setFont(new Font("Arial", 24));
                    label.setTextFill(Color.web("SEASHELL"));

                    listaAlimentiAggiunti.getItems().add(label);
                    for (String s : giorni) {
                        for (Dieta d : diete) {
                            if (d.getGiorno().equals(s)) {

                                 input = null;
                                try {
                                    input = new FileInputStream("C:\\Users\\Bogdan\\IdeaProjects\\TesiFX\\src\\main\\resources\\tesi\\tesifx\\Images\\table-calendar.png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                 image = new Image(input);
                                 imageView = new ImageView(image);

                                Label labelGiorno = new Label(s,imageView);
                                labelGiorno.setFont(new Font("Arial", 20));
                                labelGiorno.setTextFill(Color.web("ANTIQUEWHITE"));
                                listaAlimentiAggiunti.getItems().add(labelGiorno);
                                System.out.println("Giorno :" + s);
                                if (d.getAlimentiColazione() != null || d.getAlimentiColazione().size()>0) {
                                    Label pasto = new Label("Colazione");
                                    pasto.setFont(new Font("Arial", 15));
                                    pasto.setTextFill(Color.web("WHITE"));
                                    listaAlimentiAggiunti.getItems().add(pasto);
                                    for (Alimento a : d.getAlimentiColazione()) {
                                        Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                                        listaAlimentiAggiunti.getItems().add(labelPasto);
                                        System.out.println(a.getNome() + " " + a.getGrammi());
                                    }
                                }
                                if (d.getAlimentiPranzo() != null || d.getAlimentiPranzo().size()>0) {
                                    Label pasto = new Label("Pranzo");
                                    pasto.setFont(new Font("Arial", 15));
                                    pasto.setTextFill(Color.web("WHITE"));
                                    listaAlimentiAggiunti.getItems().add(pasto);
                                    for (Alimento a : d.getAlimentiPranzo()) {
                                        Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                                        listaAlimentiAggiunti.getItems().add(labelPasto);
                                        System.out.println(a.getNome() + " " + a.getGrammi());
                                    }
                                }
                                if (d.getAlimentiCena() != null || d.getAlimentiCena().size()>0) {
                                    Label pasto = new Label("Cena");
                                    pasto.setFont(new Font("Arial", 15));
                                    pasto.setTextFill(Color.web("WHITE"));
                                    listaAlimentiAggiunti.getItems().add(pasto);
                                    for (Alimento a : d.getAlimentiCena()) {
                                        Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                                        listaAlimentiAggiunti.getItems().add(labelPasto);
                                        System.out.println(a.getNome() + " " + a.getGrammi());
                                    }
                                }
                                if (d.getAlimentiSpuntino() != null || d.getAlimentiSpuntino().size()>0) {
                                    Label pasto = new Label("Spuntino");
                                    pasto.setFont(new Font("Arial", 15));
                                    pasto.setTextFill(Color.web("WHITE"));
                                    listaAlimentiAggiunti.getItems().add(pasto);
                                    for (Alimento a : d.getAlimentiSpuntino()) {
                                        Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                                        listaAlimentiAggiunti.getItems().add(labelPasto);
                                        System.out.println(a.getNome() + " " + a.getGrammi());
                                    }
                                }
                            }
                        }
                    }

                }
            }
        });






                //.setStyle("-fx-background-color:LIGHTGREY");


    }
    public void showInfoSecond()
    {

        int proteineTotali= (int) (l.dammiCalorieTotali(p)*0.30);
        System.out.println(proteineTotali);
        int carboidratiTotali= (int) (l.dammiCalorieTotali(p)*0.45);
        System.out.println(carboidratiTotali);
        int grassiTotali=  (int) (l.dammiCalorieTotali(p)*0.25);
        System.out.println(grassiTotali);
        double setProteine=(double)proteine/proteineTotali;
        System.out.println(setProteine);
        double setCarboidrati=(double)carboidrati/carboidratiTotali;
        double setGrassi=(double)grassi/grassiTotali;
        FXMLLoader   fxmlLoader = new FXMLLoader(getClass().getResource("showInfoSecond.fxml"));
        try {
            Parent root=(Parent) fxmlLoader.load();
            infoSecondSceneController controller = fxmlLoader.getController();

            System.out.println("AAAAAAA"+proteine);
            controller.inizializzaValori(setProteine,setCarboidrati,setGrassi,proteine,carboidrati,grassi,l.dammiCalorieTotali(p));
            Stage stage= new Stage();
            stage.setScene(new Scene(root,350,100));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void inviaText()
    {
        if(!textAreaNota.getText().equals("")) {
            textAreaNota.setText(textAreaNota.getText() + " " + textFieldNota.getText());
        }
        if(textAreaNota.getText().equals(""))
        {
            textAreaNota.setText(textFieldNota.getText());
        }
        textFieldNota.clear();
    }
   /* @FXML
    private TextField searchAlimento;
    @FXML
    private ListView listaAlimenti;
    @FXML
    private Button aggiungiAlimento;
    @FXML
    private ListView areaAlimenti;

    private ObservableList<String> lista;
    private Logic l;
    public void giveAlimenti()
    {   l=new Logic();
        lista= FXCollections.observableArrayList();
        System.out.println("ciao");



        List<Alimento> a=new ArrayList<Alimento>();
        a=l.getAlimenti();
        for(Alimento a2:a)
        {
          lista.add(a2.getNome()+" "+String.valueOf(a2.getCalorie())+" "+String.valueOf(a2.getProteine())+" "+String.valueOf(a2.getCarboidrati())+" "+String.valueOf(a2.getColesterolo())+
          " "+String.valueOf(a2.getFibre()));
        }
        listaAlimenti.setItems(lista);
        listaAlimenti.setVisible(true);
    }
    @FXML
    private void addToDiet()
    {
         areaAlimenti.setItems((ObservableList) listaAlimenti.getSelectionModel().getSelectedItem());
    }

*/
}
