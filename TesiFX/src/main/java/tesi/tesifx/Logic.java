package tesi.tesifx;
import javafx.scene.control.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Logic {

    private static Logic instance = null;


    public static Logic getInstance() {

        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }
    List<Dieta> diete;
    public Logic() {
        diete = new ArrayList<Dieta>();
    }

public void inizializza() {
    diete = new ArrayList<Dieta>();
    List<Persona> persone = new ArrayList<Persona>();
    String url = "jdbc:sqlite:database.db";
    Connection conn = null;
    try {

        conn = DriverManager.getConnection(url);
        String sql = "Select * From Persona";


        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // loop through the result set
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Persona p2 = new Persona(rs.getString("nome"), rs.getString("cognome"), rs.getInt("altezza")
                    , rs.getInt("peso"), rs.getInt("eta"), rs.getString("genere"), rs.getString("allenamento"), rs.getString("obbiettivo"));
            p2.setData(rs.getString("data"));
            p2.setID(rs.getInt("id"));

            persone.add(p2);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    try {
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    inizializzaDieta(persone,"Lunedi");
    inizializzaDieta(persone,"Martedi");
    inizializzaDieta(persone,"Mercoledi");
    inizializzaDieta(persone,"Giovedi");
    inizializzaDieta(persone,"Venerdi");
    inizializzaDieta(persone,"Sabato");
    inizializzaDieta(persone,"Domenica");
    for(Dieta d:diete)
    {
        System.out.println(d);
    }
}
public void inizializzaDieta(List<Persona> persone,String giorno) {
    String url = "jdbc:sqlite:database.db";
    Connection conn = null;


    try {
        conn = DriverManager.getConnection(url);
        for (Persona p : persone) {
            List<Alimento> alimentiColazione=new ArrayList<Alimento>();
            List<Alimento> alimentiPranzo=new ArrayList<Alimento>();
            List<Alimento> alimentiCena=new ArrayList<Alimento>();
            List<Alimento> alimentiSpuntino=new ArrayList<Alimento>();
            String sql = "Select * From Dieta Where IDPersona='"+p.getID()+"' AND Giorno='"+giorno+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String nota="";
            while (true)

            {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                String alimento=rs.getString("alimento");
                String grammi=rs.getString("quantita");
                if(rs.getString("pasto").equals("Colazione")) {

                    alimentiColazione.add(dammiAlimento(alimento));
                    alimentiColazione.get(alimentiColazione.size()-1).setGrammi(grammi);

                }
                if(rs.getString("pasto").equals("Pranzo")) {
                    alimentiPranzo.add(dammiAlimento(alimento));
                    alimentiPranzo.get(alimentiPranzo.size()-1).setGrammi(grammi);

                }
                if(rs.getString("pasto").equals("Cena")) {
                    alimentiCena.add(dammiAlimento(alimento));
                    alimentiCena.get(alimentiCena.size()-1).setGrammi(grammi);
                }
                if(rs.getString("pasto").equals("Spuntino")) {
                    alimentiSpuntino.add(dammiAlimento(alimento));
                    alimentiSpuntino.get(alimentiSpuntino.size()-1).setGrammi(grammi);
                }
                if(nota.equals(""))
                {
                    nota=rs.getString("nota");
                }


            }

           //qui c'e il problema

            if(alimentiColazione.size()>0 || alimentiPranzo.size()>0 || alimentiCena.size()>0 || alimentiSpuntino.size()>0) {


                Dieta dieta = new Dieta(giorno, p, alimentiColazione, alimentiPranzo, alimentiCena, alimentiSpuntino);
                dieta.setNota(nota);
                System.out.println(dieta.getPersona()+" "+dieta.getGiorno()+" "+dieta.getNota());

                diete.add(dieta);


                }



        }


    } catch (SQLException e) {
        e.printStackTrace();
    }
    try {
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

}
public Alimento dammiAlimento(String alimento) {
    String url = "jdbc:sqlite:database.db";
    Connection conn = null;


    try {
        conn = DriverManager.getConnection(url);

            String sql = "Select * From Alimenti Where alimenti='" + alimento + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                Alimento a2=new Alimento(rs.getString("alimenti"),rs.getDouble("calorie"),rs.getDouble("proteine")
                        ,rs.getDouble("carboidrati"), rs.getDouble("fibra"),rs.getDouble("colesterolo"),rs.getDouble("b1"),rs.getDouble("b2"),rs.getDouble("b6"),rs.getDouble("c"),rs.getDouble("d"),rs.getDouble("e"));
                conn.close();
                return a2;
            }




    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public void rimuoviAlimento(String alimento,String giorno , String pasto , int id)
{
    String url = "jdbc:sqlite:database.db";
    Connection conn = null;


    try {
        conn = DriverManager.getConnection(url);

        Statement stmt = conn.createStatement();


        String sql = "DELETE FROM Dieta WHERE IDPersona ='"+id+"' AND Giorno='"+giorno+"' AND Pasto='"+pasto+"' AND Alimento='"+alimento+"'";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();


    } catch (SQLException e) {
        e.printStackTrace();
    }
    try {
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}







    public List<Dieta> getDiete()
    {
        return diete;
    }
    public void inserisciDieta(Persona p) {

        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        String personaNome = p.getNome();
        String personaCognome = p.getCognome();


            try {
                conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                String sql2 = "Select ID From Persona WHERE nome='" + personaNome + "' AND cognome='" + personaCognome + "'";

                ResultSet rs = stmt.executeQuery(sql2);

                String sql = "INSERT INTO Dieta (IDPersona) VALUES (?)";
                PreparedStatement pstmt = null;

                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, rs.getInt("ID"));


                pstmt.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public void aggiungiDieta(List<Alimento> a, String giorno, String pasto) {
        diete.get(diete.size()).setGiorno(giorno);
        if (pasto == "Colazione") {
            diete.get(diete.size()).setAlimentiColazione(a);
        }
        if (pasto == "Pranzo") {
            diete.get(diete.size()).setAlimentiPranzo(a);
        }
        if (pasto == "Cena") {
            diete.get(diete.size()).setAlimentiCena(a);
        }
        if (pasto == "Spuntino") {
            diete.get(diete.size()).setAlimentiSpuntino(a);
        }
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;


        try {
            conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();
            String sql2 = "Select ID From Persona WHERE nome='" + diete.get(diete.size() - 1).getPersona().getNome() + "' AND cognome='" + diete.get(diete.size() - 1).getPersona().getCognome() + "'";

            ResultSet rs = stmt.executeQuery(sql2);


            for (Alimento a2 : a) {
                String sql = "INSERT INTO Dieta(Alimento,Pasto,Giorno,IDPersona) VALUES(?,?,?,?)";

                PreparedStatement pstmt = null;
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, a2.getNome());
                pstmt.setString(2, pasto);
                pstmt.setString(3, String.valueOf(giorno));
                pstmt.setInt(4, rs.getInt("IDPersona"));


                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public double dammiCalorieTotali(Persona p)
    {

        double mb = 0;
        double laf = 0;
        System.out.println(p.getGenere()+" "+p.getAltezza());
        if (p.getGenere().equals("Maschio")) {
            mb = 447.593 + (9.247 * p.getPeso()) + (3.098 * p.getAltezza()) - (4.33 * p.getEta());
        }
        if (p.getGenere().equals("Femmina")) {

            mb = 88.362 + (13.397 * p.getPeso()) + (4.799 * p.getAltezza()) - (5.677 * p.getEta());
        }
        if (p.getAllenamento() .equals( "Nessuno")) {
            laf = 1.45;
        }
        if (p.getAllenamento() .equals( "Leggero")) {
            laf = 1.60;
        }
        if (p.getAllenamento() .equals("Moderato")) {
            laf = 1.75;
        }
        if (p.getAllenamento() .equals( "Intenso")) {
            laf = 2.10;
        }

        if (p.getObbiettivo() .equals("Dimagrimento")) {
            System.out.println(mb*laf-400);
            return (mb * laf) - 400;
        }

        if (p.getObbiettivo() .equals( "Aumento massa")) {
            System.out.println(mb*laf+400);
            return (mb * laf) + 400;
        }

        System.out.println(mb*laf);
        return mb * laf;
    }


    public Dieta dietaDi(String s,String n)
    {System.out.println(s+" "+n);
        System.out.println(diete.size());
        for(Dieta d:diete)
        {   System.out.println(d.getPersona().getNome()+" "+d.getPersona().getCognome());
            if(d.getPersona().getNome().equals(s) && d.getPersona().getCognome().equals(n))
                return d;
        }
        return null;
    }
    public void eliminaDieta(String s,String n)
    {
        System.out.println(" diete.size :"+diete.size());
        List<Dieta> dieteErase=new ArrayList<Dieta>();
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(s) && d.getPersona().getCognome().equals(n))
                dieteErase.add(d);
        }
        System.out.println("DieteErase:"+dieteErase.size());
        diete.removeAll(dieteErase);
        System.out.println("DieteErase:"+dieteErase.size());
        System.out.println(" diete.size :"+diete.size());

    }
    public List<Dieta> dieteDi(String s,String n)
    {   List<Dieta> diete2=new ArrayList<Dieta>();
        System.out.println(" diete.size :"+diete.size());
        for(Dieta d:diete)
        {
            if(d.getPersona().getNome().equals(s) && d.getPersona().getCognome().equals(n))
                diete2.add(d);
        }
        return diete2;
    }
    public boolean essistePersona(String nome,String cognome)
    {
        for(Persona p:getPersone())
        {
            if(p.getNome().equals(nome) && p.getCognome().equals(cognome))
            {
                return true;
            }
        }
        return  false;
    }
    public void aggiornaPersona(Persona p)
    {
        System.out.println("All'inizio"+p.getAllenamento());
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Itinerario(nome,cognome,peso,calorie,data,allenamento,obiettivo) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setInt(3, p.getPeso());
            pstmt.setInt(4, (int)dammiCalorieTotali(p));
            pstmt.setString(5, p.getData());



            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
System.out.println("Persona id:"+p.getID());
        try {
            conn = DriverManager.getConnection(url);
            String sql="UPDATE Persona SET eta = ? , " + "allenamento = ? ," + "obbiettivo = ? ,"+ "peso = ? " +"WHERE id = ?";

            //String sql = "INSERT INTO Persona(nome,cognome,altezza,peso,eta,genere,data,allenamento,obbiettivo) VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, p.getEta());
            pstmt.setString(2, p.getAllenamento());
            pstmt.setString(3, p.getObbiettivo());
            pstmt.setInt(4, p.getPeso());
            pstmt.setInt(5, p.getID());
            System.out.println("Siamo nell'update"+p.getAllenamento());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    public void inserisciPersona(Persona p)
    {


        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Persona(nome,cognome,altezza,peso,eta,genere,data,allenamento,obbiettivo) VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setInt(3, p.getAltezza());
            pstmt.setInt(4, p.getPeso());
            pstmt.setInt(5, p.getEta());
            pstmt.setString(6, p.getGenere());
            pstmt.setString(7, p.getData());
            pstmt.setString(8, p.getAllenamento());
            pstmt.setString(9, p.getObbiettivo());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Itinerario(nome,cognome,peso,calorie,data) VALUES(?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setInt(3, p.getPeso());
            pstmt.setInt(4, (int)dammiCalorieTotali(p));
            pstmt.setString(5, p.getData());


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }





    }
    public List<Persona> getPersone()
    {
        List<Persona> p=new ArrayList<Persona>();
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {

                conn = DriverManager.getConnection(url);
            String sql = "Select * From Persona";


            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

                // loop through the result set
                while (true) {
                    try {
                        if (!rs.next()) break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Persona p2=new Persona(rs.getString("nome"),rs.getString("cognome"),rs.getInt("altezza")
                            ,rs.getInt("peso"), rs.getInt("eta"),rs.getString("genere"),rs.getString("allenamento"),rs.getString("obbiettivo"));
                    p2.setData(rs.getString("data"));
                    p2.setID(rs.getInt("id"));
                    p.add(p2);

                }





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
    public void rimuoviPersona(String s,String n)
    {
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;


        try {
            conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();
            String sql2 = "Select ID From Persona WHERE nome='" +s+"' AND cognome='" + n + "'";

            ResultSet rs = stmt.executeQuery(sql2);
            int id=rs.getInt("ID");

            String sql = "DELETE FROM Persona WHERE ID ='"+id+"'";
            String sq3 = "DELETE FROM Dieta WHERE IDPersona ='"+id+"'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt=conn.prepareStatement(sq3);
            pstmt.executeUpdate();
            for(Dieta d:diete)
            {
                if(d.getPersona().getNome().equals(s) && d.getPersona().getCognome().equals(n))
                {
                    diete.remove(d);
                }
            }
            for(Dieta d:diete)
            {
                System.out.println(d.getPersona().getNome());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Alimento> getAlimenti()
    {
        List<Alimento> a=new ArrayList<Alimento>();
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url);
            String sql = "Select * From Alimenti";


            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Alimento a2=new Alimento(rs.getString("alimenti"),rs.getDouble("calorie"),rs.getDouble("proteine")
                        ,rs.getDouble("carboidrati"), rs.getDouble("fibra"),rs.getDouble("colesterolo"),rs.getDouble("b1"),rs.getDouble("b2"),rs.getDouble("b6"),rs.getDouble("c"),rs.getDouble("d"),rs.getDouble("e"));

                a.add(a2);

            }





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }
}
