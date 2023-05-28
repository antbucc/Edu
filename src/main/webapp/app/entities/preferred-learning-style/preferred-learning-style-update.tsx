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
import { IPreferredLearningStyle } from 'app/shared/model/preferred-learning-style.model';
import { LearningStyleType } from 'app/shared/model/enumerations/learning-style-type.model';
import { getEntity, updateEntity, createEntity, reset } from './preferred-learning-style.reducer';

export const PreferredLearningStyleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const educatorPreferences = useAppSelector(state => state.educatorPreference.entities);
  const preferredLearningStyleEntity = useAppSelector(state => state.preferredLearningStyle.entity);
  const loading = useAppSelector(state => state.preferredLearningStyle.loading);
  const updating = useAppSelector(state => state.preferredLearningStyle.updating);
  const updateSuccess = useAppSelector(state => state.preferredLearningStyle.updateSuccess);
  const learningStyleTypeValues = Object.keys(LearningStyleType);

  const handleClose = () => {
    navigate('/preferred-learning-style');
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
      ...preferredLearningStyleEntity,
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
          style: 'VISUAL',
          ...preferredLearningStyleEntity,
          educatorPreference: preferredLearningStyleEntity?.educatorPreference?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.preferredLearningStyle.home.createOrEditLabel" data-cy="PreferredLearningStyleCreateUpdateHeading">
            <Translate contentKey="eduApp.preferredLearningStyle.home.createOrEditLabel">Create or edit a PreferredLearningStyle</Translate>
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
                  id="preferred-learning-style-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.preferredLearningStyle.style')}
                id="preferred-learning-style-style"
                name="style"
                data-cy="style"
                type="select"
              >
                {learningStyleTypeValues.map(learningStyleType => (
                  <option value={learningStyleType} key={learningStyleType}>
                    {translate('eduApp.LearningStyleType.' + learningStyleType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="preferred-learning-style-educatorPreference"
                name="educatorPreference"
                data-cy="educatorPreference"
                label={translate('eduApp.preferredLearningStyle.educatorPreference')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/preferred-learning-style" replace color="info">
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

export default PreferredLearningStyleUpdate;
