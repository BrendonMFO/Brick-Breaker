package brick;

import java.util.Random;

import javafx.scene.image.ImageView;

public class Bloco extends ImageView {
  private final TipoBloco tipo;
  private String cor;
  private int vida;

  public Bloco(TipoBloco _tipo, double _X, double _Y) {
    this.tipo = _tipo;
    this.vida = _tipo.getValor();
    this.setTranslateX((_X * 45));
    this.setTranslateY(_Y * 30);
    this.setColor();
  }

  public TipoBloco getTipo() {
    return this.tipo;
  }

  private void setColor() {
    switch (this.tipo) {
      case NORMAL:
        this.randomColor();
        break;
      case REFORCADO:
        this.setImage(Recursos.getBlocos("ReforcadoN"));
        this.cor = "";
        break;
      case INDESTRUTIVEL:
        this.setImage(Recursos.getBlocos("Cinza"));
    }
  }

  public String getCor() {
    return cor;
  }

  private void randomColor() {
    Random gerador = new Random();
    switch (gerador.nextInt(3)) {
      case 0:
        this.setImage(Recursos.getBlocos("Vermelho"));
        this.cor = "Vermelho";
        break;
      case 1:
        this.setImage(Recursos.getBlocos("Azul"));
        this.cor = "Azul";
        break;
      case 2:
        this.setImage(Recursos.getBlocos("Verde"));
        this.cor = "Verde";
        break;
    }
  }

  public boolean diminuirVida() {
    this.vida--;
    if (this.getTipo() == TipoBloco.REFORCADO) {
      this.setImage(Recursos.getBlocos("ReforcadoR"));
    }
    return this.vida == 0;
  }

}