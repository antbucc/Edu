import { ICompetence } from 'app/shared/model/competence.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IGoal } from 'app/shared/model/goal.model';
import { IPrecondition } from 'app/shared/model/precondition.model';
import { IEffect } from 'app/shared/model/effect.model';

export interface IConcept {
  id?: string;
  title?: string | null;
  description?: string | null;
  parents?: IConcept[] | null;
  childs?: IConcept | null;
  competences?: ICompetence[] | null;
  activities?: IActivity[] | null;
  goals?: IGoal[] | null;
  preconditions?: IPrecondition[] | null;
  effects?: IEffect[] | null;
}

export const defaultValue: Readonly<IConcept> = {};
