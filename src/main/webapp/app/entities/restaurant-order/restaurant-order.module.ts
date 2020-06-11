import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared/shared.module';
import { RestaurantOrderComponent } from './restaurant-order.component';
import { RestaurantOrderDetailComponent } from './restaurant-order-detail.component';
import { RestaurantOrderUpdateComponent } from './restaurant-order-update.component';
import { RestaurantOrderDeleteDialogComponent } from './restaurant-order-delete-dialog.component';
import { restaurantOrderRoute } from './restaurant-order.route';

@NgModule({
  imports: [MyAppSharedModule, RouterModule.forChild(restaurantOrderRoute)],
  declarations: [
    RestaurantOrderComponent,
    RestaurantOrderDetailComponent,
    RestaurantOrderUpdateComponent,
    RestaurantOrderDeleteDialogComponent,
  ],
  entryComponents: [RestaurantOrderDeleteDialogComponent],
})
export class MyAppRestaurantOrderModule {}
