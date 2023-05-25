import { IFragment } from 'app/shared/model/fragment.model';

export interface ISetOf {
  id?: string;
  title?: string | null;
  fragments?: IFragment[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<ISetOf> = {};
