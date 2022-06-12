package Containers;

import Agents.Joueur2;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Joueur2Container extends Application {

    static Joueur2 joueur2;
    public ObservableList<String> itemsList = FXCollections.observableArrayList();

    public void setAgent(Joueur2 joueur2) {
        this.joueur2 = joueur2;
    }

    public Joueur2 getPlayer2() {
        return joueur2;
    }

    public static void main(String[] args) throws ControllerException {
        Joueur2Container player2Container = new Joueur2Container();
        player2Container.startContainer();

        launch(args);
    }

    public void startContainer() throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("Joueur2", "Agents.Joueur2", new Object[]{this});
        agentController.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();


        HBox hBox = new HBox();
        hBox.setPadding(new javafx.geometry.Insets(40, 10, 25, 10));
        hBox.setSpacing(10.0);
        Label label = new Label("Message : ");
        label.setPadding(new javafx.geometry.Insets(5, 10, 0, 10));
        TextField textField = new TextField();
        textField.setPadding(new javafx.geometry.Insets(10));
        textField.setPrefWidth(350);
        Button buttonEnvoyer = new Button("Envoyer");
        buttonEnvoyer.setPadding(new javafx.geometry.Insets(10, 20, 10, 20));
        // Set Style
        buttonEnvoyer.setStyle("-fx-font: 18 arial; -fx-text-fill: white; -fx-base: #225780;");


        hBox.getChildren().addAll(label, textField, buttonEnvoyer);
        root.setTop(hBox);

        ListView<String> listView = new ListView<>();
        listView.setItems(itemsList);
        listView.setStyle("-fx-border-color: #8e9090; -fx-border-width: 1" );

        VBox vBox = new VBox();
        vBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(listView);
        root.setCenter(vBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Joueur2");
        primaryStage.show();

        buttonEnvoyer.setOnAction(event -> {
            String message = textField.getText();
            GuiEvent guiEvent = new GuiEvent(event, 0);
            guiEvent.addParameter(message);
            guiEvent.addParameter("Server");
            guiEvent.addParameter(listView);
            joueur2.onGuiEvent(guiEvent);
        });
    }
}
