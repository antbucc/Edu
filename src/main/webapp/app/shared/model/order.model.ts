import { ISequence } from 'app/shared/model/sequence.model';

export interface IOrder {
  id?: string;
  order?: number;
  sequences?: ISequence[] | null;
}

export const defaultValue: Readonly<IOrder> = {};
