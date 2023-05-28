import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { ModalityType } from 'app/shared/model/enumerations/modality-type.model';

export interface IPreferredModality {
  id?: string;
  modality?: ModalityType | null;
  educatorPreference?: IEducatorPreference | null;
}

export const defaultValue: Readonly<IPreferredModality> = {};
