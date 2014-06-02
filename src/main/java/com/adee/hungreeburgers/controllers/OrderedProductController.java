package com.adee.hungreeburgers.controllers;

import com.adee.hungreeburgers.entities.OrderedProduct;
import com.adee.hungreeburgers.beans.OrderedProductFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "orderedProductController")
@ViewScoped
public class OrderedProductController extends AbstractController<OrderedProduct> implements Serializable {

    private static final long serialVersionUID = 3L;

    @Inject
    private OrderedProductFacade ejbFacade;

    public OrderedProductController() {
        super(OrderedProduct.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    @Override
    protected void setEmbeddableKeys() {
        this.getSelected().getOrderedProductPK().setCustomerOrderId(this.getSelected().getCustomerOrder().getId());
        this.getSelected().getOrderedProductPK().setProductId(this.getSelected().getProduct().getId());
    }

    @Override
    protected void initializeEmbeddableKey() {
        this.getSelected().setOrderedProductPK(new com.adee.hungreeburgers.entities.OrderedProductPK());
    }

}
