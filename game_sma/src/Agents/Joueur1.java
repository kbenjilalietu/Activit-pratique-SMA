package Agents;

import Containers.Joueur1Container;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Joueur1 extends GuiAgent {

    // le compteur
    private int cmp = 0;
    private Joueur1Container myGui;
    private Joueur1 joueur1 = this;
    static ListView listView;
    public ObservableList<String> itemsList = FXCollections.observableArrayList();

    public void onGuiEvent(GuiEvent guiEvent) {
        String message = guiEvent.getParameter(0).toString();
        ACLMessage messageAcl = new ACLMessage(ACLMessage.INFORM);
        messageAcl.addReceiver(new AID(guiEvent.getParameter(1).toString(), AID.ISLOCALNAME));
        listView = (ListView) guiEvent.getParameter(2);
        listView.setItems(itemsList);
        messageAcl.setContent(message);
        send(messageAcl);
    }

    @Override
    protected void setup() {
        myGui = (Joueur1Container) getArguments()[0];
        myGui.setAgent(joueur1);
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    itemsList.add(aclMessage.getContent());
                } else {
                    block();
                }
            }
        });
        System.out.println("*********** Agent 1 " + getLocalName() + " est démarré ***********");
    }

    @Override
    protected void takeDown() {
        System.out.println("*********** Agent " + getLocalName() + " terminée ***********");
    }

    @Override
    protected void beforeMove() {
        System.out.println("*********** Agent " + getLocalName() + " avant de mouvement ***********");
    }
    @Override
    protected void afterMove() {
        System.out.println("*********** Agent " + getLocalName() + " aprés de mouvement ***********");
    }

}
