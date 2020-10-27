package brick;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;

public class Jogo extends AnchorPane {
  @FXML
  private GridPane painelVida;
  @FXML
  private ImageView imgControle, fundo;
  @FXML
  private Text pontuacao;

  private final Level level;
  private final Prancha prancha;
  private final Bolinha bolinha;
  private final Scene scene;
  private Timeline atualizar;
  private Timeline tempoProtegido;
  private Timeline tempoPassar;
  private int quantidadeBlocos;
  private int vida;
  private float multiplicadorPontos;
  private boolean protecao;
  private boolean passarDireto;

  public Jogo(Level _level, Scene _scene) {
    this.carregarFXML();
    this.scene = _scene;
    this.level = _level;
    this.protecao = false;
    this.passarDireto = false;
    this.prancha = new Prancha();
    this.bolinha = new Bolinha(this.prancha);
    this.quantidadeBlocos = 0;
    this.multiplicadorPontos = 1.0f;
    this.fundo.setImage(Recursos.getSprite("Fundo_0" + JogoConfiguracao.getInstance().getBackground()));
    this.fundo.setFitHeight(1080);
    this.fundo.setFitWidth(1920);
    this.imgControle.setImage(Recursos.getSprite("Controle"));
    this.getChildren().add(bolinha);
    this.getChildren().add(prancha);
    this.gerarBlocos();
    this.setarTimer();
    this.listenerTeclado();
    this.setVida(6);
  }

