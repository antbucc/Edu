import { IDomain } from 'app/shared/model/domain.model';
import { IEducator } from 'app/shared/model/educator.model';
import { ICompetence } from 'app/shared/model/competence.model';
import { ILearner } from 'app/shared/model/learner.model';
import { IModule1 } from 'app/shared/model/module-1.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IScenario {
  id?: string;
  title?: string | null;
  description?: string | null;
  language?: Language | null;
  domain?: IDomain | null;
  educators?: IEducator[] | null;
  competences?: ICompetence[] | null;
  learners?: ILearner[] | null;
  module1?: IModule1 | null;
}

export const defaultValue: Readonly<IScenario> = {};
