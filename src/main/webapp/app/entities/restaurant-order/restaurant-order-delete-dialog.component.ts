import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRestaurantOrder } from 'app/shared/model/restaurant-order.model';
import { RestaurantOrderService } from './restaurant-order.service';

@Component({
  templateUrl: './restaurant-order-delete-dialog.component.html',
})
export class RestaurantOrderDeleteDialogComponent {
  restaurantOrder?: IRestaurantOrder;

  constructor(
    protected restaurantOrderService: RestaurantOrderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.restaurantOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('restaurantOrderListModification');
      this.activeModal.close();
    });
  }
}
