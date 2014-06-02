package com.adee.hungreeburgers.front;

import com.adee.hungreeburgers.beans.CustomerFacade;
import com.adee.hungreeburgers.beans.CustomerOrderFacade;
import com.adee.hungreeburgers.beans.OrderedProductFacade;
import com.adee.hungreeburgers.beans.ProductFacade;
import com.adee.hungreeburgers.entities.Customer;
import com.adee.hungreeburgers.entities.CustomerOrder;
import com.adee.hungreeburgers.entities.OrderedProduct;
import com.adee.hungreeburgers.entities.OrderedProductPK;
import com.adee.hungreeburgers.entities.Product;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author alex
 */
@Named(value = "frontBean")
@ViewScoped
public class FrontBean implements Serializable {

    private static final long serialVersionUID = 100L;

    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private CustomerOrderFacade customerOrderFacade;
    @Inject
    private OrderedProductFacade orderedProductFacade;
    @Inject
    private ProductFacade productFacade;

    private List<Product> products;
    private Customer customer;
    private CustomerOrder customerOrder;
//    private ListDataModel<OrderedProduct> orderedProducts;
    private List<OrderedProduct> customerCart;
    private int cartSumItems;
    private double cartTotal;
    private double productTotal;
    private boolean customerExisting;

    public FrontBean() {
    }

    @PostConstruct
    public void initialize() {
        init();
    }

    private void init() {
        products = productFacade.findAll();
        customer = new Customer();
        customerOrder = new CustomerOrder();
//        orderedProducts = new ListDataModel<>();
        customerCart = new ArrayList<>();
        cartTotal = 0.00;
        productTotal = 0.00;
        customerExisting = false;
        for (Product prod : products) {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(prod);
            customerCart.add(orderedProduct);
        }
//        orderedProducts.setWrappedData(customerCart);
    }

    public void handleNickChange() {
        String nickName = customer.getNick();
        customer = customerFacade.findByNick(nickName);
        customerExisting = customer.getId() > 0;
    }

    public void postOrder() {
        try {
            if (!customerExisting) {
                customerFacade.create(customer);
                customerFacade.flush();
            }
            Random random = new Random();
            int i = random.nextInt(999999999);
            Date date = new Date();

            customerOrder = new CustomerOrder();
            customerOrder.setAmount(BigDecimal.valueOf(cartTotal));
            customerOrder.setConfirmationNumber(i);
            customerOrder.setCustomerId(customer);
            customerOrder.setDateCreated(new Timestamp(date.getTime()));
            customerOrderFacade.create(customerOrder);
            customerOrderFacade.flush();

            List<OrderedProduct> orderedProductCollection = new ArrayList();
            for (OrderedProduct orderedProduct : customerCart) {
                if (orderedProduct.getQty() > 0 || orderedProduct.getCheeseQty() > 0 || orderedProduct.getEggQty() > 0 || orderedProduct.getEggCheeseQty() > 0) {
                    int productId = orderedProduct.getProduct().getId();

                    OrderedProductPK orderedProductPK = new OrderedProductPK();
                    orderedProductPK.setCustomerOrderId(customerOrder.getId());
                    orderedProductPK.setProductId(productId);

                    OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

                    orderedItem.setQty((short) orderedProduct.getQty());
                    orderedItem.setCheeseQty((short) orderedProduct.getCheeseQty());
                    orderedItem.setEggQty((short) orderedProduct.getEggQty());
                    orderedItem.setEggCheeseQty((short) orderedProduct.getEggCheeseQty());
                    orderedItem.setProduct(orderedProduct.getProduct());
                    orderedItem.setCustomerOrder(customerOrder);

                    orderedProductFacade.create(orderedItem);
                    orderedProductCollection.add(orderedItem);
                }
            }
            orderedProductFacade.flush();

            if (!orderedProductCollection.isEmpty()) {
                customerOrder.setOrderedProductCollection(orderedProductCollection);
                customerOrderFacade.edit(customerOrder);
                customerOrderFacade.flush();
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "FrontBean error",  "postOrder() method: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getCartTotal() {
        cartTotal = 0.00;
        for (OrderedProduct orderedProduct : customerCart) {
            cartTotal += getProductTotal(orderedProduct);
        }
        return new BigDecimal(cartTotal);
    }

    public int getCartSumItems() {
        cartSumItems = 0;
        for (OrderedProduct orderedProduct : customerCart) {
            cartSumItems += orderedProduct.getQty() + orderedProduct.getCheeseQty() +orderedProduct.getEggQty() + orderedProduct.getEggCheeseQty();
        }
        return cartSumItems;
    }

    public double getProductTotal(OrderedProduct orderedProduct) {
        double prodPrice = orderedProduct.getProduct().getPrice().doubleValue();
        double eggPrice = orderedProduct.getProduct().getEggPrice().doubleValue();
        double cheesePrice = orderedProduct.getProduct().getCheesePrice().doubleValue();
        int qty = orderedProduct.getQty();
        int cheeseQty = orderedProduct.getCheeseQty();
        int eggQty = orderedProduct.getEggQty();
        int cheeseeggQty = orderedProduct.getEggCheeseQty();
        productTotal = (qty * prodPrice) +
                       (cheeseQty * (prodPrice + cheesePrice)) +
                       (eggQty * (prodPrice + eggPrice)) +
                       (cheeseeggQty * (prodPrice + cheesePrice + eggPrice));
        return productTotal;
    }

    public List<OrderedProduct> getCustomerCart() {
        return customerCart;
    }

    public void setCustomerCart(List<OrderedProduct> customerCart) {
        this.customerCart = customerCart;
    }

//    public ListDataModel<OrderedProduct> getOrderedProducts() {
//        return orderedProducts;
//    }
//
//    public void setOrderedProducts(ListDataModel<OrderedProduct> orderedProducts) {
//        this.orderedProducts = orderedProducts;
//    }

    public boolean isCustomerExisting() {
        return customerExisting;
    }
}
