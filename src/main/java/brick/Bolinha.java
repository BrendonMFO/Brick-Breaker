package brick;

import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Circle;

public class Bolinha extends Circle {
  private double velocidadeX;
  private double velocidadeY;

  public Bolinha(Prancha _prancha) {
    this.setCenterX(100.0f);
    this.setCenterY(100.0f);
    this.setRadius(10.0f);
    this.setFill(JogoConfiguracao.getInstance().getCor());
    this.setEffect(new Glow(50));
    this.resertarBolinha(_prancha);
  }

  public double getVelocidadeX() {
    return this.velocidadeX;
  }

  public void setVelocidadeX(double _velocidadeX) {
    this.velocidadeX = _velocidadeX;
  }

  public double getVelocidadeY() {
    return this.velocidadeY;
  }

  public void setVelocidadeY(double _velocidadeY) {
    this.velocidadeY = _velocidadeY;
  }

  public void inverterVelocidadeY() {
    this.velocidadeY = -this.velocidadeY;
  }

  public void inverterVelocidadeX() {
    this.velocidadeX = -this.velocidadeX;
  }

  public void resertarBolinha(Prancha _prancha) {
    this.velocidadeX = 0f;
    this.velocidadeY = -5f;
    this.setTranslateX(_prancha.getTranslateX());
    this.setTranslateY(_prancha.getTranslateY() - 140);
  }

  public void moverBolinha() {
    this.setTranslateX(this.getTranslateX() + this.velocidadeX);
    this.setTranslateY(this.getTranslateY() + this.velocidadeY);
  }

  public void colisaoBloco(Node _node) {
    double centroBolinha = this.getBoundsInParent().getMaxX() - 10;
    double parteEsquerdaBloco = ((_node.getBoundsInParent().getWidth() * .15) + _node.getBoundsInParent().getMinX());
    double parteDireitaBloco = (_node.getBoundsInParent().getMaxX() - (_node.getBoundsInParent().getWidth() * .15));
    if (centroBolinha > parteEsquerdaBloco && centroBolinha < parteDireitaBloco) {
      this.inverterVelocidadeY();
    } else if ((centroBolinha < _node.getBoundsInParent().getMinX())
        || (centroBolinha > _node.getBoundsInParent().getMaxX())) {
      this.inverterVelocidadeX();
      this.inverterVelocidadeY();
    } else {
      if (centroBolinha > parteEsquerdaBloco) {
        double maxX = _node.getBoundsInParent().getMaxX();
        this.setVelocidadeX((2.5d + (2.5d * ((100 - Math.sqrt(Math.pow(centroBolinha - maxX, 2))) / 100))));
      }
      if (centroBolinha < parteDireitaBloco) {
        double minX = _node.getBoundsInParent().getMinX();
        this.setVelocidadeX((-2.5d + (-2.5d * ((100 - Math.sqrt(Math.pow(centroBolinha - minX, 2))) / 100))));
      }
      this.inverterVelocidadeY();
    }
    while (_node.getBoundsInParent().intersects(this.getBoundsInParent())) {
      this.moverBolinha();
    }
  }

  public void colisaoPrancha(Node _node) {
    double centroBolinha = this.getBoundsInParent().getMaxX() - 10;
    double parteEsquerdaPrancha = ((_node.getBoundsInParent().getWidth() * .45) + _node.getBoundsInParent().getMinX());
    double parteDireitaPrancha = (_node.getBoundsInParent().getMaxX() - (_node.getBoundsInParent().getWidth() * .45));
    if (centroBolinha > parteEsquerdaPrancha && centroBolinha < parteDireitaPrancha) {
      this.inverterVelocidadeY();
      this.setVelocidadeX(0);
    } else if ((centroBolinha < _node.getBoundsInParent().getMinX())
        || (centroBolinha > _node.getBoundsInParent().getMaxX())) {
      if (centroBolinha > parteEsquerdaPrancha) {
        this.setVelocidadeX(6.5d);
      } else {
        this.setVelocidadeX(-6.5d);
      }
      this.inverterVelocidadeY();
    } else {
      if (centroBolinha > parteEsquerdaPrancha) {
        double maxX = _node.getBoundsInParent().getMaxX();
        this.setVelocidadeX((2.5d + (2.5d * ((100 - Math.sqrt(Math.pow(centroBolinha - maxX, 2))) / 100))));
      }
      if (centroBolinha < parteDireitaPrancha) {
        double minX = _node.getBoundsInParent().getMinX();
        this.setVelocidadeX((-2.5d + (-2.5d * ((100 - Math.sqrt(Math.pow(centroBolinha - minX, 2))) / 100))));
      }
      this.inverterVelocidadeY();
    }
    this.colapso(_node);
  }

  private void colapso(Node _node) {
    while (this.getBoundsInParent().intersects(_node.getBoundsInParent())) {
      this.moverBolinha();
    }
  }
}
