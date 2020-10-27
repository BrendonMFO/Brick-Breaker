package brick;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.animation.KeyValue;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.animation.Interpolator;
import javafx.scene.control.TextField;
import javafx.beans.property.StringProperty;
import static brick.Recursos.QUANTIDADE_LEVEL;

public class Criador extends SubMenus {
  @FXML
  private TextField linha, linha1, linha2, linha3, linha4, linha5, linha6, linha7, linha8, linha9, linha10, linha11;

  public Criador() {
    this.carregarFXML();
    this.carregarImagens();
    this.iniciarLinhas();
    this.animar(true, 1, 1, .6, new KeyValue(this.linha.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha1.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha2.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha3.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha4.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha5.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha6.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha7.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha8.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha9.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha10.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.linha11.opacityProperty(), 1, Interpolator.EASE_BOTH));
  }

  @Override
  protected final void carregarImagens() {
    this.setFundo(1500, 1500, Color.WHITE);
    this.setFrameImage("img/frameGerador.png");
    this.getChildren().add(this.frame);
  }

  @Override
  protected void adicionarListenerTeclado() {
    this.getScene().setOnKeyPressed((KeyEvent tecla) -> {
      this.keyEvent(tecla.getCode());
    });
  }

  private void carregarFXML() {
    try {
      FXMLLoader fXMLLoader = new FXMLLoader(Recursos.class.getResource("FXMLConstrutor.fxml"));
      fXMLLoader.setRoot(this);
      fXMLLoader.setController(this);
      fXMLLoader.load();
    } catch (IOException ex) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro ao carregar FXML");
      alert.setHeaderText("Não foi possivel carregar o FXML do menu de seleção");
      alert.setContentText(ex.getLocalizedMessage());
      alert.showAndWait();
      Platform.exit();
    }
  }

  private void iniciarLinhas() {
    this.getChildren().forEach(node -> {
      if (node instanceof TextField) {
        ((TextField) node).setOpacity(0);
        ((TextField) node).textProperty().addListener(text -> {
          StringProperty textfield = (StringProperty) text;
          if (textfield.getValue().length() > 17) {
            ((TextField) node).setText(textfield.getValue().substring(0, 17));
          }
        });
      }
    });
  }

  private void keyEvent(KeyCode _code) {
    switch (_code) {
      case ENTER:
        this.gerarLevel();
        break;
      case ESCAPE:
        this.encerrarListeners();
        this.animar(false, 2, 2, 0, new KeyValue(this.linha.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha1.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha2.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha3.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha4.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha5.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha6.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha7.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha8.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha9.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha10.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.linha11.opacityProperty(), 0, Interpolator.EASE_BOTH));
        break;
      default:
        break;
    }
  }

  private void encerrarListeners() {
    this.removerListener();
  }

  private String gerarStringPainel() {
    String retorno = "3\n";
    retorno = this.getChildren().stream().filter((node) -> (node instanceof TextField))
        .map((node) -> ((TextField) node).getText() + "\n").reduce(retorno, String::concat);
    return retorno;
  }

  private void gerarLevel() {
    String stringPainel = gerarStringPainel();
    Painel p = new Painel(stringPainel);
    Level.adicionarLevel(stringPainel, p.getImage(), Integer.parseInt(stringPainel.substring(0, 1)));
    QUANTIDADE_LEVEL++;
  }
}
