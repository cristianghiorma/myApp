import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRestaurantOrder } from 'app/shared/model/restaurant-order.model';

type EntityResponseType = HttpResponse<IRestaurantOrder>;
type EntityArrayResponseType = HttpResponse<IRestaurantOrder[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantOrderService {
  public resourceUrl = SERVER_API_URL + 'api/restaurant-orders';

  constructor(protected http: HttpClient) {}

  create(restaurantOrder: IRestaurantOrder): Observable<EntityResponseType> {
    return this.http.post<IRestaurantOrder>(this.resourceUrl, restaurantOrder, { observe: 'response' });
  }

  update(restaurantOrder: IRestaurantOrder): Observable<EntityResponseType> {
    return this.http.put<IRestaurantOrder>(this.resourceUrl, restaurantOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRestaurantOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRestaurantOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
