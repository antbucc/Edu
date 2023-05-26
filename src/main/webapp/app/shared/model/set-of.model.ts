import { IFragment } from 'app/shared/model/fragment.model';

export interface ISetOf {
  id?: string;
  title?: string;
  fragment?: IFragment | null;
  partofSets?: IFragment[] | null;
}

export const defaultValue: Readonly<ISetOf> = {};
