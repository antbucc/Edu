import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IScenario } from 'app/shared/model/scenario.model';

export interface IEducator {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  educatorPreferences?: IEducatorPreference[] | null;
  activities?: IActivity[] | null;
  scenarios?: IScenario[] | null;
}

export const defaultValue: Readonly<IEducator> = {};
