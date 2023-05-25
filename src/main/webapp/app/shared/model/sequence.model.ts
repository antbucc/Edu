import { ISequenceFragment } from 'app/shared/model/sequence-fragment.model';

export interface ISequence {
  id?: string;
  title?: string;
  fragments?: ISequenceFragment[] | null;
}

export const defaultValue: Readonly<ISequence> = {};
