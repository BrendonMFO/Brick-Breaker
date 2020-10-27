package brick;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.Interpolator;
import javafx.scene.layout.AnchorPane;

public class Menu extends AnchorPane {
  private final ImageView menu;
  private final ImageView logo[];
  private final ImageView opcao[];

  private Login login;
  private Records records;
  private Criador criador;
  private Rectangle escurecer;
  private Seletor seletorFase;
  private Configuracao configuracao;

  public Menu() {
    this.menu = new ImageView(Recursos.getSprite("Menu"));
    this.logo = new ImageView[2];
    this.opcao = new ImageView[4];
    this.setarImagens();
    this.getChildren().addAll(this.menu, this.logo[0], this.logo[1], this.opcao[0], this.opcao[1], this.opcao[2],
        this.opcao[3]);
    this.animarMenu();
  }

  private void setarImagens() {
    this.logo[0] = new ImageView(Recursos.getSprite("Logo1"));
    this.logo[0].setTranslateX(-400);
    this.logo[0].setTranslateY(60);
    this.logo[1] = new ImageView(Recursos.getSprite("Logo2"));
    this.logo[1].setTranslateX(1100);
    this.logo[1].setTranslateY(170);
    this.opcao[0] = new ImageView(Recursos.getSprite("Op1"));
    this.opcao[0].setTranslateX(387);
    this.opcao[0].setTranslateY(900);
    this.opcao[1] = new ImageView(Recursos.getSprite("Op2"));
    this.opcao[1].setTranslateX(-300);
    this.opcao[1].setTranslateY(586);
    this.opcao[2] = new ImageView(Recursos.getSprite("Op3"));
    this.opcao[2].setTranslateX(1200);
    this.opcao[2].setTranslateY(586);
    this.opcao[3] = new ImageView(Recursos.getSprite("Op4"));
    this.opcao[3].setTranslateX(700);
    this.opcao[3].setTranslateY(-300);
  }

  private void animarMenu() {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarLogo1 = new KeyValue(this.logo[1].xProperty(), -700, Interpolator.EASE_BOTH);
    final KeyValue animarLogo2 = new KeyValue(this.logo[0].xProperty(), 500, Interpolator.EASE_BOTH);
    final KeyValue animarOp1 = new KeyValue(this.opcao[0].yProperty(), -300, Interpolator.EASE_BOTH);
    final KeyValue animarOp2 = new KeyValue(this.opcao[1].xProperty(), 350, Interpolator.EASE_BOTH);
    final KeyValue animarOp3 = new KeyValue(this.opcao[2].xProperty(), -480, Interpolator.EASE_BOTH);
    final KeyFrame animacao = new KeyFrame(Duration.seconds(2), animarLogo2, animarLogo1, animarOp1, animarOp2,
        animarOp3);
    timeline.setOnFinished(value -> {
      voltarAoMenu();
    });
    timeline.getKeyFrames().add(animacao);
    timeline.play();
  }

  public final void inserirTelaLogin() {
    this.getChildren().remove(this.escurecer);
    this.login = new Login(this);
    this.escurecer = new Rectangle(1500, 1550);
    this.escurecer.setOpacity(0);
    this.login.setOpacity(0);
    this.getChildren().addAll(this.escurecer, this.login);
    this.animarLogin(.8, 1, false);
  }

  public void animarLogin(double _valor1, double _valor2, boolean _remover) {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarFundo = new KeyValue(this.escurecer.opacityProperty(), _valor1, Interpolator.EASE_BOTH);
    final KeyValue animarLogin = new KeyValue(this.login.opacityProperty(), _valor2, Interpolator.EASE_BOTH);
    final KeyFrame animacao = new KeyFrame(Duration.seconds(2), animarLogin, animarFundo);
    timeline.getKeyFrames().add(animacao);
    if (_remover) {
      timeline.setOnFinished(lambda -> {
        this.getChildren().removeAll(this.escurecer, this.login);
        this.menuOpcoesTeclado();
      });
    }
    timeline.play();
  }

  public void voltarAoMenu() {
    this.getChildren().remove(this.seletorFase);
    this.getChildren().remove(this.configuracao);
    this.getChildren().remove(this.records);
    this.getChildren().remove(this.criador);
    this.menuOpcoesTeclado();
    this.animarOp4();
  }

  private void animarOp4() {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarOp4 = new KeyValue(this.opcao[3].yProperty(), 250, Interpolator.EASE_BOTH);
    final KeyFrame animacao = new KeyFrame(Duration.seconds(2), animarOp4);
    timeline.getKeyFrames().add(animacao);
    timeline.play();
  }

  private void menuOpcoesTeclado() {
    this.getScene().setOnKeyPressed((final KeyEvent tecla) -> {
      switch (tecla.getCode()) {
        case R:
          this.encerrarListeners();
          this.inserirTelaLogin();
          break;
        case NUMPAD1:
          this.encerrarListeners();
          this.seletorFase = new Seletor();
          this.getChildren().add(this.seletorFase);
          break;
        case NUMPAD2:
          this.encerrarListeners();
          this.records = new Records();
          this.getChildren().add(this.records);
          break;
        case NUMPAD3:
          this.encerrarListeners();
          this.configuracao = new Configuracao();
          this.getChildren().add(this.configuracao);
          break;
        case NUMPAD4:
          this.encerrarListeners();
          this.criador = new Criador();
          this.getChildren().add(this.criador);
          break;
        default:
          break;
      }
    });
  }

  private void encerrarListeners() {
    this.getScene().setOnKeyPressed(null);
  }

  public void evento(String _eventName, Object _sender) {
    this.getChildren().remove(login);
    this.animarLogin(0, 0, true);
    this.animarOp4();
  }
}
