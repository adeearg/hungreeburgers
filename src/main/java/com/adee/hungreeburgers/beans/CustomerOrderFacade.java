package com.adee.hungreeburgers.beans;

import com.adee.hungreeburgers.entities.CustomerOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alex
 */
@Stateless
public class CustomerOrderFacade extends AbstractFacade<CustomerOrder> {
    @PersistenceContext(unitName = "HungreeBurgersPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerOrderFacade() {
        super(CustomerOrder.class);
    }

    public List<CustomerOrder> findAllPending() {
        TypedQuery<CustomerOrder> query = em.createQuery("SELECT c FROM CustomerOrder c WHERE c.dateDelivered IS NULL", CustomerOrder.class);
        return query.getResultList();
    }

    public List<CustomerOrder> findAllInTransit() {
        TypedQuery<CustomerOrder> query = em.createQuery("SELECT c FROM CustomerOrder c WHERE c.dateDelivered IS NOT NULL AND c.dateReceived IS NULL",
                                                         CustomerOrder.class);
        return query.getResultList();
    }

}
