import { IFragment } from 'app/shared/model/fragment.model';

export interface ISequence {
  id?: string;
  title?: string | null;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<ISequence> = {};
