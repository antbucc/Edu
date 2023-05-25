import { IActivity } from 'app/shared/model/activity.model';
import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';
import { ISequence } from 'app/shared/model/sequence.model';
import { ISetOf } from 'app/shared/model/set-of.model';
import { IOrder } from 'app/shared/model/order.model';
import { IModule } from 'app/shared/model/module.model';

export interface IFragment {
  id?: string;
  title?: string | null;
  activity?: IActivity | null;
  abstractActivity?: IAbstractActivity | null;
  sequence?: ISequence | null;
  setOf?: ISetOf | null;
  order?: IOrder | null;
  sequence?: ISequence | null;
  modules?: IModule[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
