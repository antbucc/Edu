import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';
import { Tool } from 'app/shared/model/enumerations/tool.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IPreferredActivity {
  id?: string;
  title?: string | null;
  description?: string | null;
  type?: ActivityType | null;
  tool?: Tool | null;
  difficulty?: Difficulty | null;
}

export const defaultValue: Readonly<IPreferredActivity> = {};
