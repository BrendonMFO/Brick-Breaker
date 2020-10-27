package brick;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Cadastro extends AnchorPane {
  @FXML
  private ImageView cadastro, fundo;
  @FXML
  private TextField usuario, senha, confimarSenha, email;
  @FXML
  private Button btnLimpar, btnConfirmar;
  @FXML
  private RadioButton tipo1, tipo2, tipo3;

  public Cadastro() {
    this.carregarFXML();
    this.carregarImagens();
    this.setarPosicao();
    this.animar(1, true);
  }

  private void carregarFXML() {
    try {
      FXMLLoader fXMLLoader = new FXMLLoader(Recursos.class.getResource("FXMLCadastro.fxml"));
      fXMLLoader.setRoot(this);
      fXMLLoader.setController(this);
      fXMLLoader.load();
    } catch (IOException ex) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Não foi possivel iniciar a janela de cadastro", ButtonType.CLOSE);
      alert.showAndWait();
      Platform.exit();
    }
  }

  private void carregarImagens() {
    this.cadastro.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/cadastro.png")));
    this.btnLimpar.setGraphic(new ImageView(new Image(Recursos.class.getResourceAsStream("img/Login/limpar.png"))));
    this.btnLimpar.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
    ((ImageView) this.btnLimpar.getGraphic()).setFitHeight(75);
    ((ImageView) this.btnLimpar.getGraphic()).setFitWidth(210);
    this.btnConfirmar
        .setGraphic(new ImageView(new Image(Recursos.class.getResourceAsStream("img/Login/confirmar.png"))));
    this.btnConfirmar.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
    ((ImageView) this.btnConfirmar.getGraphic()).setFitHeight(75);
    ((ImageView) this.btnConfirmar.getGraphic()).setFitWidth(210);
    this.fundo.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/fundo.jpg")));
    this.tipo1.setGraphic(new ImageView(new Image(Recursos.class.getResourceAsStream("img/Cadastro/U1.png"))));
    this.tipo1.getGraphic().setTranslateX(this.tipo1.getGraphic().getTranslateX() - 150);
    this.tipo1.getGraphic().setTranslateY(this.tipo1.getGraphic().getTranslateY() - 43);
    this.tipo2.setGraphic(new ImageView(new Image(Recursos.class.getResourceAsStream("img/Cadastro/U2.png"))));
    this.tipo2.getGraphic().setTranslateX(this.tipo2.getGraphic().getTranslateX() - 150);
    this.tipo2.getGraphic().setTranslateY(this.tipo2.getGraphic().getTranslateY() - 43);
    this.tipo3.setGraphic(new ImageView(new Image(Recursos.class.getResourceAsStream("img/Cadastro/U3.png"))));
    this.tipo3.getGraphic().setTranslateX(this.tipo3.getGraphic().getTranslateX() - 150);
    this.tipo3.getGraphic().setTranslateY(this.tipo3.getGraphic().getTranslateY() - 43);
    this.setScaleY(0);
  }

  private void setarPosicao() {
    this.setTranslateX(200);
    this.setTranslateY(100);
  }

  private void animar(double _valor, boolean _listener) {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarLogo1 = new KeyValue(this.scaleYProperty(), _valor, Interpolator.EASE_BOTH);
    final KeyFrame animacao = new KeyFrame(Duration.seconds(.2), animarLogo1);
    timeline.getKeyFrames().add(animacao);
    if (_listener) {
      timeline.setOnFinished(lambda -> this.listenerTeclado());
    } else {
      timeline.setOnFinished(lambda -> this.voltar());
    }
    timeline.play();
  }

  private void voltar() {
    this.getScene().setOnKeyPressed(null);
  }

  private void listenerTeclado() {
    this.btnLimpar.setOnAction(lambda -> {
      this.usuario.setText("");
      this.senha.setText("");
      this.confimarSenha.setText("");
      this.email.setText("");
    });
    this.btnConfirmar.setOnAction(lambda -> {
      this.getScene().setOnKeyPressed(null);
      this.animar(0, false);
    });
    this.getScene().setOnKeyPressed((KeyEvent tecla) -> {
      switch (tecla.getCode()) {
        case ENTER:
          this.getScene().setOnKeyPressed(null);
          this.animar(0, false);
          break;
        case ESCAPE:
          this.animar(0, false);
          break;
        default:
          break;
      }
    });
  }

}
