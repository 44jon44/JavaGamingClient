/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import transferObjects.Client;


/**
 * Interfaz ClientManager para aislar la capa de presentación de la capa de lógica
 * @author Alex Hurtado
 */
public interface ClientManager {

    public Client findClientById(String idClient) throws Exception;

    public String countREST() throws Exception;

    public Client findClientByEmail(String email) throws Exception;

    public void edit(Client client, String id) throws Exception;

    public Client find(String id) throws Exception;

    public Collection<Client> findRange(String from, String to) throws Exception;

    public Client findClientByLogin(String login) throws Exception;

    public void create(Client client) throws Exception;
    
    public Client findClientByFullName(String fullName) throws Exception;

    public Collection<Client> findAllClients() throws Exception;

    public void remove(String id) throws Exception;

    public void close();
}
