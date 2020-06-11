import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.MyAppCityModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.MyAppOrderModule),
      },
      {
        path: 'restaurant-order',
        loadChildren: () => import('./restaurant-order/restaurant-order.module').then(m => m.MyAppRestaurantOrderModule),
      },
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.MyAppRestaurantModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.MyAppProductModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.MyAppLocationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MyAppEntityModule {}
