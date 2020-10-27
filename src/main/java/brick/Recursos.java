package brick;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.scene.image.Image;

public abstract class Recursos {
  public static Integer QUANTIDADE_LEVEL;
  private static Map<String, Image> ANIMACOES;
  private static Map<String, Image> SPRITES;
  private static Map<String, Image> IMG_BLOCOS;

  public static void carregar() throws IOException {
    QUANTIDADE_LEVEL = 0;
    carregarAnimacoes();
    carregarSprites();
    carregarImgBlocos();
    carregarDataLevel();
  }

  private static void carregarDataLevel() throws IOException {
    String retorno = "", temp;
    InputStream dataLevel = App.class.getResourceAsStream("DataLevel.dt");
    BufferedReader dataLevelStream = new BufferedReader(new InputStreamReader(dataLevel));
    while ((temp = dataLevelStream.readLine()) != null) {
      retorno += temp + "\n";
    }
    Painel p;
    for (String level : retorno.split("@-@")) {
      p = new Painel(level);
      Level.adicionarLevel(level, p.getImage(), Integer.parseInt(level.substring(0, 1)));
      QUANTIDADE_LEVEL++;
    }
  }

  private static void carregarAnimacoes() throws IOException {
    ANIMACOES = new HashMap<>();
    ANIMACOES.put("Artificio_Vermelho", new Image(App.class.getResourceAsStream("animacoes/effect_008.png")));
    ANIMACOES.put("Artificio_Azul", new Image(App.class.getResourceAsStream("animacoes/effect_009.png")));
    ANIMACOES.put("Artificio_Verde", new Image(App.class.getResourceAsStream("animacoes/effect_006.png")));
    ANIMACOES.put("Coracao", new Image(App.class.getResourceAsStream("animacoes/special_003.png")));
    ANIMACOES.put("Escudo", new Image(App.class.getResourceAsStream("animacoes/magic_004.png")));
    ANIMACOES.put("Gelo", new Image(App.class.getResourceAsStream("animacoes/heal_001.png")));
    ANIMACOES.put("Fogo", new Image(App.class.getResourceAsStream("animacoes/fire_001.png")));
    ANIMACOES.put("Protecao", new Image(App.class.getResourceAsStream("animacoes/darkness_001.png")));
  }

  private static void carregarSprites() {
    SPRITES = new HashMap<>();
    SPRITES.put("Menu", new Image(App.class.getResourceAsStream("img/Menu/Menu.png")));
    SPRITES.put("Logo1", new Image(App.class.getResourceAsStream("img/Menu/titulo1.png")));
    SPRITES.put("Logo2", new Image(App.class.getResourceAsStream("img/Menu/titulo2.png")));
    SPRITES.put("Op1", new Image(App.class.getResourceAsStream("img/Menu/op1.png")));
    SPRITES.put("Op2", new Image(App.class.getResourceAsStream("img/Menu/op2.png")));
    SPRITES.put("Op3", new Image(App.class.getResourceAsStream("img/Menu/op3.png")));
    SPRITES.put("Op4", new Image(App.class.getResourceAsStream("img/Menu/op4.png")));
    SPRITES.put("Fundo_01", new Image(App.class.getResourceAsStream("img/fundo1.jpg")));
    SPRITES.put("Fundo_02", new Image(App.class.getResourceAsStream("img/fundo2.jpg")));
    SPRITES.put("Fundo_03", new Image(App.class.getResourceAsStream("img/fundo3.jpg")));
    SPRITES.put("Fundo_04", new Image(App.class.getResourceAsStream("img/fundo4.jpg")));
    SPRITES.put("Fundo_05", new Image(App.class.getResourceAsStream("img/fundo5.jpg")));
    SPRITES.put("Fundo_06", new Image(App.class.getResourceAsStream("img/fundo6.jpg")));
    SPRITES.put("Controle", new Image(App.class.getResourceAsStream("img/Controle.fw.png")));
    SPRITES.put("Vida", new Image(App.class.getResourceAsStream("img/vida.png")));
    SPRITES.put("Fogo", new Image(App.class.getResourceAsStream("img/aumentar.png")));
    SPRITES.put("Gelo", new Image(App.class.getResourceAsStream("img/diminuir.png")));
    SPRITES.put("Escudo", new Image(App.class.getResourceAsStream("img/proteger.png")));
    SPRITES.put("Prancha_1", new Image(App.class.getResourceAsStream("img/prancha.png")));
    SPRITES.put("Prancha_2", new Image(App.class.getResourceAsStream("img/prancha2.png")));
    SPRITES.put("Prancha_3", new Image(App.class.getResourceAsStream("img/prancha3.png")));
    SPRITES.put("Fase1", new Image(App.class.getResourceAsStream("img/Seletor/fases01.png")));
    SPRITES.put("Fase2", new Image(App.class.getResourceAsStream("img/Seletor/fases02.png")));
    SPRITES.put("Fase3", new Image(App.class.getResourceAsStream("img/Seletor/fases03.png")));
    SPRITES.put("Frase01", new Image(App.class.getResourceAsStream("img/Configuracao/frase01.png")));
    SPRITES.put("Frase02", new Image(App.class.getResourceAsStream("img/Configuracao/frase02.png")));
    SPRITES.put("Frase03", new Image(App.class.getResourceAsStream("img/Configuracao/frase03.png")));
  }

  private static void carregarImgBlocos() {
    IMG_BLOCOS = new HashMap<>();
    IMG_BLOCOS.put("Vermelho", new Image(App.class.getResourceAsStream("blocos/vermelho.png")));
    IMG_BLOCOS.put("Azul", new Image(App.class.getResourceAsStream("blocos/azul.png")));
    IMG_BLOCOS.put("Verde", new Image(App.class.getResourceAsStream("blocos/verde.png")));
    IMG_BLOCOS.put("Cinza", new Image(App.class.getResourceAsStream("blocos/cinza.png")));
    IMG_BLOCOS.put("ReforcadoN", new Image(App.class.getResourceAsStream("blocos/reforcadoN.png")));
    IMG_BLOCOS.put("ReforcadoR", new Image(App.class.getResourceAsStream("blocos/reforcadoR.png")));
  }

  public static Image getAnimacao(String _nome) {
    return ANIMACOES.get(_nome);
  }

  public static Image getSprite(String _nome) {
    return SPRITES.get(_nome);
  }

  public static Image getBlocos(String _nome) {
    return IMG_BLOCOS.get(_nome);
  }
}
