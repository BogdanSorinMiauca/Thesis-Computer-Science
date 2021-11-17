package tesi.tesifx;

public class Alimento {
    private String giorno;
    private String nome;
    private double calorie;
    private double proteine;
    private double carboidrati;
    private double fibre;
    private double colesterolo;
    private double b1;
    private double b2;
    private double b6;
    private double c;
    private double d;
    private double e;
    private String grammi;



    public String getGrammi() {
        return grammi;
    }

    public void setGrammi(String grammi) {
        this.grammi = grammi;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public Alimento(String nome, double calorie, double proteine, double carboidrati, double fibre, double colesterolo, double b1, double b2, double b6, double c, double d, double e) {
        this.nome = nome;
        this.calorie = calorie;
        this.proteine = proteine;
        this.carboidrati = carboidrati;
        this.fibre = fibre;
        this.colesterolo = colesterolo;
        this.b1 = b1;
        this.b2 = b2;
        this.b6 = b6;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public Alimento() {

    }

    public String getNome() {
        return nome;
    }

    public double getCalorie() {
        return calorie;
    }

    public double getProteine() {
        return proteine;
    }

    public double getCarboidrati() {
        return carboidrati;
    }

    public double getFibre() {
        return fibre;
    }

    public double getColesterolo() {
        return colesterolo;
    }

    public double getB1() {
        return b1;
    }

    public double getB2() {
        return b2;
    }

    public double getB6() {
        return b6;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public double getE() {
        return e;
    }
    public double CaloriePerGrammo(int grammi){
        float quantita=(float)grammi/100;
        return calorie*quantita;}
    public double ProteinePerGrammo(int grammi)
    {
        float quantita=(float)grammi/100;
        return proteine*quantita;
    }
    public double CarboidratiPerGrammo(int grammi)
    {
        float quantita=(float)grammi/100;
        return carboidrati*quantita;
    }
    public double FibrePerGrammo(int grammi)
    {
        float quantita=(float)grammi/100;
        return fibre*quantita;
    }
    public double ColesteroloPerGrammo(int grammi)
    {
        float quantita=(float)grammi/100;
        return colesterolo*quantita;
    }
    public double B1PerGrammo(int grammi)
    {
        return b1*(grammi/100);
    }
    public double B2PerGrammo(int grammi)
    {
        return b2*(grammi/100);
    }
    public double B6PerGrammo(int grammi)
    {
        return b6*(grammi/100);
    }
    public double CPerGrammo(int grammi)
    {
        return c*(grammi/100);
    }
    public double DPerGrammo(int grammi)
    {
        return d*(grammi/100);
    }
    public double EPerGrammo(int grammi)
    {
        return e*(grammi/100);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "alimento{" +
                "nome='" + nome + '\'' +
                ", calorie=" + calorie +
                ", proteine=" + proteine +
                ", carboidrati=" + carboidrati +
                ", fibre=" + fibre +
                ", colesterolo=" + colesterolo +
                ", b1=" + b1 +
                ", b2=" + b2 +
                ", b6=" + b6 +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                '}';
    }
}
