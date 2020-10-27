package brick;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Resultado extends AnchorPane {

  @FXML
  private ImageView fundo, textoResultado;
  @FXML
  private Text pontuacao;

  private Rectangle fundoRetangulo;

  public enum TipoResultado {
    PERDEU, VENCEU
  };

  public Resultado(TipoResultado _resultado, Integer _levelID, String _pontuacao, Integer _pontos) {
    this.setRetangulo();
    this.carregarFXML();
    this.setImagens(_resultado);
    this.pontuacao.setOpacity(0);
    this.pontuacao.setText(_pontuacao);
    this.setTranslateX(219);
    this.setTranslateY(192);
    this.animarImagens();
  }

  private void setImagens(TipoResultado _resultado) {
    this.fundo.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/fundo.jpg")));
    if (_resultado == TipoResultado.VENCEU) {
      this.textoResultado.setImage(new Image(Recursos.class.getResourceAsStream("img/Resultado/vitoria.png")));
    } else {
      this.textoResultado.setImage(new Image(Recursos.class.getResourceAsStream("img/Resultado/derrota.png")));
    }
    this.textoResultado.setFitWidth(571);
    this.textoResultado.setFitHeight(84);
    this.fundo.setOpacity(0);
    this.textoResultado.setOpacity(0);
  }

  private void setRetangulo() {
    this.fundoRetangulo = new Rectangle(1500, 1500);
    this.fundoRetangulo.setOpacity(0);
    this.fundoRetangulo.setTranslateX(-500);
    this.fundoRetangulo.setTranslateY(-800);
    this.getChildren().add(this.fundoRetangulo);
  }

  private void carregarFXML() {
    FXMLLoader fXMLLoader = new FXMLLoader(Recursos.class.getResource("FXMLResultado.fxml"));
    fXMLLoader.setRoot(this);
    fXMLLoader.setController(this);
    try {
      fXMLLoader.load();
    } catch (IOException ex) {
    }
  }

  private void animarImagens() {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarFrase = new KeyValue(this.textoResultado.opacityProperty(), 1, Interpolator.EASE_BOTH);
    final KeyValue animarFundo = new KeyValue(this.fundo.opacityProperty(), 1, Interpolator.EASE_BOTH);
    final KeyValue animarRetangulo = new KeyValue(this.fundoRetangulo.opacityProperty(), .6, Interpolator.EASE_BOTH);
    final KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), animarFrase, animarFundo, animarRetangulo);
    timeline.getKeyFrames().add(keyFrame);
    timeline.setOnFinished(lambda -> {
      this.segundaAnimacao();
    });
    timeline.play();
  }

  private void segundaAnimacao() {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarFrase = new KeyValue(this.textoResultado.translateYProperty(), -120, Interpolator.EASE_BOTH);
    final KeyValue animarPontuacao = new KeyValue(this.pontuacao.opacityProperty(), 1, Interpolator.EASE_BOTH);
    final KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), animarFrase, animarPontuacao);
    timeline.getKeyFrames().add(keyFrame);
    timeline.setOnFinished(lambda -> {
      this.adicionarListenerTeclado();
    });
    timeline.play();
  }

  private void adicionarListenerTeclado() {
    this.getScene().setOnKeyPressed((KeyEvent tecla) -> {
      switch (tecla.getCode()) {
        case ENTER:
        case ESCAPE:
          Menu menu = new Menu();
          this.getScene().setRoot(menu); {
          menu.voltarAoMenu();
        }
        break;
        default:
          break;
      }
    });
  }
}
