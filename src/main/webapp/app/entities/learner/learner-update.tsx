import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILearningDisability } from 'app/shared/model/learning-disability.model';
import { getEntities as getLearningDisabilities } from 'app/entities/learning-disability/learning-disability.reducer';
import { IScenario } from 'app/shared/model/scenario.model';
import { getEntities as getScenarios } from 'app/entities/scenario/scenario.reducer';
import { ILearner } from 'app/shared/model/learner.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { getEntity, updateEntity, createEntity, reset } from './learner.reducer';

export const LearnerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const learningDisabilities = useAppSelector(state => state.learningDisability.entities);
  const scenarios = useAppSelector(state => state.scenario.entities);
  const learnerEntity = useAppSelector(state => state.learner.entity);
  const loading = useAppSelector(state => state.learner.loading);
  const updating = useAppSelector(state => state.learner.updating);
  const updateSuccess = useAppSelector(state => state.learner.updateSuccess);
  const genderTypeValues = Object.keys(GenderType);

  const handleClose = () => {
    navigate('/learner');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLearningDisabilities({}));
    dispatch(getScenarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...learnerEntity,
      ...values,
      learningDisability: learningDisabilities.find(it => it.id.toString() === values.learningDisability.toString()),
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
          gender: 'MALE',
          ...learnerEntity,
          learningDisability: learnerEntity?.learningDisability?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.learner.home.createOrEditLabel" data-cy="LearnerCreateUpdateHeading">
            <Translate contentKey="eduApp.learner.home.createOrEditLabel">Create or edit a Learner</Translate>
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
                  id="learner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.learner.firstName')}
                id="learner-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.learner.lastName')}
                id="learner-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField label={translate('eduApp.learner.email')} id="learner-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('eduApp.learner.phoneNumber')}
                id="learner-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField label={translate('eduApp.learner.gender')} id="learner-gender" name="gender" data-cy="gender" type="select">
                {genderTypeValues.map(genderType => (
                  <option value={genderType} key={genderType}>
                    {translate('eduApp.GenderType.' + genderType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="learner-learningDisability"
                name="learningDisability"
                data-cy="learningDisability"
                label={translate('eduApp.learner.learningDisability')}
                type="select"
              >
                <option value="" key="0" />
                {learningDisabilities
                  ? learningDisabilities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.disabilityType}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/learner" replace color="info">
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

export default LearnerUpdate;
