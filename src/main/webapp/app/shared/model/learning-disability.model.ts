import { ILearner } from 'app/shared/model/learner.model';
import { DisabilityType } from 'app/shared/model/enumerations/disability-type.model';

export interface ILearningDisability {
  id?: string;
  name?: string | null;
  description?: string | null;
  disabilityType?: DisabilityType | null;
  learnarDisabilities?: ILearner | null;
}

export const defaultValue: Readonly<ILearningDisability> = {};
