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
import { ILearningDisability } from 'app/shared/model/learning-disability.model';
import { DisabilityType } from 'app/shared/model/enumerations/disability-type.model';
import { getEntity, updateEntity, createEntity, reset } from './learning-disability.reducer';

export const LearningDisabilityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const learners = useAppSelector(state => state.learner.entities);
  const learningDisabilityEntity = useAppSelector(state => state.learningDisability.entity);
  const loading = useAppSelector(state => state.learningDisability.loading);
  const updating = useAppSelector(state => state.learningDisability.updating);
  const updateSuccess = useAppSelector(state => state.learningDisability.updateSuccess);
  const disabilityTypeValues = Object.keys(DisabilityType);

  const handleClose = () => {
    navigate('/learning-disability');
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
      ...learningDisabilityEntity,
      ...values,
      learnarDisabilities: learners.find(it => it.id.toString() === values.learnarDisabilities.toString()),
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
          disabilityType: 'DYSLEXIA',
          ...learningDisabilityEntity,
          learnarDisabilities: learningDisabilityEntity?.learnarDisabilities?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.learningDisability.home.createOrEditLabel" data-cy="LearningDisabilityCreateUpdateHeading">
            <Translate contentKey="eduApp.learningDisability.home.createOrEditLabel">Create or edit a LearningDisability</Translate>
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
                  id="learning-disability-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.learningDisability.name')}
                id="learning-disability-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.learningDisability.description')}
                id="learning-disability-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.learningDisability.disabilityType')}
                id="learning-disability-disabilityType"
                name="disabilityType"
                data-cy="disabilityType"
                type="select"
              >
                {disabilityTypeValues.map(disabilityType => (
                  <option value={disabilityType} key={disabilityType}>
                    {translate('eduApp.DisabilityType.' + disabilityType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="learning-disability-learnarDisabilities"
                name="learnarDisabilities"
                data-cy="learnarDisabilities"
                label={translate('eduApp.learningDisability.learnarDisabilities')}
                type="select"
              >
                <option value="" key="0" />
                {learners
                  ? learners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/learning-disability" replace color="info">
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

export default LearningDisabilityUpdate;
