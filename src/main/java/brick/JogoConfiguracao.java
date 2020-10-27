package brick;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.paint.Color;

public final class JogoConfiguracao implements Serializable {

  private static final long serialVersionUID = 1L;
  private static JogoConfiguracao configuracoes;
  private int background;
  private int prancha;
  private Color cor;

  public final static JogoConfiguracao getInstance() {
    if (configuracoes == null) {
      configuracoes = new JogoConfiguracao();
      try {
        configuracoes.deserializar();
      } catch (IOException ex) {
      }
    }
    return configuracoes;
  }

  private JogoConfiguracao() {
    this.background = this.prancha = 1;
    this.cor = Color.YELLOW;
  }

  private void deserializar() throws IOException {
    try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("conf.bd"))) {
      this.background = input.readInt();
      this.prancha = input.readInt();
      this.cor = new Color(input.readDouble(), input.readDouble(), input.readDouble(), input.readDouble());
    }
  }

  public final void serializar() throws IOException {
    try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("conf.bd"))) {
      output.writeInt(this.background);
      output.writeInt(this.prancha);
      output.writeDouble(this.cor.getRed());
      output.writeDouble(this.cor.getGreen());
      output.writeDouble(this.cor.getBlue());
      output.writeDouble(this.cor.getOpacity());
    }
  }

  public final int getBackground() {
    return this.background;
  }

  public final int getPrancha() {
    return this.prancha;
  }

  public final Color getCor() {
    return this.cor;
  }

  public final void setBackground(int _background) {
    this.background = _background;
  }

  public final void setPrancha(int _prancha) {
    this.prancha = _prancha;
  }

  public final void setCor(Color _cor) {
    this.cor = _cor;
  }
}
