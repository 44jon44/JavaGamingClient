/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import factories.ClientManagerFactory;
import factories.PurchaseManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.OperationNotSupportedException;
import transferObjects.Client;
import transferObjects.Purchase;

/**
 *
 * @author Alex Hurtado
 */
public class TestRESTPurchase {
    public static void main(String[] args){
        try {
            PurchaseManager pm = PurchaseManagerFactory.createPurchaseManager("REST_WEB_CLIENT");
            ClientManager cm = ClientManagerFactory.createClientManager("REST_WEB_CLIENT");
            Collection<Purchase> purchases = pm.getAllPurchasess();
            System.out.println("purchases size: " + purchases.size());
            ArrayList<String> clientIds = new ArrayList<>();
            purchases.stream().forEach(p -> clientIds.add(String.valueOf(p.getIdPurchase().getIdClient())));
            Collection<Client> clients =new  ArrayList<>();
            clientIds.stream().forEach((id) -> clients.add(cm.find(id)));
            purchases.stream().forEach(p->System.out.println(p.toString()));
            //clients.stream().forEach(c -> System.out.println(c.toString()));
            clientIds.forEach((id) -> {
                System.out.println(id);
            });
            clients.forEach(c -> System.out.println(c.toString()));
            //System.out.println(purchases.size());
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(TestRESTPurchase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
