package brick;

import static brick.Recursos.QUANTIDADE_LEVEL;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Seletor extends SubMenus {
  @FXML
  private ImageView img1, img2, img3, img4, numeros;
  @FXML
  private ImageView overlay1, overlay2, overlay3, overlay4;

  private Integer pagina;

  public Seletor() {
    this.pagina = 0;
    this.carregarFXML();
    this.carregarImagens();
    this.carregarOverlay();
    this.imagensLeveis();
    this.animar(true, 1, 1, .6, new KeyValue(this.img1.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.img2.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.img3.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.img4.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.overlay1.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.overlay2.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.overlay3.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.overlay4.opacityProperty(), 1, Interpolator.EASE_BOTH),
        new KeyValue(this.numeros.opacityProperty(), 1, Interpolator.EASE_BOTH));
  }

  @Override
  protected final void carregarImagens() {
    this.setFundo(1500, 1500, Color.WHITE);
    this.setFrameImage("img/Seletor/frame.png");
    this.getChildren().add(this.frame);
    this.img1.setOpacity(0);
    this.img2.setOpacity(0);
    this.img3.setOpacity(0);
    this.img4.setOpacity(0);
    this.numeros.setImage(new Image(Recursos.class.getResourceAsStream("img/Leveis/Numeros.png")));
    this.numeros.setOpacity(0);
    this.corrigirPosicaoZ();
  }

  @Override
  protected final void adicionarListenerTeclado() {
    this.getScene().setOnKeyPressed((KeyEvent tecla) -> {
      this.keyEvents(tecla.getCode());
    });
  }

  private void carregarFXML() {
    try {
      FXMLLoader fXMLLoader = new FXMLLoader(Recursos.class.getResource("FXMLSeletor.fxml"));
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

  private void checarPermissao() {
    Level level = Level.getLevel(this.pagina * 4);
    if (level != null) {
      if (level.getPermissao()) {
        this.img1.setEffect(new ColorAdjust(0, -1, 0, 0));
      } else {
        this.img1.setEffect(null);
      }
    }
    level = Level.getLevel((this.pagina * 4) + 1);
    if (level != null) {
      if (level.getPermissao()) {
        this.img2.setEffect(new ColorAdjust(0, -1, 0, 0));
      } else {
        this.img2.setEffect(null);
      }
    }
    level = Level.getLevel((this.pagina * 4) + 2);
    if (level != null) {
      if (level.getPermissao()) {
        this.img3.setEffect(new ColorAdjust(0, -1, 0, 0));
      } else {
        this.img3.setEffect(null);
      }
    }
    level = Level.getLevel((this.pagina * 4) + 3);
    if (level != null) {
      if (level.getPermissao()) {
        this.img4.setEffect(new ColorAdjust(0, -1, 0, 0));
      } else {
        this.img4.setEffect(null);
      }
    }
  }

  private void imagensLeveis() {
    this.img1.setImage(Level.getImageFromIndex((this.pagina * 4)));
    this.img1.setFitWidth(370);
    this.img2.setImage(Level.getImageFromIndex((this.pagina * 4) + 1));
    this.img2.setFitWidth(370);
    this.img3.setImage(Level.getImageFromIndex((this.pagina * 4) + 2));
    this.img3.setFitWidth(370);
    this.img4.setImage(Level.getImageFromIndex((this.pagina * 4) + 3));
    this.img4.setFitWidth(370);
    this.checarPermissao();
  }

  private void carregarOverlay() {
    this.overlay1.setImage(new Image(Recursos.class.getResourceAsStream("img/Leveis/overlay.png")));
    this.overlay1.setOpacity(0);
    this.overlay2.setImage(new Image(Recursos.class.getResourceAsStream("img/Leveis/overlay.png")));
    this.overlay2.setOpacity(0);
    this.overlay3.setImage(new Image(Recursos.class.getResourceAsStream("img/Leveis/overlay.png")));
    this.overlay3.setOpacity(0);
    this.overlay4.setImage(new Image(Recursos.class.getResourceAsStream("img/Leveis/overlay.png")));
    this.overlay4.setOpacity(0);
  }

  private void corrigirPosicaoZ() {
    this.img1.toFront();
    this.img2.toFront();
    this.img3.toFront();
    this.img4.toFront();
    this.overlay1.toFront();
    this.overlay2.toFront();
    this.overlay3.toFront();
    this.overlay4.toFront();
    this.numeros.toFront();
  }

  private void keyEvents(KeyCode _tecla) {
    Level level;
    switch (_tecla) {
      case NUMPAD7:
        if (this.pagina > 0) {
          this.pagina--;
          this.imagensLeveis();
        }
        break;
      case NUMPAD9:
        if (this.pagina < ((QUANTIDADE_LEVEL - 1) / 4)) {
          this.pagina++;
          this.imagensLeveis();
        }
        break;
      case NUMPAD1:
        level = Level.getLevel((this.pagina * 4));
        if (level != null) {
          if (!level.getPermissao()) {
            this.encerrarListeners();
            this.getScene().setRoot(new Jogo(level, this.getScene()));
          }
        }
        break;
      case NUMPAD2:
        level = Level.getLevel((this.pagina * 4) + 1);
        if (level != null) {
          if (!level.getPermissao()) {
            this.removerListener();
            this.getScene().setRoot(new Jogo(level, this.getScene()));
          }
        }
        break;
      case NUMPAD3:
        level = Level.getLevel((this.pagina * 4) + 2);
        if (level != null) {
          if (!level.getPermissao()) {
            this.encerrarListeners();
            this.getScene().setRoot(new Jogo(level, this.getScene()));
          }
        }
        break;
      case NUMPAD4:
        level = Level.getLevel((this.pagina * 4) + 2);
        if (level != null) {
          if (!level.getPermissao()) {
            this.encerrarListeners();
            this.getScene().setRoot(new Jogo(level, this.getScene()));
          }
        }
        break;
      case ESCAPE:
        this.encerrarListeners();
        this.animar(false, 2, 2, 0, new KeyValue(this.img1.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.img2.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.img3.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.img4.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.overlay1.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.overlay2.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.overlay3.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.overlay4.opacityProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(this.numeros.opacityProperty(), 0, Interpolator.EASE_BOTH));
        break;
      default:
        break;
    }
  }

  private void encerrarListeners() {
    this.removerListener();
  }
}