  private void carregarFXML() {
    FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("FXMLBrick.fxml"));
    fXMLLoader.setRoot(this);
    fXMLLoader.setController(this);
    try {
      fXMLLoader.load();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void setarTimer() {
    this.atualizar = new Timeline(new KeyFrame(Duration.seconds(0.016f), ae -> this.update()));
    this.atualizar.setCycleCount(Animation.INDEFINITE);
    this.atualizar.play();
  }

  private void gerarBlocos() {
    float X, Y = 2;
    for (String linha : this.level.getLevelFormat().split("\n")) {
      X = 0;
      for (char bloco : linha.toCharArray()) {
        switch (bloco) {
          case 'N':
            this.getChildren().add(new Bloco(TipoBloco.NORMAL, X, Y));
            this.quantidadeBlocos++;
            break;
          case 'R':
            this.getChildren().add(new Bloco(TipoBloco.REFORCADO, X, Y));
            this.quantidadeBlocos++;
            break;
          case 'I':
            this.getChildren().add(new Bloco(TipoBloco.INDESTRUTIVEL, X, Y));
            break;
        }
        X++;
      }
      Y++;
    }
  }

  private void checarArea() {
    if (this.bolinha.getTranslateX() > 636) {
      this.bolinha.setTranslateX(635);
      this.bolinha.inverterVelocidadeX();
    }
    if (this.bolinha.getTranslateX() < -100) {
      this.bolinha.setTranslateX(-99);
      this.bolinha.inverterVelocidadeX();
    }
    if (this.bolinha.getTranslateY() < -100) {
      this.bolinha.setTranslateY(-99);
      this.bolinha.inverterVelocidadeY();
    }
    if (this.bolinha.getBoundsInParent().getMaxY() > 830) {
      this.diminuirVida();
    }
  }

  private void diminuirVida() {
    if (!this.protecao) {
      this.vida--;
      this.painelVida.getChildren().remove(this.painelVida.getChildren().get(this.vida));
      if (this.vida == 0) {
        this.atualizar.stop();
        this.removerListenerTeclado();
        this.getChildren().add(new Resultado(Resultado.TipoResultado.PERDEU, this.level.getIndexLevel(),
            this.pontuacao.getText(), Integer.parseInt(this.pontuacao.getText())));
      } else {
        this.bolinha.resertarBolinha(this.prancha);
      }
    } else {
      this.bolinha.inverterVelocidadeY();
    }
  }

  private void setVida(int _vida) {
    this.vida = _vida;
    this.painelVida.getChildren().clear();
    for (int j = 0; j < 3; j++) {
      for (int k = 0; k < 3; k++) {
        _vida--;
        this.painelVida.add(new ImageView(Recursos.getSprite("Vida")), j, k);
        if (_vida == 0) {
          return;
        }
      }
    }
  }

  private void levelSuperado() {
    if (this.quantidadeBlocos == 0) {
      this.atualizar.stop();
      this.removerListenerTeclado();
      String pontos = this.pontuacao.getText() + " * " + this.painelVida.getChildren().size() + " = ";
      Integer p = Integer.parseInt(this.pontuacao.getText()) * this.painelVida.getChildren().size();
      pontos += p;
      this.getChildren().add(new Resultado(Resultado.TipoResultado.VENCEU, this.level.getIndexLevel(), pontos, p));
    }
  }

  private void inserirDrop(Bloco _bloco) {
    Random gerador = new Random();
    switch (_bloco.getTipo()) {
      case NORMAL:
        if (gerador.nextInt(100) > 80) {
          this.getChildren().add(new Drop(_bloco));
        }
        break;
      case REFORCADO:
        if (gerador.nextInt(100) > 70) {
          this.getChildren().add(new Drop(_bloco));
        }
        break;
      default:
        break;
    }
  }

  private void efetuarDrop(Drop _drop) {
    switch (_drop.getTipo()) {
      case VIDA:
        if (this.vida < 9) {
          this.setVida(++this.vida);
          this.getChildren()
              .add(new Animacao(Recursos.getAnimacao("Coracao"), Duration.seconds(.03), null, _drop, 3, 5));
          this.pontuacao.setText(String.valueOf(Integer.parseInt(this.pontuacao.getText()) + 150));
        }
        break;
      case ESCUDO:
        this.protecao = true;
        this.getChildren().add(new Animacao(Recursos.getAnimacao("Escudo"), Duration.seconds(.03), null, _drop, 7, 5));
        this.tempoProtegido = new Timeline(new KeyFrame(Duration.seconds(20), lambda -> {
          this.protecao = false;
        }));
        this.tempoProtegido.play();
        this.pontuacao.setText(String.valueOf(Integer.parseInt(this.pontuacao.getText()) + 100));
        break;
      case EXPLODIR:
        this.getChildren().add(new Animacao(Recursos.getAnimacao("Fogo"), Duration.seconds(.03), null, _drop, 4, 5));
        this.diminuirVida();
        this.pontuacao.setText(String.valueOf(Integer.parseInt(this.pontuacao.getText()) + 600));
        break;
      case ESPECIAL:
        if (!this.passarDireto) {
          this.passarDireto = true;
          this.getChildren().add(new Animacao(Recursos.getAnimacao("Gelo"), Duration.seconds(.03), null, _drop, 5, 5));
          this.getChildren().add(new Animacao(Recursos.getAnimacao("Protecao"), Duration.seconds(.03),
              Duration.seconds(8), this.bolinha, 6, 5));
          this.tempoPassar = new Timeline(new KeyFrame(Duration.seconds(8), lambda -> this.passarDireto = false));
          this.tempoPassar.play();
          this.pontuacao.setText(String.valueOf(Integer.parseInt(this.pontuacao.getText()) + 400));
        }
    }
  }

  private void inserirAnimacoesBloco(Bloco _bloco) {
    switch (_bloco.getCor()) {
      case "Vermelho":
        this.getChildren()
            .add(new Animacao(Recursos.getAnimacao("Artificio_Vermelho"), Duration.seconds(.03), null, _bloco, 10, 5));
        break;
      case "Verde":
        this.getChildren()
            .add(new Animacao(Recursos.getAnimacao("Artificio_Verde"), Duration.seconds(.03), null, _bloco, 6, 5));
        break;
      case "Azul":
        this.getChildren()
            .add(new Animacao(Recursos.getAnimacao("Artificio_Azul"), Duration.seconds(.03), null, _bloco, 8, 5));
    }
  }

  private void listenerTeclado() {
    this.scene.setOnKeyPressed(tecla -> {
      switch (tecla.getCode()) {
        case LEFT:
          this.prancha.setarMovimentoTeclado(-7);
          break;
        case RIGHT:
          this.prancha.setarMovimentoTeclado(7);
          break;
        case E:
          this.getChildren()
              .add(new Animacao(Recursos.getAnimacao("Fogo"), Duration.seconds(.03), null, this.prancha, 4, 5));
          this.diminuirVida();
          break;
        default:
          break;
      }
    });
    this.scene.setOnKeyReleased(tecla -> {
      switch (tecla.getCode()) {
        case LEFT:
        case RIGHT:
          this.prancha.setarMovimentoTeclado(0);
          break;
        default:
          break;
      }
    });
  }

  private void removerListenerTeclado() {
    this.scene.setOnKeyPressed(null);
    this.scene.setOnKeyReleased(null);
  }

  private void aplicarMovimentos() {
    this.prancha.movimentoTeclado();
    this.bolinha.moverBolinha();
  }

  private void colisaoPrancha(Node node) {
    if (node instanceof Prancha && node.getBoundsInParent().intersects(this.bolinha.getBoundsInParent())) {
      this.bolinha.colisaoPrancha(node);
      this.multiplicadorPontos = 1.0f;
    }
  }

  private boolean colisaoBloco(Node node) {
    if (node instanceof Bloco && node.getBoundsInParent().intersects(this.bolinha.getBoundsInParent())) {
      if (!this.passarDireto || ((Bloco) node).getTipo() == TipoBloco.INDESTRUTIVEL) {
        this.bolinha.colisaoBloco(node);
      }
      if (((Bloco) node).diminuirVida()) {
        this.inserirAnimacoesBloco((Bloco) node);
        this.quantidadeBlocos--;
        int pontos = (int) ((100 * this.multiplicadorPontos) + Integer.parseInt(this.pontuacao.getText()));
        this.pontuacao.setText(String.valueOf(pontos));
        this.multiplicadorPontos += 0.2f;
        this.inserirDrop((Bloco) node);
        this.levelSuperado();
        return true;
      }
    }
    return false;
  }

  private boolean colisaoDrop(Node node) {
    if (node instanceof Drop) {
      ((Drop) node).moverDrop();
      if (node.getBoundsInParent().intersects(this.prancha.getBoundsInParent())) {
        this.efetuarDrop((Drop) node);
        return true;
      } else if (node.getBoundsInParent().getMaxY() > 820) {
        return true;
      }
    }
    return false;
  }

  private void calcularColisoes() {
    ArrayList<Node> nodes = new ArrayList<>();
    for (int i = 0; i < this.getChildren().size(); i++) {
      Node node = this.getChildren().get(i);
      this.colisaoPrancha(node);
      if (this.colisaoBloco(node)) {
        nodes.add(node);
      }
      if (this.colisaoDrop(node)) {
        nodes.add(node);
      }
      if (node instanceof Animacao) {
        if (((Animacao) node).isAnimacaoTerminou()) {
          nodes.add(node);
          break;
        }
      }
    }
    this.getChildren().removeAll(nodes);
  }

  private void update() {
    this.aplicarMovimentos();
    this.checarArea();
    this.calcularColisoes();
  }
}
