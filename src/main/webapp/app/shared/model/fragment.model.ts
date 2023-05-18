import { IPrecondition } from 'app/shared/model/precondition.model';
import { IEffect } from 'app/shared/model/effect.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IGoal } from 'app/shared/model/goal.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  preconditions?: IPrecondition[] | null;
  effects?: IEffect[] | null;
  activities?: IActivity[] | null;
  goals?: IGoal[] | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
