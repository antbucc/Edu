import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';

export interface IPreferredActivity {
  id?: string;
  activity?: ActivityType | null;
  educatorPreference?: IEducatorPreference | null;
}

export const defaultValue: Readonly<IPreferredActivity> = {};
