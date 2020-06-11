package demo.web.rest;

import demo.MyApp;
import demo.domain.RestaurantOrder;
import demo.repository.RestaurantOrderRepository;
import demo.service.RestaurantOrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RestaurantOrderResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RestaurantOrderResourceIT {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    @Autowired
    private RestaurantOrderRepository restaurantOrderRepository;

    @Autowired
    private RestaurantOrderService restaurantOrderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestaurantOrderMockMvc;

    private RestaurantOrder restaurantOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestaurantOrder createEntity(EntityManager em) {
        RestaurantOrder restaurantOrder = new RestaurantOrder()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .total(DEFAULT_TOTAL);
        return restaurantOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestaurantOrder createUpdatedEntity(EntityManager em) {
        RestaurantOrder restaurantOrder = new RestaurantOrder()
            .orderNumber(UPDATED_ORDER_NUMBER)
            .total(UPDATED_TOTAL);
        return restaurantOrder;
    }

    @BeforeEach
    public void initTest() {
        restaurantOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestaurantOrder() throws Exception {
        int databaseSizeBeforeCreate = restaurantOrderRepository.findAll().size();
        // Create the RestaurantOrder
        restRestaurantOrderMockMvc.perform(post("/api/restaurant-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantOrder)))
            .andExpect(status().isCreated());

        // Validate the RestaurantOrder in the database
        List<RestaurantOrder> restaurantOrderList = restaurantOrderRepository.findAll();
        assertThat(restaurantOrderList).hasSize(databaseSizeBeforeCreate + 1);
        RestaurantOrder testRestaurantOrder = restaurantOrderList.get(restaurantOrderList.size() - 1);
        assertThat(testRestaurantOrder.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testRestaurantOrder.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createRestaurantOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantOrderRepository.findAll().size();

        // Create the RestaurantOrder with an existing ID
        restaurantOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantOrderMockMvc.perform(post("/api/restaurant-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantOrder)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantOrder in the database
        List<RestaurantOrder> restaurantOrderList = restaurantOrderRepository.findAll();
        assertThat(restaurantOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRestaurantOrders() throws Exception {
        // Initialize the database
        restaurantOrderRepository.saveAndFlush(restaurantOrder);

        // Get all the restaurantOrderList
        restRestaurantOrderMockMvc.perform(get("/api/restaurant-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurantOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)));
    }
    
    @Test
    @Transactional
    public void getRestaurantOrder() throws Exception {
        // Initialize the database
        restaurantOrderRepository.saveAndFlush(restaurantOrder);

        // Get the restaurantOrder
        restRestaurantOrderMockMvc.perform(get("/api/restaurant-orders/{id}", restaurantOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurantOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL));
    }
    @Test
    @Transactional
    public void getNonExistingRestaurantOrder() throws Exception {
        // Get the restaurantOrder
        restRestaurantOrderMockMvc.perform(get("/api/restaurant-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurantOrder() throws Exception {
        // Initialize the database
        restaurantOrderService.save(restaurantOrder);

        int databaseSizeBeforeUpdate = restaurantOrderRepository.findAll().size();

        // Update the restaurantOrder
        RestaurantOrder updatedRestaurantOrder = restaurantOrderRepository.findById(restaurantOrder.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurantOrder are not directly saved in db
        em.detach(updatedRestaurantOrder);
        updatedRestaurantOrder
            .orderNumber(UPDATED_ORDER_NUMBER)
            .total(UPDATED_TOTAL);

        restRestaurantOrderMockMvc.perform(put("/api/restaurant-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRestaurantOrder)))
            .andExpect(status().isOk());

        // Validate the RestaurantOrder in the database
        List<RestaurantOrder> restaurantOrderList = restaurantOrderRepository.findAll();
        assertThat(restaurantOrderList).hasSize(databaseSizeBeforeUpdate);
        RestaurantOrder testRestaurantOrder = restaurantOrderList.get(restaurantOrderList.size() - 1);
        assertThat(testRestaurantOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testRestaurantOrder.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingRestaurantOrder() throws Exception {
        int databaseSizeBeforeUpdate = restaurantOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantOrderMockMvc.perform(put("/api/restaurant-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantOrder)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantOrder in the database
        List<RestaurantOrder> restaurantOrderList = restaurantOrderRepository.findAll();
        assertThat(restaurantOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRestaurantOrder() throws Exception {
        // Initialize the database
        restaurantOrderService.save(restaurantOrder);

        int databaseSizeBeforeDelete = restaurantOrderRepository.findAll().size();

        // Delete the restaurantOrder
        restRestaurantOrderMockMvc.perform(delete("/api/restaurant-orders/{id}", restaurantOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RestaurantOrder> restaurantOrderList = restaurantOrderRepository.findAll();
        assertThat(restaurantOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
