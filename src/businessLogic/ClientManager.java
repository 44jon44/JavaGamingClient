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

    public Client findClientById(String idClient) throws ClientErrorException;

    public String countREST() throws ClientErrorException;

    public Client findClientByEmail(String email) throws ClientErrorException;

    public void edit(Client client, String id) throws ClientErrorException;

    public Client find(String id) throws ClientErrorException;

    public Collection<Client> findRange(String from, String to) throws ClientErrorException;

    public Client findClientByLogin(String login) throws ClientErrorException;

    public void create(Client client) throws ClientErrorException;
    
    public Client findClientByFullName(String fullName) throws ClientErrorException;

    public Collection<Client> findAllClients() throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public void close();
}
