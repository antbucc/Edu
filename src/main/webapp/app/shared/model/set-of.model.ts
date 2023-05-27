import { IFragment } from 'app/shared/model/fragment.model';

export interface ISetOf {
  id?: string;
  title?: string;
  atLeast?: number | null;
  noLess?: number | null;
  fragments?: IFragment[] | null;
  fragment1?: IFragment | null;
}

export const defaultValue: Readonly<ISetOf> = {};
