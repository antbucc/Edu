import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConcept } from 'app/shared/model/concept.model';
import { getEntities as getConcepts } from 'app/entities/concept/concept.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IPrecondition } from 'app/shared/model/precondition.model';
import { getEntity, updateEntity, createEntity, reset } from './precondition.reducer';

export const PreconditionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const concepts = useAppSelector(state => state.concept.entities);
  const activities = useAppSelector(state => state.activity.entities);
  const preconditionEntity = useAppSelector(state => state.precondition.entity);
  const loading = useAppSelector(state => state.precondition.loading);
  const updating = useAppSelector(state => state.precondition.updating);
  const updateSuccess = useAppSelector(state => state.precondition.updateSuccess);

  const handleClose = () => {
    navigate('/precondition');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConcepts({}));
    dispatch(getActivities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...preconditionEntity,
      ...values,
      concepts: mapIdList(values.concepts),
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
          ...preconditionEntity,
          concepts: preconditionEntity?.concepts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.precondition.home.createOrEditLabel" data-cy="PreconditionCreateUpdateHeading">
            <Translate contentKey="eduApp.precondition.home.createOrEditLabel">Create or edit a Precondition</Translate>
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
                  id="precondition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eduApp.precondition.title')}
                id="precondition-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.precondition.concept')}
                id="precondition-concept"
                data-cy="concept"
                type="select"
                multiple
                name="concepts"
              >
                <option value="" key="0" />
                {concepts
                  ? concepts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/precondition" replace color="info">
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

export default PreconditionUpdate;
