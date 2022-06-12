package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

import java.util.Locale;

public class Server extends Agent {

    @Override
    protected void setup() {
        //générer un nombre aléatoirement entre 0 et 100,
        int magicNumber = (int) (Math.random() * 100);
        System.out.println("------------------- Le nombre magique est : "+magicNumber+" ---------------------------");
        System.out.println("---------------------------------------------------------------------------------------");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    int usernumber = 0;
                    ACLMessage messageAcl = new ACLMessage(ACLMessage.INFORM);
                    try {
                        usernumber = Integer.parseInt(aclMessage.getContent());
                        if (usernumber == magicNumber) {
                            // retourner tous les agents
                            AMSAgentDescription [] agents = null;
                            SearchConstraints c = new SearchConstraints();
                            c.setMaxResults ( new Long(-1) );
                            agents = AMSService.search(Server.this, new AMSAgentDescription(), c);

                            //l'agent Server va envoyer un message à tous les joueurs pour l’informer du nombre magique trouvé et arrête le jeu,
                            for (int i=0; i<agents.length;i++){
                                messageAcl.addReceiver(new AID(agents[i].getName().getLocalName(), AID.ISLOCALNAME));
                            }
                            messageAcl.setContent(aclMessage.getSender().getLocalName().toUpperCase(Locale.ROOT)+" EST LE GAGNANT DONC FIN DU JEU!!!\n");
                            send(messageAcl);
                            doDelete();

                            // Le nombre saisi est inférieur au nombre magique
                        } else if (usernumber < magicNumber) {
                            messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                            messageAcl.setContent("CHERCHER AU-DESSUS DE "+usernumber+" !");
                            send(messageAcl);

                        }
                        // Le nombre saisi est supérieur au nombre magique
                        else {
                            messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                            messageAcl.setContent("CHERCHER AU-DESSOUS DE "+usernumber+" !");
                            send(messageAcl);
                        }
                    } catch (Exception e) {
                        messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                        messageAcl.setContent("Entrer un nombre ! ");
                        send(messageAcl);
                    }
                } else {
                    block();
                }

            }
        });
        System.out.println("*********** Agent Server " + getLocalName() + " est démarré ***********");
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
