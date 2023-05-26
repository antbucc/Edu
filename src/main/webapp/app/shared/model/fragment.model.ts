import { IActivity } from 'app/shared/model/activity.model';
import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';
import { ISequence } from 'app/shared/model/sequence.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  activity?: IActivity | null;
  abstractActivity?: IAbstractActivity | null;
  sequence?: ISequence | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
