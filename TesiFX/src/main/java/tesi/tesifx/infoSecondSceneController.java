package tesi.tesifx;

import com.itextpdf.text.api.Indentable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class infoSecondSceneController implements Initializable {
    double proteine;
    double carboidrati;
    double grassi;
    @FXML
    private Label proteineLabel;
    @FXML
    private Label carboidratiLabel;
    @FXML
    private Label grassiLabel;
    @FXML
    private ProgressBar progressProteine;
    @FXML
    private ProgressBar progressCarboidrati;
    @FXML
    private ProgressBar progressGrassi;
    public void inizializzaValori(double proteine2,double carboidrati,double grassi,double p,double c,double g,double t)
{
    this.proteine=proteine2;
    this.carboidrati=carboidrati;
    this.grassi=grassi;
    progressProteine.setProgress(proteine);
    progressCarboidrati.setProgress(carboidrati);
    progressGrassi.setProgress(grassi);
    proteineLabel.setText(" Proteine "+(int) p+"/"+(int)(t*0.30));
    carboidratiLabel.setText(" Carboidrati "+(int) c+"/"+(int)(t*0.45));
    grassiLabel.setText(" Grassi "+(int)g+"/"+(int)(t*0.25));
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }
}
