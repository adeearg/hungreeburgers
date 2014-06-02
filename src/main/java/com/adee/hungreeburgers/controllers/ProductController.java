package com.adee.hungreeburgers.controllers;

import com.adee.hungreeburgers.entities.Product;
import com.adee.hungreeburgers.beans.ProductFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;

@Named(value = "productController")
@ViewScoped
public class ProductController extends AbstractController<Product> implements Serializable {

    private static final long serialVersionUID = 4L;

    @Inject
    private ProductFacade ejbFacade;

    public ProductController() {
        super(Product.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

}
