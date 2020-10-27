package brick;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Level {
  private static List<Level> dataLeveis;
  private final Integer indexLevel;
  private final String levelFormat;
  private final Image levelDesing;
  private final BooleanProperty bloqueado;

  private Level(String _levelInfo, Image _imageInfo, Integer _tipos) {
    this.indexLevel = dataLeveis.size() + 1;
    this.levelFormat = _levelInfo;
    this.levelDesing = _imageInfo;
    this.bloqueado = new SimpleBooleanProperty(false);
  }

  public final static boolean adicionarLevel(String _leveInfo, Image _imageInfo, Integer _tipos) {
    if (dataLeveis == null) {
      dataLeveis = new ArrayList<>();
    }
    return dataLeveis.add(new Level(_leveInfo, _imageInfo, _tipos));
  }

  public final static Level getLevel(Integer _index) {
    if (_index <= dataLeveis.size() - 1) {
      return dataLeveis.get(_index);
    }
    return null;
  }

  public final static Image getImageFromIndex(int _index) {
    if (_index >= dataLeveis.size()) {
      return null;
    }
    return dataLeveis.get(_index).levelDesing;
  }

  public final Integer getIndexLevel() {
    return this.indexLevel;
  }

  public final String getLevelFormat() {
    return this.levelFormat;
  }

  public final Image getLevelDesign() {
    return this.levelDesing;
  }

  public final boolean getPermissao() {
    return this.bloqueado.getValue();
  }
}
