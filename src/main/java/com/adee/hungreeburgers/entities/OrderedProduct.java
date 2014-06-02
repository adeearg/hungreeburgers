package com.adee.hungreeburgers.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "ordered_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderedProduct.findAll", query = "SELECT o FROM OrderedProduct o"),
    @NamedQuery(name = "OrderedProduct.findByCustomerOrderId", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.customerOrderId = :customerOrderId"),
    @NamedQuery(name = "OrderedProduct.findByProductId", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.productId = :productId"),
    @NamedQuery(name = "OrderedProduct.findByQty", query = "SELECT o FROM OrderedProduct o WHERE o.qty = :qty"),
    @NamedQuery(name = "OrderedProduct.findByEggQty", query = "SELECT o FROM OrderedProduct o WHERE o.eggQty = :eggQty"),
    @NamedQuery(name = "OrderedProduct.findByCheeseQty", query = "SELECT o FROM OrderedProduct o WHERE o.cheeseQty = :cheeseQty"),
    @NamedQuery(name = "OrderedProduct.findByEggCheeseQty", query = "SELECT o FROM OrderedProduct o WHERE o.eggCheeseQty = :eggCheeseQty")})
public class OrderedProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedProductPK orderedProductPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private short qty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "egg_qty")
    private short eggQty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cheese_qty")
    private short cheeseQty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "egg_cheese_qty")
    private short eggCheeseQty;
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "customer_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;

    public OrderedProduct() {
    }

    public OrderedProduct(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public OrderedProduct(OrderedProductPK orderedProductPK, short qty, short eggQty, short cheeseQty, short eggCheeseQty) {
        this.orderedProductPK = orderedProductPK;
        this.qty = qty;
        this.eggQty = eggQty;
        this.cheeseQty = cheeseQty;
        this.eggCheeseQty = eggCheeseQty;
    }

    public OrderedProduct(int customerOrderId, int productId) {
        this.orderedProductPK = new OrderedProductPK(customerOrderId, productId);
    }

    public OrderedProductPK getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public short getQty() {
        return qty;
    }

    public void setQty(short qty) {
        this.qty = qty;
    }

    public short getEggQty() {
        return eggQty;
    }

    public void setEggQty(short eggQty) {
        this.eggQty = eggQty;
    }

    public short getCheeseQty() {
        return cheeseQty;
    }

    public void setCheeseQty(short cheeseQty) {
        this.cheeseQty = cheeseQty;
    }

    public short getEggCheeseQty() {
        return eggCheeseQty;
    }

    public void setEggCheeseQty(short eggCheeseQty) {
        this.eggCheeseQty = eggCheeseQty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderedProductPK != null ? orderedProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedProduct)) {
            return false;
        }
        OrderedProduct other = (OrderedProduct) object;
        return (this.orderedProductPK != null || other.orderedProductPK == null) && (this.orderedProductPK == null || this.orderedProductPK.equals(other.orderedProductPK));
    }

    @Override
    public String toString() {
        return "entities.OrderedProduct[ orderedProductPK=" + orderedProductPK + " ]";
    }

}
