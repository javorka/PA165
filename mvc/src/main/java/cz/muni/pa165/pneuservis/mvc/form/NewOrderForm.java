package cz.muni.pa165.pneuservis.mvc.form;

import cz.muni.pa165.pneuservis.api.dto.OrderDTO;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
public class NewOrderForm {

    private OrderDTO order;
    private long tireId;

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public long getTireId() {
        return tireId;
    }

    public void setTireId(long tireId) {
        this.tireId = tireId;
    }
}
