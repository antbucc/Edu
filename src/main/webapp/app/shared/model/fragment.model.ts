import { IOrder } from 'app/shared/model/order.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IAbstractActivity } from 'app/shared/model/abstract-activity.model';
import { ISetOf } from 'app/shared/model/set-of.model';
import { ISequence } from 'app/shared/model/sequence.model';
import { IModule1 } from 'app/shared/model/module-1.model';

export interface IFragment {
  id?: string;
  title?: string;
  order?: IOrder | null;
  activity?: IActivity | null;
  abstractActivity?: IAbstractActivity | null;
  setOf?: ISetOf | null;
  sequence?: ISequence | null;
  module1s?: IModule1[] | null;
  setOf1s?: ISetOf[] | null;
}

export const defaultValue: Readonly<IFragment> = {};
