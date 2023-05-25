import { ISequence } from 'app/shared/model/sequence.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface ISequenceFragment {
  id?: string;
  order?: number;
  sequences?: ISequence[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<ISequenceFragment> = {};
