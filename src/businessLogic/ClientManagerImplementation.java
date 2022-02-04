/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import rest.ClientRESTful;
import transferObjects.Client;

/**
 *
 * @author Alex Hurtado
 */
public class ClientManagerImplementation implements ClientManager{

    //Logger de la implementación de la interfaz ClientManager
    private static final Logger LOG = Logger.getLogger(ClientManagerImplementation.class.getName());
    private ClientRESTful webClient;
    
    public ClientManagerImplementation(){
        webClient = new ClientRESTful();
    }
    
    @Override
    public Client findClientById(String idClient) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Client client = null;
        try {
            LOG.log(Level.INFO, "ClientManager: find client by id: {0}.", idClient);
            client = webClient.findClientById(Client.class, idClient);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE,
                    "ClientManager: Error finding client by id, {0}",
                    ex.getMessage());
            }
        return client;
    }

    @Override
    public String countREST() throws Exception {
        return webClient.countREST();
    }

    @Override
    public Client findClientByEmail(String email) throws Exception {
        Client client = null;     
        try {
            LOG.log(Level.INFO, "ClientManager: find client by email: {0}.", email);
            client = webClient.findClientByEmail(Client.class, email);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE,
                    "ClientManager: Error finding client by email, {0}",
                    ex.getMessage());
            }
        return client;
    }

    @Override
    public void edit(Client client, String id) throws Exception {
        webClient.edit(client, id);
    }

    @Override
    public Client find(String id) throws Exception {
        return webClient.find(Client.class, id);
    }

    @Override
    public void create(Client client) throws Exception {
        webClient.create(client);
    }

    @Override
    public Collection<Client> findAllClients() throws Exception {
        Collection<Client> clients = null;
        
        try {
            LOG.log(Level.INFO, "ClientManager: find all clients: {0}.");
           clients = webClient.findAll(new GenericType<Collection<Client>>(){});
        } catch (Exception ex) {
            LOG.log(Level.SEVERE,
                    "ClientManager: Error finding all clients, {0}",
                    ex.getMessage());
            }
        return clients;
    }

    @Override
    public void remove(String id) throws Exception {
        webClient.remove(id);
    }

    @Override
    public void close() {
        webClient.close();
    }
    
}
