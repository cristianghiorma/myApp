import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { RestaurantOrderDetailComponent } from 'app/entities/restaurant-order/restaurant-order-detail.component';
import { RestaurantOrder } from 'app/shared/model/restaurant-order.model';

describe('Component Tests', () => {
  describe('RestaurantOrder Management Detail Component', () => {
    let comp: RestaurantOrderDetailComponent;
    let fixture: ComponentFixture<RestaurantOrderDetailComponent>;
    const route = ({ data: of({ restaurantOrder: new RestaurantOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [RestaurantOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RestaurantOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RestaurantOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load restaurantOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.restaurantOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
