import { IRestaurant } from 'app/shared/model/restaurant.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IProduct {
  id?: number;
  productName?: string;
  productPrice?: number;
  restaurant?: IRestaurant;
  order?: IOrder;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string,
    public productPrice?: number,
    public restaurant?: IRestaurant,
    public order?: IOrder
  ) {}
}
