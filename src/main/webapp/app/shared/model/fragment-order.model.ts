import { IFragment } from 'app/shared/model/fragment.model';
import { IFragmentSet } from 'app/shared/model/fragment-set.model';

export interface IFragmentOrder {
  id?: string;
  order?: number;
  fragment?: IFragment | null;
  set?: IFragmentSet | null;
}

export const defaultValue: Readonly<IFragmentOrder> = {};
