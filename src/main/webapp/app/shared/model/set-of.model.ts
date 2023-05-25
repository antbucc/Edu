import { IFragment } from 'app/shared/model/fragment.model';
import { ISetOfFragment } from 'app/shared/model/set-of-fragment.model';

export interface ISetOf {
  id?: string;
  title?: string | null;
  fragments?: IFragment[] | null;
  fragmemts?: ISetOfFragment[] | null;
}

export const defaultValue: Readonly<ISetOf> = {};
