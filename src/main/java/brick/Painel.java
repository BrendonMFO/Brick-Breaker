package brick;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.SnapshotParameters;

public class Painel extends AnchorPane {

  public Painel(String _dado) {
    float X, Y = 0;
    for (String linha : _dado.split("\n")) {
      X = 0;
      for (char bloco : linha.toCharArray()) {
        switch (bloco) {
          case 'N':
            this.getChildren().add(new Bloco(TipoBloco.NORMAL, X, Y));
            break;
          case 'R':
            this.getChildren().add(new Bloco(TipoBloco.REFORCADO, X, Y));
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

  public Image getImage() {
    return this.snapshot(new SnapshotParameters(), null);
  }
}
