/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import transferObjects.Purchase;

/**
 *
 * @author Alex Hurtado
 */
public interface PurchaseManager {

    public String countREST() throws ClientErrorException;

    public Purchase findPurchaseById(String idClient, String idGame) throws Exception;

    public Collection<Purchase> findPurchasesByPurchaseDate(String purchaseDate) throws Exception;

    public Collection<Purchase> findPurchasesByPrice(String price) throws Exception;
    
    public Collection<Purchase> findPurchasesByPriceRange(String minPrice, String maxPrice) throws Exception;

    public void edit(Object requestEntity, String id) throws Exception;

    public Purchase find(String id) throws Exception;

    public Collection<Purchase> findRange(String from, String to) throws Exception;

    public void create(Object requestEntity) throws Exception;

    public Collection<Purchase> getAllPurchasess() throws Exception;

    public void remove(String id) throws Exception;

    public Collection<Purchase> findPurchasesByClientId(String idClient) throws Exception;

    public void deletePurchase(String idClient, String idGame) throws Exception;
    
    public void close();
    
    public Collection<Purchase> findPurchasesByPurDateAndPriceRange(String purchaseDate, String minPrice, String maxPrice) throws Exception;

    public Purchase updatePurchase(String idClient, String idGame, String purchaseDate) throws Exception;

    public Collection<Purchase> findPurchasesByClientAndPurchaseDate(String idClient, String purchaseDate) throws Exception;

    public Collection<Purchase> findPurchasesByClientAndPriceRange(String idClient, String minPrice, String maxPrice) throws Exception;

    public Purchase createPurchase(String idClient, String idGame, String purchaseDate) throws Exception;

    public Collection<Purchase> findPurchasesByClientAndPurDateAndPriceRange(String idClient, String purchaseDate, String minPrice, String maxPrice) throws Exception;

}
