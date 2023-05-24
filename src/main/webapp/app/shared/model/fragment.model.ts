import { ISequence } from 'app/shared/model/sequence.model';
import { ISet } from 'app/shared/model/set.model';
import { IPrecondition } from 'app/shared/model/precondition.model';
import { IEffect } from 'app/shared/model/effect.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  sequence?: ISequence | null;
  set?: ISet | null;
  preconditions?: IPrecondition[] | null;
  effects?: IEffect[] | null;
  activities?: IActivity[] | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
