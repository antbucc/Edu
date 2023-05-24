import { IConcept } from 'app/shared/model/concept.model';

export interface IGoal {
  id?: string;
  title?: string | null;
  concepts?: IConcept[] | null;
}

export const defaultValue: Readonly<IGoal> = {};
