import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRestaurantOrder, RestaurantOrder } from 'app/shared/model/restaurant-order.model';
import { RestaurantOrderService } from './restaurant-order.service';

@Component({
  selector: 'jhi-restaurant-order-update',
  templateUrl: './restaurant-order-update.component.html',
})
export class RestaurantOrderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    orderNumber: [],
    total: [],
  });

  constructor(
    protected restaurantOrderService: RestaurantOrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurantOrder }) => {
      this.updateForm(restaurantOrder);
    });
  }

  updateForm(restaurantOrder: IRestaurantOrder): void {
    this.editForm.patchValue({
      id: restaurantOrder.id,
      orderNumber: restaurantOrder.orderNumber,
      total: restaurantOrder.total,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const restaurantOrder = this.createFromForm();
    if (restaurantOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.restaurantOrderService.update(restaurantOrder));
    } else {
      this.subscribeToSaveResponse(this.restaurantOrderService.create(restaurantOrder));
    }
  }

  private createFromForm(): IRestaurantOrder {
    return {
      ...new RestaurantOrder(),
      id: this.editForm.get(['id'])!.value,
      orderNumber: this.editForm.get(['orderNumber'])!.value,
      total: this.editForm.get(['total'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurantOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
