import { IFragmentOrder } from 'app/shared/model/fragment-order.model';

export interface IFragmentSet {
  id?: string;
  fragments?: IFragmentOrder[] | null;
}

export const defaultValue: Readonly<IFragmentSet> = {};
