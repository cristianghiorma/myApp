package demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_price")
    private Integer totalPrice;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "orderLists", allowSetters = true)
    private RestaurantOrder restaurantOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalPrice() {
        totalPrice =0;
        Iterator<Product> it = products.iterator();
        while(it.hasNext()){
            totalPrice += it.next().getProductPrice();
        }
        return totalPrice;
    }
    public Order totalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Order products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Order addProduct(Product product) {
        this.products.add(product);
        product.setOrder(this);
        return this;
    }

    public Order removeProduct(Product product) {
        this.products.remove(product);
        product.setOrder(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public RestaurantOrder getRestaurantOrder() {
        return restaurantOrder;
    }

    public Order restaurantOrder(RestaurantOrder restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
        return this;
    }

    public void setRestaurantOrder(RestaurantOrder restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
