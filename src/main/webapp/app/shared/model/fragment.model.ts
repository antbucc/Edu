import { IActivity } from 'app/shared/model/activity.model';
import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';
import { IModule } from 'app/shared/model/module.model';
import { ISetOf } from 'app/shared/model/set-of.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  activity?: IActivity | null;
  abstractActivities?: IAbstractActivity[] | null;
  modules?: IModule[] | null;
  setofs?: ISetOf[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
