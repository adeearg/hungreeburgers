package com.adee.hungreeburgers.controllers;

import com.adee.hungreeburgers.entities.CustomerOrder;
import com.adee.hungreeburgers.beans.CustomerOrderFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "customerOrderController")
@ViewScoped
public class CustomerOrderController extends AbstractController<CustomerOrder> implements Serializable {

    private static final long serialVersionUID = 2L;

    @Inject
    private CustomerOrderFacade ejbFacade;

    public CustomerOrderController() {
        super(CustomerOrder.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
