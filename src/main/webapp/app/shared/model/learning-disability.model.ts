import { DisabilityType } from 'app/shared/model/enumerations/disability-type.model';

export interface ILearningDisability {
  id?: string;
  name?: string | null;
  description?: string | null;
  disabilityType?: DisabilityType | null;
}

export const defaultValue: Readonly<ILearningDisability> = {};
