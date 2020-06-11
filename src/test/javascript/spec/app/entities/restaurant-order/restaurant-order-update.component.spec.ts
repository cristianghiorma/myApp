import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { RestaurantOrderUpdateComponent } from 'app/entities/restaurant-order/restaurant-order-update.component';
import { RestaurantOrderService } from 'app/entities/restaurant-order/restaurant-order.service';
import { RestaurantOrder } from 'app/shared/model/restaurant-order.model';

describe('Component Tests', () => {
  describe('RestaurantOrder Management Update Component', () => {
    let comp: RestaurantOrderUpdateComponent;
    let fixture: ComponentFixture<RestaurantOrderUpdateComponent>;
    let service: RestaurantOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [RestaurantOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RestaurantOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RestaurantOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestaurantOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RestaurantOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RestaurantOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
