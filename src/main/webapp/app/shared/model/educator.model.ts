import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { IScenario } from 'app/shared/model/scenario.model';

export interface IEducator {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  preferences?: IEducatorPreference[] | null;
  scenarios?: IScenario[] | null;
}

export const defaultValue: Readonly<IEducator> = {};
