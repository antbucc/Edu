import { IActivity } from 'app/shared/model/activity.model';
import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';
import { ISetOf } from 'app/shared/model/set-of.model';
import { ISequence } from 'app/shared/model/sequence.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string;
  activity?: IActivity | null;
  abstractActivity?: IAbstractActivity | null;
  setOf?: ISetOf | null;
  sequence?: ISequence | null;
  modules?: IModule[] | null;
  setOf1s?: ISetOf[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
