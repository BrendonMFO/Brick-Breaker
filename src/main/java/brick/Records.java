package brick;

import javafx.scene.paint.Color;
import javafx.animation.KeyValue;

import javafx.animation.Interpolator;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Records extends SubMenus {
  private final TableView<?> tabela;
  private boolean userOnly;

  public Records() {
    this.tabela = new TableView();
    this.userOnly = true;
    this.setFundo(1500, 1500, Color.WHITE);
    this.carregarImagens();
    this.configurarTabela();
    this.animar(true, 1, 1, .6, new KeyValue(this.tabela.opacityProperty(), 1, Interpolator.EASE_BOTH));
  }

  private void configurarTabela() {
    this.tabela.setOpacity(0);
    this.tabela.setTranslateX(100);
    this.tabela.setTranslateY(133);
    this.tabela.setMinSize(840, 490);
    this.tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.setTabela();
  }

  private void setTabela() {
    if (userOnly) {
      this.tabelaPessoal();
    } else {
      tabelaTopRecord();
    }
  }

  private void tabelaPessoal() {
    this.tabela.getColumns().clear();
    TableColumn<String, String> levelID = new TableColumn<>("LevelID");
    TableColumn<String, String> pontuacao = new TableColumn<>("Pontuacao");
    TableColumn<String, String> data = new TableColumn<>("Data");
    levelID.setCellValueFactory(new PropertyValueFactory<>("LevelID"));
    levelID.setStyle("-fx-alignment: CENTER;");
    pontuacao.setCellValueFactory(new PropertyValueFactory<>("Pontuacao"));
    pontuacao.setStyle("-fx-alignment: CENTER;");
    data.setCellValueFactory(new PropertyValueFactory<>("Data"));
    data.setStyle("-fx-alignment: CENTER;");
    this.tabela.getItems().clear();
  }

  private void tabelaTopRecord() {
    this.tabela.getColumns().clear();
    TableColumn<String, String> nome = new TableColumn<>("Nome");
    TableColumn<String, String> levelID = new TableColumn<>("LevelID");
    TableColumn<String, String> pontuacao = new TableColumn<>("Pontuacao");
    TableColumn<String, String> data = new TableColumn<>("Data");
    nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
    nome.setStyle("-fx-alignment: CENTER;");
    levelID.setCellValueFactory(new PropertyValueFactory<>("LevelID"));
    levelID.setStyle("-fx-alignment: CENTER;");
    pontuacao.setCellValueFactory(new PropertyValueFactory<>("Pontuacao"));
    pontuacao.setStyle("-fx-alignment: CENTER;");
    data.setCellValueFactory(new PropertyValueFactory<>("Data"));
    data.setStyle("-fx-alignment: CENTER;");
    this.tabela.getItems().clear();
  }

  @Override
  protected void carregarImagens() {
    this.setFrameImage("img/Configuracao/frame.png");
    this.getChildren().addAll(this.tabela, this.frame);
  }

  @Override
  protected void adicionarListenerTeclado() {
    this.getScene().setOnKeyReleased(tecla -> {
      switch (tecla.getCode()) {
        case ESCAPE:
          this.animar(false, 2, 2, 0, new KeyValue(this.tabela.opacityProperty(), 0, Interpolator.EASE_BOTH));
          this.encerrarListeners();
          break;
        case LEFT:
          userOnly = true;
          this.setTabela();
          break;
        case RIGHT:
          userOnly = false;
          this.setTabela();
          break;
        default:
          break;
      }
    });
  }

  private void encerrarListeners() {
    this.getScene().setOnKeyReleased(null);
  }
}
