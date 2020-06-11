package demo.web.rest;

import demo.domain.RestaurantOrder;
import demo.service.RestaurantOrderService;
import demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link demo.domain.RestaurantOrder}.
 */
@RestController
@RequestMapping("/api")
public class RestaurantOrderResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantOrderResource.class);

    private static final String ENTITY_NAME = "restaurantOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestaurantOrderService restaurantOrderService;

    public RestaurantOrderResource(RestaurantOrderService restaurantOrderService) {
        this.restaurantOrderService = restaurantOrderService;
    }

    /**
     * {@code POST  /restaurant-orders} : Create a new restaurantOrder.
     *
     * @param restaurantOrder the restaurantOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restaurantOrder, or with status {@code 400 (Bad Request)} if the restaurantOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restaurant-orders")
    public ResponseEntity<RestaurantOrder> createRestaurantOrder(@RequestBody RestaurantOrder restaurantOrder) throws URISyntaxException {
        log.debug("REST request to save RestaurantOrder : {}", restaurantOrder);
        if (restaurantOrder.getId() != null) {
            throw new BadRequestAlertException("A new restaurantOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestaurantOrder result = restaurantOrderService.save(restaurantOrder);
        return ResponseEntity.created(new URI("/api/restaurant-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restaurant-orders} : Updates an existing restaurantOrder.
     *
     * @param restaurantOrder the restaurantOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurantOrder,
     * or with status {@code 400 (Bad Request)} if the restaurantOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restaurantOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restaurant-orders")
    public ResponseEntity<RestaurantOrder> updateRestaurantOrder(@RequestBody RestaurantOrder restaurantOrder) throws URISyntaxException {
        log.debug("REST request to update RestaurantOrder : {}", restaurantOrder);
        if (restaurantOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestaurantOrder result = restaurantOrderService.save(restaurantOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, restaurantOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /restaurant-orders} : get all the restaurantOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restaurantOrders in body.
     */
    @GetMapping("/restaurant-orders")
    public List<RestaurantOrder> getAllRestaurantOrders() {
        log.debug("REST request to get all RestaurantOrders");
        return restaurantOrderService.findAll();
    }

    /**
     * {@code GET  /restaurant-orders/:id} : get the "id" restaurantOrder.
     *
     * @param id the id of the restaurantOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restaurantOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restaurant-orders/{id}")
    public ResponseEntity<RestaurantOrder> getRestaurantOrder(@PathVariable Long id) {
        log.debug("REST request to get RestaurantOrder : {}", id);
        Optional<RestaurantOrder> restaurantOrder = restaurantOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurantOrder);
    }

    /**
     * {@code DELETE  /restaurant-orders/:id} : delete the "id" restaurantOrder.
     *
     * @param id the id of the restaurantOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restaurant-orders/{id}")
    public ResponseEntity<Void> deleteRestaurantOrder(@PathVariable Long id) {
        log.debug("REST request to delete RestaurantOrder : {}", id);

        restaurantOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
