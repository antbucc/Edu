import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IScenario } from 'app/shared/model/scenario.model';
import { getEntities as getScenarios } from 'app/entities/scenario/scenario.reducer';
import { IFragment } from 'app/shared/model/fragment.model';
import { getEntities as getFragments } from 'app/entities/fragment/fragment.reducer';
import { IModule1 } from 'app/shared/model/module-1.model';
import { Level } from 'app/shared/model/enumerations/level.model';
import { getEntity, updateEntity, createEntity, reset } from './module-1.reducer';

export const Module1Update = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const scenarios = useAppSelector(state => state.scenario.entities);
  const fragments = useAppSelector(state => state.fragment.entities);
  const module1Entity = useAppSelector(state => state.module1.entity);
  const loading = useAppSelector(state => state.module1.loading);
  const updating = useAppSelector(state => state.module1.updating);
  const updateSuccess = useAppSelector(state => state.module1.updateSuccess);
  const levelValues = Object.keys(Level);

  const handleClose = () => {
    navigate('/module-1');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getScenarios({}));
    dispatch(getFragments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endData = convertDateTimeToServer(values.endData);

    const entity = {
      ...module1Entity,
      ...values,
      fragments: mapIdList(values.fragments),
      scenario: scenarios.find(it => it.id.toString() === values.scenario.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startDate: displayDefaultDateTime(),
          endData: displayDefaultDateTime(),
        }
      : {
          level: 'BEGINNER',
          ...module1Entity,
          startDate: convertDateTimeFromServer(module1Entity.startDate),
          endData: convertDateTimeFromServer(module1Entity.endData),
          scenario: module1Entity?.scenario?.id,
          fragments: module1Entity?.fragments?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.module1.home.createOrEditLabel" data-cy="Module1CreateUpdateHeading">
            <Translate contentKey="eduApp.module1.home.createOrEditLabel">Create or edit a Module1</Translate>
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
                  id="module-1-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.module1.title')} id="module-1-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('eduApp.module1.description')}
                id="module-1-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eduApp.module1.startDate')}
                id="module-1-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eduApp.module1.endData')}
                id="module-1-endData"
                name="endData"
                data-cy="endData"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('eduApp.module1.level')} id="module-1-level" name="level" data-cy="level" type="select">
                {levelValues.map(level => (
                  <option value={level} key={level}>
                    {translate('eduApp.Level.' + level)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="module-1-scenario"
                name="scenario"
                data-cy="scenario"
                label={translate('eduApp.module1.scenario')}
                type="select"
              >
                <option value="" key="0" />
                {scenarios
                  ? scenarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.module1.fragment')}
                id="module-1-fragment"
                data-cy="fragment"
                type="select"
                multiple
                name="fragments"
              >
                <option value="" key="0" />
                {fragments
                  ? fragments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/module-1" replace color="info">
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

export default Module1Update;
