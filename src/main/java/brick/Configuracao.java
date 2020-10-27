package brick;

import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.animation.KeyValue;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.animation.Interpolator;
import javafx.scene.control.ColorPicker;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Configuracao extends SubMenus {
  private final ImageView fundos;
  private final ImageView frase;
  private final ImageView prancha;
  private final JogoConfiguracao configuracao;
  private final ColorPicker color;
  private final IntegerProperty funcao;

  public Configuracao() {
    this.setFundo(1500, 1500, Color.WHITE);
    this.fundos = new ImageView();
    this.frase = new ImageView();
    this.prancha = new ImageView();
    this.configuracao = JogoConfiguracao.getInstance();
    this.color = new ColorPicker(this.configuracao.getCor());
    this.color.setVisible(false);
    this.funcao = new SimpleIntegerProperty(0);
    this.listenerTransicaoFuncao();
    this.carregarImagens();
    this.animar(true, 1, 1, .6, new KeyValue(this.frase.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.fundos.opacityProperty(), 1, Interpolator.EASE_BOTH));
  }

  @Override
  protected void carregarImagens() {
    this.setFrameImage("img/Configuracao/frame.png");
    this.frase.setImage(Recursos.getSprite("Frase01"));
    this.frase.setTranslateX(43);
    this.frase.setTranslateY(91);
    this.frase.setOpacity(0);
    this.fundos.setFitWidth(600);
    this.fundos.setFitHeight(480);
    this.fundos.setTranslateX(203);
    this.fundos.setTranslateY(143);
    this.fundos.setOpacity(0);
    this.mudarImagensFundo();
    this.getChildren().addAll(this.frame, this.fundos, this.frase);
    this.configurarColor();
    this.configurarPrancha();
  }

  @Override
  protected void adicionarListenerTeclado() {
    this.getScene().setOnKeyPressed(tecla -> {
      this.keyEvents(tecla.getCode());
    });
  }

  private void configurarColor() {
    this.color.setTranslateX(250);
    this.color.setTranslateY(200);
    this.color.setMinWidth(500);
    this.getChildren().add(color);
  }

  private void configurarPrancha() {
    this.prancha.setTranslateX(425);
    this.prancha.setTranslateY(250);
    this.prancha.setVisible(false);
    this.mudarImagensPrancha();
    this.getChildren().add(this.prancha);
  }

  private void ocultarElementos() {
    this.color.setVisible(false);
    this.fundos.setVisible(false);
    this.prancha.setVisible(false);
  }

  private void mudarSelecaoOpcoes() {
    this.ocultarElementos();
    switch (this.funcao.getValue()) {
      case 0:
        this.fundos.setVisible(true);
        this.frase.setImage(Recursos.getSprite("Frase01"));
        break;
      case 1:
        this.color.setVisible(true);
        this.frase.setImage(Recursos.getSprite("Frase02"));
        break;
      case 2:
        this.prancha.setVisible(true);
        this.frase.setImage(Recursos.getSprite("Frase03"));
    }
  }

  private void listenerTransicaoFuncao() {
    this.funcao.addListener(listener -> {
      this.mudarSelecaoOpcoes();
    });
    this.color.setOnAction(action -> {
      this.configuracao.setCor(this.color.getValue());
    });
    this.color.setOnKeyPressed(value -> {
      if (value.getCode() == KeyCode.ESCAPE) {
        this.voltarSalvar();
      }
    });
  }

  private void keyEvents(KeyCode _tecla) {
    switch (_tecla) {
      case NUMPAD7:
        if (this.funcao.getValue() > 0) {
          this.funcao.setValue(this.funcao.getValue() - 1);
        }
        break;
      case NUMPAD9:
        if (this.funcao.getValue() < 2) {
          this.funcao.setValue(this.funcao.getValue() + 1);
        }
        break;
      case LEFT:
        if (this.funcao.getValue() == 0) {
          if (this.configuracao.getBackground() > 1) {
            this.configuracao.setBackground(this.configuracao.getBackground() - 1);
            this.mudarImagensFundo();
          }
        }
        if (this.funcao.getValue() == 2) {
          if (this.configuracao.getPrancha() > 1) {
            this.configuracao.setPrancha(this.configuracao.getPrancha() - 1);
            this.mudarImagensPrancha();
          }
        }
        break;
      case RIGHT:
        if (this.funcao.getValue() == 0) {
          if (this.configuracao.getBackground() < 6) {
            this.configuracao.setBackground(this.configuracao.getBackground() + 1);
            this.mudarImagensFundo();
          }
        }
        if (this.funcao.getValue() == 2) {
          if (this.configuracao.getPrancha() < 3) {
            this.configuracao.setPrancha(this.configuracao.getPrancha() + 1);
            this.mudarImagensPrancha();
          }
        }
        break;
      case ESCAPE:
        this.voltarSalvar();
        break;
      default:
        break;
    }
  }

  private void voltarSalvar() {
    try {
      this.configuracao.serializar();
    } catch (IOException ex) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro ao salvar configurações");
      alert.setHeaderText("Não foi possivel salvar as configurações");
      alert.setContentText(ex.getLocalizedMessage());
      alert.showAndWait();
    }
    this.removerListener();
    this.animar(false, 2, 2, 0, new KeyValue(this.frase.opacityProperty(), 0, Interpolator.EASE_BOTH),
        new KeyValue(this.fundos.opacityProperty(), 0, Interpolator.EASE_BOTH),
        new KeyValue(this.color.opacityProperty(), 0, Interpolator.EASE_BOTH),
        new KeyValue(this.prancha.opacityProperty(), 0, Interpolator.EASE_BOTH));
  }

  private void mudarImagensFundo() {
    this.fundos.setImage(Recursos.getSprite("Fundo_0" + this.configuracao.getBackground()));
  }

  private void mudarImagensPrancha() {
    this.prancha.setImage(Recursos.getSprite("Prancha_" + this.configuracao.getPrancha()));
  }
}
