package demo.service;

import demo.domain.RestaurantOrder;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RestaurantOrder}.
 */
public interface RestaurantOrderService {

    /**
     * Save a restaurantOrder.
     *
     * @param restaurantOrder the entity to save.
     * @return the persisted entity.
     */
    RestaurantOrder save(RestaurantOrder restaurantOrder);

    /**
     * Get all the restaurantOrders.
     *
     * @return the list of entities.
     */
    List<RestaurantOrder> findAll();


    /**
     * Get the "id" restaurantOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RestaurantOrder> findOne(Long id);

    /**
     * Delete the "id" restaurantOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
