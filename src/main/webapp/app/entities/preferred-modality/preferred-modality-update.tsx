import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { getEntities as getEducatorPreferences } from 'app/entities/educator-preference/educator-preference.reducer';
import { IPreferredModality } from 'app/shared/model/preferred-modality.model';
import { ModalityType } from 'app/shared/model/enumerations/modality-type.model';
import { getEntity, updateEntity, createEntity, reset } from './preferred-modality.reducer';

export const PreferredModalityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const educatorPreferences = useAppSelector(state => state.educatorPreference.entities);
  const preferredModalityEntity = useAppSelector(state => state.preferredModality.entity);
  const loading = useAppSelector(state => state.preferredModality.loading);
  const updating = useAppSelector(state => state.preferredModality.updating);
  const updateSuccess = useAppSelector(state => state.preferredModality.updateSuccess);
  const modalityTypeValues = Object.keys(ModalityType);

  const handleClose = () => {
    navigate('/preferred-modality');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEducatorPreferences({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...preferredModalityEntity,
      ...values,
      educatorPreference: educatorPreferences.find(it => it.id.toString() === values.educatorPreference.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          modality: 'ONLINE',
          ...preferredModalityEntity,
          educatorPreference: preferredModalityEntity?.educatorPreference?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.preferredModality.home.createOrEditLabel" data-cy="PreferredModalityCreateUpdateHeading">
            <Translate contentKey="eduApp.preferredModality.home.createOrEditLabel">Create or edit a PreferredModality</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="preferred-modality-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.preferredModality.modality')}
                id="preferred-modality-modality"
                name="modality"
                data-cy="modality"
                type="select"
              >
                {modalityTypeValues.map(modalityType => (
                  <option value={modalityType} key={modalityType}>
                    {translate('eduApp.ModalityType.' + modalityType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="preferred-modality-educatorPreference"
                name="educatorPreference"
                data-cy="educatorPreference"
                label={translate('eduApp.preferredModality.educatorPreference')}
                type="select"
              >
                <option value="" key="0" />
                {educatorPreferences
                  ? educatorPreferences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/preferred-modality" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PreferredModalityUpdate;
