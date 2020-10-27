package brick;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

public class Prancha extends ImageView {
  private double movimentoAnterior;
  private double forcaMovimento;
  private double movimentoTeclado;
  private int contador;

  public Prancha() {
    this.setImage(Recursos.getSprite("Prancha_" + JogoConfiguracao.getInstance().getPrancha()));
    this.setEffect(new Glow(50));
    this.setTranslateX((322));
    this.setTranslateY(706);
    this.movimentoAnterior = -1;
    this.contador = 0;
  }

  public void movimentaPrancha() {
    if (this.forcaMovimento != -1) {
      if (this.movimentoAnterior - this.forcaMovimento > 100 || this.forcaMovimento - this.movimentoAnterior < -100
          || this.movimentoAnterior == -1 || this.contador == 15) {
        this.movimentoAnterior = this.forcaMovimento;
        this.contador = 0;
        this.setTranslateX(this.forcaMovimento);
        this.limiteArea();
      } else {
        this.contador++;
      }
    }
  }

  public void setarMovimentoTeclado(int _movimento) {
    this.movimentoTeclado = _movimento;
  }

  public void movimentoTeclado() {
    this.setTranslateX(this.getTranslateX() + this.movimentoTeclado);
    this.limiteArea();
  }

  private void limiteArea() {
    if (this.getBoundsInParent().getMaxX() > 790) {
      this.setTranslateX(640);
    }
    if (this.getTranslateX() < 0) {
      this.setTranslateX(0);
    }
  }
}
