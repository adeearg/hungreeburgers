package com.adee.hungreeburgers.controllers;

import com.adee.hungreeburgers.entities.Customer;
import com.adee.hungreeburgers.beans.CustomerFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "customerController")
@ViewScoped
public class CustomerController extends AbstractController<Customer> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CustomerFacade ejbFacade;

    public CustomerController() {
        super(Customer.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
