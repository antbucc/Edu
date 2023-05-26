import { IFragment } from 'app/shared/model/fragment.model';

export interface ISetOf {
  id?: string;
  title?: string;
  fragment2?: IFragment | null;
  fragment1?: IFragment | null;
}

export const defaultValue: Readonly<ISetOf> = {};
