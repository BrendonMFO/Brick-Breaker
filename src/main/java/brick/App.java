package brick;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class App extends Application {

  private Menu menu;

  @Override
  public void start(Stage stage) throws Exception {
    Recursos.carregar();
    this.menu = new Menu();
    Scene scene = new Scene(this.menu, 1024, 900);
    stage.setResizable(false);
    stage.setScene(scene);
    stage.setTitle("Brick breaker");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}