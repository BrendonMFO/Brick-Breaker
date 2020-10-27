package brick;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Login extends AnchorPane {
  @FXML
  ImageView fundo, btnLimpar, btnConfirmar, login;
  @FXML
  TextField usuario;
  @FXML
  PasswordField senha;

  public final static String EVENTO_LOGIN_CADASTRO_VOLTAR = "!login_cadastro!";
  public final static String EVENTO_LOGIN_CADASTRO_FINALIZAR = "!login_cadastro_finalizar!";

  private Menu menu;
  private Cadastro cadastro;

  public Login(Menu _menu) {
    try {
      this.carregarFXML();
      this.carregarImagens();
      this.setarPosicao();
      this.menu = _menu;
      this.adicionarListenersControle();
      this.listenerTeclado();
    } catch (IOException ex) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Descrição do erro");
      alert.setContentText(ex.getLocalizedMessage());
      alert.show();
    }
  }

  private void adicionarListenersControle() {
    usuario.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.length() > 35) {
        usuario.setText(newValue.substring(0, 35));
      }
    });
    senha.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.length() > 30) {
        senha.setText(newValue.substring(0, 30));
      }
    });
  }

  private void listenerTeclado() {
    this.btnLimpar.setOnMouseClicked(lambda -> {
      this.usuario.setText("");
      this.senha.setText("");
    });

    this.setOnKeyPressed(tecla -> {
      switch (tecla.getCode()) {
        case ENTER:
          break;
        case ALT:
          this.transicao(0, true);
          break;
        default:
          break;
      }
    });
  }

  private void transicao(double _valor, boolean _janelaCadastro) {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarLogo1 = new KeyValue(this.scaleYProperty(), _valor, Interpolator.EASE_BOTH);
    final KeyFrame animacao = new KeyFrame(Duration.seconds(.2), animarLogo1);
    timeline.getKeyFrames().add(animacao);
    if (_janelaCadastro) {
      timeline.setOnFinished(lambda -> this.janelaCadastro());
    }
    timeline.play();
  }

  private void janelaCadastro() {
    this.setOnKeyPressed(null);
    this.menu.getChildren().add(new Cadastro());
  }

  private void carregarFXML() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLLogin.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    fxmlLoader.load();
  }

  private void carregarImagens() {
    this.fundo.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/fundo.jpg")));
    this.btnLimpar.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/limpar.png")));
    this.btnConfirmar.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/confirmar.png")));
    this.login.setImage(new Image(Recursos.class.getResourceAsStream("img/Login/Login.png")));
  }

  private void setarPosicao() {
    this.setTranslateX(200);
    this.setTranslateY(100);
  }

  public void evento(String _eventName, Object _sender) {
    this.transicao(0, false);
    this.menu.getChildren().remove(this.cadastro);
  }
}
