package brick;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animacao extends ImageView {
  private int frameLinha;
  private int frameColuna;
  private final int numeroLinhas;
  private final int numeroColunas;
  private final boolean animacaoContinua;
  private final Rectangle2D[][] frames;
  private final Timeline tempo;
  private Timeline tempoDuracao;
  private final Node noPai;
  private boolean animacaoTerminou;

  public Animacao(Image _image, Duration _tempo, Duration _duracao, Node _node, int _linhas, int _colunas) {
    double linhas = _image.getHeight() / _linhas;
    double colunas = _image.getWidth() / _colunas;
    this.frames = new Rectangle2D[_linhas][_colunas];
    for (int i = 0; i < _linhas; i++) {
      for (int j = 0; j < _colunas; j++) {
        this.frames[i][j] = new Rectangle2D(j * colunas, linhas * i, linhas, colunas);
      }
    }
    this.noPai = _node;
    this.animacaoTerminou = false;
    this.frameLinha = 0;
    this.frameColuna = 0;
    this.numeroLinhas = _linhas;
    this.numeroColunas = _colunas;
    this.setTranslateX(this.noPai.getTranslateX() - 70);
    this.setTranslateY(this.noPai.getTranslateY() - 70);
    this.setImage(_image);
    this.setViewport(this.frames[0][0]);
    if (_duracao != null) {
      this.animacaoContinua = true;
      this.tempoDuracao = new Timeline(new KeyFrame(_duracao, lambda -> this.finalizarAnimacao()));
      this.tempoDuracao.play();
    } else {
      this.animacaoContinua = false;
    }
    this.tempo = new Timeline(new KeyFrame(_tempo, lambda -> this.animarFrames()));
    this.tempo.setCycleCount(Animation.INDEFINITE);
    this.tempo.play();
  }

  private void animarFrames() {
    if (this.animacaoContinua) {
      this.setTranslateX(this.noPai.getTranslateX());
      this.setTranslateY(this.noPai.getTranslateY());
    }
    if (++this.frameColuna > this.numeroColunas - 1) {
      this.frameColuna = 0;
      this.frameLinha++;
    }
    if (this.frameLinha > this.numeroLinhas - 1) {
      if (this.animacaoContinua) {
        this.frameColuna = this.frameLinha = 0;
      } else {
        this.tempo.stop();
        this.finalizarAnimacao();
      }
    } else {
      this.setViewport(this.frames[this.frameLinha][this.frameColuna]);
    }
  }

  private void finalizarAnimacao() {
    this.animacaoTerminou = true;
  }

  public boolean isAnimacaoTerminou() {
    return this.animacaoTerminou;
  }
}
