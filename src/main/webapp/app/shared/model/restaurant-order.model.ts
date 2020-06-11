import { IOrder } from 'app/shared/model/order.model';

export interface IRestaurantOrder {
  id?: number;
  orderNumber?: number;
  total?: number;
  orderLists?: IOrder[];
}

export class RestaurantOrder implements IRestaurantOrder {
  constructor(public id?: number, public orderNumber?: number, public total?: number, public orderLists?: IOrder[]) {}
}
