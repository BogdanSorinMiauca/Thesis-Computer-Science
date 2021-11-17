package tesi.tesifx;
import java.util.Date;
public class Persona {
    private String nome;
    private String cognome;
    private int eta;
    private int altezza;
    private int peso;
    private String genere;
    private String allenamento;
    private String obbiettivo;
    private String corporatura;
    private String dataString;
    private int ID;
    private Date data;
    private String nota;
    public Persona(String nome, String cognome,  int altezza, int peso,int eta, String genere,String allenamento,String obbiettivo) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.altezza = altezza;
        this.peso = peso;
        this.genere = genere;
        this.allenamento=allenamento;
        this.obbiettivo=obbiettivo;





    }
    public void setID(int id)
    {
        this.ID=id;
    }
    public int getID()
    {
        return ID;
    }
    public String getAllenamento() {
        return allenamento;
    }

    public void setAllenamento(String allenamento) {
        this.allenamento = allenamento;
    }

    public String getObbiettivo() {
        return obbiettivo;
    }

    public void setObbiettivo(String obbiettivo) {
        this.obbiettivo = obbiettivo;
    }

    public void setData(String data)
    {
        this.dataString=data;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getCorporatura() {
        return corporatura;
    }
    public String getData() {
        return dataString;
    }
    public void setCorporatura(String corporatura) {
        this.corporatura = corporatura;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", eta=" + eta +
                ", altezza=" + altezza +
                ", peso=" + peso +
                ", genere='" + genere + '\'' +
                ", data='" + dataString + '\'' +
                '}';
    }
}
