import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreferredActivity } from 'app/shared/model/preferred-activity.model';
import { getEntities as getPreferredActivities } from 'app/entities/preferred-activity/preferred-activity.reducer';
import { IEducator } from 'app/shared/model/educator.model';
import { getEntities as getEducators } from 'app/entities/educator/educator.reducer';
import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { LearningStyleType } from 'app/shared/model/enumerations/learning-style-type.model';
import { ModalityType } from 'app/shared/model/enumerations/modality-type.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { getEntity, updateEntity, createEntity, reset } from './educator-preference.reducer';

export const EducatorPreferenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const preferredActivities = useAppSelector(state => state.preferredActivity.entities);
  const educators = useAppSelector(state => state.educator.entities);
  const educatorPreferenceEntity = useAppSelector(state => state.educatorPreference.entity);
  const loading = useAppSelector(state => state.educatorPreference.loading);
  const updating = useAppSelector(state => state.educatorPreference.updating);
  const updateSuccess = useAppSelector(state => state.educatorPreference.updateSuccess);
  const learningStyleTypeValues = Object.keys(LearningStyleType);
  const modalityTypeValues = Object.keys(ModalityType);
  const difficultyValues = Object.keys(Difficulty);

  const handleClose = () => {
    navigate('/educator-preference');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPreferredActivities({}));
    dispatch(getEducators({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...educatorPreferenceEntity,
      ...values,
      preferredActivities: preferredActivities.find(it => it.id.toString() === values.preferredActivities.toString()),
      educator: educators.find(it => it.id.toString() === values.educator.toString()),
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
          modality: 'ONLINE',
          difficulty: 'LOW',
          ...educatorPreferenceEntity,
          preferredActivities: educatorPreferenceEntity?.preferredActivities?.id,
          educator: educatorPreferenceEntity?.educator?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.educatorPreference.home.createOrEditLabel" data-cy="EducatorPreferenceCreateUpdateHeading">
            <Translate contentKey="eduApp.educatorPreference.home.createOrEditLabel">Create or edit a EducatorPreference</Translate>
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
                  id="educator-preference-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.educatorPreference.title')}
                id="educator-preference-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.educatorPreference.style')}
                id="educator-preference-style"
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
                label={translate('eduApp.educatorPreference.modality')}
                id="educator-preference-modality"
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
                label={translate('eduApp.educatorPreference.difficulty')}
                id="educator-preference-difficulty"
                name="difficulty"
                data-cy="difficulty"
                type="select"
              >
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {translate('eduApp.Difficulty.' + difficulty)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="educator-preference-preferredActivities"
                name="preferredActivities"
                data-cy="preferredActivities"
                label={translate('eduApp.educatorPreference.preferredActivities')}
                type="select"
              >
                <option value="" key="0" />
                {preferredActivities
                  ? preferredActivities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="educator-preference-educator"
                name="educator"
                data-cy="educator"
                label={translate('eduApp.educatorPreference.educator')}
                type="select"
              >
                <option value="" key="0" />
                {educators
                  ? educators.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/educator-preference" replace color="info">
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

export default EducatorPreferenceUpdate;
