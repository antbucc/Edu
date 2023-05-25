import { IConcept } from 'app/shared/model/concept.model';
import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';

export interface IGoal {
  id?: string;
  title?: string | null;
  concepts?: IConcept[] | null;
  goals?: IAbstractActivity | null;
}

export const defaultValue: Readonly<IGoal> = {};
