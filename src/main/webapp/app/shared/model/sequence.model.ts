import { ISequenceFragment } from 'app/shared/model/sequence-fragment.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface ISequence {
  id?: string;
  title?: string;
  fragmemts?: ISequenceFragment[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<ISequence> = {};
