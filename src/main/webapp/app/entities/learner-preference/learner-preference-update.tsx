import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILearner } from 'app/shared/model/learner.model';
import { getEntities as getLearners } from 'app/entities/learner/learner.reducer';
import { ILearnerPreference } from 'app/shared/model/learner-preference.model';
import { LearningStyleType } from 'app/shared/model/enumerations/learning-style-type.model';
import { ModalityType } from 'app/shared/model/enumerations/modality-type.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { DisabilityType } from 'app/shared/model/enumerations/disability-type.model';
import { getEntity, updateEntity, createEntity, reset } from './learner-preference.reducer';

export const LearnerPreferenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const learners = useAppSelector(state => state.learner.entities);
  const learnerPreferenceEntity = useAppSelector(state => state.learnerPreference.entity);
  const loading = useAppSelector(state => state.learnerPreference.loading);
  const updating = useAppSelector(state => state.learnerPreference.updating);
  const updateSuccess = useAppSelector(state => state.learnerPreference.updateSuccess);
  const learningStyleTypeValues = Object.keys(LearningStyleType);
  const modalityTypeValues = Object.keys(ModalityType);
  const difficultyValues = Object.keys(Difficulty);
  const disabilityTypeValues = Object.keys(DisabilityType);

  const handleClose = () => {
    navigate('/learner-preference');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLearners({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...learnerPreferenceEntity,
      ...values,
      learner: learners.find(it => it.id.toString() === values.learner.toString()),
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
          disability: 'DYSLEXIA',
          ...learnerPreferenceEntity,
          learner: learnerPreferenceEntity?.learner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.learnerPreference.home.createOrEditLabel" data-cy="LearnerPreferenceCreateUpdateHeading">
            <Translate contentKey="eduApp.learnerPreference.home.createOrEditLabel">Create or edit a LearnerPreference</Translate>
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
                  id="learner-preference-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.learnerPreference.title')}
                id="learner-preference-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.learnerPreference.style')}
                id="learner-preference-style"
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
                label={translate('eduApp.learnerPreference.modality')}
                id="learner-preference-modality"
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
                label={translate('eduApp.learnerPreference.difficulty')}
                id="learner-preference-difficulty"
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
                label={translate('eduApp.learnerPreference.disability')}
                id="learner-preference-disability"
                name="disability"
                data-cy="disability"
                type="select"
              >
                {disabilityTypeValues.map(disabilityType => (
                  <option value={disabilityType} key={disabilityType}>
                    {translate('eduApp.DisabilityType.' + disabilityType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="learner-preference-learner"
                name="learner"
                data-cy="learner"
                label={translate('eduApp.learnerPreference.learner')}
                type="select"
              >
                <option value="" key="0" />
                {learners
                  ? learners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.surname}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/learner-preference" replace color="info">
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

export default LearnerPreferenceUpdate;
