import { IRestaurantOrder } from 'app/shared/model/restaurant-order.model';
import { ILocation } from 'app/shared/model/location.model';
import { IProduct } from 'app/shared/model/product.model';
import { ICity } from 'app/shared/model/city.model';

export interface IRestaurant {
  id?: number;
  restaurantName?: string;
  restaurantOrder?: IRestaurantOrder;
  location?: ILocation;
  products?: IProduct[];
  city?: ICity;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public restaurantName?: string,
    public restaurantOrder?: IRestaurantOrder,
    public location?: ILocation,
    public products?: IProduct[],
    public city?: ICity
  ) {}
}
