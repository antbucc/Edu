import { ISequence } from 'app/shared/model/sequence.model';

export interface ISequenceFragment {
  id?: string;
  order?: number;
  sequences?: ISequence[] | null;
}

export const defaultValue: Readonly<ISequenceFragment> = {};
