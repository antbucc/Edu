import { ISetOf } from 'app/shared/model/set-of.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface ISetOfFragment {
  id?: string;
  order?: number;
  setOf?: ISetOf | null;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<ISetOfFragment> = {};
