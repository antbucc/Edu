import { IFragment } from 'app/shared/model/fragment.model';
import { ISequenceFragment } from 'app/shared/model/sequence-fragment.model';

export interface ISequence {
  id?: string;
  title?: string;
  fragment?: IFragment | null;
  fragments?: ISequenceFragment[] | null;
}

export const defaultValue: Readonly<ISequence> = {};
