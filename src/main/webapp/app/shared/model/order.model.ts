import { ISequence } from 'app/shared/model/sequence.model';

export interface IOrder {
  id?: string;
  order?: number;
  sequence?: ISequence | null;
}

export const defaultValue: Readonly<IOrder> = {};
