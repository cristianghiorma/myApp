import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRestaurantOrder, RestaurantOrder } from 'app/shared/model/restaurant-order.model';
import { RestaurantOrderService } from './restaurant-order.service';
import { RestaurantOrderComponent } from './restaurant-order.component';
import { RestaurantOrderDetailComponent } from './restaurant-order-detail.component';
import { RestaurantOrderUpdateComponent } from './restaurant-order-update.component';

@Injectable({ providedIn: 'root' })
export class RestaurantOrderResolve implements Resolve<IRestaurantOrder> {
  constructor(private service: RestaurantOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRestaurantOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((restaurantOrder: HttpResponse<RestaurantOrder>) => {
          if (restaurantOrder.body) {
            return of(restaurantOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RestaurantOrder());
  }
}

export const restaurantOrderRoute: Routes = [
  {
    path: '',
    component: RestaurantOrderComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RestaurantOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RestaurantOrderDetailComponent,
    resolve: {
      restaurantOrder: RestaurantOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RestaurantOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RestaurantOrderUpdateComponent,
    resolve: {
      restaurantOrder: RestaurantOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RestaurantOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RestaurantOrderUpdateComponent,
    resolve: {
      restaurantOrder: RestaurantOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RestaurantOrders',
    },
    canActivate: [UserRouteAccessService],
  },
];
