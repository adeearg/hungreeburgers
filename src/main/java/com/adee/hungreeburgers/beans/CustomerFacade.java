package com.adee.hungreeburgers.beans;

import com.adee.hungreeburgers.entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alex
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {
    @PersistenceContext(unitName = "HungreeBurgersPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    public Customer findByNick(String nick) {
        try {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findByNick", Customer.class);
            return query.setParameter("nick", nick).getSingleResult();
        } catch (Exception e) {
            Customer customer = new Customer();
            customer.setNick(nick);
            return customer;
        }
    }

}
