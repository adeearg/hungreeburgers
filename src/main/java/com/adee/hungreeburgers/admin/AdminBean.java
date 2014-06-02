package com.adee.hungreeburgers.admin;

import com.adee.hungreeburgers.beans.CustomerOrderFacade;
import com.adee.hungreeburgers.entities.CustomerOrder;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author alex
 */
@Named(value = "adminBean")
@ViewScoped
public class AdminBean implements Serializable {

    private static final long serialVersionUID = 200L;

    @Inject
    private CustomerOrderFacade customerOrderFacade;

    private CustomerOrder customerOrder;
    private ListDataModel<CustomerOrder> customerOrdersPending;
    private ListDataModel<CustomerOrder> customerOrdersInTransit;

    public AdminBean() {
    }

    public void deliver() {
        Date date = new Date();
        Timestamp date_delivered = new Timestamp(date.getTime());
        customerOrder = customerOrdersPending.getRowData();
        try {
            customerOrder.setDateDelivered(date_delivered);
            customerOrderFacade.edit(customerOrder);
            customerOrderFacade.flush();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "AdminBean error", "deliver() method: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void done() {
        Date date = new Date();
        Timestamp date_received = new Timestamp(date.getTime());
        customerOrder = customerOrdersInTransit.getRowData();
        try {
            customerOrder.setDateReceived(date_received);
            customerOrderFacade.edit(customerOrder);
            customerOrderFacade.flush();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "AdminBean error", "done() method: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public ListDataModel<CustomerOrder> getCustomerOrdersPending() {
        updateCustomerOrdersPending();
        return customerOrdersPending;
    }

    public void updateCustomerOrdersPending() {
        customerOrdersPending = new ListDataModel<>();
        List<CustomerOrder> customerOrders;
        try {
            customerOrders = customerOrderFacade.findAllPending();
            customerOrdersPending.setWrappedData(customerOrders);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "AdminBean error", "updateCustomerOrdersPending() method: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public ListDataModel<CustomerOrder> getCustomerOrdersInTransit() {
        updateCustomerOrdersInTransit();
        return customerOrdersInTransit;
    }

    public void updateCustomerOrdersInTransit() {
        customerOrdersInTransit = new ListDataModel<>();
        List<CustomerOrder> customerOrders;
        try {
            customerOrders = customerOrderFacade.findAllInTransit();
            customerOrdersInTransit.setWrappedData(customerOrders);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "AdminBean error", "updateCustomerOrdersInTransit() method: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
//
//    public CustomerOrder getCustomerOrder() {
//        return customerOrder;
//    }
//
//    public void setCustomerOrder(CustomerOrder customerOrder) {
//        this.customerOrder = customerOrder;
//    }
}
