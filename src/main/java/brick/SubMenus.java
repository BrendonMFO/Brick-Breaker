package brick;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class SubMenus extends AnchorPane {
  protected final Rectangle fundo = new Rectangle();
  protected final ImageView frame = new ImageView();
  protected final BooleanProperty fechar = new SimpleBooleanProperty();

  protected abstract void carregarImagens();

  protected abstract void adicionarListenerTeclado();

  protected final void animar(boolean _setarFim, double _scaleX, double _scaleY, double _opacity,
      final KeyValue... _animacoes) {
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    final KeyValue animarFrameX = new KeyValue(this.frame.scaleXProperty(), _scaleX, Interpolator.EASE_BOTH);
    final KeyValue animarFrameY = new KeyValue(this.frame.scaleYProperty(), _scaleY, Interpolator.EASE_BOTH);
    final KeyValue animarFundo = new KeyValue(this.fundo.opacityProperty(), _opacity, Interpolator.EASE_BOTH);
    final KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), animarFrameY, animarFrameX, animarFundo);
    timeline.getKeyFrames().add(keyFrame);
    for (final KeyValue animacao : _animacoes) {
      timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), animacao));
    }
    if (_setarFim) {
      timeline.setOnFinished(lambda -> {
        this.adicionarListenerTeclado();
      });
    } else {
      timeline.setOnFinished(lambda -> {
        this.retornarMenu();
      });
    }
    timeline.play();
  }

  protected final void setFundo(double _largura, double _altura, Paint _cor) {
    this.fundo.setWidth(_largura);
    this.fundo.setHeight(_altura);
    this.fundo.setFill(_cor);
    this.fundo.setOpacity(0);
    this.getChildren().add(this.fundo);
    this.fundo.toBack();
  }

  protected final void setFrameImage(String _nomeImg) {
    this.frame.setImage(new Image(Recursos.class.getResourceAsStream(_nomeImg)));
    this.frame.setScaleX(2);
    this.frame.setScaleY(2);
  }

  protected void removerListener() {
    this.getScene().setOnKeyPressed(null);
  }

  protected final void retornarMenu() {
    Menu menu = (Menu) this.getParent();
    menu.voltarAoMenu();
  }

  public final boolean getFechar() {
    return this.fechar.get();
  }

  public final void setFechar(boolean _valor) {
    this.fechar.set(_valor);
  }

  public final BooleanProperty fecharProperty() {
    return this.fechar;
  }
}
