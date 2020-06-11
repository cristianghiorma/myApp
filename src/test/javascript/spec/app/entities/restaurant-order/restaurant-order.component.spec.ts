import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { RestaurantOrderComponent } from 'app/entities/restaurant-order/restaurant-order.component';
import { RestaurantOrderService } from 'app/entities/restaurant-order/restaurant-order.service';
import { RestaurantOrder } from 'app/shared/model/restaurant-order.model';

describe('Component Tests', () => {
  describe('RestaurantOrder Management Component', () => {
    let comp: RestaurantOrderComponent;
    let fixture: ComponentFixture<RestaurantOrderComponent>;
    let service: RestaurantOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [RestaurantOrderComponent],
      })
        .overrideTemplate(RestaurantOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RestaurantOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestaurantOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RestaurantOrder(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.restaurantOrders && comp.restaurantOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
