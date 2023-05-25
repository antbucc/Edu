import { IFragment } from 'app/shared/model/fragment.model';

export interface IOrder {
  id?: string;
  order?: number;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<IOrder> = {};
