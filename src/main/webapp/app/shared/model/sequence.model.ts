import { IOrder } from 'app/shared/model/order.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface ISequence {
  id?: string;
  title?: string;
  orders?: IOrder[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<ISequence> = {};
