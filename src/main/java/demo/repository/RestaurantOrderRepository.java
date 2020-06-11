package demo.repository;

import demo.domain.RestaurantOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RestaurantOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Long> {
}
