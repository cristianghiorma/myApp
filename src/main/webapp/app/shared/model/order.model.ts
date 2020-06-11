import { IProduct } from 'app/shared/model/product.model';
import { IRestaurantOrder } from 'app/shared/model/restaurant-order.model';

export interface IOrder {
  id?: number;
  totalPrice?: number;
  products?: IProduct[];
  restaurantOrder?: IRestaurantOrder;
}

export class Order implements IOrder {
  constructor(public id?: number, public totalPrice?: number, public products?: IProduct[], public restaurantOrder?: IRestaurantOrder) {}
}
