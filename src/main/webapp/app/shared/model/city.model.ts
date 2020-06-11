export interface ICity {
  id?: number;
  cityName?: string;
}

export class City implements ICity {
  constructor(public id?: number, public cityName?: string) {}
}
