package com.adee.hungreeburgers.converters;

import com.adee.hungreeburgers.entities.OrderedProduct;
import com.adee.hungreeburgers.beans.OrderedProductFacade;
import com.adee.hungreeburgers.controllers.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "orderedProductConverter")
public class OrderedProductConverter implements Converter {

    @Inject
    private OrderedProductFacade ejbFacade;

    private static final String SEPARATOR = "#";
    private static final String SEPARATOR_ESCAPED = "\\#";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    com.adee.hungreeburgers.entities.OrderedProductPK getKey(String value) {
        com.adee.hungreeburgers.entities.OrderedProductPK key;
        String values[] = value.split(SEPARATOR_ESCAPED);
        key = new com.adee.hungreeburgers.entities.OrderedProductPK();
        key.setCustomerOrderId(Integer.parseInt(values[0]));
        key.setProductId(Integer.parseInt(values[1]));
        return key;
    }

    String getStringKey(com.adee.hungreeburgers.entities.OrderedProductPK value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value.getCustomerOrderId());
        sb.append(SEPARATOR);
        sb.append(value.getProductId());
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof OrderedProduct) {
            OrderedProduct o = (OrderedProduct) object;
            return getStringKey(o.getOrderedProductPK());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OrderedProduct.class.getName()});
            return null;
        }
    }

}
