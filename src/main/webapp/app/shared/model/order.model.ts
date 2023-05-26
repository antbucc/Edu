import { IFragment } from 'app/shared/model/fragment.model';
import { ISequence } from 'app/shared/model/sequence.model';

export interface IOrder {
  id?: string;
  order?: number;
  fragment?: IFragment | null;
  sequence?: ISequence | null;
}

export const defaultValue: Readonly<IOrder> = {};
