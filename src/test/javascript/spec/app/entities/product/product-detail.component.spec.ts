import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { ProductDetailComponent } from 'app/entities/product/product-detail.component';
import { Product } from 'app/shared/model/product.model';

describe('Component Tests', () => {
  describe('Product Management Detail Component', () => {
    let comp: ProductDetailComponent;
    let fixture: ComponentFixture<ProductDetailComponent>;
    const route = ({ data: of({ product: new Product(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyAppTestModule],
        declarations: [ProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load product on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.product).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
