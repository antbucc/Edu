import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISequence } from 'app/shared/model/sequence.model';
import { getEntities as getSequences } from 'app/entities/sequence/sequence.reducer';
import { ISet } from 'app/shared/model/set.model';
import { getEntities as getSets } from 'app/entities/set/set.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IModule } from 'app/shared/model/module.model';
import { getEntities as getModules } from 'app/entities/module/module.reducer';
import { IFragment } from 'app/shared/model/fragment.model';
import { getEntity, updateEntity, createEntity, reset } from './fragment.reducer';

export const FragmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sequences = useAppSelector(state => state.sequence.entities);
  const sets = useAppSelector(state => state.set.entities);
  const activities = useAppSelector(state => state.activity.entities);
  const modules = useAppSelector(state => state.module.entities);
  const fragmentEntity = useAppSelector(state => state.fragment.entity);
  const loading = useAppSelector(state => state.fragment.loading);
  const updating = useAppSelector(state => state.fragment.updating);
  const updateSuccess = useAppSelector(state => state.fragment.updateSuccess);

  const handleClose = () => {
    navigate('/fragment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSequences({}));
    dispatch(getSets({}));
    dispatch(getActivities({}));
    dispatch(getModules({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fragmentEntity,
      ...values,
      activities: mapIdList(values.activities),
      sequence: sequences.find(it => it.id.toString() === values.sequence.toString()),
      set: sets.find(it => it.id.toString() === values.set.toString()),
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
          ...fragmentEntity,
          sequence: fragmentEntity?.sequence?.id,
          set: fragmentEntity?.set?.id,
          activities: fragmentEntity?.activities?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eduApp.fragment.home.createOrEditLabel" data-cy="FragmentCreateUpdateHeading">
            <Translate contentKey="eduApp.fragment.home.createOrEditLabel">Create or edit a Fragment</Translate>
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
                  id="fragment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('eduApp.fragment.title')} id="fragment-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                id="fragment-sequence"
                name="sequence"
                data-cy="sequence"
                label={translate('eduApp.fragment.sequence')}
                type="select"
              >
                <option value="" key="0" />
                {sequences
                  ? sequences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="fragment-set" name="set" data-cy="set" label={translate('eduApp.fragment.set')} type="select">
                <option value="" key="0" />
                {sets
                  ? sets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('eduApp.fragment.activity')}
                id="fragment-activity"
                data-cy="activity"
                type="select"
                multiple
                name="activities"
              >
                <option value="" key="0" />
                {activities
                  ? activities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fragment" replace color="info">
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

export default FragmentUpdate;
