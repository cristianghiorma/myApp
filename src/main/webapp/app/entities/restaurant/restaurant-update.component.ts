import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IRestaurant, Restaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from './restaurant.service';
import { IRestaurantOrder } from 'app/shared/model/restaurant-order.model';
import { RestaurantOrderService } from 'app/entities/restaurant-order/restaurant-order.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';

type SelectableEntity = IRestaurantOrder | ILocation | ICity;

@Component({
  selector: 'jhi-restaurant-update',
  templateUrl: './restaurant-update.component.html',
})
export class RestaurantUpdateComponent implements OnInit {
  isSaving = false;
  restaurantorders: IRestaurantOrder[] = [];
  locations: ILocation[] = [];
  cities: ICity[] = [];

  editForm = this.fb.group({
    id: [],
    restaurantName: [],
    restaurantOrder: [],
    location: [],
    city: [],
  });

  constructor(
    protected restaurantService: RestaurantService,
    protected restaurantOrderService: RestaurantOrderService,
    protected locationService: LocationService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurant }) => {
      this.updateForm(restaurant);

      this.restaurantOrderService
        .query({ filter: 'restaurant-is-null' })
        .pipe(
          map((res: HttpResponse<IRestaurantOrder[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRestaurantOrder[]) => {
          if (!restaurant.restaurantOrder || !restaurant.restaurantOrder.id) {
            this.restaurantorders = resBody;
          } else {
            this.restaurantOrderService
              .find(restaurant.restaurantOrder.id)
              .pipe(
                map((subRes: HttpResponse<IRestaurantOrder>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRestaurantOrder[]) => (this.restaurantorders = concatRes));
          }
        });

      this.locationService
        .query({ filter: 'restaurant-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!restaurant.location || !restaurant.location.id) {
            this.locations = resBody;
          } else {
            this.locationService
              .find(restaurant.location.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => (this.locations = concatRes));
          }
        });

      this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));
    });
  }

  updateForm(restaurant: IRestaurant): void {
    this.editForm.patchValue({
      id: restaurant.id,
      restaurantName: restaurant.restaurantName,
      restaurantOrder: restaurant.restaurantOrder,
      location: restaurant.location,
      city: restaurant.city,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const restaurant = this.createFromForm();
    if (restaurant.id !== undefined) {
      this.subscribeToSaveResponse(this.restaurantService.update(restaurant));
    } else {
      this.subscribeToSaveResponse(this.restaurantService.create(restaurant));
    }
  }

  private createFromForm(): IRestaurant {
    return {
      ...new Restaurant(),
      id: this.editForm.get(['id'])!.value,
      restaurantName: this.editForm.get(['restaurantName'])!.value,
      restaurantOrder: this.editForm.get(['restaurantOrder'])!.value,
      location: this.editForm.get(['location'])!.value,
      city: this.editForm.get(['city'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
