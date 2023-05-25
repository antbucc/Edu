import { ISequence } from 'app/shared/model/sequence.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface ISequenceFragment {
  id?: string;
  order?: number;
  sequence?: ISequence | null;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<ISequenceFragment> = {};
