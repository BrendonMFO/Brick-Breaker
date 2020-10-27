package brick;

public enum TipoBloco {
  NORMAL(1), INDESTRUTIVEL(-1), REFORCADO(2);

  private final int vida;

  TipoBloco(int _vida) {
    this.vida = _vida;
  }

  public int getValor() {
    return this.vida;
  }
}
