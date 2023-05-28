import { IScenario } from 'app/shared/model/scenario.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';

export interface ILearner {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  gender?: GenderType | null;
  scenarios?: IScenario[] | null;
}

export const defaultValue: Readonly<ILearner> = {};
