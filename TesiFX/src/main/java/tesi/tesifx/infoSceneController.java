package tesi.tesifx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;
public class infoSceneController {
    private Persona p;
    @FXML
    private ListView listaInfo;
    @FXML
    private Label nomeLabel;
    private Logic l = Logic.getInstance();
    @FXML
    private LineChart<String, Number> chart;
    @FXML
    private LineChart<String, Number> chartPeso;

    @FXML


    public void addPersona(Persona p) {
        this.p = p;
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
        nomeLabel = new Label(p.getNome() + " " + p.getCognome(), imageView);
        nomeLabel.setFont(new Font("Arial", 24));
        nomeLabel.setTextFill(Color.web("SEASHELL"));

        listaInfo.getItems().add(nomeLabel);
        for (String s : giorni) {
            int risColazione = 0;
            int risPranzo = 0;
            int risCena = 0;
            int risSpuntino = 0;
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

                    Label labelGiorno = new Label(s, imageView);
                    labelGiorno.setFont(new Font("Arial", 20));
                    labelGiorno.setTextFill(Color.web("ANTIQUEWHITE"));
                    listaInfo.getItems().add(labelGiorno);
                    System.out.println("Giorno :" + s);
                    if (d.getAlimentiColazione() != null && d.getAlimentiColazione().size() > 0) {
                        int index = listaInfo.getItems().size();
                        for (Alimento a : d.getAlimentiColazione()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaInfo.getItems().add(labelPasto);
                            int calorie = (int) a.getCalorie();
                            String gramm = a.getGrammi();
                            String[] g = gramm.split(" ");
                            int gr = Integer.parseInt(g[0]);
                            double percentuale = (double) gr / 100;
                            calorie = (int) ((int) a.getCalorie() * percentuale);
                            risColazione = risColazione + calorie;

                        }
                        Label calorie = new Label("Colazione " + String.valueOf(risColazione) + " calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaInfo.getItems().add(index, calorie);

                    }
                    if (d.getAlimentiPranzo() != null && d.getAlimentiPranzo().size() > 0) {
                        int index = listaInfo.getItems().size();
                        for (Alimento a : d.getAlimentiPranzo()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaInfo.getItems().add(labelPasto);
                            System.out.println(a.getNome() + " " + a.getGrammi());
                            int calorie = (int) a.getCalorie();
                            String gramm = a.getGrammi();
                            String[] g = gramm.split(" ");
                            int gr = Integer.parseInt(g[0]);
                            double percentuale = (double) gr / 100;
                            calorie = (int) ((int) a.getCalorie() * percentuale);
                            risPranzo = risPranzo + calorie;
                        }
                        Label calorie = new Label("Pranzo " + String.valueOf(risPranzo) + " calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaInfo.getItems().add(index, calorie);
                    }
                    if (d.getAlimentiCena() != null && d.getAlimentiCena().size() > 0) {
                        int index = listaInfo.getItems().size();
                        for (Alimento a : d.getAlimentiCena()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaInfo.getItems().add(labelPasto);
                            System.out.println(a.getNome() + " " + a.getGrammi());
                            int calorie = (int) a.getCalorie();
                            String gramm = a.getGrammi();
                            String[] g = gramm.split(" ");
                            int gr = Integer.parseInt(g[0]);
                            double percentuale = (double) gr / 100;
                            calorie = (int) ((int) a.getCalorie() * percentuale);
                            risCena = risCena + calorie;
                        }
                        Label calorie = new Label("Cena " + String.valueOf(risCena) + " calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaInfo.getItems().add(index, calorie);
                    }
                    if (d.getAlimentiSpuntino() != null && d.getAlimentiSpuntino().size() > 0) {
                        int index = listaInfo.getItems().size();
                        for (Alimento a : d.getAlimentiSpuntino()) {
                            Label labelPasto = new Label(a.getNome() + " " + a.getGrammi());
                            listaInfo.getItems().add(labelPasto);
                            System.out.println(a.getNome() + " " + a.getGrammi());
                            int calorie = (int) a.getCalorie();
                            String gramm = a.getGrammi();
                            String[] g = gramm.split(" ");
                            int gr = Integer.parseInt(g[0]);
                            double percentuale = (double) gr / 100;
                            calorie = (int) ((int) a.getCalorie() * percentuale);
                            risSpuntino = risSpuntino + calorie;
                        }
                        Label calorie = new Label("Spuntino " + String.valueOf(risSpuntino) + " calorie");
                        calorie.setFont(new Font("Arial", 15));
                        calorie.setTextFill(Color.web("WHITE"));
                        listaInfo.getItems().add(index, calorie);
                        if(!d.getNota().equals("")){
                        Label notaTitle = new Label("Nota");
                        notaTitle.setFont(new Font("Arial", 15));
                        notaTitle.setTextFill(Color.web("ANTIQUEWHITE"));
                        listaInfo.getItems().add(notaTitle);
                        Label nota = new Label(d.getNota());
                        nota.setFont(new Font("Arial", 10));
                        nota.setTextFill(Color.web("BLACK"));
                        listaInfo.getItems().add(nota);}


                    }
                }
            }
        }



        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();

        series1.setName("Peso");
        series2.setName("Calorie");


        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url);
            String sql = "Select * From Itinerario WHERE nome='" +p.getNome()+"' AND cognome='" + p.getCognome() + "'";;


            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String data=rs.getString("data");

                String[] dataSplitted=data.split(" ");
                StringBuilder builder= new StringBuilder(dataSplitted[1] + " ");
                builder.append(dataSplitted[2]+" ");
                builder.append(dataSplitted[5]);



                series1.getData().add(new XYChart.Data<String, Number>(builder.toString(), rs.getInt("peso")));
                 series2.getData().add(new XYChart.Data<String, Number>(builder.toString(), rs.getInt("calorie")));
            }





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chart.getData().addAll(series1);
        chartPeso.getData().addAll(series2);


    }
}


