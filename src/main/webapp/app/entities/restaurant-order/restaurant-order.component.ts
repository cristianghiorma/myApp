import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRestaurantOrder } from 'app/shared/model/restaurant-order.model';
import { RestaurantOrderService } from './restaurant-order.service';
import { RestaurantOrderDeleteDialogComponent } from './restaurant-order-delete-dialog.component';

@Component({
  selector: 'jhi-restaurant-order',
  templateUrl: './restaurant-order.component.html',
})
export class RestaurantOrderComponent implements OnInit, OnDestroy {
  restaurantOrders?: IRestaurantOrder[];
  eventSubscriber?: Subscription;

  constructor(
    protected restaurantOrderService: RestaurantOrderService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.restaurantOrderService.query().subscribe((res: HttpResponse<IRestaurantOrder[]>) => (this.restaurantOrders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRestaurantOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRestaurantOrder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRestaurantOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('restaurantOrderListModification', () => this.loadAll());
  }

  delete(restaurantOrder: IRestaurantOrder): void {
    const modalRef = this.modalService.open(RestaurantOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.restaurantOrder = restaurantOrder;
  }
}
