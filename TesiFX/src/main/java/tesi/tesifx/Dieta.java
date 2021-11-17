package tesi.tesifx;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dieta {
    private String giorno;
    private String nota;
    private Persona persona;
    private List<Alimento> alimentiColazione;
    private List<Alimento> alimentiPranzo;
    private List<Alimento> alimentiCena;
    private List<Alimento> alimentiSpuntino;

    @Override
    public String toString() {
        return "Dieta{" +
                "giorno='" + giorno + '\'' +
                ", persona=" + persona +
                ", alimentiColazione=" + alimentiColazione +
                ", alimentiPranzo=" + alimentiPranzo +
                ", alimentiCena=" + alimentiCena +
                ", alimentiSpuntino=" + alimentiSpuntino +
                '}';
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Dieta()
    {
        alimentiColazione=new ArrayList<Alimento>();
        alimentiPranzo=new ArrayList<Alimento>();
        alimentiCena=new ArrayList<Alimento>();
        alimentiSpuntino=new ArrayList<Alimento>();
    }

    public Dieta(String giorno, Persona persona, List<Alimento> alimentiColazione, List<Alimento> alimentiPranzo, List<Alimento> alimentiCena, List<Alimento> alimentiSpuntino) {
        this.giorno = giorno;
        this.persona = persona;
        this.alimentiColazione = alimentiColazione;
        this.alimentiPranzo = alimentiPranzo;
        this.alimentiCena = alimentiCena;
        this.alimentiSpuntino = alimentiSpuntino;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Alimento> getAlimentiColazione() {
        return alimentiColazione;
    }
    public List<Alimento> getTuttiAlimenti() {

        List<Alimento> alimenti=new ArrayList<Alimento>();
        for(Alimento a:alimentiColazione)
        {
            alimenti.add(a);
        }
        for(Alimento a:alimentiPranzo)
        {
            alimenti.add(a);
        }
        for(Alimento a:alimentiCena)
        {
            alimenti.add(a);
        }
        for(Alimento a:alimentiSpuntino)
        {
            alimenti.add(a);
        }
        return alimenti;

    }

    public void setAlimentiColazione(List<Alimento> alimentiColazione) {
        this.alimentiColazione = alimentiColazione;
    }

    public List<Alimento> getAlimentiPranzo() {
        return alimentiPranzo;
    }

    public void setAlimentiPranzo(List<Alimento> alimentiPranzo) {
        this.alimentiPranzo = alimentiPranzo;
    }

    public List<Alimento> getAlimentiCena() {
        return alimentiCena;
    }

    public void setAlimentiCena(List<Alimento> alimentiCena) {
        this.alimentiCena = alimentiCena;
    }

    public List<Alimento> getAlimentiSpuntino() {
        return alimentiSpuntino;
    }

    public void setAlimentiSpuntino(List<Alimento> alimentiSpuntino) {
        this.alimentiSpuntino = alimentiSpuntino;
    }
}
