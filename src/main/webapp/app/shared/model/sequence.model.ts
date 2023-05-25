import { IFragment } from 'app/shared/model/fragment.model';
import { IOrder } from 'app/shared/model/order.model';

export interface ISequence {
  id?: string;
  title?: string;
  fragment?: IFragment | null;
  orders?: IOrder[] | null;
}

export const defaultValue: Readonly<ISequence> = {};
