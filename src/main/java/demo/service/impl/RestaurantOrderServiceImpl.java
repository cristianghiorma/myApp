package demo.service.impl;

import demo.service.RestaurantOrderService;
import demo.domain.RestaurantOrder;
import demo.repository.RestaurantOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RestaurantOrder}.
 */
@Service
@Transactional
public class RestaurantOrderServiceImpl implements RestaurantOrderService {

    private final Logger log = LoggerFactory.getLogger(RestaurantOrderServiceImpl.class);

    private final RestaurantOrderRepository restaurantOrderRepository;

    public RestaurantOrderServiceImpl(RestaurantOrderRepository restaurantOrderRepository) {
        this.restaurantOrderRepository = restaurantOrderRepository;
    }

    /**
     * Save a restaurantOrder.
     *
     * @param restaurantOrder the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RestaurantOrder save(RestaurantOrder restaurantOrder) {
        log.debug("Request to save RestaurantOrder : {}", restaurantOrder);
        return restaurantOrderRepository.save(restaurantOrder);
    }

    /**
     * Get all the restaurantOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RestaurantOrder> findAll() {
        log.debug("Request to get all RestaurantOrders");
        return restaurantOrderRepository.findAll();
    }


    /**
     * Get one restaurantOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RestaurantOrder> findOne(Long id) {
        log.debug("Request to get RestaurantOrder : {}", id);
        return restaurantOrderRepository.findById(id);
    }

    /**
     * Delete the restaurantOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RestaurantOrder : {}", id);

        restaurantOrderRepository.deleteById(id);
    }
}
