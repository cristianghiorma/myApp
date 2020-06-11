package demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @OneToOne
    @JoinColumn(unique = true)
    private RestaurantOrder restaurantOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "restaurant")
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "restaurants", allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Restaurant restaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public RestaurantOrder getRestaurantOrder() {
        return restaurantOrder;
    }

    public Restaurant restaurantOrder(RestaurantOrder restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
        return this;
    }

    public void setRestaurantOrder(RestaurantOrder restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

    public Location getLocation() {
        return location;
    }

    public Restaurant location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Restaurant products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Restaurant addProduct(Product product) {
        this.products.add(product);
        product.setRestaurant(this);
        return this;
    }

    public Restaurant removeProduct(Product product) {
        this.products.remove(product);
        product.setRestaurant(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public City getCity() {
        return city;
    }

    public Restaurant city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            "}";
    }
}
