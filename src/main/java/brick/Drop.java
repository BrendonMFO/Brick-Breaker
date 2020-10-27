package brick;

import java.util.Random;

import javafx.scene.image.ImageView;

public class Drop extends ImageView {
  private DropTipo tipo;
  private final float velocidadeY = 2.5f;

  public Drop(Bloco _bloco) {
    this.sortearDrop();
    this.setImage(this.tipo.getImg());
    this.setTranslateX(_bloco.getTranslateX());
    this.setTranslateY(_bloco.getTranslateY());
  }

  private void sortearDrop() {
    Random random = new Random();
    switch (random.nextInt(4)) {
      case 0:
        this.tipo = DropTipo.VIDA;
        break;
      case 1:
        this.tipo = DropTipo.ESCUDO;
        break;
      case 2:
        this.tipo = DropTipo.ESPECIAL;
        break;
      case 3:
        this.tipo = DropTipo.EXPLODIR;
    }
  }

  public void moverDrop() {
    this.setTranslateY(this.getTranslateY() + this.velocidadeY);
  }

  public DropTipo getTipo() {
    return this.tipo;
  }
}
