export interface ILocation {
  id?: number;
  streetAddress?: string;
}

export class Location implements ILocation {
  constructor(public id?: number, public streetAddress?: string) {}
}
