package brick;

import javafx.scene.image.Image;

public enum DropTipo {
  VIDA(Recursos.getSprite("Vida")), EXPLODIR(Recursos.getSprite("Fogo")), ESPECIAL(Recursos.getSprite("Gelo")),
  ESCUDO(Recursos.getSprite("Escudo"));

  private final Image img;

  DropTipo(Image _img) {
    img = _img;
  }

  public Image getImg() {
    return img;
  }
}