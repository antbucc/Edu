import { IFragment } from 'app/shared/model/fragment.model';

export interface ISequence {
  id?: string;
  name?: string;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<ISequence> = {};
