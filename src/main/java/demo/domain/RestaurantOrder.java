package demo.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A RestaurantOrder.
 */
@Entity
@Table(name = "restaurant_order")
public class RestaurantOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "total")
    private Integer total;

    @OneToMany(mappedBy = "restaurantOrder")
    private Set<Order> orderLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        orderNumber = (Integer)orderLists.size();
        return orderNumber;
    }

    public RestaurantOrder orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getTotal() {
        //total = 0;
       /* Iterator<Order> it = orderLists.iterator();
        while(it.hasNext()){
            total += it.next().getTotalPrice();
        }*/
        return total;
    }

    public RestaurantOrder total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Set<Order> getOrderLists() {
        return orderLists;
    }

    public RestaurantOrder orderLists(Set<Order> orders) {
        this.orderLists = orders;
        return this;
    }

    public RestaurantOrder addOrderList(Order order) {
        this.orderLists.add(order);
        order.setRestaurantOrder(this);
        return this;
    }

    public RestaurantOrder removeOrderList(Order order) {
        this.orderLists.remove(order);
        order.setRestaurantOrder(null);
        return this;
    }

    public void setOrderLists(Set<Order> orders) {
        this.orderLists = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantOrder)) {
            return false;
        }
        return id != null && id.equals(((RestaurantOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantOrder{" +
            "id=" + getId() +
            ", orderNumber=" + getOrderNumber() +
            ", total=" + getTotal() +
            "}";
    }
}
