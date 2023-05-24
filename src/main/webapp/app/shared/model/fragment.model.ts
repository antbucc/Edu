import { IActivity } from 'app/shared/model/activity.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  activity?: IActivity | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
